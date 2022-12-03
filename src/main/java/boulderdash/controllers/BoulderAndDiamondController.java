package boulderdash.controllers;

import boulderdash.helpers.AudioLoadHelper;
import boulderdash.models.DirtModel;
import boulderdash.models.DisplayableElementModel;
import boulderdash.models.LevelModel;

/**
 * Updates position of all elements displayed on the map, according to their
 * next potential position. Each object has a weight, which is used to compare
 * their power to destroy in the food chain.
 */
public class BoulderAndDiamondController implements Runnable {
    private final LevelModel levelModel;
    private final AudioLoadHelper audioLoadHelper;

    /**
     * @param levelModel Level model
     */
    public BoulderAndDiamondController(LevelModel levelModel, AudioLoadHelper audioLoadHelper) {
        this.levelModel = levelModel;
        this.audioLoadHelper = audioLoadHelper;

        // Start thread
        Thread elementMovingThread = new Thread(this);
        elementMovingThread.start();
    }

    /**
     * Watches for elements to be moved
     */
    public void run() {
        while (levelModel.isGameRunning()) {
            if (!levelModel.getGamePaused()) {
                manageFallingObject();
            }
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace(); // TODO rethrow exception
            }
        }
    }

    /**
     * Scan the ground to detect the boulders & the diamonds, then make them
     * fall if necessary
     * Note: scan of the ground upside down: we want things to fall slowly!
     */
    private void manageFallingObject() {
        for (int x = levelModel.getSizeWidth() - 1; x >= 0; x--) {
            for (int y = levelModel.getSizeHeight() - 1; y >= 0; y--) {
                // Gets the spriteName of actual DisplayableElementModel object scanned
                DisplayableElementModel elementModel = levelModel.getGroundLevelModel()[x][y];

                if (elementModel == null) {
                    elementModel = new DirtModel();
                }

                String spriteName = elementModel.getSpriteName();

                // If it is a boulder or a diamond...
                if ("boulder".equals(spriteName) || "diamond".equals(spriteName)) {
                    manageFall(x, y);
                } else if ("expandingwall".equals(spriteName)) {
                    if ("left".equals(expandWall(x, y))) {
                        x -= 1; // TODO assignment to loop variable, is this good?
                    }
                }
            }
        }
    }

    /**
     * Expand the wall at left & right
     *
     * @param x Horizontal position
     * @param y Vertical position
     */
    private String expandWall(int x, int y) {
        DisplayableElementModel elementLeft = levelModel.getGroundLevelModel()[x - 1][y];
        DisplayableElementModel elementRight = levelModel.getGroundLevelModel()[x + 1][y];
        String spriteNameLeft = elementLeft.getSpriteName();
        String spriteNameRight = elementRight.getSpriteName();

        String way = "";
        if ("black".equals(spriteNameLeft)) {
            levelModel.expandThisWallToLeft(x, y);
            way = "left";
        }
        if ("black".equals(spriteNameRight)) {
            levelModel.expandThisWallToRight(x, y);
            way = "right";
        }
        return way;
    }

    /**
     * Manages the fall of elements
     *
     * @param x Horizontal position
     * @param y Vertical position
     */
    private void manageFall(int x, int y) {
        // Get informed about Rockford surroundings
        DisplayableElementModel elementBelow = levelModel.getGroundLevelModel()[x][y + 1];
        DisplayableElementModel elementLeft = levelModel.getGroundLevelModel()[x - 1][y];
        DisplayableElementModel elementRight = levelModel.getGroundLevelModel()[x + 1][y];

        String spriteNameBelow = elementBelow.getSpriteName();
        String spriteNameLeft = elementLeft.getSpriteName();
        String spriteNameRight = elementRight.getSpriteName();

        // Then, process in case of the surrounding
        if ("black".equals(spriteNameBelow)) {
            levelModel.makeThisDisplayableElementFall(x, y);
        } else if ("boulder".equals(spriteNameBelow)) {
            // Boulders have to roll if they hit another boulder
            if ("black".equals(levelModel.getGroundLevelModel()[x - 1][y + 1].getSpriteName())) {
                levelModel.makeThisBoulderSlideLeft(x, y);
            } else if ("black".equals(levelModel.getGroundLevelModel()[x + 1][y + 1].getSpriteName())) {
                levelModel.makeThisBoulderSlideRight(x, y);
            }
        } else if ("rockford".equals(spriteNameBelow) && levelModel.getGroundLevelModel()[x][y].isFalling()) {
            levelModel.exploseGround(x, y + 1);

            audioLoadHelper.playSound("die");

            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace(); // TODO rethrow exception
            }

            levelModel.setGameRunning(false);
        } else if ("magicwall".equals(spriteNameBelow)) {
            if ("boulder".equals(levelModel.getGroundLevelModel()[x][y].getSpriteName())
                    && ("dirt".equals(levelModel.getGroundLevelModel()[x][y + 2].getSpriteName()) ||
                    "black".equals(levelModel.getGroundLevelModel()[x][y + 2].getSpriteName()))) {
                if (levelModel.getGroundLevelModel()[x][y].isConvertible()) {
                    levelModel.transformThisBoulderIntoADiamond(x, y);
                } else {
                    levelModel.deleteThisBoulder(x, y);
                }
            }
        } else if (elementBelow.isDestructible() && !"dirt".equals(spriteNameBelow) && levelModel.getGroundLevelModel()[x][y].isFalling()) {
            levelModel.exploseThisBrickWall(x, y);
        } else if ("rockford".equals(spriteNameLeft) && levelModel.getRockford().isRunningRight() && "black".equals(levelModel.getGroundLevelModel()[x + 1][y].getSpriteName())) {
            levelModel.moveThisBoulderToRight(x, y);
        } else if ("rockford".equals(spriteNameRight) && levelModel.getRockford().isRunningLeft() && "black".equals(levelModel.getGroundLevelModel()[x - 1][y].getSpriteName())) {
            levelModel.moveThisBoulderToLeft(x, y);
        } else {
            levelModel.getGroundLevelModel()[x][y].setFalling(false);
        }
    }
}
