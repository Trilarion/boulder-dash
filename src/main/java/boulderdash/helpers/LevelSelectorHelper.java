package boulderdash.helpers;

import boulderdash.views.LevelEditorView;
import boulderdash.views.MenuLevelSelector;

/**
 * Level selector helper
 */
public class LevelSelectorHelper {
    private LevelEditorView levelEditorView;

    public LevelSelectorHelper(boolean hasEmptyElement) {
    }

    public LevelSelectorHelper(boolean hasEmptyElement, LevelEditorView levelEditorView) {
        this(hasEmptyElement);

        this.levelEditorView = levelEditorView;
    }

    /**
     * Creates the level list
     *
     * @return Level list selector
     */
    public MenuLevelSelector createLevelList() {
        String[] availableLevels = listAvailableLevels();

        // Proceed available levels listing
        MenuLevelSelector menuLevelList = new MenuLevelSelector(availableLevels, levelEditorView);

        if (availableLevels.length > 0) {
            menuLevelList.setChoiceValue(availableLevels[0]);
            menuLevelList.setSelectedIndex(0);
        }

        menuLevelList.addActionListener(menuLevelList);

        return menuLevelList;
    }

    /**
     * Lists available levels and store them in instance context
     *
     * @return Available levels
     */
    private static String[] listAvailableLevels() {
        return new String[]{"01", "02", "03", "04", "05"};
    }
}
