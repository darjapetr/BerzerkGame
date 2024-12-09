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
    private final Scene scene;
    private final Canvas canvas;
    private final GraphicsContext gc;
    private Image wonImage, loseImage, startImage;
    private final List<String> mapFiles = List.of("map1.txt", "map2.txt", "map3.txt");

    public static final int CELL_SIZE = 20;
    private Player player;
    private Map map;
    private GameState gameState;
    private final LaserManager laserManager;
    private int currentLevel;
    private final Set<KeyCode> activeKeys = new HashSet<>();
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<LevelStrategy> levelStrategies;

    public GameRules() {
        gameState = GameState.PLAYING;
        currentLevel = 0;
        laserManager = new LaserManager();

        Pane pane = new Pane();
        canvas = new Canvas();
        gc = canvas.getGraphicsContext2D();
        pane.getChildren().add(canvas);
        scene = new Scene(pane);
        scene.setOnKeyPressed(event -> activeKeys.add(event.getCode()));
        scene.setOnKeyReleased(event -> activeKeys.remove(event.getCode()));

        loadImageResources();
        levelStrategies = List.of(new Level0Strategy(), new Level1Strategy(), new Level2Strategy());
        loadLevel(currentLevel);
        startGameLoop();
    }

    private void loadImageResources() {
        startImage = new Image(getClass().getResource("/start.png").toExternalForm());
        wonImage = new Image(getClass().getResource("/won.png").toExternalForm());
        loseImage = new Image(getClass().getResource("/lose.png").toExternalForm());
    }

    private void loadLevel(int level) {
        map = new Map(mapFiles.get(level), CELL_SIZE);
        canvas.setWidth(map.getWidth() * CELL_SIZE);
        canvas.setHeight(map.getHeight() * CELL_SIZE);
        levelStrategies.get(level).initialize(this);
    }

    private void startGameLoop() {
        gc.drawImage(startImage, 0, 0, canvas.getWidth(), canvas.getHeight());
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> {
            AnimationTimer gameLoop = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    handleGameState();
                }
            };
            gameLoop.start();
        });
        pause.play();
    }

    private void handleGameState() {
        switch (gameState) {
            case PLAYING -> handlePlayingState();
            case LEVEL_COMPLETE -> gc.drawImage(wonImage, 0, 0, canvas.getWidth(), canvas.getHeight());
            case GAME_OVER -> gc.drawImage(loseImage, 0, 0, canvas.getWidth(), canvas.getHeight());
        }
    }

    private void handlePlayingState() {
        handleInput();
        update();
        render();
        if (CollisionManager.isCharacterCollidingWithExit(player, map)) {
            currentLevel++;
            if (currentLevel < mapFiles.size()) {
                loadLevel(currentLevel);
            } else {
                gameState = GameState.LEVEL_COMPLETE;
            }
        } else if (!player.isAlive()) {
            gameState = GameState.GAME_OVER;
        }
    }

    private void update() {
        laserManager.update(map, player, enemies);
        handleEnemyShooting(enemies);

        if (!player.isAlive()) {
            gameState = GameState.GAME_OVER;
        }
    }

    private void handleInput() {
        double oldX = player.getX();
        double oldY = player.getY();

        if (activeKeys.contains(KeyCode.W)) player.moveUp();
        if (activeKeys.contains(KeyCode.S)) player.moveDown();
        if (activeKeys.contains(KeyCode.A)) player.moveLeft();
        if (activeKeys.contains(KeyCode.D)) player.moveRight();
        checkCharactersCollision(oldX, oldY);
        checkMapCollision(oldX, oldY);

        if (activeKeys.contains(KeyCode.SPACE)) {
            laserManager.shootLaser(player, player.getDx(), player.getDy());
        }
    }

    private void handleEnemyShooting(List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (enemy.isAlive() && enemy.isReadyToFire()) {
                laserManager.shootLaser(enemy, enemy.getLaserDirectionX(), enemy.getLaserDirectionY());
            }
        }
    }

    private void checkCharactersCollision(double oldX, double oldY) {
        for (Enemy enemy : enemies) {
            if (CollisionManager.isCharactersColliding(player, enemy)) {
                player.setX(oldX);
                player.setY(oldY);
                break;
            }
        }
    }

    private void checkMapCollision(double oldX, double oldY) {
        if (CollisionManager.isCharacterCollidingWithMap(player, map)) {
            player.setX(oldX);
            player.setY(oldY);
        }
    }

    private void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        map.render(gc);
        player.render(gc);
        for (Enemy enemy : enemies) {
            enemy.render(gc);
        }
        laserManager.render(gc);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public Scene getScene() {
        return scene;
    }
}