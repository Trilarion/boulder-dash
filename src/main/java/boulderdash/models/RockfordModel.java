package boulderdash.models;

import boulderdash.Options;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Represents the hero of the game.
 */
public class RockfordModel extends DisplayableElementModel {
    private static final String spriteName;
    private static final boolean isDestructible;
    private static final boolean canMove;
    private static final boolean impactExplosive;
    private static final boolean animate;
    private static final int priority;
    private static final boolean falling;
    private static final String collideSound;

    /**
     * Maps the sub images of the sprite file
     */
    private static ArrayList<BufferedImage> framesBlinking;
    private static ArrayList<BufferedImage> framesRunningLeft;
    private static ArrayList<BufferedImage> framesRunningRight;
    private static ArrayList<BufferedImage> framesRunningUpOrDown;

    /*
      Static dataset
      Specifies the physical parameters of the object
     */
    static {
        spriteName = "rockford";
        isDestructible = true;
        canMove = true;
        impactExplosive = true;
        animate = true;
        priority = 1;
        falling = false;
        collideSound = null;
    }

    /**
     * Defines the current speed of the object
     */
    private long speed;
    /**
     * Maps possible states for Rockford
     */
    private boolean isCollisionDone;
    private boolean isStaying = true;
    private boolean isRunningLeft;
    private boolean isRunningRight;
    private boolean isRunningUp;
    private boolean isRunningDown;
    private long previousTime;
    private int currentFrame;
    private boolean hasExploded;


    public RockfordModel() {
        super(isDestructible, canMove, spriteName, priority, impactExplosive, animate, falling, collideSound);
        // Speed of the animation of the sprite
        setSpeed(100);
        // Init the sprites in arrays

        // Initializes all sprites from the main image
        //  Takes the sub images and append them into storage arrays
        framesBlinking = new ArrayList<>();
        framesRunningLeft = new ArrayList<>();
        framesRunningRight = new ArrayList<>();
        framesRunningUpOrDown = new ArrayList<>();

        /* INIT SPRITE ARRAYS FOR ROCKFORD */
        for (int i = 0; i < 8; i++) {
            framesBlinking.add(
                    grabSprite(loadSprite(spriteName), 7 + (24 * i), 79, Options.SPRITE_SIZE_X, Options.SPRITE_SIZE_Y)
            );

            framesRunningLeft.add(
                    grabSprite(loadSprite(spriteName), 7 + (24 * i), 103, Options.SPRITE_SIZE_X, Options.SPRITE_SIZE_Y)
            );

            framesRunningRight.add(
                    grabSprite(loadSprite(spriteName), 7 + (24 * i), 127, Options.SPRITE_SIZE_X, Options.SPRITE_SIZE_Y)
            );
        }

        framesRunningUpOrDown.add(
                grabSprite(loadSprite(spriteName), 7, 7, Options.SPRITE_SIZE_X, Options.SPRITE_SIZE_Y)
        );
        hasExploded = false;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Updates the sprite animation
     * (And only that single thing)
     */
    public void update(long time) {
        if (time - previousTime >= speed) {
            // Update the animation
            previousTime = time;
            try {
                currentFrame += 1;

                if (isStaying) {
                    setSprite(framesBlinking.get(currentFrame));
                } else if (isRunningLeft) {
                    setSprite(framesRunningLeft.get(currentFrame));
                } else if (isRunningRight) {
                    setSprite(framesRunningRight.get(currentFrame));
                } else if (isRunningUpOrDown()) {
                    setSprite(framesRunningUpOrDown.get(currentFrame));
                }
            } catch (IndexOutOfBoundsException e) {
                currentFrame = 0;
            }
        }
    }

    /**
     * Stops the Rockford movement
     */
    public void startStaying() {
        isCollisionDone = false;
        isStaying = true;
        isRunningLeft = false;
        isRunningRight = false;
        isRunningUp = false;
        isRunningDown = false;
        previousTime = 0;
        currentFrame = 0;
    }

    /**
     * Starts moving Rockford to the left
     */
    public void startRunningLeft() {
        isCollisionDone = false;
        isStaying = false;
        isRunningLeft = true;
        isRunningRight = false;
        isRunningUp = false;
        isRunningDown = false;
        previousTime = 0;
    }

    /**
     * Starts moving Rockford to the right
     */
    public void startRunningRight() {
        isCollisionDone = false;
        isStaying = false;
        isRunningLeft = false;
        isRunningRight = true;
        isRunningUp = false;
        isRunningDown = false;
        previousTime = 0;
    }

    /**
     * Rockford running up
     */
    public void startRunningUp() {
        isCollisionDone = false;
        isStaying = false;
        isRunningLeft = false;
        isRunningRight = false;
        isRunningUp = true;
        isRunningDown = false;
        previousTime = 0;
    }

    /**
     * Rockford running down
     */
    public void startRunningDown() {
        isCollisionDone = false;
        isStaying = false;
        isRunningLeft = false;
        isRunningRight = false;
        isRunningUp = false;
        isRunningDown = true;
        previousTime = 0;
    }

    /**
     * Gets whether Rockford collision has been handled or not
     *
     * @return Rockford's collision handled or not
     */
    public boolean isCollisionDone() {
        return isCollisionDone;
    }

    /**
     * Sets whether Rockford's collision has been handled or not
     *
     * @param isCollisionDone Rockford's collision handled or not
     */
    public void setCollisionDone(boolean isCollisionDone) {
        this.isCollisionDone = isCollisionDone;
    }

    /**
     * Gets whether Rockford is standing still or not
     *
     * @return Rockford staying or not
     */
    public boolean isStaying() {
        return isStaying;
    }

    /**
     * Gets whether Rockford is running to the left or not
     *
     * @return Rockford running to the left or not
     */
    public boolean isRunningLeft() {
        return isRunningLeft;
    }

    /**
     * Gets whether Rockford is running to the right or not
     *
     * @return Rockford running to the right or not
     */
    public boolean isRunningRight() {
        return isRunningRight;
    }

    /**
     * Gets whether Rockford is running up or not
     *
     * @return Rockford running up, or not
     */
    public boolean isRunningUp() {
        return isRunningUp;
    }

    /**
     * Gets whether Rockford is running down or not
     *
     * @return Rockford running down, or not
     */
    public boolean isRunningDown() {
        return isRunningDown;
    }

    /**
     * Gets whether Rockford is running up or down, or not
     *
     * @return Rockford running up or down, or not
     */
    public boolean isRunningUpOrDown() {
        return isRunningUp || isRunningDown;
    }

    /**
     * Return true if rockford has exploded (you = lose)
     *
     * @return Whether Rockford has exploded or not
     */
    public boolean getHasExploded() {
        return hasExploded;
    }

    /**
     * Set rockford exploded state
     *
     * @param hasExploded Whether Rockford has exploded or not
     */
    public void setHasExploded(boolean hasExploded) {
        this.hasExploded = hasExploded;
    }
}
