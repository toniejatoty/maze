/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jimp2.jimp2_maze;

import javax.swing.JFrame;

/**
 *
 * @author piotr-sosnowski
 */
public class GUI {
     static JFrame frame = new JFrame();
    static final int frameX=2000;
    static final int frameY=1000;
    
    public static void addFrame() {
        frame.setSize(frameX, frameY);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
