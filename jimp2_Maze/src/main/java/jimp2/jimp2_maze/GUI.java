/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jimp2.jimp2_maze;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JPanel;

/**
 *
 * @author piotr-sosnowski
 */
public class GUI {
    static JFrame frame;
    static final int frameX=1600;
    static final int frameY=1400;
    static final int defaultWidth=1600;
    static final int defaultHeight=defaultWidth-200;

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
        
        JPanel mazeCanvas = new JPanel();
        mazeCanvas.setBackground(Color.darkGray);
        mazeCanvas.setSize(defaultWidth,defaultHeight);
        
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(mazeCanvas, BorderLayout.CENTER);
        frame.repaint();
        
        JMenu importMenu = new JMenu("Import maze");
        JMenu saveMenu = new JMenu("Save");
        JMenuBar menuBar = new JMenuBar();
        JMenuItem importTextItem = new JMenuItem("Import text maze");
        JMenuItem importBinaryItem= new JMenuItem("Import binary maze");
        
         importTextItem.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent TextLoadEvent) {
                JFileChooser fileChooser = new JFileChooser();
                if(fileChooser.showOpenDialog(frame) == fileChooser.APPROVE_OPTION); {
                    File inputFile = fileChooser.getSelectedFile();
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
        
    }
}
