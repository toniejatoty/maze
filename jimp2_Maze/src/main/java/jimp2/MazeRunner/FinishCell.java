/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jimp2.MazeRunner;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author piotr-sosnowski
 */
public class FinishCell implements MazeCellType {

    @Override
    public void increaseStartOrFinishAmount(Maze maze) {
        maze.increaseAmountK();
    }

    @Override
    public char getCharacter() {
        return 'K';
    }

    @Override
    public void drawCell(Graphics g, int i, int j, int squareSize) {
        g.setColor(Color.BLUE);
        g.fillRect(squareSize * j, squareSize * i, squareSize, squareSize);
    }
}
