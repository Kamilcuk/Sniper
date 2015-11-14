package sniper.game;
 
import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.scene.Node;
 
/**
 * The Sprite class represents a image or node to be displayed.
 * In a 2D game a sprite will contain a velocity for the image to
 * move across the scene area. The game loop will call the update()
 * and collide() method at every interval of a key frame. A list of
 * animations can be used during different situations in the game
 * such as rocket thrusters, walking, jumping, etc.
 * @author cdea
 */
public abstract class Sprite {
	
    /** Current display node */
    public Node node;
 
    /**
     * Updates this sprite object's velocity, or animations.
     */
    public abstract void update();
 
    /**
     * Did this sprite collide into the other sprite?
     *
     * @param other - The other sprite.
     * @return
     */
    public boolean collide(Sprite other) {
        return false;
    }
}