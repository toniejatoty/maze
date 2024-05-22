package jimp2.MazeRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class Load {

    Maze maze;

    public Load(Maze maze) {
        this.maze = maze;
        maze.setRows(0);
        maze.setColumns(0);
        maze.setAmountP(0);
        maze.setAmountK(0);
    }

    public void loadFromTxt(File file) {
        String line;
        try (BufferedReader mazeloadsize = new BufferedReader(new FileReader(file));) {

            while ((line = mazeloadsize.readLine()) != null) // this loop to get maze size
            {
                if (maze.getRows() == 0) {
                    maze.setColumns(line.length());
                }
                if (maze.getColumns() != line.length()) {
                    throw new IOException("Not adequate amount of column in row " + maze.getRows());
                }
                maze.setRows(maze.getRows() + 1);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        try (BufferedReader mazeload = new BufferedReader(new FileReader(file));) {
            maze.setMazeSize(maze.getRows(), maze.getColumns());
            maze.setRows(0);
            while ((line = mazeload.readLine()) != null) // this loop to load maze to char[][]
            {
                maze.setMazeRow(maze.getRows(), line.toCharArray());
                maze.setRows(maze.getRows() + 1);
            }
            for (int i = 0; i < maze.getRows(); i++) {
                for (int j = 0; j < maze.getColumns(); j++) {
                    if (maze.isMazeCellStart(i, j)) {
                        maze.increaseAmountP();
                    }
                    if (maze.isMazeCellFinish(i, j)) {
                        maze.increaseAmountK();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public void findPathInMaze() throws IOException {

        int isVertex = 0; // it will check if maze[i][j] is verticle it will check if above / under/ next to is space if isvertex>=3 maze[i][j] is vertex
        int vertexnumber = 2; // it will represent number of vertex to differ vertexs starting 2 becouse 0 is P 1 is K

        maze.getVertexNumber().setVertexNumberArraySize(maze);
        for (int i = 1; i < maze.getRows() - 1; i++) {
            for (int j = 1; j < maze.getColumns() - 1; j++) {
                if (maze.isMazeCellEmpty(i, j)) {
                    if (maze.getVertexNumber().getVertexNumberArrayCell(i - 1, j) >= -1 || maze.isMazeCellStart(i - 1, j) || maze.isMazeCellFinish(i - 1, j)) {
                        isVertex++;
                    }
                    if (maze.getVertexNumber().getVertexNumberArrayCell(i + 1, j) >= -1 || maze.isMazeCellStart(i + 1, j) || maze.isMazeCellFinish(i + 1, j)) {
                        isVertex++;
                    }
                    if (maze.getVertexNumber().getVertexNumberArrayCell(i, j - 1) >= -1 || maze.isMazeCellStart(i, j - 1) || maze.isMazeCellFinish(i, j - 1)) {
                        isVertex++;
                    }
                    if (maze.getVertexNumber().getVertexNumberArrayCell(i, j + 1) >= -1 || maze.isMazeCellStart(i, j + 1) || maze.isMazeCellFinish(i, j + 1)) {
                        isVertex++;
                    }
                    if (isVertex >= 3) {
                        maze.getVertexNumber().setVertexNumberArrayCell(i, j, vertexnumber);
                        //maze.setMazeCell(i, j, (char)('X'+vertexnumber));
                        vertexnumber++;
                    }
                }

                isVertex = 0;
            }
        }
        Vertex vertex = new Vertex(maze);
        Graph mazegraph = new Graph(vertexnumber + 1, maze);

        for (int i = 0; i < vertexnumber + 1; i++) {
            mazegraph.add(i);
        }
        int from = 0; //1 up 2 down 3 left 4 right to identyfy what position is vertex source
        for (int i = 0; i < maze.getRows(); i++) {
            for (int j = 0; j < maze.getColumns(); j++) {
                if (maze.getVertexNumber().getVertexNumberArrayCell(i, j) >= 0) {
                    if (i - 1 >= 0 && (maze.getVertexNumber().getVertexNumberArrayCell(i - 1, j) >= -1)) {
                        from = 2;
                        mazegraph.makeconnection(i - 1, j, from, i, j);
                    }
                    if (i + 1 <= maze.getRows() - 1 && (maze.getVertexNumber().getVertexNumberArrayCell(i + 1, j) >= -1)) {
                        from = 1;
                        //mazegraph[Maze[i][j]].makegraph(Maze, from, i + 1, j, Maze[i][j], i, j);
                        mazegraph.makeconnection(i + 1, j, from, i, j);
                    }
                    if (j + 1 <= maze.getColumns() - 1 && (maze.getVertexNumber().getVertexNumberArrayCell(i, j + 1) >= -1)) {
                        from = 3;
                        //mazegraph[Maze[i][j]].makegraph(Maze, from, i, j + 1, Maze[i][j], i, j);
                        mazegraph.makeconnection(i, j + 1, from, i, j);
                    }
                    if (j - 1 >= 0 && (maze.getVertexNumber().getVertexNumberArrayCell(i, j - 1) >= -1)) {
                        from = 4;
                        //mazegraph[Maze[i][j]].makegraph(Maze, from, i, j - 1, Maze[i][j], i, j);
                        mazegraph.makeconnection(i, j - 1, from, i, j);
                    }
                }

            }
        }
        int start = 0; // this will determine the number of vertex where maze is starting 
        Solver Solution = new Solver(mazegraph, start);

        Solution.solve();
        GraphToMazeSolutionConverter interpret = new GraphToMazeSolutionConverter(maze, Solution.getDirections(), mazegraph.getVertex(start).getEdge(0).getX(), mazegraph.getVertex(start).getEdge(0).getY(), Solution.getDistanceFromStartToFinish());
        interpret.getPoints();

    }

    public void loadFromBin(File file) {
        byte[] FileID = new byte[4];
        byte[] Escape = new byte[1];
        byte[] Columns = new byte[2];
        byte[] Lines = new byte[2];
        byte[] EntryX = new byte[2];
        byte[] EntryY = new byte[2];
        byte[] ExitX = new byte[2];
        byte[] ExitY = new byte[2];
        byte[] Reserved = new byte[12];
        byte[] Counter = new byte[4];
        byte[] SolutionOffset = new byte[4];
        byte[] Separator = new byte[1];
        byte[] Wall = new byte[1];
        byte[] Path = new byte[1];

        try (FileInputStream input = new FileInputStream(file);) {
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

            String hexString = "";
            int x, y;
            for (int i = Lines.length - 1; i >= 0; i--) {
                hexString += String.format("%02X", Lines[i]);
            }
            x = Integer.parseInt(hexString, 16);
            hexString = "";
            for (int i = Columns.length - 1; i >= 0; i--) {
                hexString += String.format("%02X", Columns[i]);
            }
            y = Integer.parseInt(hexString, 16);
            hexString = "";
            maze.setMazeSize(x, y);
            maze.setRows(maze.getMaze().length);
            maze.setColumns(maze.getMaze()[0].length);
            int countlines = 0;
            int countcolumns = 0;
            byte[] count = new byte[1];
            String Scount;
            byte[] value = new byte[1];
            byte[] separatorloop = new byte[1];
            do {
                input.read(separatorloop);
                input.read(value);
                input.read(count);
                Scount = String.format("%02X", count[0]);
                for (int i = 0; i <= Integer.parseInt(Scount, 16); i++) {
                    if (countcolumns == maze.getMaze()[0].length) {
                        countcolumns = 0;
                        countlines++;
                        if (countlines == maze.getMaze().length) {
                            break;
                        }
                    }
                    maze.setMazeCell(countlines, countcolumns, (char) value[0]);
                    countcolumns++;
                }
            } while (countlines != (maze.getMaze().length));
            for (int i = EntryX.length - 1; i >= 0; i--) {
                hexString += String.format("%02X", EntryX[i]);
            }
            x = Integer.parseInt(hexString, 16);
            hexString = "";
            for (int i = EntryY.length - 1; i >= 0; i--) {
                hexString += String.format("%02X", EntryY[i]);
            }
            y = Integer.parseInt(hexString, 16);
            hexString = "";
            maze.isMazeCellStart(y - 1, x - 1);
            for (int i = ExitX.length - 1; i >= 0; i--) {
                hexString += String.format("%02X", ExitX[i]);
            }
            x = Integer.parseInt(hexString, 16);
            hexString = "";
            for (int i = ExitY.length - 1; i >= 0; i--) {
                hexString += String.format("%02X", ExitY[i]);
            }
            y = Integer.parseInt(hexString, 16);
            hexString = "";
            maze.isMazeCellFinish(y - 1, x - 1);

            maze.setAmountK(1);
            maze.setAmountP(1);

        } catch (IOException ex) {
            System.out.println("ERROR WITH BINARY FILE");
        }

    }

}
