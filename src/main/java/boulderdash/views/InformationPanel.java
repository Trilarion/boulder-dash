package boulderdash.views;

import boulderdash.models.LevelModel;
import boulderdash.utils.Observer;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;


/**
 * Information panel element.
 */
public class InformationPanel extends JPanel implements Observer<String> {
    private final LevelModel levelModel;
    private final JTextArea text;

    public InformationPanel(LevelModel levelModel) {
        this.levelModel = levelModel;
        text = new JTextArea();
        text.setEditable(false);
        this.levelModel.getGameInformationModel().addObserver(this);

        text.setText(
                "Score : " + levelModel.getGameInformationModel().getScore() +
                        "\nRemaining diamonds : " + levelModel.getGameInformationModel().getRemainingDiamonds()
        );

        add(text);
    }

    /**
     * Updates the panel
     */
    @Override
    public void update(@NotNull String notification) {
        text.setText(
                "Score : " + levelModel.getGameInformationModel().getScore() +
                        "\nRemaining diamonds : " + levelModel.getGameInformationModel().getRemainingDiamonds()
        );
    }
}
