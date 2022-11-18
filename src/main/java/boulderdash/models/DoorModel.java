package boulderdash.models;

/**
 * DoorModel
 * <p>
 * Represents escape door.
 *
 * @author Colin Leverger <me@colinleverger.fr>
 * @since 2015-06-19
 */
public class DoorModel extends DisplayableElementModel {
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
        spriteName = "door";
        isDestructible = false;
        canMove = false;
        impactExplosive = false;
        animate = false;
        priority = 0;
        falling = false;
        collideSound = null;
    }

    /**
     * Class constructor
     */
    public DoorModel() {
        super(isDestructible, canMove, spriteName, priority, impactExplosive, animate, falling, collideSound);

        this.loadSprite(spriteName);
    }
}
