package jimp2.MazeRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Load {

    private static char[][] maze;
    private static int rows = 0;
    private static int columns = 0;
    private static int amountP;
    private static int amountK;
    
    public static char[][] loadFromTxt(File file) {
        rows = 0;
        columns = 0;
        amountP = 0;
        amountK = 0; // To check if File with maze is correct
        String line;
        try {
            BufferedReader mazeloadsize = new BufferedReader(new FileReader(file));
            while ((line = mazeloadsize.readLine()) != null) // this loop to get maze size
            {
                if (rows == 0) {
                    columns = line.length();
                }
                if (columns != line.length()) {
                    throw new IOException("Not adequate amount of column in row " + rows);
                }
                rows++;
            }
            mazeloadsize.close();
            BufferedReader mazeload = new BufferedReader(new FileReader(file));
            maze = new char[rows][columns];
            rows = 0;
            while ((line = mazeload.readLine()) != null) // this loop to load maze to char[][]
            {
                maze[rows] = line.toCharArray();
                rows++;
            }
            for(int i = 0; i < rows; i++)
                for(int j = 0; j < columns; j++) {
                    if(maze[i][j] == 'P')
                        amountP++;
                    if(maze[i][j] == 'K')
                        amountK++;
                }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return maze;
    }
    
    public static void findPathInMaze() throws IOException {
        try {
            amountP=0;
            amountK=0;
            Integer[][] Maze = new Integer[rows][columns];
            for (int i = 0; i < rows; i++) // this loop to convert char maze to int maze -1=space -2=X -3=P -4=K  
            {
                for (int j = 0; j < columns; j++) // and in addition to check if File with maze is correct
                {
                    if (maze[i][j] == ' ') {
                        Maze[i][j] = -1;
                    } else if (maze[i][j] == 'X') {
                        Maze[i][j] = -2;
                    } else if (maze[i][j] == 'P') {
                        Maze[i][j] = -3;
                    } else if (maze[i][j] == 'K') {
                        Maze[i][j] = -4;
                    } else {
                        throw new IOException("In File are existing a sight, which shouldn't be here this sight is:" + maze[i][j] + "in position(" + i + "," + j + ")");
                    }
                    if (maze[i][j] == 'P') {
                        amountP++;
                        Maze[i][j] = 0;
                        if (amountP >= 2) {
                            throw new IOException("File have more than one P letter");
                        }
                        if (i != 0 && j != 0 && i != rows - 1 && j != columns - 1) {
                            throw new IOException("In file letter P is in place it shount't be in position(" + i + "," + j + ")");
                        }
                    }
                    if (maze[i][j] == 'K') {
                        Maze[i][j] = 1;
                        amountK++;
                        if (amountK >= 2) {
                            throw new IOException("File have more than one K letter");
                        }
                        if (i != 0 && j != 0 && i != rows - 1 && j != columns - 1) {
                            throw new IOException("In file letter K is in place it shount't be in position(" + i + "," + j + ")");
                        }
                    }
                }
            }
            if (amountP == 0) {
                throw new IOException("File don't have P letter");
            }
            if (amountK == 0) {
                throw new IOException("File don't have K letter");
            }
            int isVertex = 0; // it will check if maze[i][j] is verticle it will check if above / under/ next to is space if isvertex>=3 maze[i][j] is vertex
            int vertexnumber = 2; // it will represent number of vertex to differ vertexs starting 2 becouse 0 is P 1 is K
            for (int i = 1; i < rows - 1; i++) {
                for (int j = 1; j < columns - 1; j++) {
                    if (Maze[i][j] == -1) {
                        if (Maze[i - 1][j] >= -1 || Maze[i - 1][j] == 0 || Maze[i - 1][j] == 1) {
                            isVertex++;
                        }
                        if (Maze[i + 1][j] >= -1 || Maze[i + 1][j] == 0 || Maze[i + 1][j] == 1) {
                            isVertex++;
                        }
                        if (Maze[i][j - 1] >= -1 || Maze[i][j - 1] == 0 || Maze[i][j - 1] == 1) {
                            isVertex++;
                        }
                        if (Maze[i][j + 1] >= -1 || Maze[i][j + 1] == 0 || Maze[i][j + 1] == 1) {
                            isVertex++;
                        }
                        if (isVertex >= 3) {
                            Maze[i][j] = vertexnumber;
                            vertexnumber++;
                        }
                    }
                    isVertex = 0;
                }
            }
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    System.out.print(Maze[i][j] + " ");
                }
                System.out.println();

            }
            Vertex.setMaze(Maze);
            Graph.setMaze(Maze);
            
            Graph mazegraph = new Graph(vertexnumber +1 );
            for (int i = 0; i < vertexnumber + 1; i++) {
                mazegraph.add(i); 
            }
            int from = 0; //1 up 2 down 3 left 4 right to identyfy what position is vertex source
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    if (Maze[i][j] >= 0) {
                        if (i - 1 >= 0 && Maze[i - 1][j] >= -1) {
                            from = 2;
                            mazegraph.makeconnection(i-1, j, from, i, j);
                        }
                        if (i + 1 <= rows - 1 && Maze[i + 1][j] >= -1) {
                            from = 1;
                            //mazegraph[Maze[i][j]].makegraph(Maze, from, i + 1, j, Maze[i][j], i, j);
                            mazegraph.makeconnection(i+1, j, from, i, j);
                        }
                        if (j + 1 <= columns - 1 && Maze[i][j + 1] >= -1) {
                            from = 3;
                            //mazegraph[Maze[i][j]].makegraph(Maze, from, i, j + 1, Maze[i][j], i, j);
                            mazegraph.makeconnection(i, j+1, from, i, j);
                        }
                        if (j - 1 >= 0 && Maze[i][j - 1] >= -1) {
                            from = 4;
                            //mazegraph[Maze[i][j]].makegraph(Maze, from, i, j - 1, Maze[i][j], i, j);
                            mazegraph.makeconnection(i, j-1, from, i, j);
                        }
                    }

                }
            }
                    System.out.println(mazegraph);
        
            int start = 0; // this will determine the number of vertex where maze is starting 

            solver Sollution = new solver(mazegraph, start);
            Sollution.solve();
            GraphToMazeSolutionConverter interpret = new GraphToMazeSolutionConverter(Maze, Sollution.getDirections(), mazegraph.getVertex(start).getEdge(0).getX(), mazegraph.getVertex(start).getEdge(0).getY(), Sollution.getDistanceFromStartToFinish());
            interpret.getPoints();
            System.out.println(GraphToMazeSolutionConverter.getMaze()[1][1]);
            maze = GraphToMazeSolutionConverter.getMaze();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void loadFromBin(File file) {
        maze = null;
        rows=0;
        columns=0;
        amountP = 0;
        amountK = 0;
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
            amountP = 1;
            inputStream.read(ExitXByte);
            inputStream.read(ExitYByte);
            amountK = 1;
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
    
    public static int getAmountP() {
        return amountP;
    }
    
    public static int getAmountK() {
        return amountK;
    }
    
    public static void setAmountP(int amountP) {
        Load.amountP=amountP;
    }
    public static void setAmountK(int amountK) {
        Load.amountK=amountK;
    }
}   
