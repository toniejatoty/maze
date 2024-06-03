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
            + "Please import a maze using the \"Import maze\" option int the \"Maze\" menu. The imported file should be<br>"
            + "a text file with P as the maze start, K as it's finish, X as a wall and empty space as possible paths<br>"
            + "or a binary file. Next, you may use the \"Find the shortest path\" button to find the shortest path in the maze.<br>"
            + "You may also clear the maze using \"Clear maze\" button.<br>"
            + "Buttons \"Set start position\" and \"Set finish position\" allow you to change the start and finish<br>"
            + "between which the path will be found. You can also save the maze with the found path or the maze without the found path<br>"
            + "in a text file using the \"Export maze with path\" or \"Export maze without path\" options under the \"Maze\" menu.<br>"
            + "If you export a maze with found path, the path will be marked by \'O\' symbols.";
    private final JLabel helpMessageLabel = new JLabel(helpMessage);
    private final String wrongIndexErrorMessage = "<html><center>You tried to import a maze with a wrong extension.<br>Please import a maze with either \".txt\" or \".bin\" extension.";
    private final String wrongMazeErrorMessage = "<html><center>You tried to import an incorrect maze.<br>. Please import a correct maze with a \".txt\" or \".bin\" format.";
    private final String cantFindPathErrorMessage = "<html><center>Couldn't find a path in the maze.";
    private final JLabel wrongIndexErrorLabel = new JLabel(wrongIndexErrorMessage);
    private final JLabel wrongMazeErrorLabel = new JLabel(wrongMazeErrorMessage);
    private final JLabel cantFindPathErrorLabel = new JLabel(cantFindPathErrorMessage);
    private final JTextArea eventLogLabel = new JTextArea("");
    private JMenuItem exportMazeWithoutItem;
    private JButton findShortestWayButton;
    private JButton changeStartingPositionButton;
    private JButton changeEndingPositionButton;
    private JButton clearMazeButton;
    private Load loader;
    private Maze maze;
    private JScrollPane canvasScrollPane;
    private JMenuItem exportMazeWithPathItem;
    private Save save;
    private MazeDrawer mazePaint;
    private Subject subject;

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
        exportMazeWithPathItem.setVisible(false);
        redrawMaze();
        subject.notifyAllObservers("Cleared maze");
    }

    public void savePathAction() {
        String dir = System.getProperty("user.dir");
        JFileChooser fileChooser = new JFileChooser(dir);
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
                save.exportMazeWithPath();
            }
            subject.notifyAllObservers("Saved the path as " + saveFile.getName());
        }
    }

    public void saveMazeAction() {
        String dir = System.getProperty("user.dir");
        JFileChooser fileChooser = new JFileChooser(dir);
        FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("txt files (*.txt)", "txt");
        // adding filters
        fileChooser.addChoosableFileFilter(txtFilter);
        fileChooser.setFileFilter(txtFilter);

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File saveFile = fileChooser.getSelectedFile();
            if (fileChooser.getFileFilter() == txtFilter) {
                saveFile = new File(fileChooser.getSelectedFile().getAbsolutePath() + ".txt");
                save = new Save(maze, saveFile);
                save.exportMazeWithoutPath();
            }
            subject.notifyAllObservers("Saved maze as " + saveFile.getName());
        }
    }

    private void importMazeFromFile(File inputFile) {
        try {
            if (getFileExtension(inputFile).compareTo(".txt") == 0) {
                loader = new Load(maze);
                loader.loadFromTxt(inputFile);
                findShortestWayButton.setVisible(false);
                exportMazeWithoutItem.setVisible(true);
                if (maze.getAmountK() == 1 && maze.getAmountP() == 1) {
                    findShortestWayButton.setVisible(true);
                    clearMazeButton.setVisible(true);
                }
                changeStartingPositionButton.setVisible(true);
                changeEndingPositionButton.setVisible(true);
                redrawMaze();
                subject.notifyAllObservers("Imported a maze with " + maze.getColumns() + " columns and " + maze.getRows() + " rows.");
            } else if (getFileExtension(inputFile).compareTo(".bin") == 0) {                        // Binary import
                loader = new Load(maze);
                loader.loadFromBin(inputFile);
                findShortestWayButton.setVisible(false);
                exportMazeWithoutItem.setVisible(true);
                if (maze.getAmountK() == 1 && maze.getAmountP() == 1) {
                    findShortestWayButton.setVisible(true);
                    clearMazeButton.setVisible(true);
                }
                changeStartingPositionButton.setVisible(true);
                changeEndingPositionButton.setVisible(true);
                redrawMaze();
                subject.notifyAllObservers("Imported a maze with " + maze.getColumns() + " columns and " + maze.getRows() + " rows.");
            } else {
                System.err.println("File with wrong extension");
                JOptionPane.showMessageDialog(null, wrongIndexErrorLabel, "Wrong Index Error", JOptionPane.ERROR_MESSAGE);
            }

            revalidate();
            repaint();
        } catch (IOException ex) {
            System.err.println("Error with loading maze.");
            JOptionPane.showMessageDialog(null, wrongMazeErrorLabel, "Wrong Maze Error", JOptionPane.ERROR_MESSAGE);
            loader.resetMaze();
            redrawMaze();
            clearMazeButton.setVisible(false);
            findShortestWayButton.setVisible(false);
            changeStartingPositionButton.setVisible(false);
            changeEndingPositionButton.setVisible(false);
            exportMazeWithoutItem.setVisible(false);
        }
    }

    private void findShortestPath() {
        exportMazeWithPathItem.setVisible(true);
        subject.notifyAllObservers("Found shortest path beetwen start and finish");
        try {
            loader.findPathInMaze();
        } catch (IOException ex) {
            System.err.println("Couldn't find path in maze");
            JOptionPane.showMessageDialog(null, cantFindPathErrorLabel, "Wrong Index Error", JOptionPane.ERROR_MESSAGE);
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

    public GUI(Load loader, Maze maze, Save save, MazeDrawer mazePaint, Subject subject) {
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
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);                       // so text in event log is always at the top
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

        changeStartingPositionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                maze.setAmountP(1);
                MazeDrawer newMazePaint = new MazeDrawer(maze.getRows(), maze.getColumns(), maze);
                canvasScrollPane.setViewportView(newMazePaint);
                newMazePaint.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        int c = (int) (e.getX() / newMazePaint.getSquareSize());
                        int r = (int) (e.getY() / newMazePaint.getSquareSize());
                        loader.setStart(r, c, maze.getAmountP() == 1);
                        redrawMaze();
                        subject.notifyAllObservers("Changed start position to row: " + r + ", column: " + c);
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
                    public void mousePressed(MouseEvent e) {
                        int c = (int) (e.getX() / newMazePaint.getSquareSize());
                        int r = (int) (e.getY() / newMazePaint.getSquareSize());
                        loader.setFinish(r, c, maze.getAmountK() == 1);
                        redrawMaze();
                        subject.notifyAllObservers("Changed finish position to row: " + r + ", column: " + c);
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
        JMenuItem importItem = new JMenuItem("Import maze");
        exportMazeWithPathItem = new JMenuItem("Export maze with path");
        exportMazeWithoutItem = new JMenuItem("Export maze without path");

        importItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent LoadEvent) {
                String dir = System.getProperty("user.dir");
                JFileChooser fileChooser = new JFileChooser(dir);
                // setting deafult extension choosing option as .txt and .bin
                FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("txt files (*.txt)", "txt");
                FileNameExtensionFilter binFilter = new FileNameExtensionFilter("bin files (*.bin)", "bin");
                // adding filters
                fileChooser.addChoosableFileFilter(txtFilter);
                fileChooser.addChoosableFileFilter(binFilter);
                fileChooser.setFileFilter(txtFilter);

                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    exportMazeWithPathItem.setVisible(false);
                    File inputFile = fileChooser.getSelectedFile();

                    importMazeFromFile(inputFile);
                }
            }

        });

        mazeMenu.add(importItem);

        exportMazeWithPathItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent SaveAction) {
                savePathAction();
            }
        });
        exportMazeWithoutItem.addActionListener(new ActionListener() {
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

        mazeMenu.add(exportMazeWithPathItem);
        mazeMenu.add(exportMazeWithoutItem);
        exportMazeWithPathItem.setVisible(false);
        exportMazeWithoutItem.setVisible(false);
        menuBar.add(mazeMenu);
        setJMenuBar(menuBar);

        setPreferredSize(this.getPreferredSize());
    }
}
