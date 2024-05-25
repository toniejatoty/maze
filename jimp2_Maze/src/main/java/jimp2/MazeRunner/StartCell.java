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
public class StartCell implements MazeCellType {

    @Override
    public void increaseStartOrFinishAmount(Maze maze) {
        maze.increaseAmountP();
    }

    @Override
    public char getCharacter() {
        return 'P';
    }

    @Override
    public void drawCell(Graphics g, int i, int j, int squareSize) {
        g.setColor(Color.YELLOW);
        g.fillRect(squareSize * j, squareSize * i, squareSize, squareSize);
    }
}
