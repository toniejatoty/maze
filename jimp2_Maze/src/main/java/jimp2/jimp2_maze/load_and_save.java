package jimp2.jimp2_maze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class load_and_save {

    public static void load_from_txt(File file)
    { char [][]maze;
        int rows =0, columns = 0;
        int amountP=0,amountK=0; // To check if File with maze is correct
        String line;
        try{
    BufferedReader mazeloadsize = new BufferedReader(new FileReader(file));
    while((line=mazeloadsize.readLine())!=null) // this loop to get maze size
    {
        if(rows ==0) columns=line.length();
        if(columns!=line.length()) throw new IOException("Not adequate amount of column in row " + rows);
        rows++;
    }
    mazeloadsize.close();
    BufferedReader mazeload= new BufferedReader(new FileReader(file));
    maze = new char [rows][columns];
    rows =0;
    while((line = mazeload.readLine())!=null) // this loop to load maze to char[][]
    { maze[rows] = line.toCharArray();
rows++;
    }
    int[][] Maze = new int[rows][columns];
    for(int i=0; i<rows; i++)       // this loop to convert char maze to int maze -1=space -2=X -3=P -4=K  
    for(int j=0; j<columns; j++)    // and in addition to check if File with maze is correct
    {   
        if(maze[i][j]==' ') Maze[i][j] = -1;
        else if(maze[i][j]=='X') Maze[i][j] = -2;
        else if(maze[i][j]=='P') Maze[i][j] = -3;
        else if(maze[i][j]=='K') Maze[i][j] = -4; 
        else throw new IOException("In File are existing a sight, which shouldn't be here this sight is:"+ maze[i][j] +"in position("+i+","+j+")");
        if(maze[i][j]=='P'){
            amountP++;
            if(amountP>=2) throw new IOException("File have more than one P letter");
            if(i!=0 && j!=0 && i!=rows-1 && j!=columns-1) throw new IOException("In file letter P is in place it shount't be in position("+i+","+j+")");
        }
        if(maze[i][j]=='K'){
            amountK++;
            if(amountK>=2) throw new IOException("File have more than one K letter");
            if(i!=0 && j!=0 && i!=rows-1 && j!=columns-1) throw new IOException("In file letter K is in place it shount't be in position("+i+","+j+")");
        }
    }
if(amountP==0) throw new IOException("File don't have P letter");
if(amountK==0) throw new IOException("File don't have K letter");
    int isVertex=0; // it will check if maze[i][j] is verticle it will check if above / under/ next to is space if isvertex>=3 maze[i][j] is vertex
    int vertexnumber = 0; // it will represent number of vertex to differ vertexs
    for(int i=1; i<rows-1; i++)
    {
        for(int j=1; j<columns-1; j++)
        {
            if(Maze[i][j]==-1){
                if(Maze[i-1][j]==-1||Maze[i-1][j]==-3||Maze[i-1][j]==-4) isVertex++;
                if(Maze[i+1][j]==-1||Maze[i+1][j]==-3||Maze[i+1][j]==-4) isVertex++;
                if(Maze[i][j-1]==-1||Maze[i][j-1]==-3||Maze[i][j-1]==-4) isVertex++;
                if(Maze[i][j+1]==-1||Maze[i][j+1]==-3||Maze[i][j+1]==-4) isVertex++;
                if(isVertex>=3){ Maze[i][j] = vertexnumber; vertexnumber++;}
            }isVertex=0;
        }
    }
    for(int i=0; i<rows; i++){
    for(int j=0; j<columns; j++)
    System.out.print(Maze[i][j]+" ");
System.out.println();
}
Vertex [] mazegraph = new Vertex [vertexnumber+1];
for (int i=0; i<vertexnumber+1; i++) mazegraph[i] = new Vertex();
int from =0; //1 up 2 down 3 left 4 right to identyfy what position is vertex source
for(int i=1; i<rows-1; i++)
{
    for(int j=1; j<columns-1; j++)
    {
        if(Maze[i][j]>=0 )
        {
            if(Maze[i-1][j]==-1)
            {
                from =2;
                mazegraph[Maze[i][j]].makegraph( Maze, from, i-1, j, Maze[i][j]);
            }
            if(Maze[i+1][j]==-1)
            {
                from=1;
                mazegraph[Maze[i][j]].makegraph( Maze, from, i+1, j, Maze[i][j]);
            }
            if(Maze[i][j+1]==-1)
            {
                from =3;
                mazegraph[Maze[i][j]].makegraph( Maze, from, i, j+1, Maze[i][j]);
            }
            if(Maze[i][j-1]==-1)
            {
                from =4;
                mazegraph[Maze[i][j]].makegraph( Maze, from, i, j-1, Maze[i][j]);
            }
        }
    
    }
}
for( int i=0; i<mazegraph.length; i++)
System.out.println(mazegraph[i]);
int start=0, finish=mazegraph.length-1; // this will determine the number of vertex where maze is starting and finishing

solver Sollution = new solver(mazegraph, start, finish); 
Sollution.solve();
}
        catch(IOException e )
        {
            System.out.println(e.getMessage());
        }
    }
}
