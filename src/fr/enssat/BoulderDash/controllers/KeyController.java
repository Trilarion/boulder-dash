	package fr.enssat.BoulderDash.controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import fr.enssat.BoulderDash.models.LevelModel;

public class KeyController implements KeyListener {
	private LevelModel levelModel;
	
	public KeyController(LevelModel levelModel){
		this.levelModel = levelModel;
	}
	
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		System.out.println("Entered in keypressed event");
		switch (keyCode) {
		case KeyEvent.VK_UP:
			levelModel.setPositionOfRockford(levelModel.getXPositionOfRockford(), levelModel.getYPositionOfRockford()-1);
			levelModel.displayGround();
			break;
		case KeyEvent.VK_DOWN:
			levelModel.setPositionOfRockford(levelModel.getXPositionOfRockford(), levelModel.getYPositionOfRockford()+1);
			levelModel.displayGround();
			break;
		case KeyEvent.VK_LEFT:
			levelModel.setPositionOfRockford(levelModel.getXPositionOfRockford()-1, levelModel.getYPositionOfRockford());
			levelModel.displayGround();
			break;
		case KeyEvent.VK_RIGHT:
			levelModel.setPositionOfRockford(levelModel.getXPositionOfRockford()+1, levelModel.getYPositionOfRockford());
			levelModel.displayGround();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
