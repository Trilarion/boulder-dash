package fr.enssat.BoulderDash.views;

import javax.swing.*;

public class WinLoseView extends JFrame {

    private final String winOrLose;

    /**
     * Generate the HelpView
     */
    public WinLoseView(String winOrLose) {
        this.winOrLose = winOrLose;
        this.initializeView();
        this.createLayout();
    }

    /**
     * Initializes the view
     */
    private void initializeView() {
        this.setVisible(true);
        this.setResizable(false);

        // UI parameters
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        this.setBounds(300, 300, 250, 100);

        // App parameters
        this.setTitle("END OF THE GAME ! ");
    }

    /**
     * Creates the view layout
     */
    private void createLayout() {
        JTextArea help = new JTextArea();
        help.setEditable(false);
        if (winOrLose.equals("win"))
            help.setText("YOU WIN THE GAME :-)");
        else
            help.setText("YOU LOSE THE GAME :-( TRY AGAIN!");

        this.add(help);
    }
}
