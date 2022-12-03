package boulderdash.models;

import boulderdash.Options;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Represents a diamond in the game.
 */
public class DiamondModel extends DisplayableElementModel {
    private static final String spriteName;
    private static final boolean isDestructible;
    private static final boolean canMove;
    private static final boolean impactExplosive;
    private static final boolean animate;
    private static final int priority;
    private static final String collideSound;

    /*
      Static dataset
      Specifies the physical parameters of the object
     */
    static {
        spriteName = "diamond";
        isDestructible = true;
        canMove = true;
        impactExplosive = false;
        animate = true;
        priority = 0;
        collideSound = "coin";
    }

    private long previousTime;
    private int currentFrame;
    private long speed;
    private ArrayList<BufferedImage> framesDiamond;


    public DiamondModel() {
        super(isDestructible, canMove, spriteName, priority, impactExplosive, animate, false, collideSound);

        initSprites();
    }

    /**
     * Updates the sprite (animation loop)
     *
     * @param time Current time
     */
    public void update(long time) {
        if (time - previousTime >= speed) {
            // Update the animation
            previousTime = time;

            try {
                currentFrame += 1;
                setSprite(framesDiamond.get(currentFrame));
            } catch (IndexOutOfBoundsException e) {
                currentFrame = 0;
            }
        }
    }

    /**
     * Initialize the sprites
     * This is an animated element, hence this method
     */
    private void initSprites() {
        /* Initialize object sprites */
        framesDiamond = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            framesDiamond.add(
                    grabSprite(loadSprite(spriteName), i * 24, 0, Options.SPRITE_SIZE_X, Options.SPRITE_SIZE_Y)
            );
        }
    }
}