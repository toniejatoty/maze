package jimp2.MazeRunner;

import java.io.File;

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
        if (useGUI && useTerminal) {
            TerminalObserver terminalObserver = new TerminalObserver();
            observer.addObserver(terminalObserver);
            GUI Mazegui = new GUI();
            observer.addObserver(Mazegui);
         Mazegui.solveFromObserver(new File(filePath));
        }
        else{
        if (useTerminal) {
            TerminalObserver terminalObserver = new TerminalObserver();
            observer.addObserver(terminalObserver);
            terminalObserver.Terminalsolve(new File(filePath));
        }
        if (useGUI) {
            GUI Mazegui = new GUI();
            observer.addObserver(Mazegui);
        }
    }
        if (args.length == 0) {
            GUI Mazegui = new GUI();
            observer.addObserver(Mazegui);
        }
        // SwingUtilities.invokeLater(() -> new GUI());
    }
}
