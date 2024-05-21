package jimp2.MazeRunner;


public class GraphToMazeSolutionConverter {
private Integer [] directions;
private int xstart, ystart;
private int distanceFromStartToFinish;
private PointXY [] points;
Maze maze;
public GraphToMazeSolutionConverter(Maze maze, Integer[] directions, int xstart, int ystart, int s) {
    this.maze=maze;
    distanceFromStartToFinish=s;
    this.directions = directions;
    this.xstart = xstart;
    this.ystart = ystart;
}

public void getPoints()
{
points = new PointXY[distanceFromStartToFinish+1];
points[0]=new PointXY(xstart, ystart);
int direction=0;
int vertexcount=directions.length-1;
int x=xstart;
int y=ystart;
for(int i=1; i<distanceFromStartToFinish+1; i++)
{
//if(maze.isWallCell(x,y))
if(maze.isMazeCellStart(x, y))
{
    direction = directions[vertexcount];
    if(direction==1) {points[i] = new PointXY(x-1, y); x--;}
    if(direction==2) {points[i] = new PointXY(x+1, y); x++;}
    if(direction==3) {points[i] = new PointXY(x, y-1); y--;}
    if(direction==4) {points[i] = new PointXY(x, y+1); y++;}
    vertexcount--;
}
else{
    if(maze.isMazeCellEmpty(x-1, y) && direction!=2){ direction = 1; points[i] = new PointXY(x-1, y); x--;}    
    else if(maze.isMazeCellEmpty(x-1, y) && direction!=1 ){ direction = 2; points[i] = new PointXY(x+1, y); x++;}
    else if(maze.isMazeCellEmpty(x-1, y) && direction!= 3){ direction = 4; points[i] = new PointXY(x, y+1); y++;}
    else if(maze.isMazeCellEmpty(x-1, y) && direction!= 4){ direction = 3; points[i] = new PointXY(x, y-1); y--;}
}
}
convertMazeIntChar();

}
private void convertMazeIntChar()
{
    
//maze = new char [MazeInt.length][MazeInt[0].length];

for(int i=1; i<distanceFromStartToFinish; i++)
{
    System.out.println("TEST1");
    maze.setMazeCellPath(points[i].getX(), points[i].getY());
}
//maze[points[0].getX()][points[0].getY()] = 'P';
//maze[points[distanceFromStartToFinish].getX()][points[distanceFromStartToFinish].getY()] = 'K';

}

}

