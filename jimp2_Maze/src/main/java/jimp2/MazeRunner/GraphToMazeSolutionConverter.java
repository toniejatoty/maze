package jimp2.MazeRunner;

public class GraphToMazeSolutionConverter {

    private Integer[] directions;
    private int xstart, ystart;
    private int distanceFromStartToFinish;
    private PointXY[] points;
    Maze maze;

    public GraphToMazeSolutionConverter(Maze maze, Integer[] directions, int xstart, int ystart, int s) {
        this.maze = maze;
        distanceFromStartToFinish = s;
        this.directions = directions;
        this.xstart = xstart;
        this.ystart = ystart;
    }

    public void getPoints() {
        points = new PointXY[distanceFromStartToFinish + 1];
        points[0] = new PointXY(xstart, ystart);
        int direction = 0;
        int vertexcount = directions.length - 1;
        int x = xstart;
        int y = ystart;
        for (int i = 1; i < distanceFromStartToFinish + 1; i++) {
            if (maze.getVertexNumberArrayCell(x, y) >= 0) {
                direction = directions[vertexcount];
                if (direction == 1) {
                    points[i] = new PointXY(x - 1, y);
                    x--;
                }
                if (direction == 2) {
                    points[i] = new PointXY(x + 1, y);
                    x++;
                }
                if (direction == 3) {
                    points[i] = new PointXY(x, y - 1);
                    y--;
                }
                if (direction == 4) {
                    points[i] = new PointXY(x, y + 1);
                    y++;
                }
                vertexcount--;
            } else {
                if (maze.getVertexNumberArrayCell(x - 1, y) >= -1 && direction != 2) {
                    direction = 1;
                    points[i] = new PointXY(x - 1, y);
                    x--;
                } else if (maze.getVertexNumberArrayCell(x + 1, y) >= -1 && direction != 1) {
                    direction = 2;
                    points[i] = new PointXY(x + 1, y);
                    x++;
                } else if (maze.getVertexNumberArrayCell(x, y + 1) >= -1 && direction != 3) {
                    direction = 4;
                    points[i] = new PointXY(x, y + 1);
                    y++;
                } else if (maze.getVertexNumberArrayCell(x, y - 1) >= -1 && direction != 4) {
                    direction = 3;
                    points[i] = new PointXY(x, y - 1);
                    y--;
                }
            }
        }
        convertSolutionToMaze();

    }

    private void convertSolutionToMaze() {

        for (int i = 1; i < distanceFromStartToFinish; i++) {
            maze.getMaze()[points[i].getX()][points[i].getY()] = new MazeCell(points[i].getX(), points[i].getY(), Load.pathCell);
        }

    }

}
