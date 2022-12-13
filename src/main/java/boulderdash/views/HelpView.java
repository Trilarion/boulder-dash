package boulderdash.views;

import javax.swing.*;
import java.awt.*;

/**
 * A help information view.
 */
public class HelpView extends JFrame {

    /**
     * Generate the HelpView
     */
    public HelpView() {

        // initializes the view
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(100, 100, 560, 150);
        setTitle("Boulder Dash | Help");

        // layouts the view
        JTextArea help = new JTextArea();
        help.setEditable(false);
        help.setText("To use the editor, you should :\n"
                + "- Select an item on the list,\n"
                + "- Move the RED cursor with the arrows\n"
                + "- To place the selected item on the field, use the space bar.\n"
                + "If you want to lock the placement of the things, hit shift once (to unlock, re-hit shift)\n"
                + "Then, you can save & load your creation on game.\n"
                + "You have to place at least 3 diamonds and 1 rockford!\n"
                + "Have fun"); // TODO replace with string from resources (i18n)

        add(help, BorderLayout.CENTER);
    }

}
