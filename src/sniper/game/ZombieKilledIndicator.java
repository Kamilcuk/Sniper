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

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Sprite wyświetlający tekst na górze okna.
 * @author Kamil Cukrowski
 */
public class ZombieKilledIndicator extends Sprite {

    private final String APPEND = "Martwe zombie: ";
    private final Label text = new Label();

    public ZombieKilledIndicator() {
        text.setTranslateX(250);
        text.setTranslateY(15);
        text.setFont(Font.font(null, FontWeight.BOLD, 20));
        text.setTextFill(Color.web("#800000"));

        node = text;
    }

    @Override
    public void update() {
        Player pl = SniperWorld1.getPlayer();
        ImageView bg = (ImageView) SpriteManager.getGroup().lookup("#tlo");
        //System.out.println(bg);
        String levelup = "";
        if (pl.canLevelUp()) {
            levelup = " Nastepny poziom! ";
        }
        text.setText(APPEND + ZombieManager.getDeadZombies() + " Aktualny poziom: " + pl.getPlayerLevel() + levelup);
    }
}
