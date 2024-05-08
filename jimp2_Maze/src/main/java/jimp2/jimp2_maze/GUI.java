/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jimp2.jimp2_maze;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author piotr-sosnowski
 */
public class GUI {

    static JFrame frame;
    static final int frameX = 1600;
    static final int frameY = 1400;
    static final int defaultWidth = 1600;
    static final int defaultHeight = defaultWidth - 200;
    static final String helpMessage = "Witam w programie do znajdywania ścieżki w labiryncie!.\n"
            + "Proszę wczytać labirynt za pomocą opcji \"Import maze\". Wczytany plik powinien być\n"
            + "tekstowy z oznaczenie P na początek labiryntu oraz K na jego koniec lub binarny\n"
            + "Następnie za pomocą przycisku \"Find shortest path\" można znaleźć najkrótszą ścieżkę w labiryncie.\n"
            + "Przyciski \"Change start position\" oraz \"Change finish position\" pozwalają zmieniać początek i koniec\n"
            + "między którymi szukana będzie ścieżka. Istnieje również możliwość zapisania znalezionej ścieżki\n"
            + "w formie tekstowej korzystając z opcji \"Save\".";
    static JTextArea eventLogLabel = new JTextArea("");
    
    private static void addFrame() {
        frame = new JFrame();
        frame.setSize(frameX, frameY);
        frame.setLayout(new BorderLayout());
        frame.setTitle("Maze");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private static void addLogMessage(String text) {
        eventLogLabel.setText(text + " \n " + eventLogLabel.getText());
    }

    public static void buildGUI() {
        addFrame();
        
        eventLogLabel.setFont(new Font("Arial", Font.BOLD, 20));
        eventLogLabel.setBackground(Color.LIGHT_GRAY);
        eventLogLabel.setForeground(Color.BLACK);
        eventLogLabel.setText("Please import a maze");
        DefaultCaret caret = (DefaultCaret) eventLogLabel.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);                           //so text in event log is always at the top
        JButton findShortestWayButton = new JButton("Find the shortest path");
        findShortestWayButton.setVisible(false);
        JButton changeStartingPositionButton = new JButton("Change start position");
        changeStartingPositionButton.setVisible(false);
        JButton changeEndingPositionButton = new JButton("Change finish position");
        changeEndingPositionButton.setVisible(false);
        Icon helpIcon = new ImageIcon("images/helpIcon2.jpg");
        JButton helpButton = new JButton(helpIcon);
        JButton exitButton = new JButton("Exit");

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                System.exit(0);
            }
        });

        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent HelpAction) {
                JOptionPane.showMessageDialog(frame, helpMessage, "Help", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        //spróbować flowlayout żeby przy zmniejszaniu guziki nie znikały
        JPanel bottomMenuPanel = new JPanel();
        bottomMenuPanel.setBackground(Color.LIGHT_GRAY);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.LIGHT_GRAY);
        bottomPanel.setLayout(new BorderLayout());
        JPanel eventLogPanel = new JPanel();
        eventLogPanel.setBackground(Color.LIGHT_GRAY);
        eventLogPanel.setLayout(new FlowLayout());
        JScrollPane eventLogScrollPane = new JScrollPane();
        eventLogScrollPane.setPreferredSize(new Dimension(535,35));
        eventLogScrollPane.setViewportView(eventLogLabel);

        bottomMenuPanel.add(helpButton);
        eventLogPanel.add(eventLogScrollPane);
        bottomPanel.add(bottomMenuPanel, BorderLayout.EAST);
        bottomPanel.add(eventLogPanel, BorderLayout.CENTER);

        JPanel topMenuPanel = new JPanel();
        topMenuPanel.setBackground(Color.LIGHT_GRAY);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        topMenuPanel.add(findShortestWayButton);
        topMenuPanel.add(changeStartingPositionButton);
        topMenuPanel.add(changeEndingPositionButton);
        topMenuPanel.add(exitButton);

        topPanel.add(topMenuPanel, BorderLayout.NORTH);

        JScrollPane canvasScrollPane = new JScrollPane();
        JPanel mazeCanvas = new JPanel();
        mazeCanvas.setBackground(Color.darkGray);
        mazeCanvas.setSize(defaultWidth, defaultHeight);
        //mazeCanvas.add(canvasScrollPane);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        //frame.add(mazeCanvas, BorderLayout.CENTER);
        frame.repaint();

        JMenu mazeMenu = new JMenu("Maze");
        JMenuBar menuBar = new JMenuBar();
        JMenuItem importItem = new JMenuItem("Import");

        importItem.addActionListener(new ActionListener() {
               @Override
            public void actionPerformed(ActionEvent TextLoadEvent) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(frame) == fileChooser.APPROVE_OPTION);
                {
                    File inputFile = fileChooser.getSelectedFile();
                    LoadAndSave.load_from_txt(inputFile);
                    Graphics g;
                    //MazeDrawer.paintComponent(g);
                    findShortestWayButton.setVisible(true);
                    changeStartingPositionButton.setVisible(true);
                    changeEndingPositionButton.setVisible(true);
                    addLogMessage("Imported a maze with " + LoadAndSave.getColumns() + " columns and " + LoadAndSave.getRows() + " rows.");
                    frame.add(mazeCanvas, BorderLayout.CENTER);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        ;

        });
         
        
        mazeMenu.add(importItem);
        JMenuItem exportItem = new JMenuItem("Export");

        exportItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent SaveAction) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(frame) == fileChooser.APPROVE_OPTION);
                {
                    File saveFile = fileChooser.getSelectedFile();
                }
            }
        });

        mazeMenu.add(exportItem);
        menuBar.add(mazeMenu);
        frame.setJMenuBar(menuBar);
        frame.setPreferredSize(frame.getPreferredSize());
        //frame.setPreferedSize(); -- żeby menubar nie znikał może zadziała
        //frame.setVisible(true);
    }
}

