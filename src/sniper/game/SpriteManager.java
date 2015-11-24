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
	
	/** All the sprite objects currently in play */
    private final static List<Sprite> SPRITES = new ArrayList<>();
	
    /** All nodes to be displayed in the game window. */
    private static Group group = new Group();
	
	/**
	 * zwraca KOPIE listy wszystkich spritów
	 * @return
	 */
    public static List<Sprite> getAllSprites() {
		/* kopiuj liste. dzięki temu można wprowadzać 
		 * zmiany do listy chodząc po niej	*/
        return new ArrayList<Sprite>(SPRITES);
    }
 	/**
     * Odświerza każdego sprita znajdującego się w świecie gry.
     */
    protected static void preUpdateSprites() {
        for (Sprite sprite : getAllSprites()){
            sprite.preUpdate();
        }
    }
    /**
     * Sprawdza kolizję każdych dwóch spritów.
     */
    protected static void checkCollisions() {
		final List<Sprite> temp = getAllSprites();
		for(int i=0;i<temp.size();i++) {
			Sprite spriteA = temp.get(i);
			for (int j=i+1;j<temp.size();j++) {
				Sprite spriteB = temp.get(j);
				spriteA.collide(spriteB);
				spriteB.collide(spriteA);
			}
		}
    }
	/**
     * Odświerza każdego sprita znajdującego się w świecie gry.
     */
    protected static void updateSprites() {
        for (Sprite sprite : getAllSprites()){
            sprite.update();
        }
    }
 	/**
     * Zwraca JavaFX group która się wyświetla
     * @return 
     */
    public static Group getGroup() {
        return group;
    }
 
    /**
	 * Ustawia JavaFX group.
     * @param group To jest "root container" tego co się wyświetla
     */
    protected void setGroup(Group group) {
        this.group = group;
    }
	
	/** 
	 * Dodaje node do wyświetlanej grupy
	 * @param node 
	 */
	protected void addNodeToScene(Node node) {
		if ( node != null )
			getGroup().getChildren().add(node);
	}
	
	/**
	 * Usuwa node z wyświetlanej grupy
	 * @param node 
	 */
	protected void removeNodeFromScene(Node node) {
		if ( node != null )
			getGroup().getChildren().remove(node);
	}
  
    /**
     * Dodaje sprity do managera.
     * @param sprite
     */
    public void addSprite(Sprite sprite) {
		if ( sprite == null ) return;
		if ( SPRITES.contains(sprite) ) return;
        SPRITES.add(sprite);
		addNodeToScene(sprite.node);
    }
 
    /**
     * Usuwa sprity z menagera.
     * @param sprite
     */
    public void removeSprite(Sprite sprite) {
		if ( sprite == null ) return;
		if ( !SPRITES.contains(sprite) ) return;
        SPRITES.remove(sprite);
		if ( sprite.node == null ) return;
		sprite.node.setVisible(false);
		removeNodeFromScene(sprite.node);
	}

	public static void spriteManagerUpdate() {
		preUpdateSprites();
        // check for collision
		checkCollisions();
		// update actors
        updateSprites();
	}
}
