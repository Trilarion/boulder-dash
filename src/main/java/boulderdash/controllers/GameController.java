package boulderdash.controllers;

import boulderdash.helpers.AudioLoadHelper;
import boulderdash.models.LevelModel;
import boulderdash.views.GameView;
import boulderdash.views.MenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * GameController
 * <p>
 * This system creates the view.
 * The game loop is also handled there.
 */
public class GameController implements ActionListener {
    private final AudioLoadHelper audioLoadHelper;
    private final MenuView menuView;
    private final NavigationBetweenViewController navigationBetweenViewController;
    private LevelModel levelModel;
    private boolean firstClickOnPause;
    private GameView gameView;

    /**
     * @param levelModel Level model
     */
    public GameController(LevelModel levelModel, AudioLoadHelper audioLoadHelper, NavigationBetweenViewController navigationBetweenViewController) {
        firstClickOnPause = true;

        this.navigationBetweenViewController = navigationBetweenViewController;

        this.levelModel = levelModel;
        this.audioLoadHelper = audioLoadHelper;
        gameView = new GameView(this, levelModel);
        menuView = navigationBetweenViewController.getMenuView();

        this.audioLoadHelper.stopMusic();
        this.audioLoadHelper.playSound("new");
    }

    /**
     * Handles the 'action performed' event
     *
     * @param event Action event
     */
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            case "pause":
                levelModel.setGamePaused(firstClickOnPause);

                firstClickOnPause = !firstClickOnPause;
                gameView.getGameFieldView().grabFocus();
                break;

            case "restart":
                resetGame("restart");
                audioLoadHelper.playSound("new");
                gameView.getGameFieldView().grabFocus();
                break;

            case "menu":
                menuView.setVisible(true);
                audioLoadHelper.startMusic("game");
                resetGame("menu");
                break;
        }
    }

    /**
     * Function to reset the game
     */
    private void resetGame(String source) {
        gameView.dispose();

        if ("restart".equals(source)) {
            levelModel = new LevelModel(navigationBetweenViewController.getPickedLevelIdentifier(), audioLoadHelper);
            gameView = new GameView(this, levelModel);
            gameView.setVisible(true);
        }
    }

    /**
     * Gets the audio load helper instance
     */
    public AudioLoadHelper getAudioLoadHelper() {
        return audioLoadHelper;
    }

    /**
     * Return the game view
     */
    public GameView getGameView() {
        return gameView;
    }

}