package jimp2.MazeRunner;

import java.io.File;
import javax.swing.SwingUtilities;

/**
 *
 * @author piotr-sosnowski
 */
public class MazeRunner {

    GUI GUI;

    public static void main(String[] args) {
        Maze maze = new Maze();
        Load loader = new Load(maze);
        Save save = new Save(maze, new File("Solved Maze.txt"));
        MazeDrawer mazePaint = new MazeDrawer(maze.getRows(), maze.getColumns(), maze);
        Observer subject = Observer.getInstance();
        boolean useGUI = false;
        boolean useTerminal = false;
        String filePath = null;
        Observer observer = Observer.getInstance();
        for (String arg : args) {
            if (arg.equals("--gui")) {
                useGUI = true;
            }
            if (arg.startsWith("--file=")) {
                filePath = arg.substring(7);
                useTerminal = true;
            }
        }
        final String finalFilePath = filePath;
        if (useGUI && useTerminal) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    TerminalObserver terminalObserver = new TerminalObserver();
                    observer.addObserver(terminalObserver);
                    GUI MazeGUI = new GUI(loader, maze, save, mazePaint, subject);
                    observer.addObserver(MazeGUI);
                    MazeGUI.solveFromObserver(new File(finalFilePath));
                }
            });
        } else {
            if (useTerminal) {
                TerminalObserver terminalObserver = new TerminalObserver();
                observer.addObserver(terminalObserver);
                terminalObserver.Terminalsolve(new File(filePath));
            }
            if (useGUI || args.length == 0) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        GUI MazeGUI = new GUI(loader, maze, save, mazePaint, subject);
                        observer.addObserver(MazeGUI);
                    }
                });
            }
        }
    }
}
