package boulderdash.views;

import boulderdash.controllers.LevelEditorKeyController;
import boulderdash.models.LevelModel;


/**
 * Game field view for the level editor.
 */
public class LevelEditorGroundView extends GroundView {
    /**
     * @param levelModel Level model
     */
    public LevelEditorGroundView(LevelModel levelModel, LevelEditorView levelEditorView) {
        super(levelModel);
        addKeyListener(new LevelEditorKeyController(levelModel, levelEditorView));
    }
}
