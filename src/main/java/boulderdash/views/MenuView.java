package boulderdash.views;

import boulderdash.controllers.NavigationBetweenViewController;
import boulderdash.helpers.LevelSelectorHelper;

import javax.swing.*;
import java.awt.*;


/**
 * MenuView
 * <p>
 * Menu view
 *
 * @author Valerian Saliou <valerian@valeriansaliou.name>
 * @since 2015-06-23
 */
public class MenuView extends JFrame {
    private final NavigationBetweenViewController navigationBetweenViewController;
    private MenuLevelSelector menuLevelSelector;
    private JPanel actionPanel;

    /**
     * Class constructor
     */
    public MenuView(NavigationBetweenViewController navigationBetweenViewController) {
        this.navigationBetweenViewController = navigationBetweenViewController;
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
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100, 100, 200, 100);
        this.setSize(432, 536);

        // App parameters
        this.setTitle("Boulder Dash | Menu");
    }

    /**
     * Creates the view layout
     */
    private void createLayout() {
        LevelSelectorHelper levelSelectorHelper = new LevelSelectorHelper(false);
        this.menuLevelSelector = levelSelectorHelper.createLevelList();

        JPanel targetPanel = new JPanel();

        MenuImage menuImage = new MenuImage();
        this.actionPanel = new JPanel();

        // Add some buttons on the actionPanel
        this.createButton("game", "Game");
        this.createButton("editor", "Editor");
        this.createButton("quit", "Quit");

        this.add(menuImage, BorderLayout.CENTER);
        this.add(targetPanel, BorderLayout.SOUTH);

        targetPanel.add(this.menuLevelSelector, BorderLayout.NORTH);
        targetPanel.add(this.actionPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates the given button
     *
     * @param name Button name
     */
    public void createButton(String id, String name) {
        JButton button = new JButton(name);
        button.addActionListener(this.navigationBetweenViewController);
        button.setActionCommand(id);

        this.actionPanel.add(button);

    }

    /**
     * Gets the selected level identifier!
     *
     * @return Level identifier
     */
    public String getLevelIdentifier() {
        return this.menuLevelSelector.getChoiceValue();
    }
}
