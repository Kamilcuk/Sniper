/*
 * Copyright (C) 2015 Kamil Cukrowski
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package sniper.game;

import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;


/**
 * Objekt będący naszą bohaterką.
 * @author Kamil Cukrowski
 */
public class Player extends Sprite {
	private final ImageView obraz = new ImageView();
	private final Bron bron;
	private Point2D lastMousePos = new Point2D(0, 0);
	private boolean strzelam;
	
	/*  config */
	private final int playerSpeed = 5;
	private final int imageSize = 50;
	private int playerHp = 100;
	private int playerMaxHp = 100;
	private int playerAtt = 10;
	private int playerDef = 10; // 10% mniejsze obrazenia dostanie
	
	/**
	 * Konstruktor objekty player
	 * @param gameWorld gameWorld object
	 * @param initPos
	 */
	public Player(final Point2D initPos) {
		bron = new Bron(this);
		bron.ustawBron("AK-47");
		(new SpriteManager()).addSprite(bron);
		
		obraz.setImage(bron.getPlayerImage());
		obraz.setFitHeight(imageSize);
		obraz.setPreserveRatio(true);
		obraz.setTranslateX(initPos.getX()-imageSize/2);
		obraz.setTranslateY(initPos.getY()-imageSize/2);
		node = obraz;
		
		collisionBounds = new Circle(20);
	}
	
	public Point2D getMiddle() {
		return new Point2D(
				obraz.getTranslateX()+imageSize/2,
				obraz.getTranslateY()+imageSize/2);
	}
	
	@Override
	public void onKeyPressed(final KeyEvent e) {
		if ( e.getCode() == KeyCode.W )
			vY = -playerSpeed;
		if ( e.getCode() == KeyCode.S )
			vY = playerSpeed;
		if ( e.getCode() == KeyCode.A )
			vX = -playerSpeed;
		if ( e.getCode() == KeyCode.D )
			vX = playerSpeed;
		if ( e.getCode() == KeyCode.H ) {
			playerHp = 1000;
			playerMaxHp = 1000;
		}
	}
	
	@Override
	public void onKeyReleased(final KeyEvent e) {
		if ( e.getCode() == KeyCode.W )
			vY = 0;
		if ( e.getCode() == KeyCode.S )
			vY = 0;
		if ( e.getCode() == KeyCode.A )
			vX = 0;
		if ( e.getCode() == KeyCode.D )
			vX = 0;
		
	}
		
	@Override
	public void onMouseEvent(final MouseEvent e) {
		lastMousePos = new Point2D(e.getX(), e.getY());
		
		if ( e.getEventType() == MouseEvent.MOUSE_PRESSED && e.isPrimaryButtonDown() ) {
			strzelam = true;
		}	
		if ( e.getEventType() == MouseEvent.MOUSE_RELEASED && !e.isPrimaryButtonDown() ) {
			strzelam = false;
		}
	}
	
	public void updateRotation() {
		obraz.setRotate(-GetAngleOfLineBetweenTwoPoints(getMiddle(), lastMousePos));
	}
	
	@Override
	public void update() {
		updateRotation();
		
		if ( strzelam )
			bron.strzel();
		
		// move
		if  ( vY != 0 || vX != 0 ) {
			obraz.setTranslateX(obraz.getTranslateX() + vX);
			obraz.setTranslateY(obraz.getTranslateY() + vY);
		
			// sprawdz czy nie wychodzimy poza mapę
			/*double offset = 100;
			Point2D res = gameWorld.getResolution();
			if ( obraz.getTranslateX() < offset )
				obraz.setTranslateX(offset);
			if ( obraz.getTranslateY() < offset )
				obraz.setTranslateY(offset);
			if ( obraz.getTranslateX() > res.getX()-offset )
				obraz.setTranslateX(res.getX()-offset);
			if ( obraz.getTranslateY() > res.getY()-offset )
				obraz.setTranslateY(res.getY()-offset);*/
		}
		
		collisionBounds.setTranslateX(obraz.getTranslateX()+imageSize/2);
		collisionBounds.setTranslateY(obraz.getTranslateY()+imageSize/2);
	}
		
	@Override
	public void collide(Sprite other) {
		double dist = jakBliskoCollide(other);
		if ( dist >= 0 ) return;
		if ( other.getClass().equals(Zombie.class) ) {
			//playerHp = playerHp - ((Zombie)other).getZombieAttack()*((100-playerDef)/100);
			int dupa = ((Zombie)other).getZombieAttack();
			if ( dupa != 0 ) System.out.println(dupa);
			playerHp = playerHp - dupa;
		}
	}
	
	public int getPlayerHp() {
		return playerHp;
	}
	public int getPlayerMaxHp() {
		return playerMaxHp;
	}
	public double getPlayerHpPercentage() {
		return (double)playerHp/(double)playerMaxHp;
	}

	public int getPlayerAtt() {
		return playerAtt;
	}
}
