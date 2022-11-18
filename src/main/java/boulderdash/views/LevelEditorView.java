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
 */
public class LevelEditorView extends JFrame {
    private final NavigationBetweenViewController nav;
    private LevelEditorGroundView fieldPanel;
    private String selectedLevel;
    private MenuLevelSelector menuLevelSelector;
    private LevelEditorController levelEditorController;
    private LevelModel levelModel;

    private String pickedBlockValue;


    public LevelEditorView(LevelEditorController levelEditorController, LevelModel levelModel, NavigationBetweenViewController nav) {
        this.levelEditorController = levelEditorController;
        this.levelModel = levelModel;
        this.nav = nav;

        initializeView();
        createLayout();

        fieldPanel.grabFocus();
    }

    /**
     * Initializes the view layout
     */
    private void initializeView() {
        setFocusable(true);
        setVisible(false);
        setResizable(false);

        // UI parameters
        setSize(984, 454);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // App parameters
        setTitle("Boulder Dash | Level Editor");

        Image appIcon = Toolkit.getDefaultToolkit().getImage("./res/app/app_icon.png");
        setIconImage(appIcon);
    }

    /**
     * Creates the view layout
     */
    private void createLayout() {
        // List of levels
        LevelSelectorHelper levelSelectorHelper = new LevelSelectorHelper(true, this);
        menuLevelSelector = levelSelectorHelper.createLevelList();

        // Field + select panels
        fieldPanel = new LevelEditorGroundView(levelModel, this);
        JPanel selectPanel = new JPanel();

        AssetsLevelEditorComponent assetsComponent = new AssetsLevelEditorComponent(this);
        JPanel actionsComponent = new JPanel();

        // Add actions
        actionsComponent.add(menuLevelSelector);
        actionsComponent.add(createButton("save", "Save"));
        actionsComponent.add(createButton("delete", "Delete"));
        actionsComponent.add(createButton("new", "New level"));
        actionsComponent.add(createButton("menu", "Menu"));
        actionsComponent.add(createButton("help", "Help"));

        // Add select panel subcomponents
        selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.Y_AXIS));

        selectPanel.add(assetsComponent);
        selectPanel.add(actionsComponent);

        // Add top components
        add(fieldPanel, BorderLayout.CENTER);
        add(selectPanel, BorderLayout.WEST);
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
        button.addActionListener(levelEditorController);
        button.setActionCommand(id);

        return button;
    }

    /**
     * Gets the level editor field view
     *
     * @return Level editor field view
     */
    public LevelEditorGroundView getLevelEditorGroundView() {
        return fieldPanel;
    }

    /**
     * Gets picked block value
     *
     * @return Picked block value
     */
    public String getPickedBlockValue() {
        return pickedBlockValue;
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
            pickedLevelModel = new LevelModel(selectedLevelValue, nav.getAudioLoadHelper(), "editor");
        } else {
            // New blank model for editor
            pickedLevelModel = new LevelModel(nav.getAudioLoadHelper());
        }

        pickedLevelModel.setShowCursor(true);
        pickedLevelModel.setMode("editor");
        levelModel = pickedLevelModel;

        // Hide old view
        levelEditorController.getLevelEditorView().dispose();

        levelEditorController = new LevelEditorController(levelModel, nav);

        levelEditorController.getLevelEditorView().setSelectedLevel(selectedLevelValue);
        levelEditorController.getLevelEditorView().setVisible(true);
        levelEditorController.getLevelEditorView().fieldPanel.grabFocus();

        levelEditorController.getLevelEditorView().menuLevelSelector.setChoiceValue(selectedLevelValue);
        levelEditorController.getLevelEditorView().menuLevelSelector.setSelectedValue(selectedLevelValue);
    }

    /**
     * Menu level selector change handler
     *
     * @param changedSelector Changed selector
     */
    public void menuLevelSelectorChanged(MenuLevelSelector changedSelector) {
        String selectedLevelValue = changedSelector.getChoiceValue();

        // Value didn't change?
        if (selectedLevelValue.equals(selectedLevel)) {
            return;
        }

        openedLevelChange(selectedLevelValue);
    }

    /**
     * Gets selected level
     *
     * @return Selected level
     */
    public String getSelectedLevel() {
        return selectedLevel;
    }

    /**
     * Sets selected level
     *
     * @param level Selected level
     */
    public void setSelectedLevel(String level) {
        selectedLevel = level;
    }
}