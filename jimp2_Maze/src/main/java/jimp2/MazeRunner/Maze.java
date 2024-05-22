package jimp2.MazeRunner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author piotr-sosnowski
 */
public class Maze {
    private char[][] maze;
    private int rows;
    private int columns;
    private int amountP;            // To check if File with maze is correct
    private int amountK;            // To check if File with maze is correct
    private Integer[][] vertexNumberArray;
    
    public Maze() {
        rows = 0;
        columns = 0;
        amountP = 0;
        amountK = 0; 
    }
    
    public boolean isMazeCellEmpty(int i, int j) {
        if (maze[i][j] == ' ')
            return true;
        return false;
    }
    public boolean isMazeCellWall(int i, int j) {
        if (maze[i][j] == 'X')
            return true;
        return false;
    }
    public boolean isMazeCellStart(int i, int j) {
        if (maze[i][j] == 'P')
            return true;
        return false;
    }
    public boolean isMazeCellFinish(int i, int j) {
        if (maze[i][j] == 'K')
            return true;
        return false;
    }
    public void setMazeCellPath(int i, int j) {
        maze[i][j] = 'O';
    }
    
    public char getMazeCell(int i, int j) {
        return maze[i][j];
    }
    
    public boolean isMazeCellStartOrFinish(int i, int j) {
        if(maze[i][j] == 'P' || maze[i][j] == 'K')
            return true;
        return false;
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getColumns() {
        return columns;
    }
    
    public void setRows(int rows) {
        this.rows=rows;
    }
    
    public void setColumns(int columns) {
        this.columns=columns;
    }
    public void setMazeSize(int rows, int columns) {
        maze = new char[rows][columns];
    }
    public void setVertexNumberArraySize(int rows, int columns) {
        vertexNumberArray = new Integer[rows][columns];
        for(int i = 0; i < rows; i++) {
            for(int j =0; j < columns; j++) {
                if(isMazeCellStart(i, j))
                    setVertexNumberArrayCell(i, j, 0);
                else if (isMazeCellFinish(i, j))
                    setVertexNumberArrayCell(i, j, 1);
                else if(isMazeCellEmpty(i, j))
                    setVertexNumberArrayCell(i, j, -1);
                else setVertexNumberArrayCell(i,j,-2);
            }
        }
    }
    public void setMazeRow(int row, char[] line) {
        maze[row] = line;
    }
    public int getAmountP(){
        return amountP;
    }
    public int getAmountK() {
        return amountK;
    }
    public void increaseAmountP() {
        amountP++;
    }
    public void increaseAmountK() {
        amountK++;
    }
    public void setAmountP(int value) {
        amountP = value;
    }
    public void setAmountK(int value) {
        amountK = value;
    }
    public void setMazeCell(int i, int j, char value) {
        maze[i][j]=value;
    }
    public char[][] getMaze() {
        return maze;
    }
    public void setVertexNumberArrayCell(int i, int j, int value) {
        vertexNumberArray[i][j]=value;
    }
    public Integer getVertexNumberArrayCell(int i, int j) {
        return vertexNumberArray[i][j];
    }
 }
