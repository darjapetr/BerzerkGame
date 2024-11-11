package coursework;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.util.*;

public class GameRules {
    private Scene scene;
    private Pane pane;
    private Canvas canvas;
    private GraphicsContext gc;
    private Image levelImage, wonImage, loseImage;

    private static final int CELL_SIZE = 20;

    private Player player;
    private Map map;
    private Set<KeyCode> activeKeys = new HashSet<>();
    private List<Laser> playerLasers = new ArrayList<>();
    private Enemy enemy1, enemy2, enemy3;
    private GameState gameState;

    public GameRules () {
        gameState = GameState.PLAYING;
        player = new Player(CELL_SIZE, 7 * CELL_SIZE, 1, 0, CELL_SIZE /4, CELL_SIZE,3);
        enemy1 = new Enemy(8 * CELL_SIZE, 2 * CELL_SIZE, 0, 1, CELL_SIZE, 15);
        enemy2 = new Enemy(15 * CELL_SIZE, 12 * CELL_SIZE, 0, -1, CELL_SIZE, 15);
        enemy3 = new Enemy(22 * CELL_SIZE, 2 * CELL_SIZE, 0, 1, CELL_SIZE, 15);
        map = new Map("map1.txt", CELL_SIZE);
        canvas = new Canvas(map.getWidth() * CELL_SIZE, map.getHeight() * CELL_SIZE);
        gc = canvas.getGraphicsContext2D();
        pane = new Pane();
        pane.getChildren().add(canvas);
        scene = new Scene(pane);

        scene.setOnKeyPressed(event -> activeKeys.add(event.getCode()));
        scene.setOnKeyReleased(event -> activeKeys.remove(event.getCode()));

        levelImage = new Image(getClass().getResource("/level1.png").toExternalForm());
        wonImage = new Image(getClass().getResource("/won.png").toExternalForm());
        loseImage = new Image(getClass().getResource("/lose.png").toExternalForm());

        startGameLoop(player, map);
    }

    private void startGameLoop (Player player, Map map) {
        gc.drawImage(levelImage, 0, 0, canvas.getWidth(), canvas.getHeight());
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> {
            AnimationTimer gameLoop = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (gameState == GameState.PLAYING) {
                        handleInput();
                        update();
                        render();

                        if (player.isAtExit(map)) {
                            gameState = GameState.LEVEL_COMPLETE;
                        } else if (!player.isAlive()) {
                            gameState = GameState.GAME_OVER;
                        }
                    } else if (gameState == GameState.LEVEL_COMPLETE) {
                        gc.drawImage(wonImage, 0, 0, canvas.getWidth(), canvas.getHeight());
                    } else if (gameState == GameState.GAME_OVER) {
                        gc.drawImage(loseImage, 0, 0, canvas.getWidth(), canvas.getHeight());
                    }
                }
            };
            gameLoop.start();
        });
        pause.play();
    }

    private void update() {
        playerLasers.forEach(laser -> laser.update(map));
        if(enemy1.isAlive()) {
            for (Laser laser : playerLasers) {
                laser.checkPlayerLaserHits(laser, enemy1);
            }
        }
        if(enemy2.isAlive()) {
            for (Laser laser : playerLasers) {
                laser.checkPlayerLaserHits(laser, enemy2);
            }
        }
        if(enemy3.isAlive()) {
            for (Laser laser : playerLasers) {
                laser.checkPlayerLaserHits(laser, enemy3);
            }
        }

        enemy1.update(map);
        enemy2.update(map);
        enemy3.update(map);
        if (player.isAlive()) {
            Laser enemyLaser = enemy1.getLaser();
            enemyLaser.checkEnemyLaserHits(enemyLaser, player);
        }
        if (player.isAlive()) {
            Laser enemyLaser = enemy2.getLaser();
            enemyLaser.checkEnemyLaserHits(enemyLaser, player);
        }
        if (player.isAlive()) {
            Laser enemyLaser = enemy3.getLaser();
            enemyLaser.checkEnemyLaserHits(enemyLaser, player);
        }
    }

    private void handleInput() {
        if (activeKeys.contains(KeyCode.W)) {
            player.moveUp(map);
        }
        if (activeKeys.contains(KeyCode.S)) {
            player.moveDown(map);
        }
        if (activeKeys.contains(KeyCode.A)) {
            player.moveLeft(map);
        }
        if (activeKeys.contains(KeyCode.D)) {
            player.moveRight(map);
        }
        if (activeKeys.contains(KeyCode.SPACE)) {
            Laser laser = new Laser(player.getX(), player.getY(), player.getDx(), player.getDy(), CELL_SIZE);
            playerLasers.add(laser);
        }
    }

    private void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        map.render(gc);
        player.render(gc);
        enemy1.render(gc);
        enemy2.render(gc);
        enemy3.render(gc);
        playerLasers.forEach(laser -> laser.render(gc));
    }

    public Scene getScene() {
        return scene;
    }
}