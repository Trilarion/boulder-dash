package fr.enssat.BoulderDash.views;

import fr.enssat.BoulderDash.controllers.GameController;
import fr.enssat.BoulderDash.controllers.GameKeyController;
import fr.enssat.BoulderDash.models.LevelModel;

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
    private final GameController gameController;

    /**
     * Class constructor
     *
     * @param gameController Game controller
     * @param levelModel     Level model
     */
    public GameGroundView(GameController gameController, LevelModel levelModel) {
        super(levelModel);

        this.gameController = gameController;

        this.addKeyListener(new GameKeyController(this.levelModel, this.gameController.getAudioLoadHelper()));

        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setFocusable(true);
    }
}
