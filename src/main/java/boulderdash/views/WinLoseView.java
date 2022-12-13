package boulderdash.views;

import javax.swing.*;

public class WinLoseView extends JFrame {

    /**
     * Generate the HelpView
     */
    public WinLoseView(String winOrLose) {
        // initializes the view
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(300, 300, 250, 100); // TODO replace with constants
        setTitle("END OF THE GAME ! ");

        // Creates the view layout
        JTextArea help = new JTextArea();
        help.setEditable(false);
        if ("win".equals(winOrLose))
            help.setText("You win the game!");
        else
            help.setText("You lose the game! Try again!");

        add(help);
    }

}
