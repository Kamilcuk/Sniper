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
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

/**
 *
 * @author Kamil Cukrowski
 */
public class Pocisk extends Sprite {
	
	final double angle, speed;
	public Pocisk(
			final double origX,
			final double origY,
			final double _angle,
			final int _speed) {
		angle = _angle;
		speed = _speed;
		
		Circle circle = new Circle(2, Color.web("black", 1));
		//circle.setStrokeType(StrokeType.OUTSIDE);
        //circle.setStroke(Color.web("white", 0.16));
        circle.setStrokeWidth(4);		
		circle.setTranslateX(origX);
		circle.setTranslateY(origY);
		
		node = circle;
	}
	@Override
	public void update() {
		node.setTranslateX(node.getTranslateX()-speed*Math.sin(angle*Math.PI/180));
		node.setTranslateY(node.getTranslateY()-speed*Math.cos(angle*Math.PI/180));
	}
}
