/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jimp2.jimp2_maze;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author piotr-sosnowski
 */
public class GUI {
    static JFrame frame = new JFrame();
    static final int frameX=1400;
    static final int frameY=700;
    static JButton button = new JButton();
    static JComboBox comboBox = new JComboBox();
    static JTextField textField = new JTextField();
    
    private static void addFrame() {
        frame.setSize(frameX, frameY);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private static void addButton(int x, int y, int width, int height, String text) {
        button.setBounds(x,y, width, height);
        button.setText(text);
        frame.add(button);
    }
    
    private static void addComboBox(int x, int y, int width, int height, String [] text) {
        comboBox.setBounds(x,y, width, height);
        for(int i = 0; i < text.length; i++) {
            comboBox.addItem(text[i]);
        }
        frame.add(comboBox);
    }
    
    private static void addTextField(int x, int y, int width, int height, String text) {
        textField.setBounds(x,y, width, height);
        textField.setText(text);
        frame.add(textField);
    }
    
    public static void buildGUI() {
        addFrame();
        addButton(200,100, 200, 50, "TestButton");
        String [] text = new String[3];
        text[0] = "TestComboBox1";
        text[1] = "TestComboBox2";
        text[2] = "TestComboBox3";
        addComboBox(10,10, 125, 25, text);
        addTextField(150,10,125,25,"TestTextField");
}
}
