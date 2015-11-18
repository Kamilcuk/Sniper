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
public class ZombieManager extends Sprite {
	private final GameWorld gameWorld;
	private final Player player;
	private final int maxZombies;
	private static int aliveZombies, deadZombies;
	
	public ZombieManager(
			final GameWorld gameWorld,
			final Player player,
			final int maxZombies) {
		this.gameWorld = gameWorld;
		this.player = player;
		this.maxZombies = maxZombies;
		aliveZombies = 0;
		deadZombies = 0;
	}

	public static void addDeadZombie() {
		deadZombies++;
		aliveZombies--;
	}
	
	private int cnt = 0;
	private Point2D zombieOrigPoint() {
		Point2D orig = new Point2D(0,0);
		Point2D res = WindowBound.getResolution();
		int of = 10;
		switch(cnt) {
			case 0:
				orig = new Point2D(          -of,   Rnd((int)res.getY()));
				break;
			case 1:
				orig = new Point2D(  Rnd((int)res.getX()),               -of);
				break;
			case 2:
				orig = new Point2D(res.getX()-of, Rnd((int)res.getY()));
				break;
			case 3:
				orig = new Point2D(   Rnd((int)res.getX()),   res.getY()-of);
				break;
		}
		cnt++; if( cnt == 4 ) cnt = 0;
		/*
		Point2D check = player.getMiddle().subtract(orig);
		if ( Math.abs(check.getX()) < 100 && Math.abs(check.getY()) < 100 )
			return zombieOrigPoint(); */
		return orig;
	}
	
	private void spawnZombie() {
		gameWorld.addSprite(new Zombie(player, zombieOrigPoint(), Rnd(4)));
		aliveZombies++;
	}
	
	@Override
	public void update() {
		if ( aliveZombies < maxZombies ) {
			spawnZombie();
		}
	}
	
	/** Returns an integer between low and high, inclusive. */
	public static int Rnd(int low, int high) {
	     return (int)( low + ((double)(high - low) * Math.random()) + 0.5);
	}
	/** Returns an integer between 0 and limit, inclusive. */
	public static int Rnd(int limit) {
		return Rnd(0, limit);
	}
}
