package jimp2.MazeRunner;

import java.awt.Graphics;

/**
 *
 * @author piotr-sosnowski
 */
public interface MazeCellType {

    public void increaseStartOrFinishAmount(Maze maze);

    public char getCharacter();

    public int getNumber();

    public void drawCell(Graphics g, int i, int j, int squareSize);

    public boolean isWallCell();
}
