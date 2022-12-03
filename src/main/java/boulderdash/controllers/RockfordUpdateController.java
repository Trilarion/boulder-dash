package boulderdash.controllers;

import boulderdash.models.LevelModel;

/**
 * Updates position of all elements displayed on the map, according to their
 * next potential position. Each object has a weight, which is used to compare
 * their power to destroy in the food chain. Sorry for that Darwinism.
 */
public class RockfordUpdateController implements Runnable {
    private final LevelModel levelModel;
    private int rockfordPositionX;
    private int rockfordPositionY;
    private boolean rockfordHasMoved;

    /**
     * @param levelModel Level model
     */
    public RockfordUpdateController(LevelModel levelModel) {
        this.levelModel = levelModel;
        Thread elementMovingThread = new Thread(this);
        elementMovingThread.start();
        rockfordHasMoved = false;
    }

    /**
     * Watches for elements to be moved
     */
    public void run() {
        while (levelModel.isGameRunning()) {
            if (!levelModel.getGamePaused()) {
                if (rockfordHasMoved) {
                    levelModel.setPositionOfRockford(rockfordPositionX, rockfordPositionY);
                    rockfordHasMoved = false;
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace(); // TODO rethrow exception
            }
        }
    }

    /**
     * Moves Rockford
     *
     * @param rockfordPositionX Next horizontal position on the grid
     * @param rockfordPositionY Next vertical position on the grid
     */
    public void moveRockford(int rockfordPositionX, int rockfordPositionY) {
        this.rockfordPositionX = rockfordPositionX;
        this.rockfordPositionY = rockfordPositionY;
        rockfordHasMoved = true;
    }
}
