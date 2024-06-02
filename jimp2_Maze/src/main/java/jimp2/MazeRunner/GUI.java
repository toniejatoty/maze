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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
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
public class GUI extends JFrame implements ObserverInterface {

    private final int frameX = 1600;
    private final int frameY = 1400;

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
    private JMenuItem exportMazeItem;
    private JButton findShortestWayButton;
    private JButton changeStartingPositionButton;
    private JButton changeEndingPositionButton;
    private JButton clearMazeButton;
    private Load loader;
    private Maze maze;
    private JScrollPane canvasScrollPane;
    private JMenuItem exportPathItem;
    private Save save;
    private MazeDrawer mazePaint;
    private Observer subject;

    private void addFrame() {
        setSize(frameX, frameY);
        setLayout(new BorderLayout());
        setTitle("Maze");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addLogMessage(String text) {
        eventLogLabel.setText(text + " \n " + eventLogLabel.getText());
    }

    private String getFileExtension(File file) {
        String filename = file.getName();
        int lastIndexOf = filename.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // without extension
        }
        return filename.substring(lastIndexOf);
    }

    public void redrawMaze() {
        mazePaint = new MazeDrawer(maze.getRows(), maze.getColumns(), maze);
        mazePaint.setPreferredSize(new Dimension(10 * maze.getColumns(), 10 * maze.getRows()));
        canvasScrollPane.setViewportView(mazePaint);
    }

    public void clearMazeAction() {
        loader.clearMaze();
        redrawMaze();
        subject.notifyObservers("Cleared maze");
    }

    public void savePathAction() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("txt files (*.txt)", "txt");
        // adding filters
        fileChooser.addChoosableFileFilter(txtFilter);
        fileChooser.setFileFilter(txtFilter);

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File saveFile = fileChooser.getSelectedFile();
            if (fileChooser.getFileFilter() == txtFilter) {
                saveFile = new File(fileChooser.getSelectedFile().getAbsolutePath() + ".txt");
                save = new Save(maze, saveFile);
                save.exportpath();
            }
            subject.notifyObservers("Saved the path as " + saveFile.getName());
        }
    }

    public void saveMazeAction() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("txt files (*.txt)", "txt"); // we can add more extensions in the future
        // adding filters
        fileChooser.addChoosableFileFilter(txtFilter);
        fileChooser.setFileFilter(txtFilter);

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File saveFile = fileChooser.getSelectedFile();
            if (fileChooser.getFileFilter() == txtFilter) {
                saveFile = new File(fileChooser.getSelectedFile().getAbsolutePath() + ".txt");
                save = new Save(maze, saveFile);
                save.exportpathmaze();
            }
            subject.notifyObservers("Saved maze as " + saveFile.getName());
        }
    }

    public GUI(Load loader, Maze maze, Save save, MazeDrawer mazePaint, Observer subject) {
        //subject = Observer.getInstance();
        this.maze = maze;
        this.save = save;
        this.loader = loader;
        this.mazePaint = mazePaint;
        this.subject = subject;

        addFrame();
        helpMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        wrongIndexErrorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        eventLogLabel.setFont(new Font("Arial", Font.BOLD, 20));
        eventLogLabel.setBackground(Color.LIGHT_GRAY);
        eventLogLabel.setForeground(Color.BLACK);
        eventLogLabel.setText("Please import a maze");
        DefaultCaret caret = (DefaultCaret) eventLogLabel.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE); // so text in event log is always at the top
        findShortestWayButton = new JButton("Find the shortest path");
        findShortestWayButton.setVisible(false);
        changeStartingPositionButton = new JButton("Set start position");
        changeStartingPositionButton.setVisible(false);
        changeEndingPositionButton = new JButton("Set finish position");
        changeEndingPositionButton.setVisible(false);
        clearMazeButton = new JButton("Clear Maze");
        clearMazeButton.setVisible(false);

        Icon helpIcon = new ImageIcon("images/helpIcon2.jpg");
        JButton helpButton = new JButton(helpIcon);
        JButton exitButton = new JButton("Exit");
        //maze = new Maze();
        //loader = new Load(maze);

        changeStartingPositionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                maze.setAmountP(1);
                MazeDrawer newMazePaint = new MazeDrawer(maze.getRows(), maze.getColumns(), maze);
                canvasScrollPane.setViewportView(newMazePaint);
                newMazePaint.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        System.err.println("TEST AFTER");
                        int c = (int) (e.getX() / newMazePaint.getSquareSize());
                        int r = (int) (e.getY() / newMazePaint.getSquareSize());
                        loader.setStart(r, c, maze.getAmountP()==1);
                        redrawMaze();
                        subject.notifyObservers("Changed start position to row: " + r + ", column: " + c);
                        if (maze.getAmountP() == 1 && maze.getAmountK() == 1) {
                            findShortestWayButton.setVisible(true);
                            clearMazeButton.setVisible(true);
                        }
                    }
                });
            }
        });

        changeEndingPositionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                maze.setAmountK(1);
                MazeDrawer newMazePaint = new MazeDrawer(maze.getRows(), maze.getColumns(), maze);
                canvasScrollPane.setViewportView(newMazePaint);
                newMazePaint.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int c = (int) (e.getX() / newMazePaint.getSquareSize());
                        int r = (int) (e.getY() / newMazePaint.getSquareSize());
                        loader.setFinish(r, c, maze.getAmountK()==1);
                        redrawMaze();
                        subject.notifyObservers("Changed finish position to row: " + r + ", column: " + c);
                        if (maze.getAmountP() == 1 && maze.getAmountK() == 1) {
                            findShortestWayButton.setVisible(true);
                            clearMazeButton.setVisible(true);
                        }
                    }
                });
            }
        });

        clearMazeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                clearMazeAction();
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
                JOptionPane.showMessageDialog(null, helpMessageLabel, "Help", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        // spróbować flowlayout żeby przy zmniejszaniu guziki nie znikały
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
        topMenuPanel.add(clearMazeButton);
        topMenuPanel.add(changeStartingPositionButton);
        topMenuPanel.add(changeEndingPositionButton);
        topMenuPanel.add(exitButton);

        topPanel.add(topMenuPanel, BorderLayout.NORTH);
        canvasScrollPane = new JScrollPane();

        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
        add(canvasScrollPane, BorderLayout.CENTER);
        repaint();

        JMenu mazeMenu = new JMenu("Maze");
        JMenuBar menuBar = new JMenuBar();
        JMenuItem importItem = new JMenuItem("Import");
        exportPathItem = new JMenuItem("Export path");
        exportMazeItem = new JMenuItem("Export maze");

        importItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent LoadEvent) {
                JFileChooser fileChooser = new JFileChooser();
                // setting deafult extension choosing option as .txt and .bin
                FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("txt files (*.txt)", "txt");
                FileNameExtensionFilter binFilter = new FileNameExtensionFilter("bin files (*.bin)", "bin");
                // adding filters
                fileChooser.addChoosableFileFilter(txtFilter);
                fileChooser.addChoosableFileFilter(binFilter);
                fileChooser.setFileFilter(txtFilter);

                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    exportPathItem.setVisible(false);
                    File inputFile = fileChooser.getSelectedFile();

                    importMazeFromFile(inputFile);
                }
            }

        });

        mazeMenu.add(importItem);

        exportPathItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent SaveAction) {
                savePathAction();
            }
        });
        exportMazeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent SaveAction) {
                saveMazeAction();
            }
        });

        findShortestWayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findShortestPath();
            }
        });

        mazeMenu.add(exportPathItem);
        mazeMenu.add(exportMazeItem);
        exportPathItem.setVisible(false);
        exportMazeItem.setVisible(false);
        menuBar.add(mazeMenu);
        setJMenuBar(menuBar);

        setPreferredSize(this.getPreferredSize());
    }

    private void importMazeFromFile(File inputFile) {
        if (getFileExtension(inputFile).compareTo(".txt") == 0) {
            Load loader = new Load(maze);
            loader.loadFromTxt(inputFile);
            findShortestWayButton.setVisible(false);
            exportMazeItem.setVisible(true);
            if (maze.getAmountK() == 1 && maze.getAmountP() == 1) {
                findShortestWayButton.setVisible(true);
                clearMazeButton.setVisible(true);
            }
            changeStartingPositionButton.setVisible(true);
            changeEndingPositionButton.setVisible(true);
            redrawMaze();
            subject.notifyObservers("Imported a maze with " + maze.getColumns() + " columns and " + maze.getRows() + " rows.");
        } else if (getFileExtension(inputFile).compareTo(".bin") == 0) { // Binary import
            Load loader = new Load(maze);
            loader.loadFromBin(inputFile);
            findShortestWayButton.setVisible(false);
            exportMazeItem.setVisible(true);
            if (maze.getAmountK() == 1 && maze.getAmountP() == 1) {
                findShortestWayButton.setVisible(true);
                clearMazeButton.setVisible(true);
            }
            changeStartingPositionButton.setVisible(true);
            changeEndingPositionButton.setVisible(true);
            redrawMaze();
            subject.notifyObservers("Imported a maze with " + maze.getColumns() + " columns and " + maze.getRows() + " rows.");
        } else {
            System.err.println("File with wrong extension");
            JOptionPane.showMessageDialog(null, wrongIndexErrorLabel, "Wrong Index Error", JOptionPane.ERROR_MESSAGE);
        }

        revalidate();
        repaint();
    }

    private void findShortestPath() {
        exportPathItem.setVisible(true);
        subject.notifyObservers("Found shortest path beetwen start and finish");
        try {
            loader.findPathInMaze();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        redrawMaze();
    }

    public void solveFromObserver(File file) {
        importMazeFromFile(file);
        findShortestPath();
    }

    @Override
    public void update(String message) {
        addLogMessage(message);
    }
}
