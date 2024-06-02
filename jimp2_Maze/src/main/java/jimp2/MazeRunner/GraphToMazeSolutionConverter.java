package jimp2.MazeRunner;

public class GraphToMazeSolutionConverter {

    private Integer[] directions;
    private int xStart, yStart;
    private int distanceFromStartToFinish;
    private PointXY[] points;
    private Maze maze;

    public GraphToMazeSolutionConverter(Maze maze, Integer[] directions, int xstart, int ystart, int s) {
        this.maze = maze;
        distanceFromStartToFinish = s;
        this.directions = directions;
        this.xStart = xstart;
        this.yStart = ystart;
    }

    public void getPoints(MazeCellType pathCell) {
        points = new PointXY[distanceFromStartToFinish + 1];
        points[0] = new PointXY(xStart, yStart);
        int direction = 0;
        int vertexCount = directions.length - 1;
        int x = xStart;
        int y = yStart;
        for (int i = 1; i < distanceFromStartToFinish + 1; i++) {
            if (maze.getVertexNumberArrayCell(x, y) >= 0) {
                direction = directions[vertexCount];
                points[i] = new PointXY(x, y, direction);
                switch (direction) {
                    case 1:
                        x--;
                        break;
                    case 2:
                        x++;
                        break;
                    case 3:
                        y--;
                        break;
                    case 4:
                        y++;
                        break;
                    default:
                        break;
                }
                vertexCount--;
            } else {
                if (maze.getVertexNumberArrayCell(x - 1, y) >= -1 && direction != 2) {
                    direction = 1;
                    points[i] = new PointXY(x, y, direction);
                    x--;
                } else if (maze.getVertexNumberArrayCell(x + 1, y) >= -1 && direction != 1) {
                    direction = 2;
                    points[i] = new PointXY(x, y, direction);
                    x++;
                } else if (maze.getVertexNumberArrayCell(x, y + 1) >= -1 && direction != 3) {
                    direction = 4;
                    points[i] = new PointXY(x, y, direction);
                    y++;
                } else if (maze.getVertexNumberArrayCell(x, y - 1) >= -1 && direction != 4) {
                    direction = 3;
                    points[i] = new PointXY(x, y, direction);
                    y--;
                }
            }
        }
        convertSolutionToMaze(pathCell);

    }

    private void convertSolutionToMaze(MazeCellType pathCell) {

        for (int i = 1; i < distanceFromStartToFinish; i++) {
            maze.getMaze()[points[i].getX()][points[i].getY()] = new MazeCell(points[i].getX(), points[i].getY(), pathCell);
        }

    }

}
