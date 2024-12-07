package coursework;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Laser {
    private int x, y;
    private double dx, dy;
    private double speed = 5.0;
    private int size = 5;
    private boolean active;
    private int cellSize;

    public Laser(double startX, double startY, double directionX, double directionY, int cellSize) {
        this.x = (int) startX + cellSize / 2;
        this.y = (int) startY + cellSize / 2;
        this.dx = directionX * speed;
        this.dy = directionY * speed;
        this.active = true;
        this.cellSize = cellSize;
    }

    public void update(Map map) {
        if (!active) return;
        x += dx;
        y += dy;

        int tileX = x / map.getCellSize();
        int tileY = y / map.getCellSize();

        if (isOutOfBounds(map)) {
            active = false;
            return;
        }

        if (tileY >= 0 && tileY < map.getHeight() && tileX >= 0 && tileX < map.getWidth()) {
            if (map.isWall(tileY, tileX) || map.isExit(tileY, tileX)) {
                active = false;
            }
        } else {
            active = false;
        }
    }

    private boolean isOutOfBounds(Map map) {
        return x < 0 || x > map.getWidth() * map.getCellSize() ||
                y < 0 || y > map.getHeight() * map.getCellSize();
    }

    public void render(GraphicsContext gc) {
        if (active) {
            gc.setFill(Color.RED);
            gc.fillOval(x - size / 2, y - size / 2, size, size);
        }
    }

    private boolean isPlayerHitByLaser(Laser laser, Player player) {
        double laserLeft = laser.getX() - size / 2;
        double laserRight = laser.getX() + size / 2;
        double laserTop = laser.getY() - size / 2;
        double laserBottom = laser.getY() + size / 2;

        double playerLeft = player.getX();
        double playerRight = player.getX() + player.getCellSize();
        double playerTop = player.getY();
        double playerBottom = player.getY() + player.getCellSize();

        return laserRight > playerLeft && laserLeft < playerRight &&
                laserBottom > playerTop && laserTop < playerBottom;
    }

    private boolean isEnemyHitByLaser(Laser laser, Enemy enemy) {
        double laserLeft = laser.getX() - size / 2;
        double laserRight = laser.getX() + size / 2;
        double laserTop = laser.getY() - size / 2;
        double laserBottom = laser.getY() + size / 2;

        double enemyLeft = enemy.getX();
        double enemyRight = enemy.getX() + enemy.getCellSize();
        double enemyTop = enemy.getY();
        double enemyBottom = enemy.getY() + enemy.getCellSize();

        return laserRight > enemyLeft && laserLeft < enemyRight &&
                laserBottom > enemyTop && laserTop < enemyBottom;
    }

    public void checkEnemyLaserHits(Laser laser, Player player) {
        if (laser.isActive() && isPlayerHitByLaser(laser, player)) {
            laser.deactivate();
            player.loseLife();
        }
    }

    public void checkPlayerLaserHits(Laser laser, Enemy enemy) {
        if (laser.isActive() && isEnemyHitByLaser(laser, enemy)) {
            laser.deactivate();
            enemy.loseLife();
        }
    }

    public boolean isActive() {
        return active;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void deactivate() {
        active = false;
    }
}