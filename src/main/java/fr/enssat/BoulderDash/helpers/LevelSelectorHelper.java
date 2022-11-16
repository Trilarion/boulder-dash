package fr.enssat.BoulderDash.helpers;

import fr.enssat.BoulderDash.views.MenuLevelSelector;
import fr.enssat.BoulderDash.views.LevelEditorView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * LevelSelectorHelper
 *
 * Level selector helper
 *
 * @author      Valerian Saliou <valerian@valeriansaliou.name>
 * @since       2015-06-23
 */
public class LevelSelectorHelper {
    private static String levelStorage = "./res/levels";
    private boolean hasEmptyElement = false;
    private LevelEditorView levelEditorView = null;

    /**
     * Class constructor
     */
    public LevelSelectorHelper(boolean hasEmptyElement) {
        this.hasEmptyElement = hasEmptyElement;
    }

    public LevelSelectorHelper(boolean hasEmptyElement, LevelEditorView levelEditorView) {
        this(hasEmptyElement);

        this.levelEditorView = levelEditorView;
    }

    /**
     * Creates the level list
     *
     * @return  Level list selector
     */
    public MenuLevelSelector createLevelList() {
        String[] availableLevels = this.listAvailableLevels();

        // Proceed available levels listing
        MenuLevelSelector menuLevelList = new MenuLevelSelector(availableLevels, this.levelEditorView);

        if(availableLevels.length > 0) {
            menuLevelList.setChoiceValue(availableLevels[0]);
            menuLevelList.setSelectedIndex(0);
        };

        menuLevelList.addActionListener(menuLevelList);

        return menuLevelList;
    }

    /**
     * Lists available levels and store them in instance context
     *
     * @return  Available levels
     */
    private String[] listAvailableLevels() {
        final String[] availableLevels = {"01", "02", "03", "04", "05"}; // TODO search for files in resources
        return availableLevels;
    }
}
