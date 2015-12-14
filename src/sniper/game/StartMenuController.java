/*
 * Copyright (C) 2015 deser
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author deser
 */
public class StartMenuController extends FXMLMenu implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane StartMenu;
    @FXML
    private Button buttonstart;
    @FXML
    private Button buttonzamknij;
    @FXML
    private Label labelpoziom;
    @FXML
    private ChoiceBox poziom;

    public static boolean boolStartMenu = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        GameWorld.getGameLoop().pause();
        boolStartMenu = true;
        poziom.getItems().addAll("Łatwy", "Średni", "Trudny");
        poziom.setValue("Łatwy");
    }

    @FXML
    private void buttonstart(ActionEvent event) {
        GameWorld.getGameLoop().play();
        exit(StartMenu);
        boolStartMenu = false;
        getChoice(poziom);
        String pozT = (String) poziom.getValue();
        switch (pozT) {
            case "Łatwy":
                Zombie.setPoziomTrudnosci(1);
                break;
            case "Średni":
                Zombie.setPoziomTrudnosci(1.5);
                break;
            case "Trudny":
                Zombie.setPoziomTrudnosci(3);
                break;
        }
        getGameWorld().gameRestart();
    }

    @FXML
    private void buttonzamknij(ActionEvent event) {
        Platform.exit();
    }

    public Button getbuttonzamknij() {
        return buttonzamknij;
    }

    public Button getbuttonstart() {
        return buttonstart;
    }

    public void getChoice(ChoiceBox<String> poziom) {
        String poz = poziom.getValue();
    }

}
