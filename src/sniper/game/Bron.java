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

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

/**
 *
 * @author Kamil Cukrowski
 */
class Bron {
	private TimeMeasurer timeMeasurer = new TimeMeasurer();
	
	private final Player player;
	private static int wystrzelonePociski = 0;
	
	/* ustawienia broni */
	private Class pociskClass;
	private double bulletSpeed;
	private double bulletAttack;
	private double shootingSpeed; //ms?
	private Image playerImage;
	private String type;


	
	public Bron(final Player player) {
		this.player = player;
		setType("Sniper");
	}
	
	public void setType(String type) {
		this.type = type;
		switch(type) {
			case "AK-47":
				pociskClass = PociskAK47.class;
		        playerImage = new Image("File:resources/weapon/AK-47/player.png");
				bulletSpeed = 5;
				bulletAttack = 50;
				shootingSpeed = 100;
				SoundManager.loadSoundEffects("shot", "File:resources/weapon/AK-47/shot.mp3");
				SoundManager.loadSoundEffects("reload", "file:resources/weapon/AK-47/reload.mp3");
				break;
			case "Sniper":
				pociskClass = PociskSniper.class;
		        playerImage = new Image("File:resources/weapon/Sniper/player.png");
				bulletSpeed = 50;
				bulletAttack = 200;
				shootingSpeed = 1000;
				SoundManager.loadSoundEffects("shot", "File:resources/weapon/Sniper/shot.mp3");
				SoundManager.loadSoundEffects("reload", "file:resources/weapon/Sniper/reload.mp3");
				break;
			default:
				System.out.println("ERROR! Zly typ broni!");
				Platform.exit();
				break;
		}
	}
	
	public String getType() {
		return type;
	}

	public Image getPlayerImage() {
		return playerImage;
	}
	
	public void strzel() {
		wystrzelonePociski++;
		if ( timeMeasurer.runAfterTimeHasPassed( (long)(shootingSpeed*player.getPlayerAttackSpeed()) ) ) {
			
			// http://stackoverflow.com/questions/234600/can-i-use-class-newinstance-with-constructor-arguments
			// JAVA like a pro!
			final Point2D orig = player.getMiddle();
			final double angle = player.node.getRotate();
			Pocisk pocisk = null;
			Class[] cArg = {double.class, double.class, String.class, Point2D.class, double.class};
			try {
				pocisk = (Pocisk)pociskClass
						.getDeclaredConstructor(cArg)
						.newInstance(
								bulletSpeed, 
								bulletAttack*player.getPlayerAttack(), 
								type, orig, angle);
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
				Logger.getLogger(Bron.class.getName()).log(Level.SEVERE, null, ex);
			}
			if ( pocisk != null ) 
				SpriteManager.addSprite(pocisk);
			
		}
	}
	
	public double getBulletSpeed() {
		return bulletSpeed;
	}

	public double getBulletAttack() {
		return bulletAttack*player.getPlayerAttack();
	}
	
	public static int getWystrzelonePociski() {
		return wystrzelonePociski;
	}
}
