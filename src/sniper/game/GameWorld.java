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
 
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
 
/**
 * This application demonstrates a JavaFX 2.x Game Loop.
 * Shown below are the methods which comprise of the fundamentals to a
 * simple game loop in JavaFX:
*
 *  <strong>initialize()</strong> - Initialize the game world.
 *  <strong>beginGameLoop()</strong> - Creates a JavaFX Timeline object containing the game life cycle.
 *  <strong>updateSprites()</strong> - Updates the sprite objects each period (per frame)
 *  <strong>checkCollisions()</strong> - Method will determine objects that collide with each other.
 *  <strong>cleanupSprites()</strong> - Any sprite objects needing to be removed from play.
 *
 * Skopiowane z https://github.com/carldea/JFXGen/
 */
/**
 *
 * @author Kamil Cukrowski
 */
public abstract class GameWorld {
 
    /** The JavaFX Scene as the game surface */
    private Scene scene;
    /** The game loop using JavaFX's <code>Timeline</code> API.*/
    private static Timeline gameLoop;
	
    /** Number of frames per second. */
    private final int framesPerSecond = 30;
  
	private final WindowBound windowBound = new WindowBound();
	
    /**
     * Constructor that is called by the derived class. This will
     * set the frames per second, title, and setup the game loop.
     */
    public GameWorld() {
        // create and set timeline for the game loop
        buildAndSetGameLoop();
    }
 
    /**
     * Builds and sets the game loop ready to be started.
     */
    protected final void buildAndSetGameLoop() {
        final Duration oneFrameAmt = Duration.millis(1000/getFramesPerSecond());
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt,
            new EventHandler<javafx.event.ActionEvent>() {
                public void handle(javafx.event.ActionEvent event) {
					SpriteManager.spriteManagerUpdate();
                }
        }); // oneFrame
 
        // sets the game world's game loop (Timeline)
        setGameLoop(TimelineBuilder.create()
                .cycleCount(Animation.INDEFINITE)
                .keyFrames(oneFrame)
                .build());
    }
 
    /**
     * Initialize the game world by update the JavaFX Stage.
     * @param primaryStage
     */
    public abstract void initialize(final Stage primaryStage);
 
    /**Kicks off (plays) the Timeline objects containing one key frame
     * that simply runs indefinitely with each frame invoking a method
     * to update sprite objects, check for collisions, and cleanup sprite
     * objects.
     *
     */
    public void beginGameLoop() {
        getGameLoop().play();
    }
 
    /**
     * Returns the frames per second.
     * @return int The frames per second.
     */
    protected int getFramesPerSecond() {
        return framesPerSecond;
    }

    /**
     * The game loop (Timeline) which is used to update, check collisions, and
     * cleanup sprite objects at every interval (fps).
     * @return Timeline An animation running indefinitely representing the game
     * loop.
     */
    protected static Timeline getGameLoop() {
        return gameLoop;
    }
 
    /**
     * The sets the current game loop for this game world.
     * @param gameLoop Timeline object of an animation running indefinitely
     * representing the game loop.
     */
    protected static void setGameLoop(Timeline gameLoop) {
        GameWorld.gameLoop = gameLoop;
    }
 
    /**
     * Returns the JavaFX Scene. This is called the game surface to
     * allow the developer to add JavaFX Node objects onto the Scene.
     * @return
     */
    public Scene getScene() {
        return scene;
    }
 
    /**
     * Sets the JavaFX Scene. This is called the game surface to
     * allow the developer to add JavaFX Node objects onto the Scene.
     * @param gameSurface The main game surface (JavaFX Scene).
     */
    protected void setScene(Scene gameSurface) {
        this.scene = gameSurface;
		
		windowBound.setResolution(new Point2D(getScene().getWidth(),getScene().getHeight()));
		addSprite(windowBound);
		
		/** install event handlers */
		scene.addEventHandler(KeyEvent.KEY_PRESSED,
            new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                    onKeyPressed(e);
                }
            });
        scene.addEventHandler(KeyEvent.KEY_RELEASED,
            new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                    onKeyReleased(e);
                }
            });
		scene.addEventHandler(MouseEvent.ANY,
			new EventHandler<MouseEvent>()
            {
                public void handle(MouseEvent e)
                {
					onMouseEvent(e);
                }
            });
    }
	
	protected void onKeyPressed(KeyEvent e) {
		handleOnKeyPressed(e);
		for(Sprite sprite : SpriteManager.getAllSprites()) {
			sprite.onKeyPressed(e);
		}
	}
	protected void onKeyReleased(KeyEvent e) {
		handleOnKeReleased(e);
		for(Sprite sprite : SpriteManager.getAllSprites()) {
			sprite.onKeyReleased(e);
		}
		
	}
	protected void onMouseEvent(MouseEvent e) {
		handleOnMouseEvent(e);
		for(Sprite sprite : SpriteManager.getAllSprites()) {
			sprite.onMouseEvent(e);
		}
		
	}
	
	protected void handleOnKeyPressed(KeyEvent e) {
		
	}
	protected void handleOnKeReleased(KeyEvent e) {
		
	}
	protected void handleOnMouseEvent(MouseEvent e) {
		
	}
	
	public void addSprite(Sprite sprite) {
		(new SpriteManager()).addSprite(sprite);
	}
	public void removeSprite(Sprite sprite) {
		(new SpriteManager()).addSprite(sprite);
	}
	public void setGroup(Group group) {
		(new SpriteManager()).setGroup(group);
	}
	public Group getGroup() {
		return (new SpriteManager()).getGroup();
	}
	
	public void shutdown() {
		for(Sprite sprite : SpriteManager.getAllSprites() ) {
			removeSprite(sprite);
		}
		SoundManager.shutdown();
	}
}