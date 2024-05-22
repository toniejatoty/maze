/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jimp2.MazeRunner;

/**
 *
 * @author piotr-sosnowski
 */
public class VertexNumber {
        private Integer[][] vertexNumberArray;
        
        public void setVertexNumberArraySize(Maze maze) { 
            vertexNumberArray = new Integer[maze.getRows()][maze.getColumns()];
                    for(int i = 0; i < maze.getRows(); i++) {
            for(int j =0; j < maze.getColumns(); j++) {
                if(maze.isMazeCellStart(i, j))
                    setVertexNumberArrayCell(i, j, 0);
                else if (maze.isMazeCellFinish(i, j))
                    setVertexNumberArrayCell(i, j, 1);
                else if(maze.isMazeCellEmpty(i, j))
                    setVertexNumberArrayCell(i, j, -1);
                else setVertexNumberArrayCell(i,j,-2);
            }
        }
        }
        
        public void setVertexNumberArrayCell(int i, int j, int value) {
        vertexNumberArray[i][j]=value;
    }
    public Integer getVertexNumberArrayCell(int i, int j) {
        return vertexNumberArray[i][j];
    }
}
