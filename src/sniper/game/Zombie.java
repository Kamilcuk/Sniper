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
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
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
	private Image image;
	
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
		
		obraz.setImage(image);
		obraz.setFitHeight(imageSize);
		obraz.setPreserveRatio(true);
		obraz.setTranslateX(orig.getX()+imageSize/2);
		obraz.setTranslateY(orig.getY()+imageSize/2);
        obraz.setCache(true);
        obraz.setCacheHint(CacheHint.SPEED);
		
		collisionBounds = new Circle();
		collisionBounds.setRadius(20);
		
		node = obraz;
	}
	
	public void setType(final int type) {
		image = new Image("File:resources\\monsters\\zombie.png");
		switch(type) {
		default:
		case 0: // normal
			imageSize = 50;
			movingSpeed = 1;
			hp = 100;
			zombieAttack = 20;
			zombieAttackSpeed = 2000; //ms
			break;
		case 1: // slow, big attack
			image = overrideImage(image, 1., 0., 0.);
			imageSize = 50;
			movingSpeed = .5;
			hp = 100;
			zombieAttack = 5;
			zombieAttackSpeed = 1000; //ms
			break;
		case 2: // fast, small attack
			image = overrideImage(image, 0., 0., 1.);
			imageSize = 50;
			movingSpeed = 5;
			hp = 100;
			zombieAttack = 20;
			zombieAttackSpeed = 2000; //ms
			break;
		case 3: // big, life + big attack
			image = overrideImage(image, 1., 0., 0.);
			imageSize = 100;
			movingSpeed = 1.5;
			hp = 300;
			zombieAttack = 30;
			zombieAttackSpeed = 3000; //ms
			break;
		}
	}

	@Override
	public void preUpdate() {
		// punkt srodka zombiaka wzgledem srodka playera
		Point2D p = player.getMiddle()
				.subtract(node.getTranslateX(), node.getTranslateY())
				.subtract(imageSize/2, imageSize/2);
		double angle = Math.atan2(p.getX(), -p.getY())*180/Math.PI;
		// update rotation to player		
		node.setRotate(angle);
		
		// update velocities to player
		vX = movingSpeed*Math.sin(angle*Math.PI/180);
		vY = -movingSpeed*Math.cos(angle*Math.PI/180);
	}

	@Override
	public void collide(Sprite other) {
		double dist = jakBliskoCollide(other);
		if ( dist >= 0 ) return;
		if ( other.getClass().equals(Pocisk.class) ) {
			System.out.println("IN");
			Pocisk pocisk = (Pocisk) other;
			hp = hp - (int)pocisk.getBulletAttack();
		} else if ( other.getClass().equals(Player.class) ) {
			vX = 0;
			vY = 0;
		}
	}
	
	@Override
	public void update() {
		if ( hp < 0 ) {
			ZombieManager.addDeadZombie();
			(new SpriteManager()).removeSprite(this);
		}
		
		// move
		node.setTranslateX(node.getTranslateX() + vX);
		node.setTranslateY(node.getTranslateY() + vY);
		
		// update collisionBound
		collisionBounds.setTranslateX(node.getTranslateX()+imageSize/2);
		collisionBounds.setTranslateY(node.getTranslateY()+imageSize/2);
	}

	int getZombieAttack() {			
		long currTime = System.nanoTime()/1000000;
		if ( currTime >= nextTime ) {
			nextTime = currTime + zombieAttackSpeed;
			return(zombieAttack);
		}
		return 0;
	}
	
	private static Image overrideImage(Image image, double adjRed, double adjGreen, double adjBlue) {
		double height = image.getHeight();
		double width = image.getWidth();
		PixelWriter pixelWriter;
		PixelReader pixelReader;
		pixelReader = image.getPixelReader();
		WritableImage writableImage = new WritableImage((int)width, (int)height);
        pixelWriter = writableImage.getPixelWriter();
        
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                Color color = pixelReader.getColor(x, y);
                pixelWriter.setColor(x, y, color);
                
                double red = color.getRed() + adjRed;
                if(red > 1.0){
                    red = 1.0;
                }else if(red < 0.0){
                    red = 0.0;
                }
                
                double green = color.getGreen() + adjGreen;
                if(green > 1.0){
                    green = 1.0;
                }else if(green < 0.0){
                    green = 0.0;
                }
                
                double blue = color.getBlue() + adjBlue;
                if(blue > 1.0){
                    blue = 1.0;
                }else if(blue < 0.0){
                    blue = 0.0;
                }
                
                double opacity = color.getOpacity();
                
                pixelWriter.setColor(x, y, new Color(red, green, blue, opacity));
            }
        }
		return (Image) writableImage;
	}
}
