package jimp2.jimp2_maze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class load_and_save {

    public static void load_from_txt(File file)
    { char [][]maze;
        int rows =0, columns = 0;
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
    while((line = mazeload.readLine())!=null)
    { maze[rows] = line.toCharArray();
rows++;
    }
    int[][] Maze = new int[rows][columns];
    for(int i=0; i<rows; i++)
    for(int j=0; j<columns; j++)
    {
        if(maze[i][j]==' ') Maze[i][j] = -1;
        if(maze[i][j]=='X') Maze[i][j] = -2;
        if(maze[i][j]=='P') Maze[i][j] = -3;
        if(maze[i][j]=='K') Maze[i][j] = -4; // to convert char maze to int maze -1=space -2=X -3=P -4=K 
    }

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
}
        catch(IOException e )
        {
            System.out.println(e.getMessage());
        }
    }
}
