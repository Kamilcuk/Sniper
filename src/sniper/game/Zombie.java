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
import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author Kamil Cukrowski
 */
public class Zombie extends Sprite {
	private final Player player;
	private final ImageView obraz = new ImageView();
	private boolean init = true;
	
	/** ataki się dzieją co określony czas */
	private long nextTime = 0;  // w ms
	
	/** config */
	private final int type;
	private int imageSize = 50;
	private double movingSpeed = 1;
	private int hp = 100;
	private int zombieAttack = 20;
	private int zombieAttackSpeed = 2000; //ms
	
	public Zombie(
			final Player player,
			final Point2D orig,
			final int type) {
		this.player = player;
		this.type = type;
		setType(type);
		
		obraz.setFitHeight(imageSize);
		obraz.setPreserveRatio(true);
		obraz.setTranslateX(orig.getX()-imageSize/2);
		obraz.setTranslateY(orig.getY()-imageSize/2);
        obraz.setCache(true);
        obraz.setCacheHint(CacheHint.SPEED);
		
		collisionBounds = new Circle();
		collisionBounds.setRadius(20);
		
		node = obraz;
		
		// init vX and vY - idziemy sobie troche krzywo do środka
		Point2D p = WindowBound.getResolution().multiply(0.5).subtract(orig);
		double angle = Math.atan2(p.getX(), -p.getY())*180/Math.PI;
		node.setRotate(angle);
		vX = movingSpeed*Math.sin(angle*Math.PI/180);
		vY = -movingSpeed*Math.cos(angle*Math.PI/180);
	}
	
	/**
	 * pomocnicza funkcja odpowiadająco za określenie parametrów zombiaka (życie, attack,
	 * prędkość, wygląd, wielkość) w zależności od podanego typu)
	 * @param type 
	 */
	private void setType(final int type) {
		Image image;
		switch(type) {
		default:
		case 0:
		case 1:
		case 2:
		case 3:
			image = new Image("File:resources/monsters/zombie.png");
			break;
		}
		switch(type) {
		case 0: // normal
			imageSize = 40;			movingSpeed = 0.7;
			hp = 100;				zombieAttack = 20;
			zombieAttackSpeed = 2000; //ms
			break;
		case 1: // slow, big attack
			image = Helper.overrideImage(image, 0., 1., 0.);
			imageSize = 40;			movingSpeed = 0.5;
			hp = 50;				zombieAttack = 5;
			zombieAttackSpeed = 1000; //ms
			break;
		case 2: // fast, small attack
			image = Helper.overrideImage(image, 0., 0., 1.);
			imageSize = 40;			movingSpeed = 3;
			hp = 100;				zombieAttack = 20;
			zombieAttackSpeed = 2000; //ms
			break;
		case 3: // big, life + big attack
			image = Helper.overrideImage(image, 1., 0., 0.);
			imageSize = 80;			movingSpeed = 1.5;
			hp = 300;				zombieAttack = 30;
			zombieAttackSpeed = 3000; //ms
			break;
		}
		obraz.setImage(image);
	}
	/**
	* Zwraca typ zombiaka.
	* @return 
	*/
	public int getType() {
		return type;
	}

	@Override
	public void preUpdate() {
		if ( jakBliskoCollide(player) > 200 ) {
			return;
		}
		
		// punkt srodka zombiaka wzgledem srodka playera
		Point2D p = player.getMiddle()
				.subtract(node.getTranslateX(), node.getTranslateY())
				.subtract(imageSize/2, imageSize/2);
		double angle = Math.atan2(p.getX(), -p.getY())*180/Math.PI;
		
		// update velocities to player
		vX = movingSpeed*Math.sin(angle*Math.PI/180);
		vY = -movingSpeed*Math.cos(angle*Math.PI/180);
	}

	@Override
	public void collide(Sprite other) {
		double dist = jakBliskoCollide(other);
		if ( other.getClass().equals(WindowBound.class)) {
			if ( init ) {
				if ( dist > 200 ) {
					init = false;
				}
			} else {
				if ( dist < 10 ) {
					vX = -vX;
					vY = -vY;
				}
			}
		}
		if ( dist >= 0 ) return;
		if ( other.getClass().equals(Pocisk.class) ) {
			Pocisk pocisk = (Pocisk) other;
			hp = hp - (int)pocisk.getBulletAttack();
		} else if ( other.getClass().equals(Player.class) ) {
			vX = 0;
			vY = 0;
		} else if ( other.getClass().equals(WindowBound.class) ) {
			if ( !init && dist >= 300) {
				/* co ja tutaj robie? */
				(new SpriteManager()).removeSprite(this);
			}
		}
	}
	
	@Override
	public void update() {
		if ( hp < 0 ) {
			ZombieManager.addDeadZombie(this);
			(new SpriteManager()).removeSprite(this);
		}
		
		//rotate
		if ( vX != 0 && vY != 0 )
			node.setRotate(Math.atan2(vX, -vY)*180/Math.PI);
		
		// move
		node.setTranslateX(node.getTranslateX() + vX);
		node.setTranslateY(node.getTranslateY() + vY);
		
		// update collisionBound
		collisionBounds.setTranslateX(getMiddle().getX());
		collisionBounds.setTranslateY(getMiddle().getY());
	}

	int getZombieAttack() {			
		long currTime = System.nanoTime()/1000000;
		if ( currTime >= nextTime ) {
			nextTime = currTime + zombieAttackSpeed;
			return(zombieAttack);
		}
		return 0;
	}
}
