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
	private static int aliveZombies = 0, deadZombies = 0;
	private static int deadZombiesByType[] = new int[10];
	private double nextTime = 0;
	
	/** config */
	private int maxZombies = 3;
	private final int maxMaxZombies = 1000;
	
	public ZombieManager(
			final GameWorld gameWorld,
			final Player player) {
		this.gameWorld = gameWorld;
		this.player = player;
		
		SpriteManager.addSprite(new ZombieKilledIndicator());
	}

	/**
	 * dodaje martwego zombiaka do ilości martwych
	 * funkcja wywoływana przez objeck Zombie gdy zombiak umiera
	 * @param zombie 
	 */
	public static void addDeadZombie(Zombie zombie) {
		deadZombies++;
		aliveZombies--;
		deadZombiesByType[zombie.getType()]++;
	}
	
	/**
	 * dodaje jednego zombiaka do gry
	 */
	private void spawnZombie() {
		Point2D origPoint = new Point2D(0,0);
		Point2D res = WindowBound.getResolution();
		final int of = 30;
		
		final int newType = Helper.Rnd(4);
		
		do {
			switch( Helper.Rnd(4)) {//Helper.Rnd(4)) {
				case 0:
					origPoint = new Point2D(          -of,   Helper.Rnd((int)res.getY()));
					break;
				case 1:
					origPoint = new Point2D(  Helper.Rnd((int)res.getX()),               -of);
					break;
				case 2:
					origPoint = new Point2D(res.getX()+of, Helper.Rnd((int)res.getY()));
					break;
				case 3:
					origPoint = new Point2D(   Helper.Rnd((int)res.getX()),   res.getY()+of);
					break;
			}
			Point2D check = player.getMiddle().subtract(origPoint);
			if ( !( Math.abs(check.getX()) < 100 && Math.abs(check.getY()) < 100 ) )
				break;
		} while(true);
		
		gameWorld.addSprite(new Zombie(origPoint, newType));
		aliveZombies++;
	}
	
	@Override
	public void update() {
		long currTime = System.nanoTime()/1000000;
		if ( currTime >= nextTime ) {
			nextTime = currTime +  5000;
			maxZombies++;
			if ( maxZombies > maxMaxZombies ) {
				maxZombies = maxMaxZombies;
			}
		}
		if ( aliveZombies < maxZombies+Math.sqrt(deadZombies) ) {
			spawnZombie();
		}
	}
	
	/**
	 * pobiera ilość zabitych zobiaków
	 * @return 
	 */
	static int getDeadZombies() {
		return deadZombies;
	}
}
