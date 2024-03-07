package boulderdash.models;

import org.jetbrains.annotations.NotNull;

/**
 * Represents the boulders.
 */
public class BoulderModel extends DisplayableElementModel {
    private static final @NotNull String spriteName;
    private static final boolean isDestructible;
    private static final boolean canMove;
    private static final boolean impactExplosive;
    private static final boolean animate;
    private static final int priority;
    private static final @NotNull String collideSound;

    /*
      Static dataset
      Specifies the physical parameters of the object
     */
    static { // TODO maybe create Enums from it
        spriteName = "boulder";
        isDestructible = false;
        canMove = true;
        impactExplosive = false;
        animate = true;
        priority = 2;
        collideSound = "die";
    }

    public BoulderModel(boolean convertible) {
        super(isDestructible, canMove, spriteName, priority, impactExplosive, animate, false, collideSound, convertible);
        loadSprite(spriteName);
    }

}