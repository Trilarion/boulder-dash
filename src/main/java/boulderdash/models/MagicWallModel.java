package boulderdash.models;

import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 * MagicWallModel
 * <p>
 * Represents the magic wall.
 */
public class MagicWallModel extends DisplayableElementModel {
    private static final String spriteName;
    private static final boolean isDestructible;
    private static final boolean canMove;
    private static final boolean impactExplosive;
    private static final boolean animate;
    private static final int priority;
    private static final boolean falling;
    private static final String collideSound;

    /*
      Static dataset
      Specifies the physical parameters of the object
     */
    static {
        spriteName = "magicwall";
        isDestructible = false;
        canMove = false;
        impactExplosive = false;
        animate = false;
        priority = 3;
        falling = false;
        collideSound = "touch";
    }

    private final long speed;
    /**
     * Stores the frames
     * Used for the sprites
     */
    private ArrayList<BufferedImage> framesMagicWall;
    private long previousTime;
    private int currentFrame;


    public MagicWallModel() {
        super(isDestructible, canMove, spriteName, priority, impactExplosive, animate, falling, collideSound);
        currentFrame = 0;
        speed = 100;
        initSprites();
    }

    /**
     * Function to animate the sprite
     */
    public void update(long time) {
        if (time - previousTime >= speed) {
            // Update animation
            previousTime = time;

            try {
                currentFrame += 1;

                setSprite(framesMagicWall.get(currentFrame));
            } catch (IndexOutOfBoundsException e) {
                currentFrame = 0;
            }
        }
    }

    /**
     * Init the subimages
     */
    private void initSprites() {
        framesMagicWall = new ArrayList<>();
        /* INIT SPRITE FOR DIAMOND */
        framesMagicWall.add(grabSprite(loadSprite(spriteName), 0, 0, 16, 16));
        framesMagicWall.add(grabSprite(loadSprite(spriteName), 24, 0, 16, 16));
        framesMagicWall.add(grabSprite(loadSprite(spriteName), 48, 0, 16, 16));
        framesMagicWall.add(grabSprite(loadSprite(spriteName), 72, 0, 16, 16));
    }
}
