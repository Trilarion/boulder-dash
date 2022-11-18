package boulderdash.models;

/**
 * SteelWallModel
 * <p>
 * Represents the steelWall
 *
 * @author Colin Leverger <me@colinleverger.fr>
 * @since 2015-06-19
 */
public class SteelWallModel extends DisplayableElementModel {
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
        spriteName = "steelwall";
        isDestructible = false;
        canMove = false;
        impactExplosive = false;
        animate = false;
        priority = 3;
        falling = false;
        collideSound = "touch";
    }

    /**
     * Class constructor
     */
    public SteelWallModel() {
        super(isDestructible, canMove, spriteName, priority, impactExplosive, animate, falling, collideSound);
        this.loadSprite(spriteName);
    }
}
