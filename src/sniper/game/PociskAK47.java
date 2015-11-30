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
public class PociskAK47 extends Pocisk {
	
	public PociskAK47(double bulletSpeed, double bulletAttack, String bulletType, Point2D orig, double angle) {
		super(bulletSpeed, bulletAttack, bulletType, orig, angle);
		
		Circle circle = new Circle(2, Color.web("black", 1));
		circle.setStrokeWidth(4);
		circle.setTranslateX(orig.getX());
		circle.setTranslateY(orig.getY());
		collisionBounds = circle;
		node = circle;
		
		vX = bulletSpeed*Math.sin(angle*Math.PI/180);
		vY = -bulletSpeed*Math.cos(angle*Math.PI/180);
	}
	
	@Override
	public void update() {
		node.setTranslateX(node.getTranslateX()+vX);
		node.setTranslateY(node.getTranslateY()+vY);
	}

	@Override
	protected void collide(Sprite other, double distance) {
		if ( other instanceof Zombie ) {
			System.out.println(distance);
		}
		if ( distance >= 0 ) return;
		if ( other instanceof Zombie ) {
			SpriteManager.removeSprite(this);
		}
		if ( other instanceof WindowBound ) {
			SpriteManager.removeSprite(this);
		}
	}
}
