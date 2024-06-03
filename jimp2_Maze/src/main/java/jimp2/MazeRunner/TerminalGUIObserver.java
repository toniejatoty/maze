package jimp2.MazeRunner;

import java.io.File;
import java.io.IOException;

public class TerminalGUIObserver implements ObserverInterface {

    private Maze maze;
    private final Subject subject = Subject.getInstance();

    public void Terminalsolve(File file) {
        importMazeFromFile(file);
    }

    private String getFileExtension(File file) {
        String filename = file.getName();
        int lastIndexOf = filename.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";                                  // without extension
        }
        return filename.substring(lastIndexOf);
    }

    private void importMazeFromFile(File inputFile) {
        if (getFileExtension(inputFile).compareTo(".txt") == 0) {
            maze = new Maze();
            Load loader = new Load(maze);
            try {
                loader.loadFromTxt(inputFile);
            } catch (IOException ex) {
                System.err.println("Error with observer importing txt maze.");
                loader.resetMaze();
            }
            if (maze.getAmountK() == 1 && maze.getAmountP() == 1) {
                subject.notifyAllObservers("Found shortest path beetwen start and finish");
                try {
                    loader.findPathInMaze();
                } catch (IOException ex) {
                    subject.notifyAllObservers(ex.getMessage());
                }
            }
            subject.notifyAllObservers(
                    "Imported a maze with " + maze.getColumns() + " columns and " + maze.getRows() + " rows.");
        } else if (getFileExtension(inputFile).compareTo(".bin") == 0) {                // Binary import
            maze = new Maze();
            Load loader = new Load(maze);
            try {
                loader.loadFromBin(inputFile);
            } catch (IOException ex) {
                System.err.println("Error with observer importing binaty maze.");
            }
            if (maze.getAmountK() == 1 && maze.getAmountP() == 1) {
                subject.notifyAllObservers("Found shortest path beetwen start and finish");
                try {
                    loader.findPathInMaze();
                } catch (IOException ex) {
                    subject.notifyAllObservers(ex.getMessage());
                }
            }
            subject.notifyAllObservers(
                    "Imported a maze with " + maze.getColumns() + " columns and " + maze.getRows() + " rows.");
        } else {
            subject.notifyAllObservers("File with wrong extension");
        }
    }

    @Override
    public void update(String message) {
        System.out.println(message);
    }
}
