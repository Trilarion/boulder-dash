package boulderdash.controllers;

import boulderdash.exceptions.LevelConstraintNotRespectedException;
import boulderdash.helpers.LevelRemoveHelper;
import boulderdash.helpers.LevelSaveHelper;
import boulderdash.models.LevelModel;
import boulderdash.views.HelpView;
import boulderdash.views.LevelEditorView;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Manages the level editor controller.
 */
public class LevelEditorController implements ActionListener {
    private final LevelModel levelModel;
    private final NavigationBetweenViewController nav;
    private final LevelEditorView levelEditorView;

    /**
     * @param levelModel Level model
     */
    public LevelEditorController(LevelModel levelModel, NavigationBetweenViewController nav) {
        this.levelModel = levelModel;
        this.levelModel.setShowCursor(true);

        this.nav = nav;
        this.nav.getAudioLoadHelper().stopMusic();

        levelEditorView = new LevelEditorView(this, levelModel, nav);

        // Pre-bind event watcher (hack to fix a Java issue)
        this.levelModel.decrementCursorXPosition();
    }

    /**
     * Handles the 'action performed' event
     *
     * @param event Action event
     */
    public void actionPerformed(@NotNull ActionEvent event) {
        switch (event.getActionCommand()) {
            case "menu":
                levelEditorView.setVisible(false);
                nav.setMenuView();
                nav.getAudioLoadHelper().startMusic("game");

                break;

            case "save":
                // Check constraints
                try {
                    levelModel.checkConstraints();

                    // Save action (direct save)
                    String levelId = levelEditorView.getSelectedLevel();
                    LevelSaveHelper levelSave;

                    if (levelId == null || levelId.isEmpty()) {
                        // Create a new level
                        levelSave = new LevelSaveHelper(levelModel.getGroundLevelModel());
                    } else {
                        // Overwrite existing level
                        levelSave = new LevelSaveHelper(levelId, levelModel.getGroundLevelModel());
                    }

                    JFrame frameDialog = new JFrame("Info");
                    JOptionPane.showMessageDialog(frameDialog, "Level saved");

                    levelEditorView.openedLevelChange(levelSave.getLevelId());
                } catch (LevelConstraintNotRespectedException e) {
                    JFrame frameDialog = new JFrame("Error");
                    JOptionPane.showMessageDialog(frameDialog, e.getMessage());
                }

                break;

            case "delete":
                String levelId = levelEditorView.getSelectedLevel();
                JFrame frameDialog = new JFrame("Info");

                if (levelId == null || levelId.isEmpty()) {
                    JOptionPane.showMessageDialog(frameDialog, "Level not yet saved, no need to delete it!");
                } else {
                    new LevelRemoveHelper(levelId);
                    JOptionPane.showMessageDialog(frameDialog, "Level deleted!");

                    levelEditorView.openedLevelChange(null);
                }
                break;

            case "help":
                new HelpView();
                break;

            case "new":
                levelEditorView.openedLevelChange(null);
                break;
        }

        levelEditorView.getLevelEditorGroundView().grabFocus();
    }

    /**
     * Gets the level editor view
     *
     * @return Level editor view
     */
    public LevelEditorView getLevelEditorView() {
        return levelEditorView;
    }
}