package boulderdash.helpers;

import boulderdash.exceptions.UnknownModelException;
import boulderdash.models.DisplayableElementModel;
import boulderdash.models.RockfordModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Proceeds level load routine
 * Can deserialize level data from storage, and format it to internal representation
 * to be used as a data factory from level model classes
 */
public class LevelLoadHelper {
    private static final int limitsWidth = 2;
    private static final int limitsHeight = 2;
    private static final int limitsOffsetWidth = 1;
    private static final int limitsOffsetHeight = 1;
    private final SimpleDateFormat dateFormatter;
    private String levelId;
    private Document levelDOM;
    private XPath xpathBuilder;
    // Parsed values
    private String nameValue;
    private Date dateCreatedValue; // TODO Date is obsolete type, can we use another one
    private Date dateModifiedValue;
    private int widthSizeValue;
    private int heightSizeValue;
    private RockfordModel rockfordInstance;
    private int rockfordPositionX;
    private int rockfordPositionY;

    private int diamondsToCatch;

    private DisplayableElementModel[][] groundGrid;

    /**
     * @param levelId Level identifier
     */
    public LevelLoadHelper(String levelId) {
        this.levelId = levelId;
        diamondsToCatch = 0;

        // Requirements
        dateFormatter = new SimpleDateFormat("yyy-MM-dd/HH:mm:ss", Locale.ENGLISH);

        if (this.levelId != null) {
            // Let's go.
            loadLevelData();
        }
    }

    /**
     * Gets level storage path
     *
     * @return Level path, with file extension
     */
    private @Nullable InputStream getLevelPathInDataStore() {
        String name = "/levels/level" + levelId + ".xml"; // TODO this is fragile (we don't really know if this exists)
        return LevelLoadHelper.class.getResourceAsStream(name);
    }

    /**
     * Loads the level data into instance data space
     */
    private void loadLevelData() {
        xpathBuilder = XPathFactory.newInstance().newXPath();

        InputStream pathToData = getLevelPathInDataStore();

        // Parse & process level data
        parseLevelData(pathToData);
        processLevelData();
    }

    /**
     * Parses the level data for the given file
     * Handles the task of reading and storing the parsed DOM
     *
     * @param pathToLevelData FS path to the level data
     */
    private void parseLevelData(InputStream pathToLevelData) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            // Parse data in level file
            levelDOM = documentBuilder.parse(pathToLevelData);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace(); // TODO rethrow exception
        }
    }

    /**
     * Processes the parsed level data
     */
    private void processLevelData() {
        // Parse elements from structure
        try {
            processNameElement();
            processDateElement();
            processSizeElement();
            processGridElement();
        } catch (XPathExpressionException | ParseException e) {
            e.printStackTrace(); // TODO rethrow exception
        }
    }

    /**
     * Processes the 'name' element
     */
    private void processNameElement() throws XPathExpressionException {
        // Returns level name value
        nameValue = xpathBuilder.compile("/bd-level/name").evaluate(levelDOM);
    }

    /**
     * Processes the 'date' element
     */
    private void processDateElement() throws XPathExpressionException, ParseException {
        // Returns level creation date value
        dateCreatedValue = dateFormatter.parse(xpathBuilder.compile("/bd-level/date[@format='utc']/created").evaluate(levelDOM));

        // Returns level modification date value
        dateModifiedValue = dateFormatter.parse(xpathBuilder.compile("/bd-level/date[@format='utc']/modified").evaluate(levelDOM));
    }

    /**
     * Processes the 'size' element
     */
    private void processSizeElement() throws XPathExpressionException {
        // Returns level width value
        widthSizeValue = Integer.parseInt(xpathBuilder.compile("/bd-level/size/width").evaluate(levelDOM));
        widthSizeValue += limitsWidth;

        // Returns level height value
        heightSizeValue = Integer.parseInt(xpathBuilder.compile("/bd-level/size/height").evaluate(levelDOM));
        heightSizeValue += limitsHeight;
    }

    /**
     * Processes the 'grid' element
     */
    private void processGridElement() throws XPathExpressionException {
        // Initialize the grid
        groundGrid = new DisplayableElementModel[widthSizeValue][heightSizeValue];

        // Populate the grid
        NodeList lineNode = (NodeList) xpathBuilder.compile("/bd-level/grid[@state='initial']/line").evaluate(levelDOM, XPathConstants.NODESET);

        // Parse lines
        for (int y = 0; y < lineNode.getLength(); y++) {
            Node currentLineNode = lineNode.item(y);

            // Current line
            if (currentLineNode.getNodeType() == Node.ELEMENT_NODE) {
                Element currentLineElement = (Element) currentLineNode;
                int lineIndex = Integer.parseInt(currentLineElement.getAttribute("index"));

                NodeList rowNode = currentLineNode.getChildNodes();

                for (int x = 0; x < rowNode.getLength(); x++) {
                    Node currentRowNode = rowNode.item(x);

                    // Current row
                    if (currentRowNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element currentRowElement = (Element) currentRowNode;
                        int rowIndex = Integer.parseInt(currentRowElement.getAttribute("index"));

                        NodeList spriteNode = currentRowElement.getElementsByTagName("sprite");

                        if (spriteNode.getLength() > 0) {
                            Node currentSpriteNode = spriteNode.item(0);

                            if (currentSpriteNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element currentSpriteElement = (Element) currentSpriteNode;
                                String currentSpriteName = currentSpriteElement.getAttribute("name");
                                String currentSpriteConvertibleValue = currentSpriteElement.getAttribute("convertible");
                                boolean currentSpriteConvertible = false;

                                // No name? Continue.
                                if (currentSpriteName == null || currentSpriteName.isEmpty()) {
                                    continue;
                                }

                                if ("1".equals(currentSpriteConvertibleValue)) {
                                    currentSpriteConvertible = true;
                                }

                                // Process positions
                                int pX = rowIndex + limitsOffsetWidth;
                                int pY = lineIndex + limitsOffsetHeight;

                                try {
                                    groundGrid[pX][pY] = constructGridElement(currentSpriteName, pX, pY, currentSpriteConvertible);
                                } catch (UnknownModelException e) {
                                    e.printStackTrace(); // TODO rethrow exception
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Constructs the grid element
     *
     * @param spriteName Sprite name
     * @param rowIndex   Position in row (horizontal axis)
     * @param lineIndex  Position in line (vertical axis)
     */
    private DisplayableElementModel constructGridElement(@NotNull String spriteName, int rowIndex, int lineIndex, boolean convertible) throws UnknownModelException {
        DisplayableElementModel element = ModelConvertHelper.toModel(spriteName, convertible);

        // Custom actions?
        switch (spriteName) {
            case "diamond":
                diamondsToCatch += 1;
                break;

            case "rockford":
                rockfordPositionX = rowIndex;
                rockfordPositionY = lineIndex;
                rockfordInstance = (RockfordModel) element;
                break;
        }

        return element;
    }

    /**
     * Gets the level identifier
     *
     * @return Level identifier
     */
    public String getLevelId() {
        return levelId;
    }

    /**
     * Sets the level identifier
     *
     * @param levelId Level identifier
     */
    private void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    /**
     * Gets the name value
     *
     * @return Name value
     */
    public String getNameValue() {
        return nameValue;
    }

    /**
     * Sets the name value
     *
     * @param nameValue Name value
     */
    private void setNameValue(String nameValue) {
        this.nameValue = nameValue;
    }

    /**
     * Gets the creation date value
     *
     * @return Creation date value
     */
    public Date getDateCreatedValue() {
        return dateCreatedValue;
    }

    /**
     * Sets the creation date value
     *
     * @param dateCreatedValue Creation date value
     */
    private void setDateCreatedValue(Date dateCreatedValue) {
        this.dateCreatedValue = dateCreatedValue;
    }

    /**
     * Gets the modified date value
     *
     * @return Modified date value
     */
    public Date getDateModifiedValue() {
        return dateModifiedValue;
    }

    /**
     * Sets the modified date value
     *
     * @param dateModifiedValue Modified date value
     */
    private void setDateModifiedValue(Date dateModifiedValue) {
        this.dateModifiedValue = dateModifiedValue;
    }

    /**
     * Gets the width size value
     *
     * @return Width size value
     */
    public int getWidthSizeValue() {
        return widthSizeValue;
    }

    /**
     * Sets the width size value
     *
     * @param widthSizeValue Width size value
     */
    private void setWidthSizeValue(int widthSizeValue) {
        this.widthSizeValue = widthSizeValue;
    }

    /**
     * Gets the height size value
     *
     * @return Height size value
     */
    public int getHeightSizeValue() {
        return heightSizeValue;
    }

    /**
     * Sets the eight size value
     *
     * @param heightSizeValue Height size value
     */
    private void setHeightSizeValue(int heightSizeValue) {
        this.heightSizeValue = heightSizeValue;
    }

    /**
     * Gets the horizontal position of the Rockford element
     *
     * @return Horizontal position of the Rockford element
     */
    public int getRockfordPositionX() {
        return rockfordPositionX;
    }

    /**
     * Sets the horizontal position of the Rockford element
     *
     * @param x Horizontal position of the Rockford element
     */
    public void setRockfordPositionX(int x) {
        rockfordPositionX = x;
    }

    /**
     * Gets the vertical position of the Rockford element
     *
     * @return Vertical position of the Rockford element
     */
    public int getRockfordPositionY() {
        return rockfordPositionY;
    }

    /**
     * Sets the vertical position of the Rockford element
     *
     * @param y Vertical position of the Rockford element
     */
    public void setRockfordPositionY(int y) {
        rockfordPositionY = y;
    }

    /**
     * Gets the instance of Rockford
     *
     * @return Rockford instance
     */
    public RockfordModel getRockfordInstance() {
        return rockfordInstance;
    }

    /**
     * Sets the instance of Rockford
     *
     * @param rockfordInstance Rockford instance
     */
    public void setRockfordInstance(RockfordModel rockfordInstance) {
        this.rockfordInstance = rockfordInstance;
    }

    /**
     * Gets the ground grid
     *
     * @return Ground grid
     */
    public DisplayableElementModel[][] getGroundGrid() {
        return groundGrid;
    }

    /**
     * Sets the ground grid
     *
     * @param groundGrid Ground grid
     */
    private void setGroundGrid(DisplayableElementModel[][] groundGrid) {
        this.groundGrid = groundGrid;
    }

    /**
     * Gets the number of Diamonds to catch
     *
     * @return number of Diamonds to catch
     */
    public int getDiamondsToCatch() {
        return diamondsToCatch;
    }

    /**
     * Sets the number of Diamonds to catch
     */
    public void setDiamondsToCatch(int diamondsToCatch) {
        this.diamondsToCatch = diamondsToCatch;
    }
}
