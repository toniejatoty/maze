package jimp2.jimp2_maze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

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
    
    public static void loadFromBin(File file) {
        maze = null;
        rows=0;
        columns=0;
        isStart=false;
        isFinish=false;
        byte[] ColumnsByte = new byte[2];
        byte[] RowsByte = new byte[2];
        byte[] EntryXByte = new byte[2];
        byte[] EntryYByte = new byte[2];
        byte[] ExitXByte = new byte[2];
        byte[] ExitYByte = new byte[2];
        try {
            InputStream inputStream = new FileInputStream(file);
            for(int i = 0; i < 5; i++)
                inputStream.read();             //Fileid i Escape
            inputStream.read(ColumnsByte);
            //System.out.println("UWAGA: " + ColumnsByte[0] + " ");
            //System.out.println(ColumnsByte[1]);
            inputStream.read(RowsByte);
            inputStream.read(EntryXByte);
            inputStream.read(EntryYByte);
            isStart = true;
            inputStream.read(ExitXByte);
            inputStream.read(ExitYByte);
            isFinish = true;
            for(int i = 0; i < 23; i++)
                inputStream.read();             //the rest of the header
            
            columns = ByteBuffer.wrap(ColumnsByte).getShort();        //wrap is big-endian by default
            rows = ByteBuffer.wrap(RowsByte).getShort();
            maze = new char[rows][columns];
            
            byte[] valueByte = new byte[1];
            char value;
            byte[] countByte = new byte[1];
            int count;
            int sumColumns = 1 ;
            int sumLines = 0;
            do {
                System.out.println("sumColumns: " + sumColumns + "\nsumLines: " + sumLines);
                count = 0;
                inputStream.read();           //separator
                inputStream.read(valueByte);
                String valueString = new String(valueByte, StandardCharsets.UTF_8);
                value = valueString.charAt(0);                        //value - wall/path
                count = inputStream.read();                                 //count of value character
                System.out.println("columns: " + columns + "\nrows: " + rows + "\ncount: " + count);
                
                
                for(int i = 0; i <= count; i++) {
                    System.out.println("Columns sum inside for: " + sumColumns);
                    if(sumColumns == ByteBuffer.wrap(EntryXByte).getShort() && (sumLines+1) == ByteBuffer.wrap(EntryYByte).getShort()) {
                        maze[sumColumns-1][sumLines] = 'P';
                        sumColumns++;
                        i++;
                    }
                    if(sumColumns == columns) {
                        if(sumColumns == ByteBuffer.wrap(ExitXByte).getShort() && (sumLines+1) == ByteBuffer.wrap(ExitYByte).getShort()) 
                            maze[sumColumns-1][sumLines] = 'K';
                        else
                            maze[sumColumns-1][sumLines] = 'X';
                        sumLines++;
                        sumColumns=1;
                        if(i != count) {
                            for(int j = 0; j <= count-i; j++) {
                                maze[sumColumns-1][sumLines] = value;
                                sumColumns++;
                            }
                        }
                        else
                            sumColumns=1;
                        continue;
                    }
                    maze[sumColumns-1][sumLines] = value;
                    sumColumns++;
                }
            } while (sumLines != rows);
            
            
            //maze[ByteBuffer.wrap(EntryXByte).getInt()][ByteBuffer.wrap(EntryYByte).getInt()] = 'P';
            //maze[ByteBuffer.wrap(ExitXByte).getInt()][ByteBuffer.wrap(ExitYByte).getInt()] = 'K';
            
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
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
