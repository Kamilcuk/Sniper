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
    public static Timeline gameLoop;
	
    /** Number of frames per second. */
    private final int framesPerSecond = 30;
  
	private final WindowBound windowBound = new WindowBound();
	
	private static Player player;

	
    /**
     * Konstruktor wywoływany przez derywowaną klasę.
     */
    public GameWorld() {
        // create and set timeline for the game loop
        buildAndSetGameLoop();
    }
 
    /**
     * Tworzy i ustawia gameloop.
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
     * Initializuje świat gry.
     * @param primaryStage
     */
    public abstract void initialize(final Stage primaryStage);
 
    /**
	 * Włącza gameloop;
     */
    public void beginGameLoop() {
        getGameLoop().play();
    }
 
    /**
     * Zwraca ilość ramek na sekundę.
	 * @return 
     */
    protected int getFramesPerSecond() {
        return framesPerSecond;
    }

    /**
     * Zwraca gameLoop.
	 * @return 
     */
    protected static Timeline getGameLoop() {
        return gameLoop;
    }
 
    /**
	 * Ustawia aktualny gameLoop dla tego świata.
     * @param gameLoop Objekt który działa nieskończenie długo i reprezentuje pętle gry.
     */
    protected static void setGameLoop(Timeline gameLoop) {
        GameWorld.gameLoop = gameLoop;
    }
 
    /**
     * Zwraca objekt scnee  w JavaFX. To jest tak zwana powierzchnia gry,
	 * na której można dodawać inne elementy.
     * @return
     */
    public Scene getScene() {
        return scene;
    }
 
    /**
     * Ustawia scene gry. Przy okazji funkcja initializuje funkcje
	 * odbierające wejścia myszy i klawiatury.
     * @param gameSurface The main game surface (JavaFX Scene).
     */
    protected void setScene(Scene gameSurface) {
        this.scene = gameSurface;
		
		WindowBound.setResolution(new Point2D(getScene().getWidth(),getScene().getHeight()));
		addSprite(windowBound);
		
		/** install event handlers */
		scene.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent e) -> {
			onKeyPressed(e);
		});
        scene.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent e) -> {
			onKeyReleased(e);
		});
		scene.addEventHandler(MouseEvent.ANY, (MouseEvent e) -> {
			onMouseEvent(e);
		});
    }
	/**
	 * Funkcja wywoływana w przypadku naciśnięcia klawisza klawiatury.
	 * @param e 
	 */
	protected void onKeyPressed(KeyEvent e) {
		handleOnKeyPressed(e);
		for(Sprite sprite : SpriteManager.getAllSprites()) {
			sprite.onKeyPressed(e);
		}
	}
	/**
	 * Funkcja wywoływana w przypadku odciśnięcia klawisza klawiatury.
	 * @param e 
	 */
	protected void onKeyReleased(KeyEvent e) {
		handleOnKeReleased(e);
		for(Sprite sprite : SpriteManager.getAllSprites()) {
			sprite.onKeyReleased(e);
		}
		
	}
	/**
	 * Funkcja wywoływana w przypadku dowolnej akcji myszy.
	 * @param e 
	 */
	protected void onMouseEvent(MouseEvent e) {
		handleOnMouseEvent(e);
		for(Sprite sprite : SpriteManager.getAllSprites()) {
			sprite.onMouseEvent(e);
		}
		
	}
	/**
	 * Funkcja wywoływana po naciśnięciu klawisza klawiatury.
	 * Istnieje celem przeładowania przez klasę dziedziczacą.
	 * @param e 
	 */
	protected void handleOnKeyPressed(KeyEvent e) {
		
	}
	/**
	 * Funkcja wywoływana po odciśnięciu klawiasza klawiatury.
	 * Istnieje celem przeładowania przez klasę dziedziczacą.
	 * @param e 
	 */
	protected void handleOnKeReleased(KeyEvent e) {
		
	}
	/**
	 * Funkcja wywoływana po dowolnej akcji myszą.
	 * Istnieje celem przeładowania przez klasę dziedziczacą.
	 * @param e
	*/
	protected void handleOnMouseEvent(MouseEvent e) {
		
	}
	/**
	 * Dodaje dany sprite do listy obsługiwanych spritów.
	 * @param sprite 
	 */
	public void addSprite(Sprite sprite) {
		(new SpriteManager()).addSprite(sprite);
	}
	/**
	 * Usuwa sprite z listy obsługiwanych spritów.
	 * @param sprite 
	 */
	public void removeSprite(Sprite sprite) {
		(new SpriteManager()).addSprite(sprite);
	}
	/**
	 * ustawia grupę wyświetlającą.
	 * @param group 
	 */
	public void setGroup(Group group) {
		(new SpriteManager()).setGroup(group);
	}
	/**
	 * Zwraca grupę wyświetlającą.
	 * @return 
	 */
	public Group getGroup() {
		return (new SpriteManager()).getGroup();
	}
	/**
	 * Funkcja wykonująca czyszczenie po zakończeniu wyświetlania.
	 */
	public void shutdown() {
		for(Sprite sprite : SpriteManager.getAllSprites() ) {
			removeSprite(sprite);
		}
		SoundManager.shutdown();
	}
	
	
	public static Player getPlayer() {
		return player;
	}

	protected static void setPlayer(Player player) {
		GameWorld.player = player;
	}
}