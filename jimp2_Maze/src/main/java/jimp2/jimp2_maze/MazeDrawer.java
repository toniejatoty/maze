/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jimp2.jimp2_maze;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author piotr-sosnowski
 */
public class MazeDrawer extends JPanel{

    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i = 0; i < LoadAndSave.getRows(); i++) {
            for(int j = 0; j < LoadAndSave.getColumns(); j++) {
                switch(LoadAndSave.getMaze()[i][j]) {
                    case ' ':
                        g.setColor(Color.WHITE);
                        g.fillRect(10*j, 10*i, 10, 10);
                        break;
                    case 'X':
                        g.setColor(Color.BLACK);
                        g.fillRect(10*j, 10*i, 10, 10);
                        break;
                    case 'P':
                        g.setColor(Color.YELLOW);
                        g.fillRect(10*j, 10*i, 10, 10);
                        break;
                    case 'K':
                        g.setColor(Color.BLUE);
                        g.fillRect(10*j, 10*i, 10, 10);
                        break;
                    default:
                        g.setColor(Color.RED);
                        g.fillRect(10*j, 10*i, 10, 10);
                        break;
                }
            }
        }
    }
}
    
