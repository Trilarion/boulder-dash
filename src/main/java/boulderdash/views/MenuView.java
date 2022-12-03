package boulderdash.views;

import boulderdash.controllers.NavigationBetweenViewController;
import boulderdash.helpers.LevelSelectorHelper;

import javax.swing.*;
import java.awt.*;

/**
 * Menu view
 */
public class MenuView extends JFrame {
    private final NavigationBetweenViewController navigationBetweenViewController;
    private final MenuLevelSelector menuLevelSelector;
    private final JPanel actionPanel;

    public MenuView(NavigationBetweenViewController navigationBetweenViewController) {
        this.navigationBetweenViewController = navigationBetweenViewController;
        // Initializes the view
        setVisible(true);
        setResizable(false);

        // UI parameters
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 200, 100); // TODO replace with constants
        setSize(432, 536);

        // App parameters
        setTitle("Boulder Dash | Menu");
        // Creates the view layout
        LevelSelectorHelper levelSelectorHelper = new LevelSelectorHelper(false);
        menuLevelSelector = levelSelectorHelper.createLevelList();

        JPanel targetPanel = new JPanel();

        MenuImage menuImage = new MenuImage();
        actionPanel = new JPanel();

        // Add some buttons on the actionPanel
        createButton("game", "Game");
        createButton("editor", "Editor");
        createButton("quit", "Quit");

        add(menuImage, BorderLayout.CENTER);
        add(targetPanel, BorderLayout.SOUTH);

        targetPanel.add(menuLevelSelector, BorderLayout.NORTH);
        targetPanel.add(actionPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates the given button
     *
     * @param name Button name
     */
    public void createButton(String id, String name) {
        JButton button = new JButton(name);
        button.addActionListener(navigationBetweenViewController);
        button.setActionCommand(id);

        actionPanel.add(button);

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
