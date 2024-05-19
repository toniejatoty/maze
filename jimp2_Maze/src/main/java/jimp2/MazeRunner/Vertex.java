package jimp2.MazeRunner;

import java.util.ArrayList;

public class Vertex {
private ArrayList<Edge> mazegraph = new ArrayList<>();
private Integer [][] Maze;
public void setMaze(Integer[][]newMaze)
{
    Maze = newMaze;
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
public void makegraph(int from, int i, int j, int xSource, int ySource, Integer [][]Maze)
{   Integer source = Maze[xSource][ySource];
    if(Maze[i][j]>=0){ mazegraph.add(new Edge(Maze[i][j], source, 1, xSource, ySource,from)); return;}
    int weight=1;
    int howmanyspace=2;
    while(howmanyspace==2){
    if(Maze[i-1][j]>=0 && Maze[i-1][j]!=source){mazegraph.add(new Edge(Maze[i-1][j], source, weight+1, xSource, ySource,2)); return;}    
    if(Maze[i+1][j]>=0 && Maze[i+1][j]!=source){mazegraph.add(new Edge(Maze[i+1][j], source, weight+1, xSource, ySource,1)); return; }
    if(Maze[i][j+1]>=0 && Maze[i][j+1]!=source){mazegraph.add(new Edge(Maze[i][j+1], source, weight+1, xSource, ySource,3));return;}
    if(Maze[i][j-1]>=0 && Maze[i][j-1]!=source){mazegraph.add(new Edge(Maze[i][j-1], source, weight+1, xSource, ySource,4)); return;}
    howmanyspace=0;
    if(Maze[i-1][j]!=-2 ){ howmanyspace++;   }
    if(Maze[i+1][j]!=-2 ){ howmanyspace++;   }
    if(Maze[i][j+1]!=-2 ){ howmanyspace++;   }
    if(Maze[i][j-1]!=-2 ){ howmanyspace++;   }
    if(howmanyspace==2)
    {
    if(Maze[i-1][j]!=-2 && from!=1 ){ i--; from=2; }
   else if(Maze[i+1][j]!=-2 && from!= 2){   i++;  from = 1;}
    else if(Maze[i][j+1]!=-2 && from !=4){   j++; from = 3;}
    else if(Maze[i][j-1]!=-2 && from!=3){   j--; from =4;}
    }
    if(i==0 || j==0 || i==Maze.length || j==Maze[0].length)return;
weight++;    
}
}
}
