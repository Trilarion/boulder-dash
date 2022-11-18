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
        this.firstClickOnPause = true;

        this.navigationBetweenViewController = navigationBetweenViewController;

        this.levelModel = levelModel;
        this.audioLoadHelper = audioLoadHelper;
        this.gameView = new GameView(this, levelModel);
        this.menuView = navigationBetweenViewController.getMenuView();

        this.getAudioLoadHelper().stopMusic();
        this.getAudioLoadHelper().playSound("new");
    }

    /**
     * Handles the 'action performed' event
     *
     * @param event Action event
     */
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            case "pause":
                this.levelModel.setGamePaused(this.firstClickOnPause);

                this.firstClickOnPause = !this.firstClickOnPause;
                this.gameView.getGameFieldView().grabFocus();
                break;

            case "restart":
                this.resetGame("restart");
                this.getAudioLoadHelper().playSound("new");
                this.gameView.getGameFieldView().grabFocus();
                break;

            case "menu":
                this.menuView.setVisible(true);
                this.getAudioLoadHelper().startMusic("game");
                this.resetGame("menu");
                break;
        }
    }

    /**
     * Function to reset the game
     */
    private void resetGame(String source) {
        this.gameView.dispose();

        if (source.equals("restart")) {
            this.levelModel = new LevelModel(this.navigationBetweenViewController.getPickedLevelIdentifier(), audioLoadHelper);
            this.gameView = new GameView(this, levelModel);
            this.gameView.setVisible(true);
        }
    }

    /**
     * Gets the audio load helper instance
     */
    public AudioLoadHelper getAudioLoadHelper() {
        return this.audioLoadHelper;
    }

    /**
     * Return the game view
     */
    public GameView getGameView() {
        return gameView;
    }

}