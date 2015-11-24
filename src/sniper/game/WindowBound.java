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

/**
 * Specjalny sprite, który ma za zadanie przechowywać informację o tym
 * jak duży jest ekran. Oraz sprawdzać kolizję, czy dany sprite
 * wychodzi poza ekran
 * @author Kamil Cukrowski
 */
public class WindowBound extends Sprite {
	private static Point2D res = new Point2D(0,0);
		
	public void setResolution(Point2D res) {
			this.res = res;
	}
	
	public static Point2D getResolution() {
			return res;
	}
	
	@Override
	public double jakBliskoCollide(Sprite other) {
		if ( other.node == null )
			return Double.POSITIVE_INFINITY;
		
		double otherX = other.getMiddle().getX();
		double otherY = other.getMiddle().getY();
		double oX = otherX;
		double oY = otherY;
		double mX = res.getX() - otherX;
		double mY = res.getY() - otherY;
		
		if ( oX < 0 && oY < 0 && mX > 0 && mY > 0 ) return Math.sqrt(oX*oX+oY*oY);
		if ( oX < 0 && oY > 0 && mX > 0 && mY < 0 ) return Math.sqrt(oX*oX+mY*mY);
		if ( oX > 0 && oY > 0 && mX < 0 && mY > 0 ) return Math.sqrt(mX*mX+mY*mY);
		if ( oX > 0 && oY < 0 && mX < 0 && mY > 0 ) return Math.sqrt(oY*oY+mY*mY);
		return Math.min(Math.min(oX, oY),Math.min(mX, mY));
	}
	
	@Override
	public void update() {
		
	}
	
}
