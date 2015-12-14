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
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Node;

/**
 *
 * @author Kamil Cukrowski
 */
public class SpriteManager {

    /**
     * All the sprite objects currently in play
     */
    private final static List<Sprite> SPRITES = new ArrayList<>();

    /**
     * All nodes to be displayed in the game window.
     */
    private static Group group = new Group();

    /**
     * zwraca KOPIE listy wszystkich spritów
     *
     * @return
     */
    public static List<Sprite> getAllSprites() {
        /* kopiuj liste. dzięki temu można wprowadzać 
		 * zmiany do listy chodząc po niej	*/
        return new ArrayList<>(SPRITES);
    }

    public static List<Sprite> getAllCollisionSprites() {
        ArrayList<Sprite> temp = new ArrayList<>();
        for (Sprite sprite : SPRITES) {
            if (sprite.collisionBounds != null
                    || sprite.getClass().equals(WindowBound.class)) {
                temp.add(sprite);
            }
        }
        return temp;
    }

    /**
     * Sprawdza kolizję każdych dwóch spritów.
     */
    protected static void checkCollisions() {
        final List<Sprite> sprites = getAllCollisionSprites();
        // remove sprites without collisionBounds
        for (int i = 0; i < sprites.size(); i++) {
            Sprite spriteA = sprites.get(i);
            for (int j = i + 1; j < sprites.size(); j++) {
                Sprite spriteB = sprites.get(j);
                double dist = spriteA.jakBliskoCollide(spriteB);

                spriteA.collide(spriteB, dist);
                spriteB.collide(spriteA, dist);

            }
        }
    }

    /**
     * Odświerza każdego sprita znajdującego się w świecie gry.
     */
    protected static void updateSprites() {
        getAllSprites().stream().forEach((sprite) -> {
            sprite.update();
        });
    }

    /**
     * Zwraca JavaFX group która się wyświetla
     *
     * @return
     */
    public static Group getGroup() {
        return group;
    }

    /**
     * Ustawia JavaFX group.
     *
     * @param group To jest "root container" tego co się wyświetla
     */
    protected static void setGroup(Group group) {
        SpriteManager.group = group;
    }

    /**
     * Dodaje node do wyświetlanej grupy
     *
     * @param node
     */
    protected static void addNodeToScene(Node node) {
        if (node != null) {
            getGroup().getChildren().add(node);
        }
    }

    /**
     * Usuwa node z wyświetlanej grupy
     *
     * @param node
     */
    protected static void removeNodeFromScene(Node node) {
        if (node != null) {
            getGroup().getChildren().remove(node);
        }
    }

    /**
     * Dodaje sprity do managera.
     *
     * @param sprite
     */
    public static void addSprite(Sprite sprite) {
        if (sprite == null) {
            return;
        }
        if (SPRITES.contains(sprite)) {
            return;
        }
        SPRITES.add(sprite);
        addNodeToScene(sprite.node);
    }

    /**
     * Usuwa sprita z menagera.
     * @param sprite
     */
    public static void removeSprite(Sprite sprite) {
        if (sprite == null) {
            return;
        }
        if (!SPRITES.contains(sprite)) {
            return;
        }
        SPRITES.remove(sprite);
        if (sprite.node == null) {
            return;
        }
        sprite.node.setVisible(false);
        removeNodeFromScene(sprite.node);
    }

    /**
     * Funkcja usuwa wszystkie sprity.
     */
    public static void removeAllSprites() {
        getAllSprites().stream().forEach((sprite) -> {
            removeSprite(sprite);
        });
    }
    
    public static void spriteManagerUpdate() {
        checkCollisions();
        // update actors
        updateSprites();
    }
}
