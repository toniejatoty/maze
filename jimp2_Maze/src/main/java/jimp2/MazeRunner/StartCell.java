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

    private static final StartCell instance = new StartCell();          /*MazeCellType implementations are singletons so we don't create unnecessary instances*/

    private StartCell() {
    }

    public static StartCell getInstance() {
        return instance;
    }

    @Override
    public void increaseStartOrFinishAmount(Maze maze) {
        maze.increaseAmountP();
    }

    @Override
    public char getCharacter() {
        return 'P';
    }

    @Override
    public int getNumber() {
        return 0;
    }

    @Override
    public void drawCell(Graphics g, int i, int j, int squareSize) {
        g.setColor(Color.YELLOW);
        g.fillRect(squareSize * j, squareSize * i, squareSize, squareSize);
    }

    @Override
    public boolean isWallCell() {
        return false;
    }
}
