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

import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
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
	private final ArrayList<String> INPUT = new ArrayList<String>();
 
	/*  config */
	private final int playerSpeed = 5;
	private final int imageSize = 50;
	private double playerHp = 100;
	private int playerMaxHp = 100;
	private double playerAtt = 1.0; // 100% obrażeń danej broni
	private double playerDef = 0.9; // 90% obrażeń otrzyma
	
	/**
	 * Konstruktor objekty player
	 * @param initPos
	 */
	public Player(final Point2D initPos) {
		bron = new Bron(this);
		bron.ustawBron("AK-47");
		
		obraz.setImage(bron.getPlayerImage());
		obraz.setFitHeight(imageSize);
		obraz.setPreserveRatio(true);
		obraz.setTranslateX(initPos.getX()-imageSize/2);
		obraz.setTranslateY(initPos.getY()-imageSize/2);
		node = obraz;
		
		collisionBounds = new Circle(20);
		
		SoundManager.loadSoundEffects("player_hit0", "File:resources/sounds/player_hit_0.mp3");
		SoundManager.loadSoundEffects("player_hit1", "File:resources/sounds/player_hit_1.mp3");
		SoundManager.loadSoundEffects("player_hit2", "File:resources/sounds/player_hit_2.mp3");
	}
	
	@Override
	public void onKeyPressed(final KeyEvent e) {
		String str = e.getCode().toString();
		if ( !INPUT.contains(str)) {
			INPUT.add(str);
			inputChange();
		}
	}
	
	@Override
	public void onKeyReleased(final KeyEvent e) {
		INPUT.remove(e.getCode().toString());
		inputChange();
	}
	
	/**
	 * Prywatna funkcja ustawiająca prędkość gracza w zależności 
	 * od wciśniętych klawiszy klawiatury.
	 */
	private void inputChange() {
		vY = 0;
		if ( INPUT.contains("W") )
			vY += -playerSpeed;
		if ( INPUT.contains("S") )
			vY += playerSpeed;
		vX = 0;
		if ( INPUT.contains("A") )
			vX += -playerSpeed;
		if ( INPUT.contains("D") )
			vX += playerSpeed;
		
		if ( INPUT.contains("H") ) {
			playerHp = 1000;
			playerMaxHp = 1000;
		}
		
	}
		
	@Override
	public void onMouseEvent(final MouseEvent e) {
		lastMousePos = new Point2D(e.getX(), e.getY());
		if ( e.getEventType() == MouseEvent.MOUSE_PRESSED && e.isPrimaryButtonDown() )
			strzelam = true;
		if ( e.getEventType() == MouseEvent.MOUSE_RELEASED && !e.isPrimaryButtonDown() )
			strzelam = false;
	}

	@Override
	public void collide(Sprite other) {
		if ( other.getClass().equals(WindowBound.class)) {
			// sprawdz czy nie wyjdziemy poza mapę
			double offset = -imageSize/2+imageSize/10;
			Point2D res = WindowBound.getResolution();
			if ( obraz.getTranslateX()+vX < offset && vX < 0)
				vX = offset - obraz.getTranslateX();
			if ( obraz.getTranslateY()+vY < offset && vY < 0 )
				vY = offset - obraz.getTranslateY();
			if ( obraz.getTranslateX()+imageSize+vX > res.getX()-offset && vX > 0 )
				vX = res.getX() - offset - imageSize - obraz.getTranslateX();
			if ( obraz.getTranslateY()+imageSize+vY > res.getY()-offset && vY > 0 )
				vY = res.getY() - offset - imageSize - obraz.getTranslateY();
		}
			
		double dist = jakBliskoCollide(other);
		if ( dist >= 0 ) return;
		if ( other.getClass().equals(Zombie.class) ) {
			//playerHp = playerHp - ((Zombie)other).getZombieAttack()*((100-playerDef)/100);
			double obrazenia = ((Zombie)other).getZombieAttack()*playerDef;
			playerHp = playerHp - obrazenia;
			if ( obrazenia > 0 ) {
				String str = "player_hit" + Helper.Rnd(2);
				SoundManager.playSound(str);
			}
		}
	}
	
	@Override
	public void update() {
		// updateRotation
		obraz.setRotate(-Helper.GetAngleOfLineBetweenTwoPoints(getMiddle(), lastMousePos));
		
		if ( strzelam )
			bron.strzel();
			
		// move
		if  ( vY != 0 || vX != 0 ) {
			obraz.setTranslateX(obraz.getTranslateX() + vX);
			obraz.setTranslateY(obraz.getTranslateY() + vY);
		}
		
		collisionBounds.setTranslateX(obraz.getTranslateX()+imageSize/2);
		collisionBounds.setTranslateY(obraz.getTranslateY()+imageSize/2);
	}
	/**
	 * pobiera wartość życia gracza.
	 * @return 
	 */
	public double getPlayerHp() {
		return playerHp;
	}
	/**
	 * pobiera maksymalną ilość życia gracza
	 * @return 
	 */
	public int getPlayerMaxHp() {
		return playerMaxHp;
	}
	/**
	 * zwraca stosunek życia gracza do maxa zycia gracza (wartość od 0.0 do 1.0).
	 * @return 
	 */
	public double getPlayerHpPercentage() {
		return (double)playerHp/(double)playerMaxHp;
	}
	/**
	 * Zwraca wartość współczynnika ataku gracza.
	 * @return 
	 */
	public double getPlayerAtt() {
		return playerAtt;
	}
}
