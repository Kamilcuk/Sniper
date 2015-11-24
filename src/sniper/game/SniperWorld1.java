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

import javafx.animation.Animation;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author Kamil Cukrowski
 */
public class SniperWorld1 extends GameWorld {

	@Override
	public void initialize(final Stage stage) {
		 // Sets the window title
        stage.setTitle("SniperWorld1");
		// Create the scene
        setGroup(new Group());
        setScene(new Scene(getGroup(), 1024, 768));
        stage.setScene(getScene());
		
		
		//background		
		ImageView bg = new ImageView();
         bg.setImage(new Image("File:resources\\images\\terrain\\base_1.png"));
         bg.setPreserveRatio(false);
         bg.setFitWidth(WindowBound.getResolution().getX());
         bg.setFitHeight(WindowBound.getResolution().getY());
         bg.setSmooth(true);
         bg.setCache(true);

		getGroup().getChildren().add(0, bg);
		
		// player
		Player player = new Player(
				new Point2D(getScene().getWidth()/2,getScene().getHeight()/2));
		addSprite(player);
		
		// zombie manager
		ZombieManager zombieManager = new ZombieManager(this, player);
		addSprite(zombieManager);
		
		PlayerHpBar playerHpBar = new PlayerHpBar(player);
		addSprite(playerHpBar);
	}
	
	@Override
	protected void handleOnKeyPressed(KeyEvent e) {
		if ( e.getCode() == KeyCode.SPACE ) {
			if ( getGameLoop().getStatus() != Animation.Status.RUNNING )
				getGameLoop().play();
			if ( getGameLoop().getStatus() == Animation.Status.RUNNING )
				getGameLoop().pause();
		}
		
	}
	
	@Override
	protected void handleOnKeReleased(KeyEvent e) {
	}
}
