package boulderdash.views;

import boulderdash.controllers.NavigationBetweenViewController;
import boulderdash.helpers.LevelSelectorHelper;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Menu view
 */
public class MenuView extends JFrame {
    private final MenuLevelSelector menuLevelSelector;

    public MenuView(NavigationBetweenViewController navigationBetweenViewController) {

        // initialize the view
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 200, 100); // TODO replace with constants
        setSize(432, 536);
        setTitle("Boulder Dash | Menu");

        // create the view layout
        LevelSelectorHelper levelSelectorHelper = new LevelSelectorHelper(null);
        menuLevelSelector = levelSelectorHelper.createLevelList();

        JPanel targetPanel = new JPanel();

        MenuImage menuImage = new MenuImage();

        add(menuImage, BorderLayout.CENTER);
        add(targetPanel, BorderLayout.SOUTH);

        @NotNull JPanel actionPanel = new JPanel();
        // add some buttons on the actionPanel
        JButton button2 = new JButton("Game");
        button2.addActionListener(navigationBetweenViewController);
        button2.setActionCommand("game");

        actionPanel.add(button2);

        JButton button1 = new JButton("Editor");
        button1.addActionListener(navigationBetweenViewController);
        button1.setActionCommand("editor");

        actionPanel.add(button1);

        JButton button = new JButton("Quit");
        button.addActionListener(navigationBetweenViewController);
        button.setActionCommand("quit");

        actionPanel.add(button);

        targetPanel.add(menuLevelSelector, BorderLayout.NORTH);
        targetPanel.add(actionPanel, BorderLayout.SOUTH);
    }

    /**
     * Gets the selected level identifier!
     *
     * @return Level identifier
     */
    public String getLevelIdentifier() {
        return menuLevelSelector.getChoiceValue();
    }
}
