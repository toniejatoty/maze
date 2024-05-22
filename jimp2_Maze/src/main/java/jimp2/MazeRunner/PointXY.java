package jimp2.MazeRunner;

public class PointXY {

    private int x, y;

    public PointXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return " Point (" + x + "," + y + ")";
    }
}
