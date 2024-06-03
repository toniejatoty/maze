package jimp2.MazeRunner;

import java.util.ArrayList;

public class Vertex {

    private final ArrayList<Edge> mazegraph = new ArrayList<>();
    private final Maze maze;

    public Vertex(Maze maze) {
        this.maze = maze;
    }

    public int findVertexConnection(int x) {
        for (int i = 0; i < mazegraph.size(); i++) {
            if (mazegraph.get(i).getDestination() == x) {
                return i;
            }
        }
        return -1;
    }

    public Edge getEdge(int i) {
        return mazegraph.get(i);
    }

    public int getsize() {
        return mazegraph.size();
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < mazegraph.size(); i++) {
            result += "" + mazegraph.get(i).toString();
        }
        return result;
    }

    protected class Edge {

        private final int destination;
        private final int source;
        private final int weight;
        private final int x;
        private final int y;
        private final int direction;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getDestination() {
            return destination;
        }

        public int getDirection() {
            return direction;
        }

        public int getWeight() {
            return weight;
        }

        public Edge(int destination, int source, int weight, int x, int y, int direction) {
            this.destination = destination;
            this.source = source;
            this.weight = weight;
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        @Override
        public String toString() {
            return "source (" + x + "," + y + ") " + +source + " destination: " + destination + " weight: " + weight + " " + "direction" + " " + direction + " ";
        }
    }

    public void makegraph(int from, int i, int j, int xSource, int ySource) {
        Integer source = maze.getVertexNumberArrayCell(xSource, ySource);
        if (maze.getVertexNumberArrayCell(i, j) >= 0) {
            mazegraph.add(new Edge(maze.getVertexNumberArrayCell(i, j), source, 1, xSource, ySource, from));
            return;
        }
        int weight = 1;
        int howmanyspace = 2;
        while (howmanyspace == 2) {
            if (maze.getVertexNumberArrayCell(i - 1, j) >= 0 && (!maze.getVertexNumberArrayCell(i - 1, j).equals(source))) {
                mazegraph.add(new Edge(maze.getVertexNumberArrayCell(i - 1, j), source, weight + 1, xSource, ySource, 2));
                return;
            }
            if (maze.getVertexNumberArrayCell(i + 1, j) >= 0 && (!maze.getVertexNumberArrayCell(i + 1, j).equals(source))) {
                mazegraph.add(new Edge(maze.getVertexNumberArrayCell(i + 1, j), source, weight + 1, xSource, ySource, 1));
                return;
            }
            if (maze.getVertexNumberArrayCell(i, j + 1) >= 0 && (!maze.getVertexNumberArrayCell(i, j + 1).equals(source))) {
                mazegraph.add(new Edge(maze.getVertexNumberArrayCell(i, j + 1), source, weight + 1, xSource, ySource, 3));
                return;
            }
            if (maze.getVertexNumberArrayCell(i, j - 1) >= 0 && (!maze.getVertexNumberArrayCell(i, j - 1).equals(source))) {
                mazegraph.add(new Edge(maze.getVertexNumberArrayCell(i, j - 1), source, weight + 1, xSource, ySource, 4));
                return;
            }
            howmanyspace = 0;
            if (!maze.getMazeCell(i - 1, j).getCellType().isWallCell()) {
                howmanyspace++;
            }
            if (!maze.getMazeCell(i + 1, j).getCellType().isWallCell()) {
                howmanyspace++;
            }
            if (!maze.getMazeCell(i, j + 1).getCellType().isWallCell()) {
                howmanyspace++;
            }
            if (!maze.getMazeCell(i, j - 1).getCellType().isWallCell()) {
                howmanyspace++;
            }
            if (howmanyspace == 2) {
                if ((!maze.getMazeCell(i - 1, j).getCellType().isWallCell()) && from != 1) {
                    i--;
                    from = 2;
                } else if ((!maze.getMazeCell(i + 1, j).getCellType().isWallCell()) && from != 2) {
                    i++;
                    from = 1;
                } else if ((!maze.getMazeCell(i, j + 1).getCellType().isWallCell()) && from != 4) {
                    j++;
                    from = 3;
                } else if ((!maze.getMazeCell(i, j - 1).getCellType().isWallCell()) && from != 3) {
                    j--;
                    from = 4;
                }
            }
            if (i == 0 || j == 0 || i == maze.getMaze().length || j == maze.getMaze()[0].length) {
                return;
            }
            weight++;
        }
    }
}
