/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package jimp2.MazeRunner;

import java.awt.Graphics;

/**
 *
 * @author piotr-sosnowski
 */
public interface MazeCellType {

    public void increaseStartOrFinishAmount(Maze maze);

    public char getCharacter();

    public void drawCell(Graphics g, int i, int j, int squareSize);
}
