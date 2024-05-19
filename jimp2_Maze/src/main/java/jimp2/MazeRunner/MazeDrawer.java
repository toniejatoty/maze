/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jimp2.MazeRunner;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author piotr-sosnowski
 */
public class MazeDrawer extends JPanel{
    
    private int squareSize;
    private int columns;
    private int rows;
    private char[][] maze;
    
    public MazeDrawer(int rows, int columns, char[][] maze) {
        squareSize = 10;
        this.rows = rows;
        this.columns = columns;
        this.maze = maze;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                switch(maze[i][j]) {
                    case ' ':
                        g.setColor(Color.WHITE);
                        break;
                    case 'X':
                        g.setColor(Color.BLACK);
                        break;
                    case 'P':
                        g.setColor(Color.YELLOW);
                        break;
                    case 'K':
                        g.setColor(Color.BLUE);
                        break;
                    case 'O':
                        g.setColor(Color.ORANGE);
                        break;
                    default:
                        g.setColor(Color.RED);
                        break;
                }
                g.fillRect(squareSize*j, squareSize*i, squareSize, squareSize);
            }
        }
    }
}
    
