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

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Klasa odpowiadająca za gre 2D jako taką
 * tworzy okno, interface, zarządza obiektami
 * @author Kamil Cukrowski
 */
public class Game extends Application {
	/** świat naszej gry */
	GameWorld gameWorld = new SniperWorld1();
	
	/**
	 * Methoda implementowana przez javaFX Application
	 */
	public void run() {
        javafx.application.Application.launch(Game.class);
	}
	
    /**
    * Startuje grę
	 * @param stage Scena w javaFX API
	*/
    @Override
    public void start(Stage stage) {
        // setup title, scene, stats, controls, and actors.
        gameWorld.initialize(stage);

        // kick off the game loop
        gameWorld.beginGameLoop();

        // display window
        stage.show();       
    }
}
