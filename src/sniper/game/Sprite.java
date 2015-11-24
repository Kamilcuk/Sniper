/*
 * Copyright (C) 2015 kamil
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
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

/**
 * Reprezentuje pojedynczy dowolnuy sprite.
 * @author Kamil Cukrowski
 */
public abstract class Sprite {
	
    /** display node */
    public Node node = null;
	
	/** velocity vector x direction */
    public double vX = 0;
 
	/** velocity vector y direction */
    public double vY = 0;
 
	/** collision shape  */
    public Circle collisionBounds = null;
	
	/**
	* Funkcja służąca odświerzeniu obiektu.
	* preUpdate jest odpalane przez sprawdzaniem kolizji, zaś update po sprawdzeniu kolizji.
	*/
	protected void preUpdate() {
		
	}
	
    /**
     * Zachowanie naszego sprite jeśli colliduje z drugim
     * @param other Inny sprite
     */
    protected void collide(Sprite other) {
		
    }
	
    /**
	 * Funkcja służąca odświarzeniu obiektu.
     */
    public void update() {

	}
 
	/**
	 * Jak blisko jest drugi sprite.
	 * @param other Inny sprite.
	 * @return Jeśli wartość mniejsza od zera, to znaczy,
	 * że dwa sprity zachodzą na siebie,
	 */
	public double jakBliskoCollide(Sprite other) {
		//return ((ax > dx)||(bx < cx)||(ay > dy)||(by < cy)); <- dwa rectangli
		if ( other.getClass().equals(WindowBound.class)) {
			return ((WindowBound)other).jakBliskoCollide(this);
		}
		
        if (collisionBounds == null || other.collisionBounds == null) {
            return Double.POSITIVE_INFINITY;
        }

        // determine it's size
        Circle otherSphere = other.collisionBounds;
        Circle thisSphere = collisionBounds;
        Point2D otherCenter = otherSphere.localToScene(otherSphere.getCenterX(), otherSphere.getCenterY());
        Point2D thisCenter = thisSphere.localToScene(thisSphere.getCenterX(), thisSphere.getCenterY());
        double dx = otherCenter.getX() - thisCenter.getX();
        double dy = otherCenter.getY() - thisCenter.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        double minDist = otherSphere.getRadius() + thisSphere.getRadius();

        return (distance - minDist);
	}
	
	public double jakiKatCollide(Sprite other) {
		 if (collisionBounds == null || other.collisionBounds == null) {
            return Double.POSITIVE_INFINITY;
        }
		 
		// determine it's size
        Circle otherSphere = other.collisionBounds;
        Circle thisSphere = collisionBounds;
        Point2D otherCenter = otherSphere.localToScene(otherSphere.getCenterX(), otherSphere.getCenterY());
        Point2D thisCenter = thisSphere.localToScene(thisSphere.getCenterX(), thisSphere.getCenterY());
		return Helper.GetAngleOfLineBetweenTwoPoints(otherCenter, thisCenter);
	}
	
	/**
	 * Akcja w przypadku naciśnięcia klawisza na klawiaturze.
	 * @param e
	 */
	protected void onKeyPressed(final KeyEvent e) {
		
	}
	/**
	 * Akcja w przypadku puszczenia klawisza na klawiaturze.
	 * @param e 
	 */
	protected void onKeyReleased(final KeyEvent e) {
		
	}
	/**
	 * Akcja w przypadku jakielkowiek akcji związanej z myszką.
	 * @param e 
	 */
	protected void onMouseEvent(final MouseEvent e) {
		
	}
	/**
	 * Zwraca punkt będący środkiem wyświetlanego node danego spritu, w przypadku
	 * prawidłowo ustawionych ograniczeń w rodzicu danego noda;
	 * @return 
	 */
	protected Point2D getMiddle() {
		if ( node == null ) return new Point2D(0, 0);
		return new Point2D(
				node.getTranslateX()+node.getBoundsInLocal().getWidth()/2,
				node.getTranslateY()+node.getBoundsInLocal().getHeight()/2 );
	}
}