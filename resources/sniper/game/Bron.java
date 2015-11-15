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
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Kamil Cukrowski
 */
class Bron extends Sprite {
	private final List<Sprite> pociski = new ArrayList<>();
	private final Group group = new Group();
	
	private long nextTime;  // w ms
	
	/* ustawienia broni */
	private int bulletSpeed;
	private int shootingSpeed; //ms?
	private Image playerImage;
	
	public Bron() {
		nextTime = 0;
		
		node = group;
	}
	
	public void ustawBron(String typ) {
		switch(typ) {
			case "AK-47":
		        playerImage = new Image("File:resources\\weapon\\AK-47\\player.png");
				bulletSpeed = 10;
				shootingSpeed = 100;
				break;
			default:
				playerImage = null;
		}
	}
	
	public Image getPlayerImage() {
		return playerImage;
	}
	
	public void strzel(final double origX, final double origY, final double angle) {
		long currTime = System.nanoTime()/1000000;
		if ( currTime >= nextTime ) {
			Pocisk pocisk = new Pocisk(origX, origY, angle, bulletSpeed);
			group.getChildren().add(pocisk.node);
			pociski.add(pocisk);
			
			nextTime = currTime + shootingSpeed;
		}
	}
	
	@Override
	public void update() {
		for(Sprite pocisk : pociski) {
			pocisk.update();
		}
	}
}
