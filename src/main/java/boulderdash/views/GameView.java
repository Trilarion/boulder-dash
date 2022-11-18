package boulderdash.views;

import boulderdash.controllers.GameController;
import boulderdash.models.LevelModel;

import javax.swing.*;
import java.awt.*;

/**
 * GameView
 * <p>
 * Specifies the game view itself.
 *
 * @author Colin Leverger <me@colinleverger.fr>
 * @since 2015-06-19
 */
public class GameView extends JFrame {
    private final GameController gameController;
    private final LevelModel levelModel;
    private GameGroundView gameGroundView;
    private JPanel actionPanel;

    /**
     * Class constructor
     *
     * @param gameController Game controller
     * @param levelModel     Level model
     */
    public GameView(GameController gameController, LevelModel levelModel) {
        this.gameController = gameController;
        this.levelModel = levelModel;

        this.initializeView();
        this.createLayout();

        this.gameGroundView.grabFocus();
    }

    /**
     * Initializes the view
     */
    private void initializeView() {
        this.setVisible(false);
        this.setResizable(false);

        // UI parameters
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setBounds(100, 100, 432, 536);

        // App parameters
        this.setTitle("Boulder Dash | Game");

        Image appIcon = Toolkit.getDefaultToolkit().getImage("./res/app/app_icon.png");
        this.setIconImage(appIcon);
    }

    /**
     * Creates the view layout
     */
    private void createLayout() {
        this.gameGroundView = new GameGroundView(this.gameController, this.levelModel);
        this.actionPanel = new JPanel();
        JPanel informationPanel = new InformationPanel(this.levelModel);
        informationPanel.setBackground(Color.white);


        // Add some buttons on the informationPanel
        this.createButton("restart", "Restart");
        this.createButton("pause", "Pause");
        this.createButton("menu", "Menu");

        this.add(this.actionPanel, BorderLayout.SOUTH);
        this.add(informationPanel, BorderLayout.NORTH);
        this.add(this.gameGroundView, BorderLayout.CENTER);
    }

    /**
     * Gets the game field view
     *
     * @return Game field view
     */
    public GameGroundView getGameFieldView() {
        return this.gameGroundView;
    }

    /**
     * Creates the given button
     *
     * @param name Button name
     */
    public void createButton(String id, String name) {
        JButton button = new JButton(name);
        button.addActionListener(this.gameController);
        button.setActionCommand(id);

        this.actionPanel.add(button);

    }
}