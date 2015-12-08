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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Kamil Cukrowski
 */
public class LevelUpMenuController extends FXMLMenu implements Initializable {
	
	@FXML
	private AnchorPane LevelUpMenu;
	
	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
		nodeName = LevelUpMenu.getId();
		
		double X = LevelUpMenu.getBoundsInLocal().getHeight();
		double Y = 208; //LevelUpMenu.getBoundsInLocal().getWidth(); // Czemu nie działa?
		LevelUpMenu.setTranslateX(WindowBound.getResolution().multiply(0.5).getX()-X/2);
		LevelUpMenu.setTranslateY(WindowBound.getResolution().multiply(0.5).getY()-Y/2);
		
		GameWorld.getGameLoop().pause();
	}
	
	@FXML
	private void buttonHpOnAction(ActionEvent event) {
		GameWorld.getPlayer().levelUp("playerHp");
		GameWorld.getGameLoop().play();
		exit();
	}

	@FXML
	private void buttonHpRegenOnAction(ActionEvent event) {
		GameWorld.getPlayer().levelUp("playerHpRegen");
		GameWorld.getGameLoop().play();
		exit();
	}

	@FXML
	private void buttonAttackOnAction(ActionEvent event) {
		GameWorld.getPlayer().levelUp("playerAttack");
		GameWorld.getGameLoop().play();
		exit();
	}

	@FXML
	private void buttonAttackSpeedOnAction(ActionEvent event) {
		GameWorld.getPlayer().levelUp("playerAttackSpeed");
		GameWorld.getGameLoop().play();
		exit();
	}

	@FXML
	private void buttonWalkSpeedOnAction(ActionEvent event) {
		GameWorld.getPlayer().levelUp("playerWalkSpeed");
		GameWorld.getGameLoop().play();
		exit();
	}
	
}
