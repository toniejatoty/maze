package jimp2.MazeRunner;

public class Solver {

    private final Graph mazegraph;
    private Integer[] directions;
    private final int start;
    private final boolean[] visited;                      // is vertex visited?
    private final Integer[] distanceFromStart;            // like distanceFromStart[50] will have value beetween start and vertex represented by 50 number
    private final Integer[] previousVertex;               // to know connections

    public Solver(Graph mazegraph, int start) {
        this.mazegraph = mazegraph;
        this.start = start;
        this.distanceFromStart = new Integer[mazegraph.getSize()];
        previousVertex = new Integer[mazegraph.getSize()];
        for (int i = 0; i < distanceFromStart.length; i++) {
            distanceFromStart[i] = Integer.MAX_VALUE;
        }
        visited = new boolean[mazegraph.getSize()];
    }

    public Integer[] getDirections() {
        return directions;
    }

    public void solve() {

        int numberMinVertex;
        previousVertex[start] = -1;
        distanceFromStart[start] = 0;
        for (int i = 0; i < mazegraph.getSize() - 1; i++) {
            numberMinVertex = findMinVertex();
            visited[numberMinVertex] = true;
            int numberconnection = mazegraph.getVertex(numberMinVertex).getsize();
            int destinationingraph;
            int distancefromPtovertex;
            int weightbeetwentwovertex;
            for (int j = 0; j < numberconnection; j++) {
                destinationingraph = mazegraph.getVertex(numberMinVertex).getEdge(j).getDestination();
                distancefromPtovertex = distanceFromStart[numberMinVertex];
                weightbeetwentwovertex = mazegraph.getVertex(numberMinVertex).getEdge(j).getWeight();
                if (!visited[destinationingraph] && distancefromPtovertex != Integer.MAX_VALUE
                        && distancefromPtovertex + weightbeetwentwovertex < distanceFromStart[destinationingraph]) {
                    distanceFromStart[destinationingraph] = distancefromPtovertex + weightbeetwentwovertex;
                    previousVertex[destinationingraph] = numberMinVertex;
                }
            }
        }

        directions = new Integer[mazegraph.getSize()];              // it will containts direction 1-up 2-down 3-left 4-right

        int vertexNumberIterator = 1;
        int i = 0;                                                  // to chronology assign values to way

        while (previousVertex[vertexNumberIterator] != -1) {
            directions[i] = mazegraph.getVertex(vertexNumberIterator).getEdge(mazegraph.getVertex(vertexNumberIterator)
                    .findVertexConnection(previousVertex[vertexNumberIterator])).getDirection();
            i++;
            vertexNumberIterator = previousVertex[vertexNumberIterator];
        }
        int directionsize = 0;
        for (int p = 0; p < mazegraph.getSize(); p++) {
            if (directions[p] != null) {
                directionsize++;
            }
        }
        Integer[] tmp = new Integer[directionsize];
        System.arraycopy(directions, 0, tmp, 0, directionsize);
        directions = tmp;
    }

    private int findMinVertex() {
        int minvalue = Integer.MAX_VALUE;
        int vertexWithMinvalue = -1;
        for (int i = 0; i < mazegraph.getSize(); i++) {
            if (distanceFromStart[i] < minvalue && visited[i] == false) {
                minvalue = distanceFromStart[i];
                vertexWithMinvalue = i;
            }

        }
        return vertexWithMinvalue;
    }

    public int getDistanceFromStartToFinish() {
        return distanceFromStart[1];
    }
}
