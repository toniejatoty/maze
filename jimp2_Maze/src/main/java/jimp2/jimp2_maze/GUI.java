/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jimp2.jimp2_maze;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
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

    private static void addFrame() {
        frame = new JFrame();
        frame.setSize(frameX, frameY);
        frame.setLayout(new BorderLayout());
        frame.setTitle("Maze");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void buildGUI() {
        addFrame();

        JButton findShortestWayButton = new JButton("Find the shortest path");
        JButton changeStartingPositionButton = new JButton("Change start position");
        JButton changeEndingPositionButton = new JButton("Change finish position");
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

        bottomMenuPanel.add(helpButton);
        bottomPanel.add(bottomMenuPanel, BorderLayout.EAST);

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
                    MazeDrawer.drawMaze(LoadAndSave.getMaze(), LoadAndSave.getRows(), LoadAndSave.getColumns(), canvasScrollPane, mazeCanvas);
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

