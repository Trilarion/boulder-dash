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
 */
public class GameGroundView extends GroundView {

    /**
     * @param gameController Game controller
     * @param levelModel     Level model
     */
    public GameGroundView(GameController gameController, LevelModel levelModel) {
        super(levelModel);

        addKeyListener(new GameKeyController(getLevelModel(), gameController.getAudioLoadHelper()));

        setBorder(BorderFactory.createLineBorder(Color.black));
        setFocusable(true);
    }
}
