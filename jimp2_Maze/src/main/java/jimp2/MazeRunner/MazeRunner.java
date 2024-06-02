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
        GUI MazeGUI;
        if (useGUI && useTerminal) {
            TerminalObserver terminalObserver = new TerminalObserver();
            observer.addObserver(terminalObserver);
            MazeGUI = new GUI();
            observer.addObserver(MazeGUI);
         MazeGUI.solveFromObserver(new File(filePath));
        }
        else{
        if (useTerminal) {
            TerminalObserver terminalObserver = new TerminalObserver();
            observer.addObserver(terminalObserver);
            terminalObserver.Terminalsolve(new File(filePath));
        }
        if (useGUI) {
            MazeGUI = new GUI();
            observer.addObserver(MazeGUI);
        }
    }
        if (args.length == 0) {
            MazeGUI = new GUI();
            observer.addObserver(MazeGUI);
        }
        //SwingUtilities.invokeLater(() -> new GUI());
    }
}
