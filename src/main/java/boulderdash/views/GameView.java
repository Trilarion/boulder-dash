package boulderdash.views;

import boulderdash.controllers.GameController;
import boulderdash.models.LevelModel;

import javax.swing.*;
import java.awt.*;

/**
 * Specifies the game view itself.
 */
public class GameView extends JFrame {
    private final GameController gameController;
    private final GameGroundView gameGroundView;
    private final JPanel actionPanel;

    /**
     * @param gameController Game controller
     * @param levelModel     Level model
     */
    public GameView(GameController gameController, LevelModel levelModel) {
        this.gameController = gameController;

        setVisible(false);
        setResizable(false);

        // UI parameters
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(100, 100, 432, 536);

        // App parameters
        setTitle("Boulder Dash | Game");

        Image appIcon = Toolkit.getDefaultToolkit().getImage("./res/app/app_icon.png"); // TODO replace with constant
        setIconImage(appIcon);

        // view layout
        gameGroundView = new GameGroundView(this.gameController, levelModel);
        actionPanel = new JPanel();
        JPanel informationPanel = new InformationPanel(levelModel);
        informationPanel.setBackground(Color.white);


        // Add some buttons on the informationPanel
        createButton("restart", "Restart");
        createButton("pause", "Pause");
        createButton("menu", "Menu");

        add(actionPanel, BorderLayout.SOUTH);
        add(informationPanel, BorderLayout.NORTH);
        add(gameGroundView, BorderLayout.CENTER);

        gameGroundView.grabFocus();
    }

    /**
     * Gets the game field view
     *
     * @return Game field view
     */
    public GameGroundView getGameFieldView() {
        return gameGroundView;
    }

    /**
     * Creates the given button
     *
     * @param name Button name
     */
    public void createButton(String id, String name) {
        JButton button = new JButton(name);
        button.addActionListener(gameController);
        button.setActionCommand(id);

        actionPanel.add(button);
    }
}