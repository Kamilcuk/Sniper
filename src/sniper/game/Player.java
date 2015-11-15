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
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


/**
 * Objekt będący naszą bohaterką.
 * @author Kamil Cukrowski
 */
public class Player extends Sprite {
	private final ImageView obraz = new ImageView();
	private final ArrayList<String> input = new ArrayList<>();
	private final double rotateOffsetX, rotateOffsetY;
	private final Bron bron = new Bron();
	
	private Group myGroup = new Group();
	private MouseButton mouseButton;
	private double mouseX, mouseY;
	
	/*  config */
	private final int playerSpeed = 2;
	private final int imageSize = 50;
	
	/**
	 * Konstruktor objekty player
	 * @param _gameWorld gameWorld object
	 */
	public Player(final Stage stage, final Point2D initPos) {
		bron.ustawBron("AK-47");
		
		obraz.setImage(bron.getPlayerImage());
		obraz.setTranslateX(initPos.getX()/2);
		obraz.setTranslateY(initPos.getY()/2);
		obraz.setFitHeight(imageSize);
		obraz.setPreserveRatio(true);
		rotateOffsetX = obraz.getBoundsInParent().getWidth()/2;
		rotateOffsetY = obraz.getBoundsInParent().getHeight()/2;
		//przypisz obraz do node
		
		myGroup.getChildren().add(0, obraz);
		myGroup.getChildren().add(1, bron.node);
		node = myGroup;
		
		stage.addEventHandler(KeyEvent.KEY_PRESSED,
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
        stage.addEventHandler(KeyEvent.KEY_RELEASED,
            new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                    String code = e.getCode().toString();
                    input.remove( code );
                }
            });
		stage.addEventHandler(MouseEvent.ANY,
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
			obraz.setTranslateY(obraz.getTranslateY() - playerSpeed );
		}
		if (input.contains("S")) {
			obraz.setTranslateY(obraz.getTranslateY() + playerSpeed );
		}
		if (input.contains("A")) {
			obraz.setTranslateX(obraz.getTranslateX() - playerSpeed );
		}
		if (input.contains("D")) {
			obraz.setTranslateX(obraz.getTranslateX() + playerSpeed );
		}
		// obracanie obrazu do myszy
		double middleX = obraz.getTranslateX()+rotateOffsetX;
		double middleY = obraz.getTranslateY()+rotateOffsetY;
		double deltaX = middleX - mouseX;
		double deltaY = middleY - mouseY;
		double angle = Math.atan2(deltaX, deltaY)*180/Math.PI;
		obraz.setRotate(-angle);
		
		//if ( mouseButton == MouseButton.PRIMARY ) {
		if ( input.contains("Q") ) {
			bron.strzel(middleX, middleY, angle);
		}
		
		bron.update();
	}
}
