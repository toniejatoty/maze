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
public class PathCell implements MazeCellType {

    @Override
    public void increaseStartOrFinishAmount(Maze maze) {
        ;
    }

    @Override
    public char getCharacter() {
        return 'O';
    }

    @Override
    public void drawCell(Graphics g, int i, int j, int squareSize) {
        g.setColor(Color.ORANGE);
        g.fillRect(squareSize * j, squareSize * i, squareSize, squareSize);
    }
}
