package jimp2.MazeRunner;


public class GraphToMazeSolutionConverter {
private Integer [][]Maze;
private static char [][]maze;
private Integer [] directions;
private int xstart, ystart;
private int distanceFromStartToFinish;
private PointXY [] points;
public GraphToMazeSolutionConverter(Integer[][] maze, Integer[] directions, int xstart, int ystart, int s) {
    Maze = maze;
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
if(Maze[x][y] >=0)
{
    direction = directions[vertexcount];
    if(direction==1) {points[i] = new PointXY(x-1, y); x--;}
    if(direction==2) {points[i] = new PointXY(x+1, y); x++;}
    if(direction==3) {points[i] = new PointXY(x, y-1); y--;}
    if(direction==4) {points[i] = new PointXY(x, y+1); y++;}
    vertexcount--;
}
else{
    if(Maze[x-1][y]>=-1 && direction!=2){ direction = 1; points[i] = new PointXY(x-1, y); x--;}    
    else if(Maze[x+1][y]>=-1 && direction!=1 ){ direction = 2; points[i] = new PointXY(x+1, y); x++;}
    else if(Maze[x][y+1]>=-1 && direction!= 3){ direction = 4; points[i] = new PointXY(x, y+1); y++;}
    else if(Maze[x][y-1]>=-1 && direction!= 4){ direction = 3; points[i] = new PointXY(x, y-1); y--;}
}
}
convertMazeIntChar();
for(int i=0; i<distanceFromStartToFinish+1; i++)
System.out.println(points[i]);
}
private void convertMazeIntChar()
{
maze = new char [Maze.length][Maze[0].length];
for(int i=0; i<Maze.length; i++)
for(int j=0; j<Maze[0].length; j++)
{
if(Maze[i][j]==-2)maze[i][j]='X';
else maze[i][j]=' ';
}
for(int i=1; i<distanceFromStartToFinish; i++)
{
    maze[points[i].getX()][points[i].getY()]='O';
}
maze[points[0].getX()][points[0].getY()] = 'P';
maze[points[distanceFromStartToFinish].getX()][points[distanceFromStartToFinish].getY()] = 'K';
for(int i=0; i<maze.length; i++){
for(int j=0; j<maze[0].length; j++)
{
    System.out.print(maze[i][j]);
}
System.out.println("");
}
}
public static char[][] getMaze() {
    return maze;
}
}
