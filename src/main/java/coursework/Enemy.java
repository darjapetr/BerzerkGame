package coursework;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.List;

public class Enemy {
    private double x, y;
    private double laserDirectionX;
    private double laserDirectionY;
    private int cellSize;
    private Laser laser;
    private long lastShotTime;
    private long shotInterval = 1000;
    private int lives;
    private boolean isAlive;

    public Enemy(double startX, double startY, double laserDirX, double laserDirY, int cellSize, int lives) {
        this.x = startX;
        this.y = startY;
        this.laserDirectionX = laserDirX;
        this.laserDirectionY = laserDirY;
        this.cellSize = cellSize;
        this.lives = lives;
        this.isAlive = true;
        this.lastShotTime = System.currentTimeMillis();
    }

    public void update(Map map) {
        if (!isAlive) {
            return;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= shotInterval) {
            fireLaser();
            lastShotTime = currentTime;
        }

        if (laser != null && laser.isActive()) {
            laser.update(map);
        }
    }

    private void fireLaser() {
        laser = new Laser(x, y, laserDirectionX, laserDirectionY, cellSize);
    }

    public void render(GraphicsContext gc) {
        if (!isAlive) {
            return;
        }

        gc.setFill(Color.RED);
        gc.fillRect(x, y, cellSize, cellSize);

        if (laser != null && laser.isActive()) {
            laser.render(gc);
        }
    }

    public void loseLife() {
        lives--;
        if (lives <= 0) {
            isAlive = false;
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public Laser getLaser() {
        return laser;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}