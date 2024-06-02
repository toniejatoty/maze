package jimp2.MazeRunner;

import java.io.File;
import java.io.IOException;

public class TerminalObserver implements Observer {
    private Maze maze;
    Subject subject = Subject.getInstance();

    public void Terminalsolve(File file) {
        importMazeFromFile(file);
    }

    private String getFileExtension(File file) {
        String filename = file.getName();
        int lastIndexOf = filename.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // without extension
        }
        return filename.substring(lastIndexOf);
    }

    private void importMazeFromFile(File inputFile) {
        if (getFileExtension(inputFile).compareTo(".txt") == 0) {
            maze = new Maze();
            Load loader = new Load(maze);
            loader.loadFromTxt(inputFile);
            if (maze.getAmountK() == 1 && maze.getAmountP() == 1) {
                subject.notifyObservers("Found shortest path beetwen start and finish");
                try {
                    loader.findPathInMaze();
                } catch (IOException ex) {
                    subject.notifyObservers(ex.getMessage());
                }
            }
            subject.notifyObservers(
                    "Imported a maze with " + maze.getColumns() + " columns and " + maze.getRows() + " rows.");
        } else if (getFileExtension(inputFile).compareTo(".bin") == 0) { // Binary import
            Load loader = new Load(maze);
            loader.loadFromBin(inputFile);
            if (maze.getAmountK() == 1 && maze.getAmountP() == 1) {
                subject.notifyObservers("Found shortest path beetwen start and finish");
                try {
                    loader.findPathInMaze();
                } catch (IOException ex) {
                    subject.notifyObservers(ex.getMessage());
                }
            }
            subject.notifyObservers(
                    "Imported a maze with " + maze.getColumns() + " columns and " + maze.getRows() + " rows.");
        } else {
            subject.notifyObservers("File with wrong extension");
        }
    }

    @Override
    public void update(String message) {
        System.out.println(message);
    }
}