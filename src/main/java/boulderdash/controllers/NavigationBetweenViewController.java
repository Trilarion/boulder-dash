package boulderdash.controllers;

import boulderdash.helpers.AudioLoadHelper;
import boulderdash.models.LevelModel;
import boulderdash.views.MenuView;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller to navigate between the different views
 */
public class NavigationBetweenViewController implements ActionListener {
    private final @NotNull AudioLoadHelper audioLoadHelper;
    private LevelEditorController levelEditorController;
    private MenuView menuView;
    private GameController gameController;
    private String pickedLevelIdentifier;

    public NavigationBetweenViewController() {
        audioLoadHelper = new AudioLoadHelper();

        // play initial music
        audioLoadHelper.startMusic("game");

        // Creation of the first view
        menuView = new MenuView(this);
    }

    /**
     * Action performed event handler
     *
     * @param event Action event
     */
    @Override
    public void actionPerformed(@NotNull ActionEvent event) {
        switch (event.getActionCommand()) {
            case "quit":
                System.exit(0);
                break;

            case "editor":
                // New blank model for editor
                LevelModel levelModelForEditor = new LevelModel(audioLoadHelper);
                levelEditorController = new LevelEditorController(levelModelForEditor, this);

                levelEditorController.getLevelEditorView().setVisible(true);
                levelEditorController.getLevelEditorView().getLevelEditorGroundView().grabFocus();

                if (gameController != null) {
                    gameController.getGameView().setVisible(false);
                }

                break;

            case "game":
                // Re-initialize the levelModelForGame...
                pickedLevelIdentifier = menuView.getLevelIdentifier();

                LevelModel levelModelForGame = new LevelModel(pickedLevelIdentifier, audioLoadHelper);
                gameController = new GameController(levelModelForGame, audioLoadHelper, this);

                if (levelEditorController != null) {
                    levelEditorController.getLevelEditorView().setVisible(false);
                }

                gameController.getGameView().setVisible(true);
                gameController.getGameView().getGameFieldView().grabFocus();

                break;
        }

        menuView.setVisible(false);
    }

    /**
     * Get the audio load helper
     */
    public AudioLoadHelper getAudioLoadHelper() {
        return audioLoadHelper;
    }

    /**
     * Get the first view
     */
    public MenuView getMenuView() {
        return menuView;
    }

    /**
     * Set the first view
     */
    public void setMenuView() {
        menuView = new MenuView(this);
    }

    /**
     * Get the pickedLevel
     */
    public String getPickedLevelIdentifier() {
        return pickedLevelIdentifier;
    }
}
