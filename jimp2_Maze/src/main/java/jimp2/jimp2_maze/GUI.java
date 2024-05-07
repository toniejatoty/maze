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
            + "Następnie za pomocą przycisku \"Find shortest way\" można znaleźć najkrótszą ścieżkę w labiryncie.\n"
            + "Przyciski \"Change starting position\" oraz \"Change ending position\" pozwalają zmieniać początek i koniec\n"
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

        JButton findShortestWayButton = new JButton("Find the shortest way");
        JButton changeStartingPositionButton = new JButton("Change starting position");
        JButton changeEndingPositionButton = new JButton("Change ending position");
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
        mazeCanvas.add(canvasScrollPane);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(mazeCanvas, BorderLayout.CENTER);
        frame.repaint();

        JMenu importMenu = new JMenu("Import maze");
        JMenu saveMenu = new JMenu("Save");
        JMenuBar menuBar = new JMenuBar();
        JMenuItem importTextItem = new JMenuItem("Import text maze");
        JMenuItem importBinaryItem = new JMenuItem("Import binary maze");

        importTextItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent TextLoadEvent) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(frame) == fileChooser.APPROVE_OPTION);
                {
                    File inputFile = fileChooser.getSelectedFile();
                    load_and_save.load_from_txt(inputFile);
                    //Maze_drawer.drawMaze(load_and_save.getMaze(), load_and_save.getRows(), load_and_save.getColumns(), canvasScrollPane);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        ;

        });
         
        importBinaryItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent BinaryLoadEvent) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(frame) == fileChooser.APPROVE_OPTION);
                {
                    File inputFile = fileChooser.getSelectedFile();
                }
            }
        ;

        });
        
        importMenu.add(importTextItem);
        importMenu.add(importBinaryItem);
        JMenuItem saveWayItem = new JMenuItem("Save way as a text file");

        saveWayItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent SaveAction) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(frame) == fileChooser.APPROVE_OPTION);
                {
                    File saveFile = fileChooser.getSelectedFile();
                }
            }
        });

        saveMenu.add(saveWayItem);
        menuBar.add(importMenu);
        menuBar.add(saveMenu);
        frame.setJMenuBar(menuBar);

    }
}
//button.setVisible(! button.isVisible())
//textField.setHorizontalAlignment(SwingConstants.CENTER
//przy ActionEvent e        ((JButton) e.getSource()).getText()         bo getSource zraca komponent
