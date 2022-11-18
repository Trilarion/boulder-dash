package boulderdash.views;

import boulderdash.models.LevelModel;
import boulderdash.utils.Observer;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;


/**
 * InformationPanel
 * <p>
 * Information panel element.
 *
 * @author Colin Leverger <me@colinleverger.fr>
 * @since 2015-06-20
 */
public class InformationPanel extends JPanel implements Observer<String> {
    private final LevelModel levelModel;
    private final JTextArea text;

    /**
     * Class constructor
     */
    public InformationPanel(LevelModel levelModel) {
        this.levelModel = levelModel;
        this.text = new JTextArea();
        this.text.setEditable(false);
        this.levelModel.getGameInformationModel().addObserver(this);

        this.text.setText(
                "Score : " + levelModel.getGameInformationModel().getScore() +
                        "\nRemaining diamonds : " + levelModel.getGameInformationModel().getRemainingsDiamonds()
        );

        this.add(this.text);
    }

    /**
     * Updates the panel
     */
    @Override
    public void update(@NotNull String notification) {
        this.text.setText(
                "Score : " + this.levelModel.getGameInformationModel().getScore() +
                        "\nRemaining diamonds : " + this.levelModel.getGameInformationModel().getRemainingsDiamonds()
        );
    }
}
