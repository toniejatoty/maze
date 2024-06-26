package jimp2.MazeRunner;

public class Graph {

    private final Vertex[] graph;
    private final int size;
    private final Maze maze;

    public Graph(int size, Maze maze) {
        this.size = size;
        this.graph = new Vertex[size];
        this.maze = maze;
    }

    public void add(int i) {
        graph[i] = new Vertex(maze);
    }

    public void makeConnection(int i, int j, int from, int xSource, int ySource) {
        int vertexnumber = maze.getVertexNumberArrayCell(xSource, ySource);
        graph[vertexnumber].makegraph(from, i, j, xSource, ySource);
    }

    public int getSize() {
        return this.size;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < size; i++) {
            result += "\n" + (graph[i]);
        }
        return result;
    }

    public Vertex getVertex(int i) {
        return graph[i];
    }
}
