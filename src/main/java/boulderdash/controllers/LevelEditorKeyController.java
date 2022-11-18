package boulderdash.controllers;

import boulderdash.models.LevelModel;
import boulderdash.views.LevelEditorView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * LevelEditorKeyController
 * <p>
 * Manages the key-events controller.
 */
public class LevelEditorKeyController implements KeyListener {
    private final LevelModel levelModel;
    private final LevelEditorView levelEditorView;
    private boolean capLocks;

    /**
     * @param levelModel      Level model
     * @param levelEditorView Level editor view
     */
    public LevelEditorKeyController(LevelModel levelModel, LevelEditorView levelEditorView) {
        this.levelModel = levelModel;
        capLocks = false;
        this.levelEditorView = levelEditorView;
    }

    /**
     * Handles the 'key pressed' event
     *
     * @param e Key event
     */
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            // Direction: UP
            case KeyEvent.VK_UP:
                levelModel.decrementCursorYPosition();
                break;

            // Direction: DOWN
            case KeyEvent.VK_DOWN:
                levelModel.incrementCursorYPosition();
                break;

            // Direction: LEFT
            case KeyEvent.VK_LEFT:
                levelModel.decrementCursorXPosition();
                break;

            // Direction: RIGHT
            case KeyEvent.VK_RIGHT:
                levelModel.incrementCursorXPosition();
                break;

            // Key: SPACE
            case KeyEvent.VK_SPACE:
                levelModel.triggerBlockChange(levelEditorView.getPickedBlockValue());
                break;

            case 16:
                capLocks = !capLocks;
                break;
        }

        // Hold block change (quick edit)
        if (capLocks) {
            levelModel.triggerBlockChange(levelEditorView.getPickedBlockValue());
        }
    }

    /**
     * Handles the 'key released' event
     *
     * @param e Key event
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // Do nothing.
    }

    /**
     * Handles the 'key typed' event
     *
     * @param e Key event
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Do nothing.
    }
}
