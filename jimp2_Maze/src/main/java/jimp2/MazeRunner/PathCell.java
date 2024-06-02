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

    private static final PathCell instance = new PathCell();            /*MazeCellType implementations are singletons so we don't create unnecessary instances*/

    private PathCell() {
    }

    public static PathCell getInstance() {
        return instance;
    }

    @Override
    public void increaseStartOrFinishAmount(Maze maze) {
    }

    @Override
    public char getCharacter() {
        return 'O';
    }

    @Override
    public int getNumber() {
        return -2;
    }

    @Override
    public void drawCell(Graphics g, int i, int j, int squareSize) {
        g.setColor(Color.ORANGE);
        g.fillRect(squareSize * j, squareSize * i, squareSize, squareSize);
    }

    @Override
    public boolean isWallCell() {
        return false;
    }
}
