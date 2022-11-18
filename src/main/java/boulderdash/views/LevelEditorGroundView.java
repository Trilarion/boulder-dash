package boulderdash.views;

import boulderdash.controllers.LevelEditorKeyController;
import boulderdash.models.LevelModel;


/**
 * LevelEditorFieldView
 * <p>
 * Game field view for the level editor.
 *
 * @author Valerian Saliou <valerian@valeriansaliou.name>
 * @since 2015-06-21
 */
public class LevelEditorGroundView extends GroundView {
    /**
     * Class constructor
     *
     * @param levelModel Level model
     */
    public LevelEditorGroundView(LevelModel levelModel, LevelEditorView levelEditorView) {
        super(levelModel);

        this.addKeyListener(new LevelEditorKeyController(levelModel, levelEditorView));
    }
}
