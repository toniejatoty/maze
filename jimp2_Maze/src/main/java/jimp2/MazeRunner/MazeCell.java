package jimp2.MazeRunner;

/**
 *
 * @author piotr-sosnowski
 */
public class MazeCell {

    private int i;
    private int j;
    private MazeCellType cellType;

    public MazeCell(int i, int j, MazeCellType cellType) {
        this.i = i;
        this.j = j;
        this.cellType = cellType;
    }

    public MazeCellType getCellType() {
        return cellType;
    }
}
