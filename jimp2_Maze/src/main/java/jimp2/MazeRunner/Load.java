package jimp2.MazeRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class Load {

    private final Maze maze;
    private final MazeCellType emptyCell;
    private final MazeCellType wallCell;
    private final MazeCellType startCell;
    private final MazeCellType finishCell;
    private final MazeCellType pathCell;

    public Load(Maze maze) {
        this.maze = maze;
        maze.setRows(0);
        maze.setColumns(0);
        maze.setAmountP(0);
        maze.setAmountK(0);
        emptyCell = EmptyCell.getInstance();
        wallCell = WallCell.getInstance();
        startCell = StartCell.getInstance();
        finishCell = FinishCell.getInstance();
        pathCell = PathCell.getInstance();
    }

    public void loadFromTxt(File file) throws IOException {
        String line;
        BufferedReader mazeLoadSize = new BufferedReader(new FileReader(file));

        while ((line = mazeLoadSize.readLine()) != null) // this loop to get maze size
        {
            if (maze.getRows() == 0) {
                maze.setColumns(line.length());
            }
            if (maze.getColumns() != line.length()) {
                throw new IOException("Not adequate amount of column in row " + maze.getRows());
            }
            maze.setRows(maze.getRows() + 1);
        }
        BufferedReader mazeload = new BufferedReader(new FileReader(file));
        maze.setMazeSize(maze.getRows(), maze.getColumns());
        int lineNumber = 0;
        while ((line = mazeload.readLine()) != null) {
            for (int columnNumber = 0; columnNumber < maze.getColumns(); columnNumber++) {
                if (line.charAt(columnNumber) == 'P') {
                    maze.setMazeCell(lineNumber, columnNumber, startCell);
                    maze.setStartColumn(columnNumber);
                    maze.setStartRow(lineNumber);
                }
                if (line.charAt(columnNumber) == 'K') {
                    maze.setMazeCell(lineNumber, columnNumber, finishCell);
                    maze.setFinishColumn(columnNumber);
                    maze.setFinishRow(lineNumber);
                }
                if (line.charAt(columnNumber) == 'X') {
                    maze.setMazeCell(lineNumber, columnNumber, wallCell);
                }
                if (line.charAt(columnNumber) == ' ') {
                    maze.setMazeCell(lineNumber, columnNumber, emptyCell);
                }
                maze.getMazeCell(lineNumber, columnNumber).getCellType().increaseStartOrFinishAmount(maze);
            }
            lineNumber++;
        }
    }

    public void findPathInMaze() throws IOException {

        clearMaze();
        int isVertex = 0;               // it will check if maze[i][j] is verticle it will check if above / under/ next to is space if isvertex>=3 maze[i][j] is vertex
        int vertexnumber = 2;           // it will represent number of vertex to differ vertexs starting 2 becouse 0 is P 1 is K

        maze.setVertexNumberArraySize();
        for (int i = 1; i < maze.getRows() - 1; i++) {
            for (int j = 1; j < maze.getColumns() - 1; j++) {
                if (maze.getMazeCell(i, j).getCellType() == emptyCell) {
                    if (maze.getVertexNumberArrayCell(i - 1, j) >= -1 || (maze.getMazeCell(i - 1, j).getCellType() == startCell) || (maze.getMazeCell(i - 1, j).getCellType() == finishCell)) {
                        isVertex++;
                    }
                    if (maze.getVertexNumberArrayCell(i + 1, j) >= -1 || (maze.getMazeCell(i + 1, j).getCellType() == startCell) || (maze.getMazeCell(i + 1, j).getCellType() == finishCell)) {
                        isVertex++;
                    }
                    if (maze.getVertexNumberArrayCell(i, j - 1) >= -1 || (maze.getMazeCell(i, j - 1).getCellType() == startCell) || (maze.getMazeCell(i, j - 1).getCellType() == finishCell)) {
                        isVertex++;
                    }
                    if (maze.getVertexNumberArrayCell(i, j + 1) >= -1 || (maze.getMazeCell(i, j + 1).getCellType() == startCell) || (maze.getMazeCell(i, j + 1).getCellType() == finishCell)) {
                        isVertex++;
                    }
                    if (isVertex >= 3) {
                        maze.setVertexNumberArrayCell(i, j, vertexnumber);
                        vertexnumber++;
                    }
                }
                isVertex = 0;
            }
        }

        Graph mazeGraph = new Graph(vertexnumber + 1, maze);

        for (int i = 0; i < vertexnumber + 1; i++) {
            mazeGraph.add(i);
        }
        int from = 0;                                           //1 up, 2 down, 3 left, 4 right to identyfy what position is vertex source
        for (int i = 0; i < maze.getRows(); i++) {
            for (int j = 0; j < maze.getColumns(); j++) {
                if (maze.getVertexNumberArrayCell(i, j) >= 0) {
                    if (i - 1 >= 0 && (maze.getVertexNumberArrayCell(i - 1, j) >= -1)) {
                        from = 2;
                        mazeGraph.makeConnection(i - 1, j, from, i, j);
                    }
                    if (i + 1 <= maze.getRows() - 1 && (maze.getVertexNumberArrayCell(i + 1, j) >= -1)) {
                        from = 1;
                        mazeGraph.makeConnection(i + 1, j, from, i, j);
                    }
                    if (j + 1 <= maze.getColumns() - 1 && (maze.getVertexNumberArrayCell(i, j + 1) >= -1)) {
                        from = 3;
                        mazeGraph.makeConnection(i, j + 1, from, i, j);
                    }
                    if (j - 1 >= 0 && (maze.getVertexNumberArrayCell(i, j - 1) >= -1)) {
                        from = 4;
                        mazeGraph.makeConnection(i, j - 1, from, i, j);
                    }
                }

            }
        }
        int start = 0;                                                  // this will determine the number of vertex where maze is starting 
        Solver solution = new Solver(mazeGraph, start);

        solution.solve();
        GraphToMazeSolutionConverter interpret = new GraphToMazeSolutionConverter(maze, solution.getDirections(), mazeGraph.getVertex(start).getEdge(0).getX(), mazeGraph.getVertex(start).getEdge(0).getY(), solution.getDistanceFromStartToFinish());
        interpret.getPoints(pathCell);

    }

    public void loadFromBin(File file) throws IOException {
        byte[] fileID = new byte[4];
        byte[] escape = new byte[1];
        byte[] columns = new byte[2];
        byte[] lines = new byte[2];
        byte[] entryX = new byte[2];
        byte[] entryY = new byte[2];
        byte[] exitX = new byte[2];
        byte[] exitY = new byte[2];
        byte[] reserved = new byte[12];
        byte[] counter = new byte[4];
        byte[] solutionOffset = new byte[4];
        byte[] separator = new byte[1];
        byte[] wall = new byte[1];
        byte[] path = new byte[1];

        try (FileInputStream input = new FileInputStream(file);) {
            input.read(fileID);
            input.read(escape);
            input.read(columns);
            input.read(lines);
            input.read(entryX);
            input.read(entryY);
            input.read(exitX);
            input.read(exitY);
            input.read(reserved);
            input.read(counter);
            input.read(solutionOffset);
            input.read(separator);
            input.read(wall);
            input.read(path);

            String hexString = "";
            int x, y;
            for (int i = lines.length - 1; i >= 0; i--) {
                hexString += String.format("%02X", lines[i]);
            }
            x = Integer.parseInt(hexString, 16);
            hexString = "";
            for (int i = columns.length - 1; i >= 0; i--) {
                hexString += String.format("%02X", columns[i]);
            }
            y = Integer.parseInt(hexString, 16);
            hexString = "";
            maze.setMazeSize(x, y);
            maze.setRows(maze.getMaze().length);
            maze.setColumns(maze.getMaze()[0].length);
            int countLines = 0;
            int countColumns = 0;
            byte[] count = new byte[1];
            String sCount;
            byte[] value = new byte[1];
            byte[] separatorloop = new byte[1];
            do {
                input.read(separatorloop);
                input.read(value);
                input.read(count);
                sCount = String.format("%02X", count[0]);
                for (int i = 0; i <= Integer.parseInt(sCount, 16); i++) {
                    if (countColumns == maze.getMaze()[0].length) {
                        countColumns = 0;
                        countLines++;
                        if (countLines == maze.getMaze().length) {
                            break;
                        }
                    }
                    if ((char) value[0] == 'X') {
                        maze.getMaze()[countLines][countColumns] = new MazeCell(wallCell);
                    }
                    if ((char) value[0] == ' ') {
                        maze.getMaze()[countLines][countColumns] = new MazeCell(emptyCell);
                    }
                    if ((char) value[0] == 'P') {
                        maze.getMaze()[countLines][countColumns] = new MazeCell(startCell);
                    }
                    if ((char) value[0] == 'K') {
                        maze.getMaze()[countLines][countColumns] = new MazeCell(finishCell);
                    }
                    countColumns++;
                }
            } while (countLines != (maze.getMaze().length));
            for (int i = entryX.length - 1; i >= 0; i--) {
                hexString += String.format("%02X", entryX[i]);
            }
            x = Integer.parseInt(hexString, 16);
            hexString = "";
            for (int i = entryY.length - 1; i >= 0; i--) {
                hexString += String.format("%02X", entryY[i]);
            }
            y = Integer.parseInt(hexString, 16);
            hexString = "";
            maze.setMazeCell(y - 1, x - 1, startCell);
            maze.setStartRow(y - 1);
            maze.setStartColumn(x - 1);
            for (int i = exitX.length - 1; i >= 0; i--) {
                hexString += String.format("%02X", exitX[i]);
            }
            x = Integer.parseInt(hexString, 16);
            hexString = "";
            for (int i = exitY.length - 1; i >= 0; i--) {
                hexString += String.format("%02X", exitY[i]);
            }
            y = Integer.parseInt(hexString, 16);
            hexString = "";
            maze.setMazeCell(y - 1, x - 1, finishCell);
            maze.setFinishRow(y - 1);
            maze.setFinishColumn(x - 1);

            maze.setAmountK(1);
            maze.setAmountP(1);

        } catch (IOException ex) {
            System.err.println("Error with binary file import");
        }

    }

    public void setStart(int r, int c, boolean isStart) {
        maze.setMazeCell(r, c, startCell);
        if (isStart) {
            maze.setMazeCell(maze.getStartRow(), maze.getStartColumn(), wallCell);
        }
        maze.setStartRow(r);
        maze.setStartColumn(c);
    }

    public void setFinish(int r, int c, boolean isFinish) {
        maze.setMazeCell(r, c, finishCell);
        if (isFinish) {
            maze.setMazeCell(maze.getFinishRow(), maze.getFinishColumn(), wallCell);
        }
        maze.setFinishRow(r);
        maze.setFinishColumn(c);
    }

    public void clearMaze() {
        for (int i = 0; i < maze.getRows(); i++) {
            for (int j = 0; j < maze.getColumns(); j++) {
                if (maze.getMazeCell(i, j).getCellType() == pathCell) {
                    maze.setMazeCell(i, j, emptyCell);
                }
            }
        }
    }
    
    public void resetMaze() {
        for (int i =0; i < maze.getRows(); i++) {
            for (int j = 0; j < maze.getColumns(); j++) {
                maze.setMazeCell(i, j, emptyCell);
            }
        }
        maze.setRows(0);
        maze.setColumns(0);
        maze.setAmountK(0);
        maze.setAmountP(0);
    }

}
