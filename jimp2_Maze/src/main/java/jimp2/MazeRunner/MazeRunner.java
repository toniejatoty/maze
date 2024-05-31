/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package jimp2.MazeRunner;

import javax.swing.SwingUtilities;

/**
 *
 * @author piotr-sosnowski
 */
public class MazeRunner {

    GUI GUI;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI());
    }
}
