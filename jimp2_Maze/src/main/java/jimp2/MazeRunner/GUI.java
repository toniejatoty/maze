/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jimp2.MazeRunner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author piotr-sosnowski
 */
public class GUI extends JFrame{

    //static JFrame frame;
    private final int frameX = 1600;
    private final int frameY = 1400;
    private final int defaultWidth = 1600;
    private final int defaultHeight = defaultWidth - 200;
    private final String helpMessage = "<html><center>Welcome to an pathfinding in a maze aplication!<br>"
            + "Please import a maze using the \"Import maze\" option. The imported file should be<br>"
            + "a text file with P as the maze start, K as it's finish, X as a wall and empty space as paths<br>"
            + "or a binary file. Next, you may use the \"Find shortest path\" button to find the shortest path in the maze.<br>"
            + "Buttons \"Set start position\" and \"Set finish position\" allow you to change the start and finish<br>"
            + "between which the path will be found. You can also save the found path or the maze with the found path<br>"
            + "in a text file using the \"Export path\" option under the \"Export\" menu. You may also export the whole maze to a text file<br>"
            + "using \"Export maze\" option in the same menu. If you export a maze with found path, the path will be marked by \'O\' symbols.";
    private final JLabel helpMessageLabel = new JLabel(helpMessage);
    private final String wrongIndexErrorMessage = "<html><center>You tried to import a maze with a wrong extension.<br>Please import a maze with either \".txt\" or \".bin\" extension.";
    private final JLabel wrongIndexErrorLabel = new JLabel(wrongIndexErrorMessage);
    private final JTextArea eventLogLabel = new JTextArea("");
    
    

    private void addFrame() {
        //frame = new JFrame();
        setSize(frameX, frameY);
        setLayout(new BorderLayout());
        setTitle("Maze");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addLogMessage(String text) {
        eventLogLabel.setText(text + " \n " + eventLogLabel.getText());
    }

    private void writeToFile(File filename, char c) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
        writer.append(c);
        writer.close();
    }

    private String getFileExtension(File file) {
        String filename = file.getName();
        int lastIndexOf = filename.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";           //without extension
        }
        return filename.substring(lastIndexOf);
    }

    public GUI() {
        addFrame();

        helpMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        wrongIndexErrorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        eventLogLabel.setFont(new Font("Arial", Font.BOLD, 20));
        eventLogLabel.setBackground(Color.LIGHT_GRAY);
        eventLogLabel.setForeground(Color.BLACK);
        eventLogLabel.setText("Please import a maze");
        DefaultCaret caret = (DefaultCaret) eventLogLabel.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);                           //so text in event log is always at the top
        JButton findShortestWayButton = new JButton("Find the shortest path");
        findShortestWayButton.setVisible(false);
        JButton changeStartingPositionButton = new JButton("Set start position");
        changeStartingPositionButton.setVisible(false);
        JButton changeEndingPositionButton = new JButton("Set finish position");
        changeEndingPositionButton.setVisible(false);
        Icon helpIcon = new ImageIcon("images/helpIcon2.jpg");
        JButton helpButton = new JButton(helpIcon);
        JButton exitButton = new JButton("Exit");

        changeStartingPositionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                LoadAndSave.setAmountP(1);
                addLogMessage("Changed start position to ");                        //dopisać x i y nowego początku
                if (LoadAndSave.getAmountP() == 1 && LoadAndSave.getAmountK() == 1) {
                    findShortestWayButton.setVisible(true);
                }
            }
        });

        changeEndingPositionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                LoadAndSave.setAmountK(1);
                addLogMessage("Changed finish position to ");                       //dopisać x i y nowego końca
                if (LoadAndSave.getAmountP() == 1 && LoadAndSave.getAmountK() == 1) {
                    findShortestWayButton.setVisible(true);
                }
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
                JOptionPane.showMessageDialog(null , helpMessageLabel, "Help", JOptionPane.INFORMATION_MESSAGE);
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
        eventLogScrollPane.setPreferredSize(new Dimension(535, 35));
        eventLogScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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

        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
        add(canvasScrollPane, BorderLayout.CENTER);
        repaint();

        JMenu mazeMenu = new JMenu("Maze");
        JMenuBar menuBar = new JMenuBar();
        JMenuItem importItem = new JMenuItem("Import");
        JMenuItem exportPathItem = new JMenuItem("Export path");
        JMenuItem exportMazeItem = new JMenuItem("Export maze");

        importItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent LoadEvent) {
                JFileChooser fileChooser = new JFileChooser();
                //setting deafult extension choosing option as .txt and .bin
                FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("txt files (*.txt)", "txt");
                FileNameExtensionFilter binFilter = new FileNameExtensionFilter("bin files (*.bin)", "bin");
                //adding filters
                fileChooser.addChoosableFileFilter(txtFilter);
                fileChooser.addChoosableFileFilter(binFilter);
                fileChooser.setFileFilter(txtFilter);

                if (fileChooser.showOpenDialog(null) == fileChooser.APPROVE_OPTION) {
                    exportPathItem.setVisible(false);
                    findShortestWayButton.setVisible(false);
                    File inputFile = fileChooser.getSelectedFile();
                    if (getFileExtension(inputFile).compareTo(".txt") == 0) {
                        LoadAndSave.loadFromTxt(inputFile);
                        char maze[][];
                        maze = LoadAndSave.loadFromTxt(inputFile); // the difference beetween this char[][] and the examples of mazes (txt) is that here letter 'O' shows the shortest way 

                        // if (getFileExtension(inputFile).compareTo(".txt") == 0) {
                        //   LoadAndSave.loadFromTxt(inputFile);
                        exportMazeItem.setVisible(true);
                        if (LoadAndSave.getAmountK() == 1 && LoadAndSave.getAmountP() == 1) {
                            findShortestWayButton.setVisible(true);                                     //do zrobienia żeby nie wyświetlało się gdy nie ma P i K w labiryncie
                        }
                        changeStartingPositionButton.setVisible(true);
                        changeEndingPositionButton.setVisible(true);
                        MazeDrawer mazePaint = new MazeDrawer();
                        mazePaint.setPreferredSize(new Dimension(10 * LoadAndSave.getColumns(), 10 * LoadAndSave.getRows()));
                        canvasScrollPane.setViewportView(mazePaint);
                        addLogMessage("Imported a maze with " + LoadAndSave.getColumns() + " columns and " + LoadAndSave.getRows() + " rows.");
                    } else if (getFileExtension(inputFile).compareTo(".bin") == 0) {
                        LoadAndSave.loadFromBin(inputFile);
                        exportMazeItem.setVisible(true);
                        if (LoadAndSave.getAmountK() == 1 && LoadAndSave.getAmountP() == 1) {
                            findShortestWayButton.setVisible(true);
                        }
                        changeStartingPositionButton.setVisible(true);
                        changeEndingPositionButton.setVisible(true);
                        MazeDrawer mazePaint = new MazeDrawer();
                        mazePaint.setPreferredSize(new Dimension(10 * LoadAndSave.getColumns(), 10 * LoadAndSave.getRows()));
                        canvasScrollPane.setViewportView(mazePaint);
                        addLogMessage("Imported a maze with " + LoadAndSave.getColumns() + " columns and " + LoadAndSave.getRows() + " rows.");
                        //binary import
                    } else {
                        System.out.println("File with wrong extension");
                        JOptionPane.showMessageDialog(null, wrongIndexErrorLabel, "Wrong Index Error", JOptionPane.ERROR_MESSAGE);

                    }

                    revalidate();
                    repaint();
                }
            }

        });

        mazeMenu.add(importItem);

        exportPathItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent SaveAction) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("txt files (*.txt)", "txt");
                //adding filters
                fileChooser.addChoosableFileFilter(txtFilter);
                fileChooser.setFileFilter(txtFilter);

                if (fileChooser.showSaveDialog(null) == fileChooser.APPROVE_OPTION);
                {
                    File saveFile = fileChooser.getSelectedFile();
                    if (fileChooser.getFileFilter() == txtFilter) {
                        saveFile = new File(fileChooser.getSelectedFile().getName() + ".txt");
                    }
                    addLogMessage("Saved the path as " + saveFile.getName());

                }
            }
        });
        exportMazeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent SaveAction) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("txt files (*.txt)", "txt");        //we can add more extension in the future if needed(ex. binary)
                //adding filters
                fileChooser.addChoosableFileFilter(txtFilter);
                fileChooser.setFileFilter(txtFilter);

                if (fileChooser.showSaveDialog(null) == fileChooser.APPROVE_OPTION);
                {
                    File saveFile = fileChooser.getSelectedFile();
                    if (fileChooser.getFileFilter() == txtFilter) {
                        saveFile = new File(saveFile.getName() + ".txt");
                    }
                    try {
                        for (int i = 0; i < LoadAndSave.getRows(); i++) {
                            for (int j = 0; j < LoadAndSave.getColumns(); j++) {

                                writeToFile(saveFile, LoadAndSave.getMaze()[i][j]);
                            }
                            writeToFile(saveFile, '\n');
                        }
                        writeToFile(saveFile, '\n');
                    } catch (IOException ex) {
                        System.err.println("Cannon write to file " + fileChooser.getSelectedFile().getName());
                    }
                    addLogMessage("Saved maze as " + saveFile.getName());
                }
            }

        });

        findShortestWayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportPathItem.setVisible(true);
                addLogMessage("Found shortest path beetwen start and finish");
                try {
                    LoadAndSave.findPathInMaze();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                MazeDrawer mazePaint = new MazeDrawer();
                mazePaint.setPreferredSize(new Dimension(10 * LoadAndSave.getColumns(), 10 * LoadAndSave.getRows()));
                canvasScrollPane.setViewportView(mazePaint);
            }
        });

        mazeMenu.add(exportPathItem);
        mazeMenu.add(exportMazeItem);
        exportPathItem.setVisible(false);
        exportMazeItem.setVisible(false);
        menuBar.add(mazeMenu);
        setJMenuBar(menuBar);

        setPreferredSize(this.getPreferredSize());
        //frame.setPreferedSize(); -- żeby menubar nie znikał może zadziała
        //frame.setVisible(true);
    }
}
