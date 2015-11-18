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
	static Point2D res;
		
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
		double othX = other.node.getTranslateX();
		double othY = other.node.getTranslateY();
		if ( othX < 0 )
			return othX;
		if ( othY < 0 )
			return othY;
		if ( othX < 0 && othY < 0 )
			return Math.sqrt(othX*othX+othY*othY);
		
		othX = res.getX() - othX;
		othY = res.getY() - othY;
		if ( othX < 0 )
			return othX;
		if ( othY < 0 )
			return othY;
		if ( othX < 0 && othY < 0 )
			return Math.sqrt(othX*othX+othY*othY);
		return 0;
	}
	
	@Override
	public void update() {
		
	}
	
}
