package boulderdash.views;

import javax.swing.*;

public class WinLoseView extends JFrame {

    /**
     * Generate the HelpView
     */
    public WinLoseView(String winOrLose) {
        // Initializes the view
        setVisible(true);
        setResizable(false);

        // UI parameters
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(300, 300, 250, 100); // TODO replace with constants

        // App parameters
        setTitle("END OF THE GAME ! ");
        // Creates the view layout
        JTextArea help = new JTextArea();
        help.setEditable(false);
        if ("win".equals(winOrLose))
            help.setText("YOU WIN THE GAME :-)");
        else
            help.setText("YOU LOSE THE GAME :-( TRY AGAIN!");

        add(help);
    }

}
