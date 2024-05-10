package jimp2.jimp2_maze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LoadAndSave {

    private static char[][] maze;
    private static int rows = 0;
    private static int columns = 0;
    private static boolean isStart = false;
    private static boolean isFinish = false;
    
    public static void loadFromTxt(File file)
    {
        maze = null;
        rows=0;
        columns=0;
        isStart=false;
        isFinish=false;
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
    for(int i=0; i<rows; i++){
    for(int j=0; j<columns; j++) {
        if(maze[i][j]=='P')
            isStart=true;
        if(maze[i][j]=='K')
            isFinish=true;
    System.out.print(maze[i][j]);
    }
System.out.println();
}
}
        catch(IOException e )
        {
            System.out.println(e.getMessage());
        }
}
    
    public static char[][] getMaze() {
        return maze;
    }
    
    public static int getRows() {
        return rows;
    }
    
    public static int getColumns() {
        return columns;
    }
    
    public static boolean getIsStart() {
        return isStart;
    }
    
    public static boolean getIsFinish() {
        return isFinish;
    }
    
    public static void setIsStart(boolean isStart) {
        LoadAndSave.isStart=isStart;
    }
    public static void setIsFinish(boolean isFinish) {
        LoadAndSave.isFinish=isFinish;
    }
}   
