package boulderdash.helpers;


import java.io.File;

/**
 * LevelRemoveHelper
 * <p>
 * Proceeds level save routine
 * Able to iterate on internal representation of a map and serialize it to XML
 */
public class LevelRemoveHelper {
    private static final String pathToDataStore = "./res/levels";
    private String levelId = null;

    /**
     * @param levelId Level identifier
     */
    public LevelRemoveHelper(String levelId) {
        this.levelId = levelId;

        File file = new File(this.getLevelPathInDataStore());
        file.delete();
    }

    /**
     * Gets level storage path
     *
     * @return Level path, with file extension
     */
    private String getLevelPathInDataStore() {
        return pathToDataStore + "/" + this.levelId + ".xml";
    }
}
