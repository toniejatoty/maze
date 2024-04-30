/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jimp2.jimp2_maze;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
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
    static final int frameX=1400;
    static final int frameY=700;

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
        
        JPanel topMenuPanel = new JPanel();
        topMenuPanel.setBackground(Color.LIGHT_GRAY);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        
        topMenuPanel.add(findShortestWayButton);
        topMenuPanel.add(changeStartingPositionButton);
        topMenuPanel.add(changeEndingPositionButton);
        
        topPanel.add(topMenuPanel, BorderLayout.NORTH);
        
        frame.add(topPanel, BorderLayout.NORTH);
        
        JMenu menu = new JMenu("Import maze");
        JMenuBar menuBar = new JMenuBar();
        JMenuItem i1 = new JMenuItem("Import text maze");
        JMenuItem i2= new JMenuItem("Import binary maze");
        menu.add(i1);
        menu.add(i2);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
}
}
