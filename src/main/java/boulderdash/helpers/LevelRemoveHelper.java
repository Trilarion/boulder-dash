package boulderdash.helpers;

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * Proceeds level save routine
 * Able to iterate on internal representation of a map and serialize it to XML
 */
public class LevelRemoveHelper {
    private final String levelId;

    /**
     * @param levelId Level identifier
     */
    public LevelRemoveHelper(String levelId) {
        this.levelId = levelId;

        File file = new File(getLevelPathInDataStore());
        file.delete();
    }

    /**
     * Gets level storage path
     *
     * @return Level path, with file extension
     */
    private @NotNull String getLevelPathInDataStore() {
        return "./res/levels/" + levelId + ".xml"; // TODO better path handling
    }
}
