package jimp2.MazeRunner;

import java.util.ArrayList;

public class Vertex {
private ArrayList<Edge> mazegraph = new ArrayList<>();
private Maze maze;
//private Integer [][] Maze;
/*public void setMaze(Integer[][]newMaze)
{
    Maze = newMaze;
}*/
public Vertex(Maze maze) {
    this.maze=maze;
}
public int findVertexConnection(int x)
{ 
    for(int i=0; i<mazegraph.size(); i++)
    {
        if(mazegraph.get(i).getDestination()==x)
        return i;
          }
return -1;
}
public Edge getEdge(int i)
{
    return mazegraph.get(i);
}
public int getsize()
{
    return mazegraph.size();
}
@Override
public String toString() {
    String result = new String("");
    for(int i=0; i<mazegraph.size(); i++)
    {
    result += ""+mazegraph.get(i).toString();
    }
return result;
}
protected class Edge
{
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    private int destination, source, weight, x,y;
    private int direction;
    
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
        this.direction=direction;
    }
    @Override
    public String toString() {
        return "source ("+ x+"," + y + ") " +  +source + " destination: " + destination + " weight: "+ weight+" "+ "direction" + " " + direction + " ";
    }
}
public void makegraph(int from, int i, int j, int xSource, int ySource)
{   Integer source = maze.getMazeCell(xSource, ySource)-'X';
    if(maze.isMazeCellStartOrFinish(i, j)){ mazegraph.add(new Edge(maze.getMazeCell(i, j)-'X', source, 1, xSource, ySource,from)); return;}
    int weight=1;
    int howmanyspace=2;
    while(howmanyspace==2){
    if(maze.isMazeCellStartOrFinish(i-1, j) && (maze.getMazeCell(i-1, j)-'X') != source){mazegraph.add(new Edge(maze.getMazeCell(i-1, j)-'X', source, weight+1, xSource, ySource,2)); return;}    
    if(maze.isMazeCellStartOrFinish(i+1, j) && (maze.getMazeCell(i+1, j)-'X') != source){mazegraph.add(new Edge(maze.getMazeCell(i+1, j)-'X', source, weight+1, xSource, ySource,1)); return; }
    if(maze.isMazeCellStartOrFinish(i, j+1) && (maze.getMazeCell(i, j+1)-'X') != source){mazegraph.add(new Edge(maze.getMazeCell(i, j+1)-'X', source, weight+1, xSource, ySource,3));return;}
    if(maze.isMazeCellStartOrFinish(i, j-1) && (maze.getMazeCell(i, j-1)-'X') != source){mazegraph.add(new Edge(maze.getMazeCell(i, j-2)-'X', source, weight+1, xSource, ySource,4)); return;}
    howmanyspace=0;
    if(!maze.isMazeCellWall(i-1, j)){ howmanyspace++;   }
    if(!maze.isMazeCellWall(i+1, j)){ howmanyspace++;   }
    if(!maze.isMazeCellWall(i, j+1)){ howmanyspace++;   }
    if(!maze.isMazeCellWall(i, j-1)){ howmanyspace++;   }
    if(howmanyspace==2)
    {
    if((!maze.isMazeCellWall(i-1, j)) && from!=1 ){ i--; from=2; }
   else if((!maze.isMazeCellWall(i+1, j)) && from!= 2){   i++;  from = 1;}
    else if((!maze.isMazeCellWall(i, j+1)) && from !=4){   j++; from = 3;}
    else if((!maze.isMazeCellWall(i, j-1)) && from!=3){   j--; from =4;}
    }
    if(i==0 || j==0 || i==maze.getMaze().length || j==maze.getMaze()[0].length)return;
weight++;    
}
}
}
