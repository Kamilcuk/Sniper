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
package sniper;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author Kamil Cukrowski
 */
public class SniperWorld1 extends GameWorld {

	
	public SniperWorld1() {
	}
	
	@Override
	public void initialize(final Stage primaryStage) {
		 // Sets the window title
        primaryStage.setTitle("SniperWorld1");
		// Create the scene
        setSceneNodes(new Group());
        setGameSurface(new Scene(getSceneNodes(), 640, 480));
        primaryStage.setScene(getGameSurface());
		
		
		
		//background		
		ImageView bg = new ImageView();
         bg.setImage(new Image("File:resources\\images\\terrain\\base_0.png"));
         bg.setPreserveRatio(false);
         bg.setFitHeight(480);
         bg.setFitWidth(640);
         bg.setSmooth(true);
         bg.setCache(true);

		getSceneNodes().getChildren().add(0, bg);
		
		// player
		Player player = new Player(primaryStage);
		getSpriteManager().addSprites(player);
		getSceneNodes().getChildren().add(1, player.node);
		
	}
	
	@Override
	public void handleUpdate(Sprite sprite){
		sprite.update();
	}
	
	
}
