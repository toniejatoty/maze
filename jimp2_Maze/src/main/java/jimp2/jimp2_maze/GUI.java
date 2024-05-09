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
    static final String helpMessage = "Welcome to an pathfinding in a maze aplication!.\n"
            + "Please import a maze using the \"Import maze\" option. The imported file should be\n"
            + "a text file with P as the maze start, K as it's finish, X as a wall and empty space as paths\n"
            + "or a binary file. Next, you may use the \"Find shortest path\" button to find the shortest path in the maze.\n"
            + "Buttons \"Change start position\" and \"Change finish position\" allow you to change the start and finish\n"         //może dodać tu info o tym set start/finish position oprócz change?
            + "between which the path will be found. You can also save the found path or the maze with the found path\n"
            + "in a text file using the \"Save\" button.";
    static final String wrongIndexError = "You tried to import a maze with a wrong extension.\nPlease import a maze with either \".txt\" or \".bin\" extension.";
    private static JTextArea eventLogLabel = new JTextArea("");
    
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
    
    private static String getFileExtension(File file) {
        String filename = file.getName();
        int lastIndexOf = filename.lastIndexOf(".");
        if(lastIndexOf == -1) {
            return "";           //without extension
        }
        return filename.substring(lastIndexOf);
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
        
        changeStartingPositionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                changeStartingPositionButton.setText("Change start position");
                addLogMessage("Changed start position to ");                        //dopisać x i y nowego początku
            }
        });
        
        changeEndingPositionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                changeEndingPositionButton.setText("Change finish position");
                addLogMessage("Changed finish position to ");                       //dopisać x i y nowego końca
            }
        });

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

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(canvasScrollPane, BorderLayout.CENTER);
        frame.repaint();

        JMenu mazeMenu = new JMenu("Maze");
        JMenuBar menuBar = new JMenuBar();
        JMenuItem importItem = new JMenuItem("Import");

        importItem.addActionListener(new ActionListener() {
               @Override
            public void actionPerformed(ActionEvent LoadEvent) {
                JFileChooser fileChooser = new JFileChooser();
                
                if (fileChooser.showOpenDialog(frame) == fileChooser.APPROVE_OPTION);
                {
                    File inputFile = fileChooser.getSelectedFile();
                    if(getFileExtension(inputFile).compareTo(".txt") == 0) {
                    LoadAndSave.loadFromTxt(inputFile);
                    findShortestWayButton.setVisible(true);                                     //do zrobienia żeby nie wyświetlało się gdy nie ma P i K w labiryncie
                    if(LoadAndSave.getIsStart() == true && LoadAndSave.getIsFinish()==true) {
                        findShortestWayButton.setVisible(true);
                        changeStartingPositionButton.setText("Change start position");
                        changeEndingPositionButton.setText("Change finish position");
                    }
                    else {
                        if(LoadAndSave.getIsStart() == false)
                            changeStartingPositionButton.setText("Set start position");
                        if(LoadAndSave.getIsFinish() == false)
                            changeEndingPositionButton.setText("Set finish position");
                    }
                    changeStartingPositionButton.setVisible(true);
                    changeEndingPositionButton.setVisible(true);
                    MazeDrawer mazePaint = new MazeDrawer();
                    mazePaint.setPreferredSize(new Dimension(10*LoadAndSave.getColumns(), 10*LoadAndSave.getRows()));
                    canvasScrollPane.setViewportView(mazePaint);
                    addLogMessage("Imported a maze with " + LoadAndSave.getColumns() + " columns and " + LoadAndSave.getRows() + " rows.");
                    }
                    else if (getFileExtension(inputFile).compareTo(".bin") == 0) {
                        //binary import
                    }
                    else {
                        System.out.println("File with wrong extension");
                        JOptionPane.showMessageDialog(frame, wrongIndexError, "Wrong Index Error", JOptionPane.ERROR_MESSAGE);
                        
                    }
                    
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

