package jimp2.jimp2_maze;

import java.awt.BorderLayout;
import java.awt.Color;
<<<<<<< HEAD
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;
=======
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

>>>>>>> d582ba462e8aa40da43ca5369e902daec1b31b12
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author piotr-sosnowski
 */
public class GUI extends JFrame {
    static JFrame frame;
<<<<<<< HEAD
    static final int frameX=1600;
    static final int frameY=1400;
    static final int defaultWidth=1600;
    static final int defaultHeight=defaultWidth-200;
    static final String helpMessage = "Witam w programie do znajdywania ścieżki w labiryncie!.\n"
            + "Proszę wczytać labirynt za pomocą opcji \"Import maze\". Wczytany plik powinien być\n"
            + "tekstowy z oznaczenie P na początek labiryntu oraz K na jego koniec lub binarny\n"
            + "Następnie za pomocą przycisku \"Find shortest way\" można znaleźć najkrótszą ścieżkę w labiryncie.\n"
            + "Przyciski \"Change starting position\" oraz \"Change ending position\" pozwalają zmieniać początek i koniec\n"
            + "między którymi szukana będzie ścieżka. Istnieje również możliwość zapisania znalezionej ścieżki\n"
            + "w formie tekstowej korzystając z opcji \"Save\".";
=======
    static final int frameX = 1600;
    static final int frameY = 1400;
    static final int defaultWidth = 1600;
    static final int defaultHeight = defaultWidth - 200;
    private static Graphics mazecanva;
    private static char[][] maze = null;
>>>>>>> d582ba462e8aa40da43ca5369e902daec1b31b12

    private static void addFrame() {
        frame = new JFrame();
        frame.setSize(frameX, frameY);
        frame.setLayout(new BorderLayout());
        frame.setTitle("Maze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mazeCanvas = new JPanel(); // to see maze in screen
        frame.add(mazeCanvas, BorderLayout.CENTER);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                mazecanva = mazeCanvas.getGraphics();
            }});
    }

    public static void buildGUI() {
        addFrame();

        JButton findShortestWayButton = new JButton("Find the shortest way");
        JButton changeStartingPositionButton = new JButton("Change starting position");
        JButton changeEndingPositionButton = new JButton("Change ending position");
<<<<<<< HEAD
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
               JOptionPane.showMessageDialog(frame, helpMessage);
           }
        });
        
        JPanel bottomMenuPanel = new JPanel();
        bottomMenuPanel.setBackground(Color.LIGHT_GRAY);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.LIGHT_GRAY);
        bottomPanel.setLayout(new BorderLayout());
        
        bottomMenuPanel.add(helpButton);
        bottomPanel.add(bottomMenuPanel, BorderLayout.EAST);
        
=======

>>>>>>> d582ba462e8aa40da43ca5369e902daec1b31b12
        JPanel topMenuPanel = new JPanel();
        topMenuPanel.setBackground(Color.LIGHT_GRAY);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        topMenuPanel.add(findShortestWayButton);
        topMenuPanel.add(changeStartingPositionButton);
        topMenuPanel.add(changeEndingPositionButton);
<<<<<<< HEAD
        topMenuPanel.add(exitButton);
        
=======

>>>>>>> d582ba462e8aa40da43ca5369e902daec1b31b12
        topPanel.add(topMenuPanel, BorderLayout.NORTH);

        JPanel mazeCanvas = new JPanel();
<<<<<<< HEAD
        mazeCanvas.setBackground(Color.darkGray);
        mazeCanvas.setSize(defaultWidth,defaultHeight);
        
=======
        mazeCanvas.setSize(defaultWidth, defaultHeight);

>>>>>>> d582ba462e8aa40da43ca5369e902daec1b31b12
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(mazeCanvas, BorderLayout.CENTER);
<<<<<<< HEAD
        frame.repaint();
        
=======

>>>>>>> d582ba462e8aa40da43ca5369e902daec1b31b12
        JMenu importMenu = new JMenu("Import maze");
        JMenu saveMenu = new JMenu("Save");
        JMenuBar menuBar = new JMenuBar();
        JMenuItem importTextItem = new JMenuItem("Import text maze");
<<<<<<< HEAD
        JMenuItem importBinaryItem= new JMenuItem("Import binary maze");
        
         importTextItem.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent TextLoadEvent) {
                JFileChooser fileChooser = new JFileChooser();
                if(fileChooser.showOpenDialog(frame) == fileChooser.APPROVE_OPTION); {
                    File inputFile = fileChooser.getSelectedFile();
                    load_and_save.load_from_txt(inputFile);
                }
        };
        
    });
         
        importBinaryItem.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent BinaryLoadEvent) {
                JFileChooser fileChooser = new JFileChooser();
                if(fileChooser.showOpenDialog(frame) == fileChooser.APPROVE_OPTION); {
                    File inputFile = fileChooser.getSelectedFile();
            }
        };
        
    });
        
=======
        importTextItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser fileChooser = new JFileChooser();
                File file = null;
                if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    file = fileChooser.getSelectedFile();
                }
                try {
                    Reader r = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    BufferedReader bufferedReadersize = new BufferedReader(r);
                    String line;
                    int row = 0;
                    int column = 0;
                    while ((line = bufferedReadersize.readLine()) != null) {
                        if (row == 0) {
                            column = line.length();
                        }
                        if (column != line.length())
                            throw new IOException("Not adequate amount of column in row " + row);
                        row++;
                    }
                    bufferedReadersize.close();
                    maze = new char[row][column];
                    row = 0;
                    while ((line = bufferedReader.readLine()) != null) {
                        maze[row] = line.toCharArray();
                        row++;
                    }
                    bufferedReader.close();
                    printmaze(maze);
                    mazeCanvas.repaint();
                } catch (IOException e) {
                    System.out.println(e.getMessage());

                }
                
            }

        });
        JMenuItem importBinaryItem = new JMenuItem("Import binary maze");
>>>>>>> d582ba462e8aa40da43ca5369e902daec1b31b12
        importMenu.add(importTextItem);
        importMenu.add(importBinaryItem);
        JMenuItem saveWayItem = new JMenuItem("Save way as a text file");
        
        saveWayItem.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent SaveAction) {
               JFileChooser fileChooser = new JFileChooser();
               if(fileChooser.showSaveDialog(frame) == fileChooser.APPROVE_OPTION); {
                    File saveFile = fileChooser.getSelectedFile();
           }
           }
        });
        
        saveMenu.add(saveWayItem);
        menuBar.add(importMenu);
        menuBar.add(saveMenu);
        frame.setJMenuBar(menuBar);
<<<<<<< HEAD
        
    }
}
=======

        frame.setVisible(true);
    }

private static void printmaze(char [][] maze)
{
    int row = maze.length;
    int column = maze[0].length;
    int Cellsize =2; // in pixel to determine square CellsizexCellsize where will be space/X/P/K
    int x,y; // to know where print symbol
    
    try
    {for(int i=0; i<row; i++)
    {
        x= i *Cellsize;
    for(int j=0; j<column; j++){
        y=j * Cellsize;
    switch(maze[i][j])
    {
        case 'X':
        mazecanva.setColor(Color.BLACK);
        mazecanva.fillRect(x, y, Cellsize, Cellsize);
        break;
        case 'P':
        mazecanva.setColor(Color.GREEN);
        mazecanva.fillRect(x, y, Cellsize, Cellsize);
        break;
        case 'K':
        mazecanva.setColor(Color.RED);
        mazecanva.fillRect(x, y, Cellsize, Cellsize);
        break;
        case ' ':
        mazecanva.setColor(Color.WHITE);
        mazecanva.fillRect(x, y, Cellsize, Cellsize);
        break;
        default:
        throw new RuntimeException("Undefinied symbol in row " + x + " column" + y);
   
    }
System.out.print(maze[i][j]);   
}
System.out.println("");}}
     catch (RuntimeException e) {
      System.out.println(e.getMessage());
     }
    


    }
}
>>>>>>> d582ba462e8aa40da43ca5369e902daec1b31b12
