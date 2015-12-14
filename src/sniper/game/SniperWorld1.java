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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Kamil Cukrowski
 */
public class SniperWorld1 extends GameWorld {

    
    Node StartMenuNode = SpriteManager.getGroup().lookup("#StartMenu");
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
		 bg.setImage(new Image("File:resources\\images\\terrain\\base_" + Helper.Rnd(3) + ".png"));		
         bg.setPreserveRatio(false);
         bg.setFitWidth(WindowBound.getResolution().getX());
         bg.setFitHeight(WindowBound.getResolution().getY());
         bg.setSmooth(true);
         bg.setCache(true);
         bg.setId("tlo");

		getGroup().getChildren().add(0, bg);
		
		// player
		Player player = new Player(
				new Point2D(getScene().getWidth()/2,getScene().getHeight()/2));
		addSprite(player);
		setPlayer(player);
		
		// zombie manager
		ZombieManager zombieManager = new ZombieManager(this, player);
		addSprite(zombieManager);
		
		PlayerHpBar playerHpBar = new PlayerHpBar(player);
		addSprite(playerHpBar);
                odpalStartMenu();
               
                    
                
	}
	
	@Override
	protected void handleOnKeyPressed(KeyEvent e) {
		Node levelUpNode = SpriteManager.getGroup().lookup("#LevelUpMenu");
                Node escapeNode = SpriteManager.getGroup().lookup("#EscapeMenu");
		if ( e.getCode() == KeyCode.F ) {
                        if ( escapeNode != null ) { 
                            return;
                        }
			if ( levelUpNode != null ) { // okno juz jest otwarte, trzeba je zamknąć
				SpriteManager.removeNodeFromScene(levelUpNode);
				getGameLoop().play();
			} else { //otwieramy nowe okno
				if ( !getPlayer().canLevelUp() ) return; //jesli gracz nie moze levelowac
				try {
					levelUpNode = FXMLLoader.load(getClass().getResource("LevelUpMenu.fxml"));
					levelUpNode.setTranslateX(WindowBound.getResolution().multiply(0.5).getX()-50);
						levelUpNode.setTranslateY(WindowBound.getResolution().multiply(0.5).getY()-60);
					SpriteManager.addNodeToScene(levelUpNode);
				} catch (IOException ex) {
					Logger.getLogger(SniperWorld1.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		if ( e.getCode() == KeyCode.ESCAPE ) {
                   
                        if ( levelUpNode != null || StartMenuController.boolStartMenu != false) { //nie wlaczy sie jak startmenu wlaczone
                            return;
                        }
			if ( escapeNode != null ) { // okno juz jest otwarte, trzeba je zamknąć
				SpriteManager.removeNodeFromScene(escapeNode);
				getGameLoop().play();
			} else { //otwieramy nowe okno
				try {
					escapeNode = FXMLLoader.load(getClass().getResource("EscapeMenu.fxml"));
					SpriteManager.addNodeToScene(escapeNode);
				} catch (IOException ex) {
					Logger.getLogger(SniperWorld1.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}
	
        //odpalanie menu na starcie
        public void odpalStartMenu() {
                                      
                     
                    
                       try {
					StartMenuNode = FXMLLoader.load(getClass().getResource("StartMenu.fxml"));
					SpriteManager.addNodeToScene(StartMenuNode);
				} catch (IOException ex) {
					Logger.getLogger(SniperWorld1.class.getName()).log(Level.SEVERE, null, ex);
				}
                        
}           
        
	@Override
	protected void handleOnKeReleased(KeyEvent e) {
	}
}
