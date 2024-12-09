package coursework;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Enemy extends GameCharacter{
    private final double laserDirectionX;
    private final double laserDirectionY;
    private long lastShotTime;
    private static final long SHOT_INTERVAL = 1000;

    public Enemy(double startX, double startY, double laserDirX, double laserDirY, int cellSize, int lives) {
        super(startX, startY, cellSize, lives);
        this.laserDirectionX = laserDirX;
        this.laserDirectionY = laserDirY;
        this.lastShotTime = System.currentTimeMillis();
    }

    public boolean isReadyToFire() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= SHOT_INTERVAL) {
            lastShotTime = currentTime;
            return true;
        }
        return false;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!isAlive) {
            return;
        }
        gc.setFill(Color.RED);
        gc.fillRect(x, y, cellSize, cellSize);
    }

    public double getLaserDirectionX() {
        return laserDirectionX;
    }

    public double getLaserDirectionY() {
        return laserDirectionY;
    }
}