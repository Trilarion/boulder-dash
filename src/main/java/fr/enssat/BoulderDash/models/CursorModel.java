package fr.enssat.BoulderDash.models;

/**
 * CursorModel
 * <p>
 * Represents the field cursor pointer.
 *
 * @author Valerian Saliou <valerian@valeriansaliou.name>
 * @since 2015-06-22
 */
public class CursorModel extends DisplayableElementModel {
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
        spriteName = "cursor";
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
    public CursorModel() {
        super(isDestructible, canMove, spriteName, priority, impactExplosive, animate, falling, collideSound);

        this.loadSprite(spriteName);
    }
}