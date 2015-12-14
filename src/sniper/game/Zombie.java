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
import javafx.scene.shape.Circle;

/**
 *
 * @author Kamil Cukrowski
 */
public class Zombie extends Sprite {
	private final ImageView obraz = new ImageView();
	private boolean init = true;
	
	/** ataki się dzieją co określony czas */
	private long nextTime = 0;  // w ms
	/** config */
	private final int type;
	private int imageSize = 75;
	private double movingSpeed = 1*poziomTrudnosci;
	private double hp = 100*poziomTrudnosci;
	private double zombieAttack = 20*poziomTrudnosci;
	private double zombieAttackSpeed = 2000/poziomTrudnosci; //ms
        static double poziomTrudnosci;
        
	
	public Zombie(
			final Point2D orig,
			final int type) {
            ////////////////////////
           // System.out.println(poziomTrudnosci);
            
		this.type = type;
		setType(type);
		
		obraz.setFitHeight(imageSize);
		obraz.setPreserveRatio(true);
		obraz.setTranslateX(orig.getX()-imageSize/2);
		obraz.setTranslateY(orig.getY()-imageSize/2);
        obraz.setCache(true);
        obraz.setCacheHint(CacheHint.SPEED);
		
		Circle circle = new Circle(imageSize/4);
		circle.setCenterX(this.getMiddle().getX());
		circle.setCenterY(this.getMiddle().getY());
		collisionBounds = circle;
		
		node = obraz;
		
		// init vX and vY - idziemy sobie troche krzywo do środka
		Point2D p = WindowBound.getResolution().multiply(0.5).subtract(orig);
		double angle = Math.atan2(p.getX(), -p.getY())*180/Math.PI;
		angle = angle + Helper.Rnd(40);
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
			imageSize = 60;			movingSpeed = 0.4*poziomTrudnosci;
			hp = 100*poziomTrudnosci;				zombieAttack = 20*poziomTrudnosci;
			zombieAttackSpeed = 2000/poziomTrudnosci; //ms
			break;
		case 1: // slow, big attack
			image = Helper.overrideImage(image, 1., 0., 1.);
			imageSize = 60;			movingSpeed = 0.25*poziomTrudnosci;
			hp = 50*poziomTrudnosci;				zombieAttack = 5*poziomTrudnosci;
			zombieAttackSpeed = 1000/poziomTrudnosci; //ms
			break;
		case 2: // fast, small attack
			image = Helper.overrideImage(image, 0., 0., 1.);
			imageSize = 60;			movingSpeed = 2*poziomTrudnosci;
			hp = 100*poziomTrudnosci;				zombieAttack = 20*poziomTrudnosci;
			zombieAttackSpeed = 2000/poziomTrudnosci; //ms
			break;
		case 3: // big, life + big attack
			image = Helper.overrideImage(image, 1., 0., 0.);
			imageSize = 80;			movingSpeed = 0.9*poziomTrudnosci;
			hp = 300*poziomTrudnosci;				zombieAttack = 30*poziomTrudnosci;
			zombieAttackSpeed = 3000/poziomTrudnosci; //ms
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
	public void collide(Sprite other, double distance) {
		if ( other instanceof Zombie ) return;
		if ( init ) {
			if ( other instanceof WindowBound ) {
				if ( distance > 20 ) {
					init = false;
				}
			} 
		} else {
			if ( other instanceof WindowBound ) {
				if ( distance < 10 ) {
					vX = -vX;
					vY = -vY;
				}
			} else if ( other instanceof Player ) {
				if ( distance > 300 ) {
					return;
				}
				// punkt srodka zombiaka wzgledem srodka playera
				double angle = Helper.GetAngleOfLineBetweenTwoPoints(other.getMiddle(), this.getMiddle());
				// update velocities to player
				vX = movingSpeed*Math.sin(angle*Math.PI/180);
				vY = movingSpeed*Math.cos(angle*Math.PI/180);
			}
		}
		
		if ( distance >= 0 ) return;
		if ( other instanceof Pocisk ) {
			Pocisk pocisk = (Pocisk) other;
			hp = hp - (int)pocisk.getBulletAttack();
		} else if ( other instanceof Player ) {
			if ( init ) return;
			vX = 0;
			vY = 0;
		} else if ( other instanceof WindowBound ) {
			if ( !init && distance <= -100) {
				/* co ja tutaj robie? */
				SpriteManager.removeSprite(this);
			}
		}
	}
	
	@Override
	public void update() {
		if ( hp < 0 ) {
			ZombieManager.addDeadZombie(this);
			SpriteManager.removeSprite(this);
		}
		
		//rotate
		if ( vX != 0 || vY != 0 ) {
			node.setRotate(Math.atan2(vX, -vY)*180/Math.PI);
		}
			
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
			nextTime = currTime + (long)zombieAttackSpeed;
			return((int)zombieAttack);
		}
		return 0;
	}
        
        public static double setPoziomTrudnosci(double poz){
        poziomTrudnosci = poz;
                return poz;}
}
