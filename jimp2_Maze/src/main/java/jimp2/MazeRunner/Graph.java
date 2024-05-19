package jimp2.MazeRunner;

public class Graph {
private Vertex [] graph;
private int size;
private  Integer [][]Maze;
public void setMaze(Integer[][]newMaze)
{
    Maze=newMaze;
}
public Graph(int size) {
    this.size = size;
    this.graph = new Vertex[size];
}
public void add(int i)
{
graph[i]=new Vertex();
}
public void makeconnection(int i, int j, int from, int xSource, int ySource)
{       
    int vertexnumber = Maze[xSource][ySource];
    graph[vertexnumber].makegraph(from, i, j, xSource, ySource, Maze);
}
public int getsize()
{
    return this.size;
}
@Override
public String toString() {
    String result="";
    for(int i=0; i<size; i++)
    {
        result+="\n" + (graph[i]);
    }
    return result;
}
public Vertex getVertex(int i)
{
    return graph[i];
}
}
