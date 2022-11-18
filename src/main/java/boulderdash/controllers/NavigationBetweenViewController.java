package boulderdash.controllers;

import boulderdash.helpers.AudioLoadHelper;
import boulderdash.models.LevelModel;
import boulderdash.views.MenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller to navigate between the different views
 */
public class NavigationBetweenViewController implements ActionListener {
    private final AudioLoadHelper audioLoadHelper;
    private LevelEditorController levelEditorController;
    private MenuView menuView;
    private GameController gameController;
    private String pickedLevelIdentifier;

    public NavigationBetweenViewController() {
        this.audioLoadHelper = new AudioLoadHelper();

        // Play game music
        this.getAudioLoadHelper().startMusic("game");

        // Creation of the first view
        this.menuView = new MenuView(this);
    }

    /**
     * Action performed event handler
     *
     * @param event Action event
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            case "quit":
                System.exit(0);
                break;

            case "editor":
                // New blank model for editor
                LevelModel levelModelForEditor = new LevelModel(audioLoadHelper);
                this.levelEditorController = new LevelEditorController(levelModelForEditor, this);

                this.levelEditorController.getLevelEditorView().setVisible(true);
                this.levelEditorController.getLevelEditorView().getLevelEditorGroundView().grabFocus();

                if (gameController != null) {
                    this.gameController.getGameView().setVisible(false);
                }

                break;

            case "game":
                // Reinit the levelModelForGame...
                pickedLevelIdentifier = this.menuView.getLevelIdentifier();

                LevelModel levelModelForGame = new LevelModel(pickedLevelIdentifier, audioLoadHelper);
                this.gameController = new GameController(levelModelForGame, audioLoadHelper, this);

                if (levelEditorController != null) {
                    this.levelEditorController.getLevelEditorView().setVisible(false);
                }

                this.gameController.getGameView().setVisible(true);
                this.gameController.getGameView().getGameFieldView().grabFocus();

                break;
        }

        this.menuView.setVisible(false);
    }

    /**
     * Get the audio load helper
     */
    public AudioLoadHelper getAudioLoadHelper() {
        return this.audioLoadHelper;
    }

    /**
     * Get the first view
     */
    public MenuView getMenuView() {
        return this.menuView;
    }

    /**
     * Set the first view
     */
    public void setMenuView() {
        this.menuView = new MenuView(this);
    }

    /**
     * Get the pickedLevel
     */
    public String getPickedLevelIdentifier() {
        return pickedLevelIdentifier;
    }

    /**
     * Set the pickedLevelIdentifier
     */
    public void setPickedLevelIdentifier(String pickedLevelIdentifier) {
        this.pickedLevelIdentifier = pickedLevelIdentifier;
    }

}
