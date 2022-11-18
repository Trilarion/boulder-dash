package boulderdash.views;

import boulderdash.controllers.GameController;
import boulderdash.controllers.GameKeyController;
import boulderdash.models.LevelModel;

import javax.swing.*;
import java.awt.*;


/**
 * GameFieldView
 * <p>
 * Game field view for the game itself.
 *
 * @author Valerian Saliou <valerian@valeriansaliou.name>
 * @since 2015-06-21
 */
public class GameGroundView extends GroundView {

    /**
     * Class constructor
     *
     * @param gameController Game controller
     * @param levelModel     Level model
     */
    public GameGroundView(GameController gameController, LevelModel levelModel) {
        super(levelModel);

        this.addKeyListener(new GameKeyController(this.levelModel, gameController.getAudioLoadHelper()));

        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setFocusable(true);
    }
}
