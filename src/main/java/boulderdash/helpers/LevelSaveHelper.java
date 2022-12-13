package boulderdash.helpers;

import boulderdash.models.DirtModel;
import boulderdash.models.DisplayableElementModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Proceeds level save routine
 * Can iterate on internal representation of a map and serialize it to XML
 */
public class LevelSaveHelper {
    private static final String pathToDataStore = "./res/levels";
    private String levelId;
    private DisplayableElementModel[][] groundGrid;

    /**
     * @param levelId    Level identifier
     * @param groundGrid Ground grid
     */
    public LevelSaveHelper(String levelId, DisplayableElementModel[][] groundGrid) {
        this.levelId = levelId;
        this.groundGrid = groundGrid;

        // Requirements
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyy-MM-dd/HH:mm:ss", Locale.ENGLISH);

        if (this.levelId != null) {
            // Let's go.
            saveLevelData();
        }
    }

    /**
     * @param groundGrid Ground grid
     */
    public LevelSaveHelper(DisplayableElementModel[][] groundGrid) {
        this(generateNewLevelId(), groundGrid);
    }

    /**
     * Generates a new level identifier
     *
     * @return Level identifier
     */
    private static String generateNewLevelId() {
        File directory = new File(pathToDataStore);
        int electedLastLevelId, tempLevelId;
        String matchedId, finalLevelId;

        // Default level identifier
        electedLastLevelId = 0;

        // File list
        File[] fileList = directory.listFiles();

        // Regex matcher
        Pattern pattern = Pattern.compile("^level([0-9]+)\\.xml");
        Matcher matcher;

        for (File file : fileList) {
            matcher = pattern.matcher(file.getName());

            if (matcher.matches()) {
                matchedId = matcher.group(1);

                if (!matchedId.isEmpty()) {
                    tempLevelId = Integer.parseInt(matchedId);

                    if (tempLevelId > electedLastLevelId) {
                        electedLastLevelId = tempLevelId;
                    }
                } else {
                    System.out.println("Match found but result empty for > " + file.getName());
                }
            } else {
                System.out.println("No match found for > " + file.getName());
            }
        }

        // Increment
        electedLastLevelId += 1;

        // Stringify
        if (electedLastLevelId < 10) {
            finalLevelId = "0" + electedLastLevelId;
        } else {
            finalLevelId = Integer.toString(electedLastLevelId);
        }

        return "level" + finalLevelId;
    }

    /**
     * Gets level storage path
     *
     * @return Level path, with file extension
     */
    private String getLevelPathInDataStore() {
        return pathToDataStore + "/" + levelId + ".xml";
    }

    /**
     * Saves the level data into XML storage
     */
    private void saveLevelData() {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element bdLevel = document.createElement("bd-level");
            bdLevel.setAttribute("xmlns", "fr.enssat.boulderdash");
            document.appendChild(bdLevel);

            // append child elements to root element
            bdLevel.appendChild(nameNode(document));
            bdLevel.appendChild(dateNode(document));
            bdLevel.appendChild(sizeNode(document));
            bdLevel.appendChild(gridNode(document));

            // Write to disk
            writeDocumentToDisk(document);
        } catch (Exception e) {
            e.printStackTrace(); // TODO rethrow exception
        }
    }

    /**
     * Writes the level document data to disk
     *
     * @param document Document to be saved
     */
    private void writeDocumentToDisk(Document document) {
        boolean isSuccessful = true;

        try {
            DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            DOMImplementationLS impls = (DOMImplementationLS) registry.getDOMImplementation("LS");
            LSSerializer serializer = impls.createLSSerializer();

            LSOutput domOutput = impls.createLSOutput();
            domOutput.setCharacterStream(new java.io.FileWriter(getLevelPathInDataStore()));

            serializer.write(document, domOutput);
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            isSuccessful = false;
            e.printStackTrace(); // TODO rethrow exception
        }
    }

    /**
     * Creates the name node
     *
     * @param document Document
     * @return Name node
     */
    private Node nameNode(Document document) {
        String nameValue;

        nameValue = "Unknown Level Name";

        return textNode(document, "name", nameValue);
    }

    /**
     * Creates the date node
     *
     * @param document Document
     * @return Date node
     */
    private Node dateNode(Document document) {
        // Get values
        String dateCreatedValue, dateModifiedValue;

        dateCreatedValue = "0000-00-00/00:00:00";
        dateModifiedValue = "0000-00-00/00:00:00";

        // Create element
        Element dateElement = document.createElement("date");

        dateElement.setAttribute("format", "utc");

        dateElement.appendChild(textNode(document, "created", dateCreatedValue));
        dateElement.appendChild(textNode(document, "modified", dateModifiedValue));

        return dateElement;
    }

    /**
     * Creates the size node
     *
     * @param document Document
     * @return Size node
     */
    private Node sizeNode(Document document) {
        // Get values
        int widthValue = 0, heightValue = 0;

        widthValue = getGroundGrid().length - 2;

        if (widthValue > 0) {
            heightValue = getGroundGrid()[0].length - 2;
        }

        if (heightValue < 0 || widthValue < 0) {
            heightValue = 0;
            widthValue = 0;
        }

        // Create element
        Element sizeElement = document.createElement("size");

        sizeElement.appendChild(textNode(document, "width", String.valueOf(widthValue)));
        sizeElement.appendChild(textNode(document, "height", String.valueOf(heightValue)));

        return sizeElement;
    }

    /**
     * Creates the grid node
     *
     * @param document Document
     * @return Grid node
     */
    private Node gridNode(Document document) {
        Element gridElement = document.createElement("grid");
        gridElement.setAttribute("state", "initial");

        // Iterate in MATRIX:{x}
        if (getGroundGrid().length > 2) {
            // XML structure matrix is the inverse of the internal representation (hence the weird loop)
            for (int curLineIndex = 1; curLineIndex < (getGroundGrid()[0].length - 1); curLineIndex++) {
                gridElement.appendChild(gridLineNode(document, curLineIndex));
            }
        }

        return gridElement;
    }

    /**
     * Creates the grid line node
     *
     * @param document     Document
     * @param curLineIndex Current line index
     * @return Grid line node
     */
    private Node gridLineNode(Document document, Integer curLineIndex) {
        Element gridLineElement = document.createElement("line");
        gridLineElement.setAttribute("index", Integer.toString(curLineIndex - 1));

        // Iterate in MATRIX:X:{y}
        if (getGroundGrid().length > 2) {
            // XML structure matrix is the inverse of the internal representation (hence the weird loop)
            for (int curItemIndex = 1; curItemIndex < (getGroundGrid().length - 1); curItemIndex++) {
                gridLineElement.appendChild(gridLineItemNode(document, curLineIndex, curItemIndex));
            }
        }

        return gridLineElement;
    }

    /**
     * Creates the grid line item node
     *
     * @param document     Document
     * @param curLineIndex Current line index
     * @param curItemIndex Current line item index
     * @return Grid line item node
     */
    private Node gridLineItemNode(Document document, Integer curLineIndex, Integer curItemIndex) {
        Element gridLineItemElement = document.createElement("item");
        gridLineItemElement.setAttribute("index", Integer.toString(curItemIndex - 1));

        gridLineItemElement.appendChild(gridLineItemSpriteNode(document, curLineIndex, curItemIndex));

        return gridLineItemElement;
    }

    /**
     * Creates the grid line sprite item node
     *
     * @param document     Document
     * @param curLineIndex Current line index
     * @param curItemIndex Current line item index
     * @return Grid line item sprite node
     */
    private Node gridLineItemSpriteNode(Document document, Integer curLineIndex, Integer curItemIndex) {
        String groupValue, nameValue, stateValue, convertibleValue;

        DisplayableElementModel curGridElement = getGroundGrid()[curItemIndex][curLineIndex];

        // Null?
        if (curGridElement == null) {
            curGridElement = new DirtModel();
        }

        // Retrieve current values
        groupValue = DisplayableElementModel.getGroupName();
        nameValue = curGridElement.getSpriteName();
        stateValue = DisplayableElementModel.getStateValue();
        convertibleValue = curGridElement.isConvertible() ? "1" : "0";

        // Create sprite XML element
        Element gridLineItemSpriteElement = document.createElement("sprite");

        // Sprite attributes
        gridLineItemSpriteElement.setAttribute("group", groupValue);
        gridLineItemSpriteElement.setAttribute("name", nameValue);
        gridLineItemSpriteElement.setAttribute("state", stateValue);

        if ("1".equals(convertibleValue)) {
            gridLineItemSpriteElement.setAttribute("convertible", convertibleValue);
        }

        return gridLineItemSpriteElement;
    }

    /**
     * Creates a bare text node
     *
     * @param document Document
     * @param name     Element name
     * @param value    Element value
     * @return Text node
     */
    private Node textNode(Document document, String name, String value) {
        Element node = document.createElement(name);
        node.appendChild(document.createTextNode(value));

        return node;
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
}
