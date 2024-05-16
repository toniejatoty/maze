package jimp2.MazeRunner;

public class Solver {
    private Graph mazegraph;
    private Integer[] directions;
    private int start;
    private boolean[] visited; // is vertex visited?
    private Integer[] distanceFromStart; // like distanceFromStart[50] will have value beetween start and vertex
                                         // represented by 50 number
    private Integer[] previousVertex; // to know connetions

    public Solver(Graph mazegraph, int start) {
        this.mazegraph = mazegraph;
        this.start = start;
        this.distanceFromStart = new Integer[mazegraph.getsize()];
        previousVertex = new Integer[mazegraph.getsize()];
        for (int i = 0; i < distanceFromStart.length; i++) {
            distanceFromStart[i] = Integer.MAX_VALUE; // nie pamiętam czy Chaber przy C nie czepiał się tego
                                                      // Integer.MAX_VALUE
        }
        visited = new boolean[mazegraph.getsize()];
    }

    public Integer[] getDirections() {
        return directions;
    }

    public void solve() {

        int numberMinVertex;
        previousVertex[start] = -1;
        distanceFromStart[start] = 0;
        for (int i = 0; i < mazegraph.getsize() - 1; i++) {
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
        directions = new Integer[mazegraph.getsize()]; // it will containts direction 1-up 2-down 3-left 4-right i
                                                       // should head in maze from finish to start

        int vertexNumberIterator = 1;
        int i = 0; // to chronology assign values to way

        while (previousVertex[vertexNumberIterator] != -1) {
            directions[i] = mazegraph.getVertex(vertexNumberIterator).getEdge(mazegraph.getVertex(vertexNumberIterator)
                    .findVertexConnection(previousVertex[vertexNumberIterator])).getDirection();
            i++;
            vertexNumberIterator = previousVertex[vertexNumberIterator];
        }
        int directionsize = 0;
        for (int p = 0; p < mazegraph.getsize(); p++)
            if (directions[p] != null) {
                directionsize++;
            }
        Integer[] tmp = new Integer[directionsize];
        System.arraycopy(directions, 0, tmp, 0, directionsize);
        directions = tmp;
        
    }


    private int findMinVertex() {
        int minvalue = Integer.MAX_VALUE;
        int vertexWithMinvalue = -1;
        for (int i = 0; i < mazegraph.getsize(); i++) {
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
