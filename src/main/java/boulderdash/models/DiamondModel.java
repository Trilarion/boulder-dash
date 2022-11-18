package boulderdash.models;

import boulderdash.Options;

import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 * DiamondModel
 * <p>
 * Represents a diamond in the game.
 *
 * @author Colin Leverger <me@colinleverger.fr>
 * @since 2015-06-19
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

    /**
     * Class constructor
     */
    public DiamondModel() {
        super(isDestructible, canMove, spriteName, priority, impactExplosive, animate, false, collideSound);

        this.initSprites();
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
                this.currentFrame += 1;
                this.setSprite(framesDiamond.get(this.currentFrame));
            } catch (IndexOutOfBoundsException e) {
                this.currentFrame = 0;
            }
        }
    }

    /**
     * Initialize the sprites
     * This is an animated element, hence this method
     */
    private void initSprites() {
        /* Initialize object sprites */
        this.framesDiamond = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            this.framesDiamond.add(
                    this.grabSprite(loadSprite(spriteName), i * 24, 0, Options.SIZE_X_OF_SPRITE, Options.SIZE_Y_OF_SPRITE)
            );
        }
    }
}