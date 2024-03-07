package boulderdash.models;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Represents a abstract displayable element
 */
public abstract class DisplayableElementModel {
    private static final @NotNull String groupName;
    private static final @NotNull String stateValue;

    /*
      Static dataset
     */
    static {
        groupName = "field";
        stateValue = "initial";
    }

    private final boolean destructible;
    private final boolean moving;
    private final String spriteName;
    private boolean animate;
    private boolean impactExplosive;
    private int priority;
    private @Nullable BufferedImage sprite;
    private boolean falling;
    private boolean convertible;
    private String collideSound;

    /**
     * @param destructible    Object destructible?
     * @param moving          Object is moving?
     * @param spriteName      Object sprite name?
     * @param priority        Object priority?
     * @param impactExplosive Object explodes on impact?
     * @param animate         Object can be animated?
     */
    public DisplayableElementModel(boolean destructible, boolean moving, String spriteName, int priority, boolean impactExplosive, boolean animate, boolean falling, String collideSound, boolean convertible) {
        this.moving = moving;
        this.destructible = destructible;
        this.spriteName = spriteName;
        this.priority = priority;
        this.animate = animate;
        this.impactExplosive = impactExplosive;
        this.falling = falling;
        this.convertible = convertible;
        this.collideSound = collideSound;
    }

    public DisplayableElementModel(boolean destructible, boolean moving, String spriteName, int priority, boolean impactExplosive, boolean animate, boolean falling, String collideSound) {
        this(
                destructible, moving, spriteName, priority, impactExplosive, animate, falling, collideSound, false
        );
    }

    /**
     * Gets the group name value
     *
     * @return Group name value
     */
    public static String getGroupName() {
        return groupName;
    }

    /**
     * Gets the state value
     *
     * @return State value
     */
    public static String getStateValue() {
        return stateValue;
    }

    /**
     * Gets the 'destructible' value
     *
     * @return Whether object is destructible or not
     */
    public boolean isDestructible() {
        return destructible;
    }

    /**
     * Gets the 'moving' value
     *
     * @return Whether object is moving or not
     */
    public boolean isMoving() {
        return moving;
    }

    /**
     * Gets the sprite name value
     *
     * @return Sprite name value
     */
    public String getSpriteName() {
        return spriteName;
    }

    /**
     * Gets the priority of the object
     *
     * @return Object priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Sets the priority of the object
     *
     * @param priority Object priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Gets the 'animate' value
     *
     * @return Whether object is animated or not
     */
    public boolean isAnimate() {
        return animate;
    }

    /**
     * Sets the 'animate' value
     */
    public void setAnimate(boolean animate) {
        this.animate = animate;
    }

    /**
     * Gets the 'impact explosive' value
     *
     * @return Whether object explodes on impact or not
     */
    public boolean isImpactExplosive() {
        return impactExplosive;
    }

    /**
     * Sets the 'impact explosive' value
     */
    public void setImpactExplosive(boolean impactExplosive) {
        this.impactExplosive = impactExplosive;
    }

    /**
     * Gets the sprite
     *
     * @return Sprite object
     */
    public BufferedImage getSprite() {
        return sprite;
    }

    /**
     * Sets the sprite
     *
     * @param sprite Sprite object
     */
    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    /**
     * Loads the target sprite
     *
     * @param spriteName Sprite name
     * @return Sprite object
     */
    public @Nullable BufferedImage loadSprite(String spriteName) {
        BufferedImage sprite = null;

        try {
            sprite = ImageIO.read(DisplayableElementModel.class.getResourceAsStream("/drawable/field/" + spriteName + ".gif"));
        } catch (IOException e) {
            e.printStackTrace(); // TODO rethrow exception
        }

        this.sprite = sprite;

        return sprite;
    }

    /**
     * Grabs the sprite from the large image containing all the static sprites items
     *
     * @param spriteSheet Sprite sheet instance
     * @param x           Sub image horizontal offset on sprite sheet
     * @param y           Sub image vertical offset on sprite sheet
     * @param width       Sub image width on sprite sheet
     * @param height      Sub image height on sprite sheet
     * @return Target sub image
     */
    public BufferedImage grabSprite(@NotNull BufferedImage spriteSheet, int x, int y, int width, int height) {
        BufferedImage subImage = spriteSheet.getSubimage(x, y, width, height);

        sprite = subImage;
        return subImage;
    }

    /**
     * Gets the falling state of the object
     *
     * @return Whether object is falling or not
     */
    public boolean isFalling() {
        return falling;
    }

    /**
     * Sets the falling state of the object
     *
     * @param falling Whether object is falling or not
     */
    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    /**
     * Gets the collide-sound of the object
     *
     * @return Collide sound
     */
    public String getCollideSound() {
        return collideSound;
    }

    /**
     * Sets the collide-sound of the object
     *
     * @param collideSound Collide sound
     */
    public void setCollideSound(String collideSound) {
        this.collideSound = collideSound;
    }

    /**
     * Gets the convertible value of the object
     *
     * @return Convertible value
     */
    public boolean isConvertible() {
        return convertible;
    }

    /**
     * Sets the convertible value of the object
     *
     * @param convertible Convertible value
     */
    public void setConvertibleValue(boolean convertible) {
        this.convertible = convertible;
    }

    /**
     * Function to update the sprites
     *
     * @param currentTimeMillis Current time in milliseconds
     */
    public void update(long currentTimeMillis) {
    }
}
