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
        double length = Math.sqrt(directionX * directionX + directionY * directionY);
        this.dx = (directionX / length) * speed;
        this.dy = (directionY / length) * speed;
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
        double dx = laser.getX() - player.getX();
        double dy = laser.getY() - player.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < cellSize;
    }

    private boolean isEnemyHitByLaser(Laser laser, Enemy enemy) {
        double dx = laser.getX() - enemy.getX();
        double dy = laser.getY() - enemy.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < cellSize;
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