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

/**
 * Klasa służy wykonywaniu pewnej określonej akcji co określony czas.
 * @author Kamil Cukrowski
 */
public class TimeMeasurer {
	private long nextTime = 0;
	
	public TimeMeasurer(long timeToPass) {
		nextTime = System.nanoTime()/1000000 + timeToPass;
	}
	
	public TimeMeasurer() {
		
	}
	
	/**
	 * Funkcja sprawdza czy minął od ostatniego uruchomienia tej funkcji czas
	 * podany w parametrze. Jeśli upłynął, zaczyna liczyć czas od momentu jej 
	 * odpalenia oraz zwraca prawdę. Jeśli ten czas nie upłynął, zwraca fałsz.
	 * @param timeToPass
	 * @return 
	 */
	public boolean runAfterTimeHasPassed(long timeToPass) {
		long currTime = System.nanoTime()/1000000;
		if ( currTime >= nextTime ) {
			nextTime = currTime +  timeToPass;
			return true;
		} else {
			return false;
		}
	}
	
}
