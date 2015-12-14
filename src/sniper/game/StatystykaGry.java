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

import sniper.Sniper;

/**
 * Klasa słóżąca wyświetlaniu statystyki gry
 *
 * @author Kamil Cukrowski
 */
public class StatystykaGry {

    private static GameWorld gameWorld;

    public static GameWorld getGameWorld() {
        return gameWorld;
    }

    public static void setGameWorld(GameWorld gameWorld) {
        StatystykaGry.gameWorld = gameWorld;
    }

    public static String getText() {
        long stop = System.currentTimeMillis();
        Player pl = SniperWorld1.getPlayer();
        return "   Statystyka gry:\n" + "Osiągnięty level:  " + pl.getPlayerLevel() + "\n"
                + "Zabite zombie:  "
                + ZombieManager.getDeadZombies() + "\n" + "Przebyta droga:  "
                + Player.getDrogaPrzebyta() + "\n" + "Czas gry:  "
                + (((stop - Sniper.start) / 1000) / 60) + " minut " + (((stop - Sniper.start) / 1000) % 60) + " sekund" + "\n"
                + "Wystrzelone pociski:  " + Bron.getWystrzelonePociski();
    }
}
