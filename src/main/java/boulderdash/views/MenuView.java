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


    public MenuView(NavigationBetweenViewController navigationBetweenViewController) {
        this.navigationBetweenViewController = navigationBetweenViewController;
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 200, 100);
        setSize(432, 536);

        // App parameters
        setTitle("Boulder Dash | Menu");
    }

    /**
     * Creates the view layout
     */
    private void createLayout() {
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
