package boulderdash;

import boulderdash.controllers.NavigationBetweenViewController;

import javax.swing.*;

/**
 * Game
 * <p>
 * Spawns the game.
 */
public class Game {
    /**
     * Class constructor
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(NavigationBetweenViewController::new);
    }
}
