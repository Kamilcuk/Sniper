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
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 *
 * @author Kamil Cukrowski
 */
public class PociskSniper extends Pocisk {

    private TimeMeasurer timeMeasurer = new TimeMeasurer((long)bulletSpeed);
    private boolean zaatakował = false;

    public PociskSniper(double bulletSpeed, double bulletAttack, String bulletType, Point2D orig, double angle) {
        super(bulletSpeed, bulletAttack, bulletType, orig, angle);

        double endX = orig.getX() + 1000 * Math.sin(angle * Math.PI / 180);
        double endY = orig.getY() - 1000 * Math.cos(angle * Math.PI / 180);

        Line line = new Line();
        line.setStartX(orig.getX());
        line.setStartY(orig.getY());
        line.setEndX(endX);
        line.setEndY(endY);
        node = line;
        collisionBounds = line;
    }

    @Override
    public void update() {
        // bulletSpeed to czas jak długo jesteśmy widoczni na ekranie! :D
        if (timeMeasurer.runAfterTimeHasPassed((long)bulletSpeed)) {
            SpriteManager.removeSprite(this);
        }
        zaatakował = true;
    }

    @Override
    public double getBulletAttack() {
        if (zaatakował) {
            return 0;
        }
        return bulletAttack;
    }

}
