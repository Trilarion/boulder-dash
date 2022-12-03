package boulderdash;

import boulderdash.controllers.NavigationBetweenViewController;

import javax.swing.*;

/**
 * Spawns the game.
 */
public class Game {
    /**
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(NavigationBetweenViewController::new);
    }
}
