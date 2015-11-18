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

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

/**
 *
 * @author Kamil Cukrowski
 */
class Bron extends SpriteManager {
	private final Player player;

	
	private long nextTime;  // w ms
	
	/* ustawienia broni */
	private double bulletSpeed;
	private double bulletAttack;
	private int shootingSpeed; //ms?
	private Image playerImage;
	
	public Bron(final Player player) {
		this.player = player;
		nextTime = 0;
	}
	
	public void ustawBron(String typ) {
		switch(typ) {
			case "AK-47":
		        playerImage = new Image("File:resources\\weapon\\AK-47\\player.png");
				bulletSpeed = 30;
				bulletAttack = 50;
				shootingSpeed = 500;
				break;
			default:
				playerImage = null;
		}
	}
	
	public Image getPlayerImage() {
		return playerImage;
	}
	
	public void strzel() {
		final Point2D orig = player.getMiddle();
		final double angle = player.node.getRotate();
		long currTime = System.nanoTime()/1000000;
		if ( currTime >= nextTime ) {
			(new SpriteManager()).addSprite(new Pocisk(this, orig, angle));
			nextTime = currTime + shootingSpeed;
		}
	}
	
	public double getBulletSpeed() {
		return bulletSpeed;
	}

	public double getBulletAttack() {
		return bulletAttack*player.getPlayerAtt();
	}
}
