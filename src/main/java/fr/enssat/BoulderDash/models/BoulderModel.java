package fr.enssat.BoulderDash.models;

/**
 * BoulderModel
 * <p>
 * Represents the boulders.
 *
 * @author Colin Leverger <me@colinleverger.fr>
 * @since 2015-06-19
 */
public class BoulderModel extends DisplayableElementModel {
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
        spriteName = "boulder";
        isDestructible = false;
        canMove = true;
        impactExplosive = false;
        animate = true;
        priority = 2;
        collideSound = "die";
    }

    /**
     * Class constructor
     */
    public BoulderModel(boolean convertible) {
        super(isDestructible, canMove, spriteName, priority, impactExplosive, animate, false, collideSound, convertible);
        this.loadSprite(spriteName);
    }

    public BoulderModel() {
        this(false);
    }
}