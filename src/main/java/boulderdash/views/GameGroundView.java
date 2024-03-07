package boulderdash.views;

import boulderdash.controllers.GameController;
import boulderdash.controllers.GameKeyController;
import boulderdash.models.LevelModel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Game field view for the game itself.
 */
public class GameGroundView extends GroundView {

    /**
     * @param gameController Game controller
     * @param levelModel     Level model
     */
    public GameGroundView(@NotNull GameController gameController, LevelModel levelModel) {
        super(levelModel);

        addKeyListener(new GameKeyController(getLevelModel(), gameController.getAudioLoadHelper()));

        setBorder(BorderFactory.createLineBorder(Color.black));
        setFocusable(true);
    }
}
