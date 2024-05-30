package jimp2.MazeRunner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Save {
    private File savefile;
    private Maze maze;

    public Save(Maze maze, File savefile) {
        this.maze = maze;
        this.savefile = savefile;
    }

    public void exportpath() {
        try {
            FileWriter fileWriter = new FileWriter(savefile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < maze.getRows(); i++) {
                for (int j = 0; j < maze.getColumns(); j++) {
                    char character = maze.getMazeCell(i, j).getCellType().getCharacter();
                    bufferedWriter.write(character);
                }
                bufferedWriter.write("\n");
            }
            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println("File Error: " + ex.getMessage());
        }
    }
    public void exportpathmaze() {
        try {
            FileWriter fileWriter = new FileWriter(savefile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < maze.getRows(); i++) {
                for (int j = 0; j < maze.getColumns(); j++) {
                    char character = maze.getMazeCell(i, j).getCellType().getCharacter();
                    if(character=='O')character=' '; 
                    bufferedWriter.write(character);
                }
                bufferedWriter.write("\n");
            }
            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println("File Error: " + ex.getMessage());
        }
    }
    
}
