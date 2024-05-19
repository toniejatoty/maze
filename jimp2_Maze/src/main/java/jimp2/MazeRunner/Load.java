package jimp2.MazeRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class Load {

    private char[][] maze;
    private int rows = 0;
    private int columns = 0;
    private int amountP;
    private int amountK;
    
    
    public void loadFromTxt(File file) {
        rows = 0;
        columns = 0;
        amountP = 0;
        amountK = 0; // To check if File with maze is correct
        String line;
        try(BufferedReader mazeloadsize = new BufferedReader(new FileReader(file));) {
            
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
        }catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
        try(BufferedReader mazeload = new BufferedReader(new FileReader(file));)
        {   
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
    
    }
    
    public void findPathInMaze() throws IOException {
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
                    }
                    if (maze[i][j] == 'K') {
                        Maze[i][j] = 1;
                        amountK++;
                        if (amountK >= 2) {
                            throw new IOException("File have more than one K letter");
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
            Vertex vertex = new Vertex();
            Graph mazegraph = new Graph(vertexnumber +1 );
            
            vertex.setMaze(Maze);
            mazegraph.setMaze(Maze);
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
        
            int start = 0; // this will determine the number of vertex where maze is starting 
            Solver Solution = new Solver(mazegraph, start);
            Solution.solve();
            GraphToMazeSolutionConverter interpret = new GraphToMazeSolutionConverter(Maze, Solution.getDirections(), mazegraph.getVertex(start).getEdge(0).getX(), mazegraph.getVertex(start).getEdge(0).getY(), Solution.getDistanceFromStartToFinish());
            interpret.getPoints();
            maze = interpret.getMaze();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void loadFromBin(File file)
{
    byte[] FileID = new byte[4];
    byte[] Escape = new byte[1];
    byte[] Columns = new byte[2];
    byte[] Lines = new byte[2];
    byte[] EntryX = new byte[2];
    byte[] EntryY = new byte[2];
    byte[] ExitX = new byte[2];
    byte[] ExitY = new byte[2];
    byte[] Reserved = new byte [12];
    byte[] Counter = new byte[4];
    byte[] SolutionOffset = new byte[4];
    byte[] Separator = new byte[1];
    byte[] Wall = new byte[1];
    byte[] Path = new byte[1];

    try(FileInputStream input = new FileInputStream(file);)
    {
        input.read(FileID);
        input.read(Escape);
        input.read(Columns);
        input.read(Lines);
        input.read(EntryX);
        input.read(EntryY);
        input.read(ExitX);
        input.read(ExitY);
        input.read(Reserved);
        input.read(Counter);
        input.read(SolutionOffset);
        input.read(Separator);
        input.read(Wall);
        input.read(Path); 

        String hexString="";
        int x,y;
        for(int i=Lines.length-1; i>=0; i--){hexString+=String.format("%02X",Lines[i]); }x=Integer.parseInt(hexString,16 ); hexString=""; 
        for(int i=Columns.length-1; i>=0; i--){hexString+=String.format("%02X",Columns[i]); }y=Integer.parseInt(hexString,16 ); hexString="";
        maze=new char[x][y];
rows = maze.length;
columns=maze[0].length;
    int countlines=0;
    int countcolumns=0;
    byte [] count=new byte[1]; String Scount;
    byte [] value=new byte[1]; 
    byte [] separatorloop = new byte[1]; 
    do {
        input.read(separatorloop);		
		input.read(value);
        input.read(count);
        Scount = String.format("%02X", count[0]);
        for (int i = 0; i <= Integer.parseInt(Scount,16); i++) {
			if(countcolumns==maze[0].length){countcolumns=0; countlines++; if(countlines==maze.length) break;} 
			maze[countlines][countcolumns]=(char)value[0];
            countcolumns++;
		}
	}while (countlines!=(maze.length));
    for(int i=EntryX.length-1; i>=0; i--){hexString+=String.format("%02X",EntryX[i]); }x=Integer.parseInt(hexString,16 ); hexString="";
    for(int i=EntryY.length-1; i>=0; i--){hexString+=String.format("%02X",EntryY[i]); }y=Integer.parseInt(hexString,16 ); hexString="";
    maze[y-1][x-1]='P';
    for(int i=ExitX.length-1; i>=0; i--){hexString+=String.format("%02X",ExitX[i]); }x=Integer.parseInt(hexString,16 ); hexString="";
    for(int i=ExitY.length-1; i>=0; i--){hexString+=String.format("%02X",ExitY[i]); }y=Integer.parseInt(hexString,16 ); hexString="";
    maze[y-1][x-1]='K';

amountK=1;
amountP=1;

}catch(IOException ex)
    {
        System.out.println("ERROR WITH BINARY FILE");
    }

}

    /*public static void loadFromBin(File file) {
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
    */
    public char[][] getMaze() {
        return maze;
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getColumns() {
        return columns;
    }
    
    public int getAmountP() {
        return amountP;
    }
    
    public int getAmountK() {
        return amountK;
    }
    
    public void setAmountP(int amountP) {
        this.amountP=amountP;
    }
    public void setAmountK(int amountK) {
        this.amountK=amountK;
    }
}   
