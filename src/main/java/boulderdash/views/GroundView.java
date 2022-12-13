package boulderdash.views;

import boulderdash.models.LevelModel;
import boulderdash.ui.UIHelper;
import boulderdash.utils.Observer;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * FieldView, created by controller; we notice that we don't need to make
 * levelModel observable; Because of the sprites we have to refresh the game
 * windows very often so don't need of observers/observable mechanism
 * <p>
 * This view is basically drawing into the Frame the levelModel.
 */
public abstract class GroundView extends JPanel implements Observer<String> {
    private final LevelModel levelModel;

    /**
     * @param levelModel Level model
     */
    public GroundView(LevelModel levelModel) {
        this.levelModel = levelModel;
        this.levelModel.addObserver(this);
    }

    /**
     * Paints the map
     *
     * @param g Map graphical object
     */
    public void paint(Graphics g) {
        int width = levelModel.getSizeWidth();
        int height = levelModel.getSizeHeight();
        /**
         * Draws the map
         *
         * @param width  Map width
         * @param height Map height
         * @param g      Map graphical object
         */

        // Draw items
        if ("game".equals(levelModel.getMode())) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    g.drawImage(levelModel.getImage(x, y), (x * 16), (y * 16), this); // TODO replace constant (16) with Options
                }
            }

            if (!levelModel.isGameRunning()) {
                if (!levelModel.getRockford().getHasExploded()) {
                    UIHelper.displayWin();
                } else {
                    UIHelper.displayLose();
                }
            }
        } else {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    g.drawImage(levelModel.getImage(x, y), (x * 16), (y * 16), this);
                }
            }
            if (levelModel.getShowCursor()) {
                g.drawImage(
                        levelModel.getCursorImage(),
                        ((levelModel.getCursorXPosition() + 1) * 16),
                        ((levelModel.getCursorYPosition() + 1) * 16),
                        this
                );
            }
        }
    }

    /**
     * Updates the view
     */
    @Override
    public void update(@NotNull String notification) {
        repaint();
    }

    public LevelModel getLevelModel() {
        return levelModel;
    }
}