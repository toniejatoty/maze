package jimp2.MazeRunner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Save {

    private final File savefile;
    private final Maze maze;

    public Save(Maze maze, File savefile) {
        this.maze = maze;
        this.savefile = savefile;
    }

    public void exportMazeWithPath() {
        try {
            FileWriter fileWriter = new FileWriter(savefile);
            try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                for (int i = 0; i < maze.getRows(); i++) {
                    for (int j = 0; j < maze.getColumns(); j++) {
                        char character = maze.getMazeCell(i, j).getCellType().getCharacter();
                        bufferedWriter.write(character);
                    }
                    bufferedWriter.write("\n");
                }
            }
        } catch (IOException ex) {
            System.err.println("Error with exporting maze with file: " + ex.getMessage());
        }
    }

    public void exportMazeWithoutPath() {
        try {
            FileWriter fileWriter = new FileWriter(savefile);
            try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                for (int i = 0; i < maze.getRows(); i++) {
                    for (int j = 0; j < maze.getColumns(); j++) {
                        char character = maze.getMazeCell(i, j).getCellType().getCharacter();
                        if (character == 'O') {
                            character = ' ';
                        }
                        bufferedWriter.write(character);
                    }
                    bufferedWriter.write("\n");
                }
            }
        } catch (IOException ex) {
            System.err.println("Error with exporting maze without path: " + ex.getMessage());
        }
    }

}
