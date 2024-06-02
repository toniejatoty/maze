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
    
    private static final FinishCell instance = new FinishCell();            /*MazeCellType implementations are singletons so we don't create unnecessary instances*/

    private FinishCell() {
    }

    public static FinishCell getInstance() {
        return instance;
    }

    @Override
    public void increaseStartOrFinishAmount(Maze maze) {
        maze.increaseAmountK();
    }

    @Override
    public char getCharacter() {
        return 'K';
    }
    
    @Override
    public int getNumber() {
        return 1;
    }

    @Override
    public void drawCell(Graphics g, int i, int j, int squareSize) {
        g.setColor(Color.BLUE);
        g.fillRect(squareSize * j, squareSize * i, squareSize, squareSize);
    }
    
    @Override
    public boolean isWallCell() {
        return false;
    }
}
