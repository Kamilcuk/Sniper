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
 *  
 * @author Kamil Cukrowski
 */
public abstract class Pocisk extends Sprite {

    /**
     * config
     */
    protected final String bulletType;
    protected final double bulletSpeed;
    protected final double bulletAttack;

    public Pocisk(
            double bulletSpeed,
            double bulletAttack,
            String bulletType,
            final Point2D orig,
            final double angle) {
        this.bulletType = bulletType;
        this.bulletAttack = bulletAttack;
        this.bulletSpeed = bulletSpeed;

        SoundManager.playSound("shot");
    }

    public double getBulletAttack() {
        return bulletAttack;
    }
}
