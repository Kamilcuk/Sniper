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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Kamil Cukrowski
 */
public class EscapeMenuController extends FXMLMenu implements Initializable {

	@FXML
	private AnchorPane EscapeMenu;
	@FXML
	private Label statystyki;
	@FXML
	private Label labelPause;
	@FXML
	private Button buttonResume;
	
	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
		nodeName = EscapeMenu.getId();
		// umiesc się na środku ekranu
		double X = EscapeMenu.getBoundsInLocal().getHeight();
		double Y = 275; //EscapeMenu.getBoundsInLocal().getWidth(); Czemu nie działa?
		EscapeMenu.setTranslateX(WindowBound.getResolution().multiply(0.5).getX()-X/2);
		EscapeMenu.setTranslateY(WindowBound.getResolution().multiply(0.5).getY()-Y/2);
		// pausuj gameWorld
		GameWorld.getGameLoop().pause();
		statystyki.setText(StatystykaGry.getText());
	}	

	@FXML
	private void buttonExit(ActionEvent event) {
		Platform.exit();
	}
        
	@FXML
	private void buttonResume(ActionEvent event) {
		GameWorld.getGameLoop().play();
		exit();
	}
	
	public Label getLabelPause() {
		return labelPause;
	}

	public Button getButtonResume() {
		return buttonResume;
	}
}
