package jimp2.MazeRunner;

/**
 *
 * @author piotr-sosnowski
 */
public class MazeCell {

    private final MazeCellType cellType;

    /**
     *
     * @param cellType
     */
    public MazeCell(MazeCellType cellType) {
        this.cellType = cellType;
    }

    public MazeCellType getCellType() {
        return cellType;
    }
}
