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

    public PointXY(int x, int y, int direction) {
        if (direction == 1) {
            this.x = x - 1;
            this.y = y;
        }
        if (direction == 2) {
            this.x = x + 1;
            this.y = y;
        }
        if (direction == 3) {
            this.x = x;
            this.y = y - 1;
        }
        if (direction == 4) {
            this.x = x;
            this.y = y + 1;
        }
    }
}
