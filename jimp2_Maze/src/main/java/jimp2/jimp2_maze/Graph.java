package jimp2.jimp2_maze;

import java.util.ArrayList;

public class Graph {
private ArrayList<Edge> mazegraph = new ArrayList<>();
@Override
public String toString() {
    String result = new String("");
    for(int i=0; i<mazegraph.size(); i++)
    {
    result += ""+mazegraph.get(i).toString();
    }
return result;
}
private class Edge
{
    int destination, source, weight;
    public Edge(int destination, int source, int weight) {
        this.destination = destination;
        this.source = source;
        this.weight = weight;
    }
    @Override
    public String toString() {
        return "source " +source + " destination: " + destination + " weight: "+ weight;
    }
}
public void makegraph( int [][] Maze, int from, int i, int j, int source)
{
    int weight=1;
    int howmanyspace=2;
    while(howmanyspace==2){
    if(Maze[i-1][j]>=0 && Maze[i-1][j]!=source){mazegraph.add(new Edge(Maze[i-1][j], source, weight)); return;}    
    if(Maze[i+1][j]>=0 && Maze[i+1][j]!=source){mazegraph.add(new Edge(Maze[i+1][j], source, weight)); return; }
    if(Maze[i][j+1]>=0 && Maze[i][j+1]!=source){mazegraph.add(new Edge(Maze[i][j+1], source, weight));return;}
    if(Maze[i][j-1]>=0 && Maze[i][j-1]!=source){mazegraph.add(new Edge(Maze[i][j-1], source, weight)); return;}
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
