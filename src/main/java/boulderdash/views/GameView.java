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
        JButton button2 = new JButton("Restart");
        button2.addActionListener(this.gameController);
        button2.setActionCommand("restart");

        actionPanel.add(button2);
        JButton button1 = new JButton("Pause");
        button1.addActionListener(this.gameController);
        button1.setActionCommand("pause");

        actionPanel.add(button1);
        JButton button = new JButton("Menu");
        button.addActionListener(this.gameController);
        button.setActionCommand("menu");

        actionPanel.add(button);

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

}