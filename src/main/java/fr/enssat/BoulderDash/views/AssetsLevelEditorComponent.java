package fr.enssat.BoulderDash.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;


/**
 * AssetsLevelEditorComponent
 * <p>
 * Information panel element.
 *
 * @author Valerian Saliou <valerian@valeriansaliou.name>
 * @since 2015-06-22
 */
public class AssetsLevelEditorComponent extends JPanel implements ActionListener {
    /**
     * Available choices
     */
    static List<String> choiceList = Arrays.asList(
            "Boulder", "Diamond", "Dirt", "Brick Wall", "Expanding Wall", "Magic Wall", "Steel Wall", "Rockford"
    );
    private final LevelEditorView levelEditorView;

    /**
     * Class constructor
     *
     * @param levelEditorView Controller for level editor
     */
    public AssetsLevelEditorComponent(LevelEditorView levelEditorView) {
        super(new BorderLayout());

        this.levelEditorView = levelEditorView;
        ButtonGroup buttonGroup = new ButtonGroup();
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));

        String curListChoice;

        for (String s : choiceList) {
            curListChoice = s;

            // Create radio buttons from list
            JRadioButton curButton = new JRadioButton(curListChoice);
            //boulderButton.setMnemonic(KeyEvent.VK_A);
            curButton.setActionCommand(curListChoice);

            // Group the radio buttons
            buttonGroup.add(curButton);

            // Register a listener for the radio buttons
            curButton.addActionListener(this);

            // Put the radio buttons in a column in a panel
            radioPanel.add(curButton);
        }

        this.add(radioPanel, BorderLayout.LINE_START);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    /**
     * Listens for action events
     *
     * @param e Action event
     */
    public void actionPerformed(ActionEvent e) {
        JRadioButton sourceButton = (JRadioButton) e.getSource();
        String sourceText = sourceButton.getText();

        this.levelEditorView.setPickedBlockValue(sourceText);
        this.levelEditorView.getLevelEditorGroundView().grabFocus();
    }
}