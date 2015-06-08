package fr.enssat.BoulderDash.models;

import java.awt.image.BufferedImage;
import java.util.Observable;

import fr.enssat.BoulderDash.helpers.LevelLoadHelper;

import fr.enssat.BoulderDash.interfaces.LevelLoadInterface;
import fr.enssat.BoulderDash.interfaces.SubscriberInterface;
//le niveau se charge ici
//a partir du fichier
//la vue connais le modele
//le controlleur va modifier le model en fonction de l'utilisateur
//le modele previens la vue qu'il y a eu des modifs

public class LevelModel extends Observable implements LevelLoadInterface, SubscriberInterface {
	private DisplayableElementModel[][] groundGrid;
    private String levelName;
    private int sizeWidth = 0;
    private int sizeHeight = 0;
    private LevelLoadHelper levelLoadHelper;
	private int posXOfRockford, posYOfRockford;
	private RockfordModel rockford;
	private DiamondModel diamond;

	public LevelModel(String levelName) {
		posXOfRockford = 1;
		posYOfRockford = 1;

        this.levelName = levelName;

        this.levelLoadHelper = new LevelLoadHelper(this.levelName);
        this.groundGrid = this.levelLoadHelper.getGroundGrid();
        this.sizeWidth = this.levelLoadHelper.getWidthSizeValue();
        this.sizeHeight = this.levelLoadHelper.getHeightSizeValue();

        fillGround();
	}

	// initial fill of the ground
	public void fillGround() {
        this.createLimits();
		this.createRockford();
		this.createDiamond();
		this.setPositionOfRockford(posXOfRockford, posYOfRockford);
		this.rockford.startStaying();
	}

    private void createLimits() {
        int maxWidth = this.sizeWidth - 1;
        int maxHeight = this.sizeHeight - 1;

        System.out.print("width -> " + Integer.toString(this.groundGrid.length));
        System.out.print("height -> " + Integer.toString(this.groundGrid[0].length));

        System.out.print("maxWidth -> " + Integer.toString(maxWidth));
        System.out.print("maxHeight -> " + Integer.toString(maxHeight));


        for (int x = 0; x < this.sizeWidth; x++) {
            this.groundGrid[x][0] = new SteelWallModel(x, 0);
            this.groundGrid[x][maxHeight] = new SteelWallModel(x, maxHeight);
        }
        for (int y = 0; y < this.sizeHeight; y++) {
            this.groundGrid[0][y] = new SteelWallModel(0, y);
            this.groundGrid[maxWidth][y] = new SteelWallModel(maxWidth, y);
        }
    }

	private void createRockford() {
		rockford = new RockfordModel(posXOfRockford, posYOfRockford);
	}
	
	private void createDiamond(){
		diamond = new DiamondModel(8, 8); 
	}
	
	public DiamondModel getDiamonds(){
		return diamond;		
	}

	public RockfordModel getRockford() {
		return rockford;
	}

    public void setPositionOfRockford(int posX, int posY) {
        // TODO is this a good method ?
        if (this.groundGrid[posX][posY].getSpriteName() != "steelwall") {
            // if (ground[posX][posY].getPriority() < rockford.getPriority()) {
            this.groundGrid[posXOfRockford][posYOfRockford] = new EmptyModel(posXOfRockford, posYOfRockford);
            posXOfRockford = posX;
            posYOfRockford = posY;
            this.groundGrid[posX][posY] = rockford;
            setChanged();
            notifyObservers();
        }
    }

	public int getXPositionOfRockford() {
		return posXOfRockford;
	}

	public int getYPositionOfRockford() {
		return posYOfRockford;
	}

	public BufferedImage getImage(int x, int y) {
		return this.groundGrid[x][y].getSprite();
	}

	public int getSizeWidth() {
		return this.sizeWidth;
	}

	public void setSizeWidth(int sizeWidth) {
		this.sizeWidth = sizeWidth;
	}

    public int getSizeHeight() {
        return this.sizeHeight;
    }

    public void setSizeHeight(int sizeHeight) {
        this.sizeHeight = sizeHeight;
    }

	// DEBUG
	public void displayGround() {
		for (int i = 0; i < this.sizeWidth; i++) {
			for (int j = 0; j < this.sizeHeight; j++) {
				if (this.groundGrid[j][i].getSpriteName() == "rockford")
					System.out.print("R ");
				else if (this.groundGrid[j][i].getSpriteName() == "steelwall")
					System.out.print("S ");
				else if (this.groundGrid[j][i].getSpriteName() == "dirt")
					System.out.print("D ");
				else if (this.groundGrid[j][i].getSpriteName() == "black")
					System.out.print("  ");
			}
			System.out.println("");
		}
		System.out.println("");
	}
}