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

    private MazeCell[][] maze;
    private int rows;
    private int columns;
    private int amountP;            // To check if File with maze is correct
    private int amountK;            // To check if File with maze is correct
    //private VertexNumber vertexNumber;
    private Integer[][] vertexNumberArray;
    private int startRow;
    private int startColumn;
    private int finishRow;
    private int finishColumn;

    public Maze() {
        rows = 0;
        columns = 0;
        amountP = 0;
        amountK = 0;
        //vertexNumber = new VertexNumber();
    }

    public MazeCell getMazeCell(int i, int j) {
        return maze[i][j];
    }

    public void setMazeCell(int i, int j, MazeCellType mazeCellType) {
        maze[i][j] = new MazeCell(i, j, mazeCellType);
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setMazeSize(int rows, int columns) {
        maze = new MazeCell[rows][columns];
    }

    public int getAmountP() {
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

    public MazeCell[][] getMaze() {
        return maze;
    }

    public void setVertexNumberArraySize() {
        vertexNumberArray = new Integer[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                setVertexNumberArrayCell(i, j, getMazeCell(i, j).getCellType().getNumber());
            }
        }
    }

    public void setVertexNumberArrayCell(int i, int j, int value) {
        vertexNumberArray[i][j] = value;
    }

    public Integer getVertexNumberArrayCell(int i, int j) {
        return vertexNumberArray[i][j];
    }
    
    public void setStartRow(int startRow){
        this.startRow=startRow;
    }
    
    public void setStartColumn(int startColumn){
        this.startColumn=startColumn;
    }
    
    public int getStartRow() {
        return startRow;
    }
    
    public int getStartColumn() {
        return startColumn;
    }
    
    public void setFinishRow(int finishRow) {
        this.finishRow=finishRow;
    }
    
    public void setFinishColumn(int finishColumn) {
        this.finishColumn=finishColumn;
    }
    
    public int getFinishRow() {
        return finishRow;
    }
    
    public int getFinishColumn() {
        return finishColumn;
    }
    
}
