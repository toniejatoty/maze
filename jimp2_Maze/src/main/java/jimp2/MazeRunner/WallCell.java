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
public class WallCell implements MazeCellType {

    private static final WallCell instance = new WallCell();            /*MazeCellType implementations are singletons so we don't create unnecessary instances*/

    private WallCell() {
    }

    public static WallCell getInstance() {
        return instance;
    }

    @Override
    public void increaseStartOrFinishAmount(Maze maze) {
        ;
    }

    @Override
    public char getCharacter() {
        return 'X';
    }

    @Override
    public int getNumber() {
        return -2;
    }

    @Override
    public void drawCell(Graphics g, int i, int j, int squareSize) {
        g.setColor(Color.BLACK);
        g.fillRect(squareSize * j, squareSize * i, squareSize, squareSize);
    }

    public boolean isWallCell() {
        return true;
    }
}
