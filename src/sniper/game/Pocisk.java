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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author Kamil Cukrowski
 */
public class Pocisk extends Sprite {
	/** config */
	private final Bron bron;
	
	public Pocisk(
			final Bron bron,
			final Point2D orig,
			final double angle) {
		this.bron =  bron;
		
		SoundManager.playSound("shot");
		
		Circle circle = new Circle(2, Color.web("black", 1));
        circle.setStrokeWidth(4);		
		circle.setTranslateX(orig.getX());
		circle.setTranslateY(orig.getY());
		
		vX = bron.getBulletSpeed()*Math.sin(angle*Math.PI/180);
		vY = -bron.getBulletSpeed()*Math.cos(angle*Math.PI/180);
		
		collisionBounds = circle;
		node = circle;
	}
	@Override
	public void update() {
		node.setTranslateX(node.getTranslateX()+vX);
		node.setTranslateY(node.getTranslateY()+vY);
	}

	@Override
	protected void collide(Sprite other, double distance) {
		if ( distance >= 0 ) return;
		if ( other.getClass().equals(Zombie.class) ) {
			SpriteManager.removeSprite(this);
		}
		if ( other.getClass().equals(WindowBound.class) ) {
			SpriteManager.removeSprite(this);
		}
	}
	
	public double getBulletAttack() {
		return bron.getBulletAttack();
	}
}
