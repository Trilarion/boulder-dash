package boulderdash.models;

/**
 * ExpandingWallModel
 * <p>
 * Represents a ExpandingWall in the game.
 *
 * @author Colin Leverger <me@colinleverger.fr>
 * @since 2015-06-19
 */
public class ExpandingWallModel extends DisplayableElementModel {
    private static final String spriteName;
    private static final boolean destructible;
    private static final boolean canMove;
    private static final boolean impactExplosive;
    private static final boolean animate;
    private static final int priority;
    private static final boolean falling;
    private static final String collideSound;

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

    /**
     * Class constructor
     */
    public ExpandingWallModel() {
        super(destructible, canMove, spriteName, priority, impactExplosive, animate, falling, collideSound);
        this.loadSprite(spriteName);
    }
}