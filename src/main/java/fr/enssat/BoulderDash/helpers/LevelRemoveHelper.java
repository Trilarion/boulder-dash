package fr.enssat.BoulderDash.helpers;


import java.io.File;

/**
 * LevelRemoveHelper
 * <p>
 * Proceeds level save routine
 * Able to iterate on internal representation of a map and serialize it to XML
 *
 * @author Valerian Saliou <valerian@valeriansaliou.name>
 * @since 2015-06-21
 */
public class LevelRemoveHelper {
    private static final String pathToDataStore = "./res/levels";
    private String levelId = null;

    /**
     * Class constructor
     *
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
