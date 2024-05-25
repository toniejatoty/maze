
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
public class MazeDrawer extends JPanel {

    private int squareSize;
    private int columns;
    private int rows;
    private Maze maze;

    public MazeDrawer(int rows, int columns, Maze maze) {
        squareSize = 10;
        this.rows = rows;
        this.columns = columns;
        this.maze = maze;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                maze.getMazeCell(i, j).getCellType().drawCell(g, i, j, squareSize);
            }
        }
    }
}
