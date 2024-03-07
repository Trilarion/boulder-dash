package boulderdash.models;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a ExpandingWall in the game.
 */
public class ExpandingWallModel extends DisplayableElementModel {
    private static final @NotNull String spriteName;
    private static final boolean destructible;
    private static final boolean canMove;
    private static final boolean impactExplosive;
    private static final boolean animate;
    private static final int priority;
    private static final boolean falling;
    private static final @Nullable String collideSound;

    /*
     * Static dataset
     * Specifies the physical parameters of the object
     */
    static {
        spriteName = "expandingwall";
        destructible = false;
        canMove = false;
        impactExplosive = false;
        animate = false;
        priority = 10;
        falling = false;
        collideSound = null;
    }

    public ExpandingWallModel() {
        super(destructible, canMove, spriteName, priority, impactExplosive, animate, falling, collideSound);
        loadSprite(spriteName);
    }
}