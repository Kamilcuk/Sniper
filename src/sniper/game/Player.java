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

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.event.DocumentEvent;


/**
 * Objekt będący naszą bohaterką.
 * @author Kamil Cukrowski
 */
public class Player extends Sprite {
	private final ImageView obraz;
	private final Stage primaryStage;
	private final ArrayList<String> input;
	private final double rotateOffsetX, rotateOffsetY;
	
	private int speed = 6;
	private double mouseX, mouseY;
	private MouseButton mouseButton;
	
	/**
	 * Konstruktor objekty player
	 * @param stage objekt stage jest potrzebny do zinicjalizowania 
	 *				wejść klaiwatury i myszy
	 */
	public Player(final Stage stage) {
		input = new ArrayList<>();
		primaryStage = stage;
		obraz = new ImageView();
		obraz.setImage(new Image("File:resources\\weapon\\AK-47\\player.png"));
		obraz.setTranslateX(0);
		obraz.setTranslateY(0);
		obraz.setFitHeight(50);
		obraz.setPreserveRatio(true);
		rotateOffsetX = obraz.getBoundsInParent().getWidth()/2;
		rotateOffsetY = obraz.getBoundsInParent().getHeight()/2;
		
		//przypisz obraz do node
		node = obraz;
		
		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED,
            new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                    String code = e.getCode().toString();
                    // only add once... prevent duplicates
                    if ( !input.contains(code) )
                        input.add( code );
                }
            });
 
        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED,
            new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                    String code = e.getCode().toString();
                    input.remove( code );
                }
            });
		primaryStage.addEventHandler(MouseEvent.ANY,
			new EventHandler<MouseEvent>()
            {
                public void handle(MouseEvent e)
                {
					mouseButton = e.getButton();
					mouseX = e.getX();
					mouseY = e.getY();
                }
            });
	}
	
	@Override
	public void update() {
		// chodzenie WSAD
		if (input.contains("W")) {
			obraz.setTranslateY( obraz.getTranslateY() - speed );
		}
		if (input.contains("S")) {
			obraz.setTranslateY( obraz.getTranslateY() + speed );
		}
		if (input.contains("A")) {
			obraz.setTranslateX( obraz.getTranslateX() - speed );
		}
		if (input.contains("D")) {
			obraz.setTranslateX( obraz.getTranslateX() + speed );
		}
		// obracanie obrazu do myszy
		double deltaY = mouseY - (obraz.getTranslateY()+rotateOffsetY);
		double deltaX = mouseX - (obraz.getTranslateX()+rotateOffsetX);
		double angle = Math.atan2(deltaY, deltaX)*180/Math.PI;
		obraz.setRotate(angle+90);
		//
		if ( mouseButton == MouseButton.PRIMARY ) {
			// SZRZELANIE! 
		}
		node = obraz;
	}
}
