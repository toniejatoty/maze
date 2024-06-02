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
public class EmptyCell implements MazeCellType {

    private static final EmptyCell instance = new EmptyCell();              /*MazeCellType implementations are singletons so we don't create unnecessary instances*/

    private EmptyCell() {
    }

    public static EmptyCell getInstance() {
        return instance;
    }

    @Override
    public void increaseStartOrFinishAmount(Maze maze) {
    }

    @Override
    public char getCharacter() {
        return ' ';
    }

    @Override
    public int getNumber() {
        return -1;
    }

    @Override
    public void drawCell(Graphics g, int i, int j, int squareSize) {
        g.setColor(Color.WHITE);
        g.fillRect(squareSize * j, squareSize * i, squareSize, squareSize);
    }

    @Override
    public boolean isWallCell() {
        return false;
    }
}
