package boulderdash.views;

import javax.swing.*;
import java.awt.*;

public class HelpView extends JFrame {

    /**
     * Generate the HelpView
     */
    public HelpView() {
        initializeView();
        createLayout();
    }

    /**
     * Initializes the view
     */
    private void initializeView() {
        setVisible(true);
        setResizable(false);

        // UI parameters
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(100, 100, 560, 150);

        // App parameters
        setTitle("Boulder Dash | Help");
    }

    /**
     * Creates the view layout
     */
    private void createLayout() {
        JTextArea help = new JTextArea();
        help.setEditable(false);
        help.setText("To use the editor, you should :\n"
                + "- Select an item on the list,\n"
                + "- Move the RED cursur with the arrows\n"
                + "- To place the selected item on the field, use SPACEBAR.\n"
                + "If you want to lock the placement of the things, hit shift once (to unlock, rehit shift)\n"
                + "Then, you can save & load your creation on game.\n"
                + "You have to place at least 3 diamonds and 1 rockford!\n"
                + "Have fun ;-)");

        add(help, BorderLayout.CENTER);
    }
}
