package boulderdash.ui;

import boulderdash.views.WinLoseView;

public class UIHelper {
    /**
     * Set the view to inform the user that he won
     */
    public static void displayWin() {
        new WinLoseView("win");
    }

    /**
     * Set the view to inform the user that he is not good at this game
     */
    public static void displayLose() {
        new WinLoseView("lose");
    }
}
