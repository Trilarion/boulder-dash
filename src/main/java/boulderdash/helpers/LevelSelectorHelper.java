package boulderdash.helpers;

import boulderdash.views.LevelEditorView;
import boulderdash.views.MenuLevelSelector;

/**
 * Level selector helper
 */
public class LevelSelectorHelper {
    private LevelEditorView levelEditorView;

    public LevelSelectorHelper(LevelEditorView levelEditorView) {
        this.levelEditorView = levelEditorView;
    }

    /**
     * Lists available levels and store them in instance context
     *
     * @return Available levels
     */
    private static String[] listAvailableLevels() {
        return new String[]{"01", "02", "03", "04", "05"};
    } // TODO discover from resources the available levels, do not hardcode

    /**
     * Creates the level list
     *
     * @return Level list selector
     */
    public MenuLevelSelector createLevelList() { // TODO this can become static and later go into a helper class
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
}
