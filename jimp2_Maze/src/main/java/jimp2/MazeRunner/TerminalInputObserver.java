package jimp2.MazeRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class TerminalInputObserver {
    private PropertyChangeSupport support;

    public TerminalInputObserver() {
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void startObserving() {
        Thread inputThread = new Thread(new InputHandler());
        inputThread.start();
    }

    private class InputHandler implements Runnable {
        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                while (true) {
                    System.out.println("Maze file name");
                    String input = reader.readLine();
                    File inputFile = new File(input);
                    if (inputFile.exists() && inputFile.isFile()) {
                        support.firePropertyChange("fileInput", null, inputFile);
                    } else {
                        System.out.println("File doesn't exist");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
