package boulderdash.views;

import boulderdash.controllers.LevelEditorController;
import boulderdash.controllers.NavigationBetweenViewController;
import boulderdash.helpers.LevelSelectorHelper;
import boulderdash.models.LevelModel;

import javax.swing.*;
import java.awt.*;


/**
 * LevelEditorView
 * <p>
 * Specifies the level editor view.
 *
 * @author Colin Leverger <me@colinleverger.fr>
 * @since 2015-06-19
 */
public class LevelEditorView extends JFrame {
    private final NavigationBetweenViewController nav;
    private LevelEditorGroundView fieldPanel;
    private String selectedLevel;
    private MenuLevelSelector menuLevelSelector;
    private LevelEditorController levelEditorController;
    private LevelModel levelModel;

    private String pickedBlockValue;

    /**
     * Class constructor
     */
    public LevelEditorView(LevelEditorController levelEditorController, LevelModel levelModel, NavigationBetweenViewController nav) {
        this.levelEditorController = levelEditorController;
        this.levelModel = levelModel;
        this.nav = nav;

        this.initializeView();
        this.createLayout();

        this.fieldPanel.grabFocus();
    }

    /**
     * Initializes the view layout
     */
    private void initializeView() {
        this.setFocusable(true);
        this.setVisible(false);
        this.setResizable(false);

        // UI parameters
        this.setSize(984, 454);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // App parameters
        this.setTitle("Boulder Dash | Level Editor");

        Image appIcon = Toolkit.getDefaultToolkit().getImage("./res/app/app_icon.png");
        this.setIconImage(appIcon);
    }

    /**
     * Creates the view layout
     */
    private void createLayout() {
        // List of levels
        LevelSelectorHelper levelSelectorHelper = new LevelSelectorHelper(true, this);
        this.menuLevelSelector = levelSelectorHelper.createLevelList();

        // Field + select panels
        this.fieldPanel = new LevelEditorGroundView(this.levelModel, this);
        JPanel selectPanel = new JPanel();

        AssetsLevelEditorComponent assetsComponent = new AssetsLevelEditorComponent(this);
        JPanel actionsComponent = new JPanel();

        // Add actions
        actionsComponent.add(this.menuLevelSelector);
        actionsComponent.add(this.createButton("save", "Save"));
        actionsComponent.add(this.createButton("delete", "Delete"));
        actionsComponent.add(this.createButton("new", "New level"));
        actionsComponent.add(this.createButton("menu", "Menu"));
        actionsComponent.add(this.createButton("help", "Help"));

        // Add select panel subcomponents
        selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.Y_AXIS));

        selectPanel.add(assetsComponent);
        selectPanel.add(actionsComponent);

        // Add top components
        this.add(this.fieldPanel, BorderLayout.CENTER);
        this.add(selectPanel, BorderLayout.WEST);
    }

    /**
     * Creates the given button
     *
     * @param id   Button identifier
     * @param name Button name
     * @return Created button
     */
    public JButton createButton(String id, String name) {
        JButton button = new JButton(name);
        button.addActionListener(this.levelEditorController);
        button.setActionCommand(id);

        return button;
    }

    /**
     * Gets the level editor field view
     *
     * @return Level editor field view
     */
    public LevelEditorGroundView getLevelEditorGroundView() {
        return this.fieldPanel;
    }

    /**
     * Gets picked block value
     *
     * @return Picked block value
     */
    public String getPickedBlockValue() {
        return this.pickedBlockValue;
    }

    /**
     * Sets picked block value
     *
     * @param pickedBlockValue Picked block value
     */
    public void setPickedBlockValue(String pickedBlockValue) {
        this.pickedBlockValue = pickedBlockValue;
    }

    /**
     * Change opened level
     *
     * @param selectedLevelValue Selected level value
     */
    public void openedLevelChange(String selectedLevelValue) {
        LevelModel pickedLevelModel;

        if (selectedLevelValue != null && !selectedLevelValue.isEmpty()) {
            // Load existing model
            pickedLevelModel = new LevelModel(selectedLevelValue, this.nav.getAudioLoadHelper(), "editor");
        } else {
            // New blank model for editor
            pickedLevelModel = new LevelModel(this.nav.getAudioLoadHelper());
        }

        pickedLevelModel.setShowCursor(true);
        pickedLevelModel.setMode("editor");
        this.levelModel = pickedLevelModel;

        // Hide old view
        this.levelEditorController.getLevelEditorView().dispose();

        this.levelEditorController = new LevelEditorController(this.levelModel, this.nav);

        this.levelEditorController.getLevelEditorView().setSelectedLevel(selectedLevelValue);
        this.levelEditorController.getLevelEditorView().setVisible(true);
        this.levelEditorController.getLevelEditorView().getLevelEditorGroundView().grabFocus();

        this.levelEditorController.getLevelEditorView().menuLevelSelector.setChoiceValue(selectedLevelValue);
        this.levelEditorController.getLevelEditorView().menuLevelSelector.setSelectedValue(selectedLevelValue);
    }

    /**
     * Menu level selector change handler
     *
     * @param changedSelector Changed selector
     */
    public void menuLevelSelectorChanged(MenuLevelSelector changedSelector) {
        String selectedLevelValue = changedSelector.getChoiceValue();

        // Value didn't change?
        if (selectedLevelValue.equals(this.getSelectedLevel())) {
            return;
        }

        this.openedLevelChange(selectedLevelValue);
    }

    /**
     * Gets selected level
     *
     * @return Selected level
     */
    public String getSelectedLevel() {
        return this.selectedLevel;
    }

    /**
     * Sets selected level
     *
     * @param level Selected level
     */
    public void setSelectedLevel(String level) {
        this.selectedLevel = level;
    }
}