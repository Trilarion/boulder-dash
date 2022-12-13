package boulderdash.models;

import boulderdash.Options;
import boulderdash.exceptions.LevelConstraintNotRespectedException;
import boulderdash.exceptions.UnknownModelException;
import boulderdash.helpers.AudioLoadHelper;
import boulderdash.helpers.LevelLoadHelper;
import boulderdash.helpers.ModelConvertHelper;
import boulderdash.utils.Observable;

import java.awt.image.BufferedImage;

/**
 * Levels are loaded from XML file. The view knows the model, the controller is
 * going to modify the model in function of the game panel. The model notifies
 * the view when there are changes on it.
 */
public class LevelModel extends Observable<String> implements Runnable {

    private final AudioLoadHelper audioLoadHelper;
    private DisplayableElementModel[][] groundGrid;
    private int sizeWidth;
    private int sizeHeight;
    private int cursorXPosition;
    private int cursorYPosition;
    private boolean showCursor;
    private CursorModel cursorModel;
    private LevelLoadHelper levelLoadHelper;
    private RockfordModel rockford;
    private GameInformationModel gameInformationModel;
    private int rockfordPositionX, rockfordPositionY;
    private boolean gameRunning;
    private boolean gamePaused;
    // Are we in editor or game mode ?
    private String mode;

    /**
     * @param levelName       Level name
     * @param audioLoadHelper Audio load helper
     * @param mode            Instance mode
     */
    public LevelModel(String levelName, AudioLoadHelper audioLoadHelper, String mode) {
        this.audioLoadHelper = audioLoadHelper;
        gamePaused = false;
        gameRunning = true;
        this.mode = mode;

        levelLoadHelper = new LevelLoadHelper(levelName);

        groundGrid = levelLoadHelper.getGroundGrid();
        sizeWidth = levelLoadHelper.getWidthSizeValue();
        sizeHeight = levelLoadHelper.getHeightSizeValue();

        cursorModel = new CursorModel();
        gameInformationModel = new GameInformationModel(levelLoadHelper.getDiamondsToCatch());

        createLimits();

        if ("game".equals(this.mode)) {
            initRockford();
            initThreadAnimator();
        }
    }

    /**
     * @param levelName       Level name
     * @param audioLoadHelper Audio load helper
     */
    public LevelModel(String levelName, AudioLoadHelper audioLoadHelper) {
        this(levelName, audioLoadHelper, "game");
    }

    /**
     * Class constructor (editor mode)
     *
     * @param audioLoadHelper Audio load helper
     */
    public LevelModel(AudioLoadHelper audioLoadHelper) {
        this.audioLoadHelper = audioLoadHelper;
        gameRunning = false;
        mode = "editor";

        sizeWidth = 25 + 2;
        sizeHeight = 25 + 2;

        // Generate dirt
        groundGrid = new DisplayableElementModel[sizeWidth][sizeHeight];

        for (int x = 0; x < sizeWidth; x++) {
            for (int y = 0; y < sizeHeight; y++) {
                groundGrid[x][y] = new DirtModel();
            }
        }

        createLimits();
    }

    /**
     * Initializes the animator thread
     */
    private void initThreadAnimator() {
        /*
         * Sprite animation thread
         */
        Thread spriteAnimator = new Thread(this);
        spriteAnimator.start();
    }

    /**
     * Initializes the Rockford position attributes
     */
    private void initRockford() {
        rockfordPositionX = levelLoadHelper.getRockfordPositionX();
        rockfordPositionY = levelLoadHelper.getRockfordPositionY();
        rockford = levelLoadHelper.getRockfordInstance();
    }

    /**
     * Creates the limits Puts steel walls all around the game panel
     */
    private void createLimits() {
        int maxWidth = sizeWidth - 1;
        int maxHeight = sizeHeight - 1;

        for (int x = 0; x < sizeWidth; x++) {
            groundGrid[x][0] = new SteelWallModel();
            groundGrid[x][maxHeight] = new SteelWallModel();
        }
        for (int y = 0; y < sizeHeight; y++) {
            groundGrid[0][y] = new SteelWallModel();
            groundGrid[maxWidth][y] = new SteelWallModel();
        }
    }

    public void resetLevelModel() {
        groundGrid = levelLoadHelper.getGroundGrid();
        gameRunning = true;
        gameInformationModel.resetInformation();
    }

    /**
     * Updates the horizontal & vertical positions of Rockford in the model
     *
     * @param posX Horizontal position of Rockford
     * @param posY Vertical position of Rockford
     */
    public void updateRockfordPosition(int posX, int posY) {
        rockfordPositionY = posY;
        rockfordPositionX = posX;
    }

    /**
     * Checks whether position is out-of-bounds or not
     *
     * @param posX Horizontal position
     * @param posY Vertical position
     */
    private boolean isOutOfBounds(int posX, int posY) {
        return posX <= 0 || posY <= 0 || posX >= levelLoadHelper.getHeightSizeValue() || posY >= levelLoadHelper.getWidthSizeValue();
    }

    /**
     * Plays collision sound
     */
    private void playCollisionSound(int posX, int posY) {
        String collisionSound = null;

        if (!rockford.isCollisionDone()) {
            // Out of bounds?
            if (isOutOfBounds(posX, posY)) {
                collisionSound = "touch";
            } else {
                DisplayableElementModel nextElement = groundGrid[posX][posY];
                collisionSound = nextElement.getCollideSound();
            }

            rockford.setCollisionDone(true);
        }

        if (collisionSound != null) {
            audioLoadHelper.playSound(collisionSound);
        }
    }

    /**
     * Gets the horizontal position of Rockford from the model
     *
     * @return Horizontal position of Rockford
     */
    public int getRockfordPositionX() {
        return rockfordPositionX;
    }

    /**
     * Sets the new Rockford position
     *
     * @param posX Next horizontal position on the grid
     * @param posY Next vertical position on the grid
     */
    public void setPositionOfRockford(int posX, int posY) {
        int oldX = rockfordPositionX;
        int oldY = rockfordPositionY;

        if ("diamond".equals(groundGrid[posX][posY].getSpriteName())) {
            gameInformationModel.incrementScore();
            gameInformationModel.decrementRemainingDiamonds();
            if (gameInformationModel.getRemainingDiamonds() == 0) {
                spawnExit();
            }
        }
        if ("door".equals(groundGrid[posX][posY].getSpriteName())) {
            gameRunning = false;
        }

        playCollisionSound(posX, posY);

        // Check that we are not out of bound...
        if (!isOutOfBounds(posX, posY)) {
            // Create a new empty model in the old pos of Rockford
            groundGrid[oldX][oldY] = new EmptyModel();

            // Save the x / y pos of Rockford in the levelModel only
            updateRockfordPosition(posX, posY);

            groundGrid[posX][posY] = rockford;
        }
    }

    /**
     * When there is no more diamonds to catch, spawn an exit door randomly in
     * the game
     */
    private void spawnExit() {
        int x = (int) (Math.random() * (sizeHeight - 2));
        int y = (int) (Math.random() * (sizeWidth - 2));
        groundGrid[x + 1][y + 1] = new DoorModel();
    }

    /**
     * Trigger block change with provided value
     *
     * @param blockValue New value
     */
    public void triggerBlockChange(String blockValue) {
        // No block value?
        if (blockValue == null || blockValue.isEmpty()) {
            return;
        }

        // Cancel if Rockford is already in model
        if (("Rockford".equals(blockValue) || "rockford".equals(blockValue)) && isRockfordInModel()) {
            return;
        }

        // Grab model value
        DisplayableElementModel targetModel;
        int xPos, yPos;

        xPos = cursorXPosition;
        yPos = cursorYPosition;

        try {
            targetModel = ModelConvertHelper.toModel(blockValue, false);

            // Apply new model in place of cursor
            groundGrid[xPos + 1][yPos + 1] = targetModel;

            // Disable cursor (important)
            //this.setShowCursor(false);
        } catch (UnknownModelException e) {
            e.printStackTrace(); // TODO rethrow exception
        }
    }

    /**
     * Gets the vertical position of Rockford from the model
     *
     * @return Vertical position of Rockford
     */
    public int getRockfordPositionY() {
        return rockfordPositionY;
    }

    /**
     * Gets the Rockford object instance
     *
     * @return Rockford object
     */
    public RockfordModel getRockford() {
        return rockford;
    }

    /**
     * Gets the displayable element at given positions
     *
     * @param x Block horizontal position
     * @param y Block vertical position
     * @return Displayable element at given positions
     */
    public DisplayableElementModel getDisplayableElement(int x, int y) {
        return groundGrid[x][y];
    }

    /**
     * Gets the image at given positions
     *
     * @param x Block horizontal position
     * @param y Block vertical position
     * @return Image at given positions
     */
    public BufferedImage getImage(int x, int y) {
        DisplayableElementModel elementModel = getDisplayableElement(x, y);

        if (elementModel == null) {
            return new DirtModel().getSprite();
        }

        return elementModel.getSprite();
    }

    /**
     * Gets the cursor image
     *
     * @return Cursor image
     */
    public BufferedImage getCursorImage() {

        if (cursorModel == null) {
            cursorModel = new CursorModel();
        }

        return cursorModel.getSprite();
    }

    /**
     * Return whether rockford is in model or not
     * Notice: not optimized, be careful
     *
     * @return Whether rockford is in model or not
     */
    public boolean isRockfordInModel() {
        boolean isInModel = false;

        // Iterate and catch it!
        for (int x = 0; x < sizeWidth && !isInModel; x++) {
            for (int y = 0; y < sizeHeight && !isInModel; y++) {
                if (groundGrid[x][y] != null && "rockford".equals(groundGrid[x][y].getSpriteName())) {
                    isInModel = true;
                    break;
                }
            }
        }

        return isInModel;
    }

    /**
     * Returns number of diamonds
     *
     * @return Number of diamonds
     */
    public int countDiamonds() {
        int numberOfDiamonds = 0;

        // Iterate and catch it!
        for (int x = 0; x < sizeWidth; x++) {
            for (int y = 0; y < sizeHeight; y++) {
                if (groundGrid[x][y] != null && "diamond".equals(groundGrid[x][y].getSpriteName())) {
                    numberOfDiamonds += 1;
                }
            }
        }

        return numberOfDiamonds;
    }

    /**
     * Returns whether constraints on model are respected or not
     */
    public void checkConstraints() throws LevelConstraintNotRespectedException {
        // Diamonds number?
        if (countDiamonds() < 3) {
            throw new LevelConstraintNotRespectedException("Add at least 3 diamonds!");
        }

        // Rockford in model?
        if (!isRockfordInModel()) {
            throw new LevelConstraintNotRespectedException("Add Rockford on the map!");
        }
    }

    /**
     * Gets the level horizontal size
     *
     * @return Horizontal size
     */
    public int getSizeWidth() {
        return sizeWidth;
    }

    /**
     * Sets the level horizontal size
     *
     * @param sizeWidth Horizontal size
     */
    public void setSizeWidth(int sizeWidth) {
        this.sizeWidth = sizeWidth;
    }

    /**
     * Gets the level vertical size
     *
     * @return Vertical size
     */
    public int getSizeHeight() {
        return sizeHeight;
    }

    /**
     * Sets the level vertical size
     */
    public void setSizeHeight(int sizeHeight) {
        this.sizeHeight = sizeHeight;
    }

    /**
     * Gets the ground level model
     *
     * @return Ground level model
     */
    public DisplayableElementModel[][] getGroundLevelModel() {
        return groundGrid;
    }

    /**
     * Notify observers about a model change
     */
    private void localNotifyObservers() {
        notifyObservers("");
    }

    /**
     * Update the current sprite Notifies the observers
     *
     * @param x Sprite block horizontal position
     * @param y Sprite block vertical position
     */
    public void updateSprites(int x, int y) {
        if (groundGrid[x][y] == null) {
            groundGrid[x][y] = new DirtModel();
        }

        groundGrid[x][y].update(System.currentTimeMillis());
        localNotifyObservers();
    }

    /**
     * Update all the sprites So that they can be animated
     */
    public void run() {
        while (gameRunning) {
            if (!gamePaused) {
                for (int x = 0; x < sizeWidth; x++) {
                    for (int y = 0; y < sizeHeight; y++) {
                        updateSprites(x, y);
                    }
                }
            }

            try {
                Thread.sleep(Options.DELAY); // TODO is this best practice? what to do better than sleep(time)
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }
        }
    }

    /**
     * Increments the user score
     */
    public void incrementScore() {
        gameInformationModel.incrementScore();
    }

    /**
     * Gets the associated level load helper
     *
     * @return Level load helper
     */
    public LevelLoadHelper getLevelLoadHelper() {
        return levelLoadHelper;
    }

    /**
     * Gets the cursor position X value
     *
     * @return Cursor position X value
     */
    public int getCursorXPosition() {
        return cursorXPosition;
    }

    /**
     * Gets the cursor position Y value
     *
     * @return Cursor position Y value
     */
    public int getCursorYPosition() {
        return cursorYPosition;
    }

    /**
     * Increments the cursor position X value
     */
    public void incrementCursorXPosition() {
        if (cursorXPosition < (sizeWidth - 1 - 2)) {
            cursorXPosition = cursorXPosition + 1;
        }

        localNotifyObservers();
    }

    /**
     * Decrements the cursor position X value
     */
    public void decrementCursorXPosition() {
        if (cursorXPosition > 0) {
            cursorXPosition = cursorXPosition - 1;
        }

        localNotifyObservers();
    }

    /**
     * Increments the cursor position Y value
     */
    public void incrementCursorYPosition() {
        if (cursorYPosition < (sizeHeight - 1 - 2)) {
            cursorYPosition = cursorYPosition + 1;
        }

        localNotifyObservers();
    }

    /**
     * Decrements the cursor position Y value
     */
    public void decrementCursorYPosition() {
        if (cursorYPosition > 0) {
            cursorYPosition = cursorYPosition - 1;
        }

        localNotifyObservers();
    }

    /**
     * tells if the game is running
     *
     * @return whether the game is running or not
     */
    public boolean isGameRunning() {
        return gameRunning;
    }

    /**
     * sets the game to a defined state
     *
     * @param gameRunning Whether game is running or not
     */
    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
        // Timer to refresh the view properly...
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(); // TODO rethrow exception
        }
        localNotifyObservers();
    }

    /**
     * Gets whether cursor is to be shown or not
     *
     * @return whether cursor needs to be shown or not
     */
    public boolean getShowCursor() {
        return showCursor;
    }

    /**
     * Sets whether cursor is to be shown or not
     *
     * @param showCursor whether cursor needs to be shown or not
     */
    public void setShowCursor(boolean showCursor) {
        this.showCursor = showCursor;
    }

    /**
     * When a boulder is falling on Rockford there is an explosion around him
     *
     * @param x Object horizontal position
     * @param y Object vertical position
     */
    public void explodeGround(int x, int y) {
        groundGrid[x][y] = new EmptyModel();
        groundGrid[x + 1][y] = new EmptyModel();
        groundGrid[x - 1][y] = new EmptyModel();
        groundGrid[x][y + 1] = new EmptyModel();
        groundGrid[x + 1][y + 1] = new EmptyModel();
        groundGrid[x - 1][y + 1] = new EmptyModel();
        groundGrid[x][y - 1] = new EmptyModel();
        groundGrid[x + 1][y - 1] = new EmptyModel();
        groundGrid[x - 1][y - 1] = new EmptyModel();
        rockford.setHasExploded(true);

        // Again a sleep to notify the observers properly
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(); // TODO rethrow exception
        }
        localNotifyObservers();
    }

    /**
     * Makes the DisplayableElement[x][y] fall one box down
     *
     * @param x Object horizontal position
     * @param y Object vertical position
     */
    public void makeThisDisplayableElementFall(int x, int y) {
        groundGrid[x][y].setFalling(true);
        groundGrid[x][y + 1] = groundGrid[x][y];
        groundGrid[x][y] = new EmptyModel();
    }

    /**
     * Makes the BoulderModel[x][y] slide left
     *
     * @param x Object horizontal position
     * @param y Object vertical position
     */
    public void makeThisBoulderSlideLeft(int x, int y) {
        groundGrid[x][y].setFalling(true);
        groundGrid[x - 1][y + 1] = groundGrid[x][y];
        groundGrid[x][y] = new EmptyModel();
    }

    /**
     * Makes the BoulderModel[x][y] slide right
     *
     * @param x Object horizontal position
     * @param y Object vertical position
     */
    public void makeThisBoulderSlideRight(int x, int y) {
        groundGrid[x][y].setFalling(true);
        groundGrid[x + 1][y + 1] = groundGrid[x][y];
        groundGrid[x][y] = new EmptyModel();
    }

    /**
     * Makes the BoulderModel[x][y] transform into a diamond
     *
     * @param x Object horizontal position
     * @param y Object vertical position
     */
    public void transformThisBoulderIntoADiamond(int x, int y) {
        groundGrid[x][y + 2] = new DiamondModel();
        groundGrid[x][y] = new EmptyModel();
    }

    /**
     * Makes the BoulderModel[x][y] moving to right
     *
     * @param x Object horizontal position
     * @param y Object vertical position
     */
    public void moveThisBoulderToRight(int x, int y) {
        groundGrid[x + 1][y] = groundGrid[x][y];
        groundGrid[x][y] = new EmptyModel();
    }

    /**
     * Makes the BoulderModel[x][y] moving to left
     *
     * @param x Object horizontal position
     * @param y Object vertical position
     */
    public void moveThisBoulderToLeft(int x, int y) {
        groundGrid[x - 1][y] = groundGrid[x][y];
        groundGrid[x][y] = new EmptyModel();
    }

    /**
     * Deletes the BoulderModel[x][y]
     *
     * @param x Object horizontal position
     * @param y Object vertical position
     */
    public void deleteThisBoulder(int x, int y) {
        groundGrid[x][y] = new EmptyModel();
    }

    /**
     * @return game information like score, remaining Diamonds etc
     */
    public GameInformationModel getGameInformationModel() {
        return gameInformationModel;
    }

    /**
     * Explode the brick wall
     */
    public void explodeThisBrickWall(int x, int y) {
        groundGrid[x][y] = new EmptyModel();
        groundGrid[x][y + 1] = new EmptyModel();
    }

    /**
     * Expand the ExpandingWallModel to left
     */
    public void expandThisWallToLeft(int x, int y) {
        groundGrid[x - 1][y] = new ExpandingWallModel();
    }

    /**
     * Expand the ExpandingWallModel to right
     */
    public void expandThisWallToRight(int x, int y) {
        groundGrid[x + 1][y] = new ExpandingWallModel();
    }

    /**
     * Get the gamePaused variable
     *
     * @return gamePaused
     */
    public boolean getGamePaused() {
        return gamePaused;
    }

    /**
     * Set the gamePaused variable
     */
    public void setGamePaused(boolean gamePaused) {
        this.gamePaused = gamePaused;
    }

    /**
     * Get the mode where this levelModel is used
     *
     * @return mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * Set the mode where this levelModel is used
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

}