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
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Reprezentuje pojedynczy dowolnuy sprite.
 *
 * @author Kamil Cukrowski
 */
public abstract class Sprite {

    /**
     * display node
     */
    public Node node = null;

    /**
     * velocity vector x direction
     */
    public double vX = 0;

    /**
     * velocity vector y direction
     */
    public double vY = 0;

    /**
     * collision shape
     */
    public Shape collisionBounds = null;

    /**
     * Zachowanie naszego sprite jeśli colliduje z drugim
     *
     * @param other Inny sprite
     */
    protected void collide(Sprite other, double distance) {

    }

    /**
     * Funkcja służąca odświarzeniu obiektu.
     */
    public void update() {

    }
    
    /**
     * Jak blisko jest drugi sprite.
     *
     * @param other Inny sprite.
     * @return Jeśli wartość mniejsza od zera, to znaczy, że dwa sprity zachodzą
     * na siebie !
     */
    public double jakBliskoCollide(Sprite other) {
        if (other instanceof WindowBound) {
            return ((WindowBound) other).jakBliskoCollide(this);
        }

        if (this.collisionBounds == null || other.collisionBounds == null) {
            return Double.POSITIVE_INFINITY;
        }

        if (other.collisionBounds instanceof Circle
                && this.collisionBounds instanceof Circle) {
            // algorytm oblicza najmniejszą odległosć pomiędzy okręgami, którą zwraca.
            Circle otherSphere = (Circle) other.collisionBounds;
            Circle thisSphere = (Circle) collisionBounds;
            Point2D otherCenter = otherSphere.localToScene(otherSphere.getCenterX(), otherSphere.getCenterY());
            Point2D thisCenter = thisSphere.localToScene(thisSphere.getCenterX(), thisSphere.getCenterY());
            double dx = otherCenter.getX() - thisCenter.getX();
            double dy = otherCenter.getY() - thisCenter.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);
            double minDist = otherSphere.getRadius() + thisSphere.getRadius();
            return (distance - minDist);
        } else if (other.collisionBounds instanceof Rectangle
                && this.collisionBounds instanceof Circle) {
            // kolizja pomiędzy dwoma rectanglami
            Rectangle rect1 = (Rectangle) other.collisionBounds;
            Rectangle rect2 = (Rectangle) this.collisionBounds;
            Shape shape = Shape.intersect(rect1, rect2);
            boolean intersects = shape.getBoundsInLocal().getWidth() != -1;
            if (intersects) {
                return -1;
            } else {
                return 1;
            }
        } else if ((this.collisionBounds instanceof Line
                && other.collisionBounds instanceof Circle) || (other.collisionBounds instanceof Line
                && this.collisionBounds instanceof Circle)) {
            // Kolizja między linią a okręgiem	
            // zrobiona na potrzeby strzelania ze snajpy
            Circle circle;
            Line line;
            if (other.collisionBounds instanceof Circle) {
                circle = (Circle) other.collisionBounds;
                line = (Line) this.collisionBounds;
            } else {
                circle = (Circle) this.collisionBounds;
                line = (Line) other.collisionBounds;
            }
            Point2D A1 = new Point2D(line.getStartX(), line.getStartY());
            Point2D A2 = new Point2D(line.getEndX(), line.getEndY());
            Point2D P = circle.localToScene(circle.getCenterX(), circle.getCenterY());
            // http://stackoverflow.com/questions/849211/shortest-distance-between-a-point-and-a-line-segment
            // x, y is your target point and x1, y1 to x2, y2 is your line segment. 
            double A = P.getX() - A1.getX();
            double B = P.getY() - A1.getY();
            double C = A2.getX() - A1.getX();
            double D = A2.getY() - A1.getY();
            double dot = A * C + B * D;
            double len_sq = C * C + D * D;
            double param = -1;
            if (len_sq != 0) //in case of 0 length line
            {
                param = dot / len_sq;
            }
            double xx, yy;
            if (param < 0) {
                xx = A1.getX();
                yy = A1.getY();
            } else if (param > 1) {
                xx = A2.getX();
                yy = A2.getY();
            } else {
                xx = A1.getX() + param * C;
                yy = A1.getY() + param * D;
            }
            double dx = P.getX() - xx;
            double dy = P.getY() - yy;
            double odleglosc = Math.sqrt(dx * dx + dy * dy);
            return odleglosc - circle.getRadius();
        }
        return Double.POSITIVE_INFINITY;
    }

    /**
     * Akcja w przypadku naciśnięcia klawisza na klawiaturze.
     *
     * @param e
     */
    protected void onKeyPressed(final KeyEvent e) {

    }

    /**
     * Akcja w przypadku puszczenia klawisza na klawiaturze.
     *
     * @param e
     */
    protected void onKeyReleased(final KeyEvent e) {

    }

    /**
     * Akcja w przypadku jakielkowiek akcji związanej z myszką.
     *
     * @param e
     */
    protected void onMouseEvent(final MouseEvent e) {

    }

    /**
     * Zwraca punkt będący środkiem wyświetlanego node danego spritu, w
     * przypadku prawidłowo ustawionych ograniczeń w rodzicu danego noda;
     *
     * @return
     */
    protected Point2D getMiddle() {
        if (node == null) {
            return new Point2D(0, 0);
        }
        return new Point2D(
                node.getTranslateX() + node.getBoundsInLocal().getWidth() / 2,
                node.getTranslateY() + node.getBoundsInLocal().getHeight() / 2);
    }
}
