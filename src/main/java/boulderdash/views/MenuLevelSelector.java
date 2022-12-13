package boulderdash.views;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Specifies the menu level selector
 */
public class MenuLevelSelector extends JComboBox<String> { // TODO composition instead of inheritance
    private String choiceValue;
    private LevelEditorView levelEditorView;

    public MenuLevelSelector(String[] items) {
        super(items);
    }

    public MenuLevelSelector(String[] items, LevelEditorView levelEditorView) {
        this(items);
        this.levelEditorView = levelEditorView;
    }

    /**
     * Called when an action is performed
     *
     * @param e Action event
     */
    public void actionPerformed(ActionEvent e) {
        choiceValue = getItemAt(getSelectedIndex());

        if (levelEditorView != null) {
            levelEditorView.menuLevelSelectorChanged(this);
        }
    }

    /**
     * Gets the choice value
     *
     * @return Choice value
     */
    public String getChoiceValue() {
        return choiceValue;
    }

    /**
     * Sets the choice value
     *
     * @param choiceValue Choice value
     */
    public void setChoiceValue(String choiceValue) {
        this.choiceValue = choiceValue;
    }

    /**
     * Selects a given value
     *
     * @param value Value to be selected
     */
    public void setSelectedValue(String value) {
        for (int i = 0; i < getItemCount(); i++) {
            if (getItemAt(i).equals(value)) {
                setSelectedIndex(i);
                break;
            }
        }
    }
}
