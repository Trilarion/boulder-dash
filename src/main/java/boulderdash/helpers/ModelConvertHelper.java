package boulderdash.helpers;

import boulderdash.exceptions.UnknownModelException;
import boulderdash.models.*;

/**
 * Provides model conversion services.
 */
public class ModelConvertHelper {

    /**
     * Gets the model associated to the string
     *
     * @param spriteName Sprite name
     * @return Model associated to given sprite name
     */
    public static DisplayableElementModel toModel(String spriteName, boolean isConvertible) throws UnknownModelException {
        DisplayableElementModel element;

        // Instantiates the sprite element matching the given sprite name
        switch (spriteName) { // TODO this is a mapping from String to Model (can be done better?)
            case "black":
            case "Black":
                element = new EmptyModel();
                break;

            case "boulder":
            case "Boulder":
                element = new BoulderModel(isConvertible);
                break;

            case "brickwall":
            case "Brick Wall":
                element = new BrickWallModel();
                break;

            case "diamond":
            case "Diamond":
                element = new DiamondModel();
                break;

            case "dirt":
            case "Dirt":
                element = new DirtModel();
                break;

            case "magicwall":
            case "Magic Wall":
                element = new MagicWallModel();
                break;

            case "rockford":
            case "Rockford":
                element = new RockfordModel();
                break;

            case "steelwall":
            case "Steel Wall":
                element = new SteelWallModel();
                break;

            case "expandingwall":
            case "Expanding Wall":
                element = new ExpandingWallModel();
                break;

            default:
                throw new UnknownModelException("Unknown model element > " + spriteName);
        }

        return element;
    }

}
