package boulderdash.models;

import org.jetbrains.annotations.NotNull;

/**
 * Represents the steelWall
 */
public class SteelWallModel extends DisplayableElementModel {
    private static final @NotNull String spriteName;
    private static final boolean isDestructible;
    private static final boolean canMove;
    private static final boolean impactExplosive;
    private static final boolean animate;
    private static final int priority;
    private static final boolean falling;
    private static final @NotNull String collideSound;

    /*
      Static dataset
      Specifies the physical parameters of the object
     */
    static {
        spriteName = "steelwall";
        isDestructible = false;
        canMove = false;
        impactExplosive = false;
        animate = false;
        priority = 3;
        falling = false;
        collideSound = "touch";
    }

    public SteelWallModel() {
        super(isDestructible, canMove, spriteName, priority, impactExplosive, animate, falling, collideSound);
        loadSprite(spriteName);
    }
}
