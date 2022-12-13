package boulderdash.controllers;

import boulderdash.helpers.AudioLoadHelper;
import boulderdash.models.DisplayableElementModel;
import boulderdash.models.LevelModel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Manages the key-events controller.
 */
public class GameKeyController implements KeyListener {
    private final LevelModel levelModel;
    private final RockfordUpdateController rockfordUpdateController;

    /**
     * @param levelModel Level model
     */
    public GameKeyController(LevelModel levelModel, AudioLoadHelper audioLoadHelper) {
        this.levelModel = levelModel;
        new BoulderAndDiamondController(levelModel, audioLoadHelper);
        rockfordUpdateController = new RockfordUpdateController(levelModel);
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
                DisplayableElementModel upElement = levelModel.getGroundLevelModel()[levelModel.getRockfordPositionX()][levelModel.getRockfordPositionY() - 1];

                if (upElement.getPriority() < levelModel.getRockford().getPriority()) {
                    rockfordUpdateController.moveRockford(levelModel.getRockfordPositionX(), levelModel.getRockfordPositionY() - 1);
                    levelModel.getRockford().startRunningUp();
                }

                break;

            // Direction: DOWN
            case KeyEvent.VK_DOWN:
                DisplayableElementModel downElement = levelModel.getGroundLevelModel()[levelModel.getRockfordPositionX()][levelModel.getRockfordPositionY() + 1];

                if (downElement.getPriority() < levelModel.getRockford().getPriority()) {
                    rockfordUpdateController.moveRockford(levelModel.getRockfordPositionX(), levelModel.getRockfordPositionY() + 1);
                    levelModel.getRockford().startRunningDown();
                }

                break;

            // Direction: LEFT
            case KeyEvent.VK_LEFT:
                DisplayableElementModel leftElement = levelModel.getGroundLevelModel()[levelModel.getRockfordPositionX() - 1][levelModel.getRockfordPositionY()];

                if (leftElement.getPriority() < levelModel.getRockford().getPriority()) {
                    rockfordUpdateController.moveRockford(levelModel.getRockfordPositionX() - 1, levelModel.getRockfordPositionY());
                    levelModel.getRockford().startRunningLeft();
                }

                break;

            // Direction: RIGHT
            case KeyEvent.VK_RIGHT:
                DisplayableElementModel rightElement = levelModel.getGroundLevelModel()[levelModel.getRockfordPositionX() + 1][levelModel.getRockfordPositionY()];

                if (rightElement.getPriority() < levelModel.getRockford().getPriority()) {
                    rockfordUpdateController.moveRockford(levelModel.getRockfordPositionX() + 1, levelModel.getRockfordPositionY());
                    levelModel.getRockford().startRunningRight();
                }

                break;
        }
    }

    /**
     * Handles the 'key released' event
     *
     * @param e Key event
     */
    @Override
    public void keyReleased(KeyEvent e) {
        levelModel.getRockford().startStaying();
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
