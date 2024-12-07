package coursework;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player {
    private int x, y, dx, dy;
    private int speed;
    private int cellSize;
    private int lives;
    private boolean isAlive;

    public Player(int startX, int startY, int directionX, int directionY, int speed, int cellSize, int lives) {
        this.x = startX;
        this.y = startY;
        this.dx = directionX;
        this.dy = directionY;
        this.speed = speed;
        this.cellSize = cellSize;
        this.lives = lives;
        this.isAlive = true;
    }

    public void moveUp(Map map, Enemy... enemies) {
        if (!isAlive) {
            return;
        }

        this.dy = -1;
        this.dx = 0;
        int newY = y + dy * speed;
        int topCellY = newY / cellSize;
        int leftCellX = x / cellSize;
        int rightCellX = (x + cellSize - 1) / cellSize;

        if (!map.isWall(topCellY, leftCellX) && !map.isWall(topCellY, rightCellX)) {
            int oldY = y;
            y = newY;
            for (Enemy enemy : enemies) {
                if (isCollidingWithEnemy(enemy)) {
                    y = oldY;
                    return;
                }
            }
        }
    }

    public void moveDown(Map map, Enemy... enemies) {
        if (!isAlive) {
            return;
        }

        this.dy = 1;
        this.dx = 0;
        int newY = y + dy * speed;
        int bottomCellY = (newY + cellSize - 1) / cellSize;
        int leftCellX = x / cellSize;
        int rightCellX = (x + cellSize - 1) / cellSize;

        if (!map.isWall(bottomCellY, leftCellX) && !map.isWall(bottomCellY, rightCellX)) {
            int oldY = y;
            y = newY;
            for (Enemy enemy : enemies) {
                if (isCollidingWithEnemy(enemy)) {
                    y = oldY;
                    return;
                }
            }
        }
    }

    public void moveLeft(Map map, Enemy... enemies) {
        if (!isAlive) {
            return;
        }

        this.dx = -1;
        this.dy = 0;
        int newX = x + dx * speed;
        int leftCellX = newX / cellSize;
        int topCellY = y / cellSize;
        int bottomCellY = (y + cellSize - 1) / cellSize;

        if (!map.isWall(topCellY, leftCellX) && !map.isWall(bottomCellY, leftCellX)) {
            int oldX = x;
            x = newX;
            for (Enemy enemy : enemies) {
                if (isCollidingWithEnemy(enemy)) {
                    x = oldX;
                    return;
                }
            }
        }
    }

    public void moveRight(Map map, Enemy... enemies) {
        if (!isAlive) {
            return;
        }

        this.dx = 1;
        this.dy = 0;
        int newX = x + dx * speed;
        int rightCellX = (newX + cellSize - 1) / cellSize;
        int topCellY = y / cellSize;
        int bottomCellY = (y + cellSize - 1) / cellSize;

        if (!map.isWall(topCellY, rightCellX) && !map.isWall(bottomCellY, rightCellX)) {
            int oldX = x;
            x = newX;
            for (Enemy enemy : enemies) {
                if (isCollidingWithEnemy(enemy)) {
                    x = oldX;
                    return;
                }
            }
        }
    }

    public void render(GraphicsContext gc) {
        if (!isAlive) {
            return;
        }
        gc.setFill(Color.GREEN);
        gc.fillOval(x, y, cellSize, cellSize);
    }

    public boolean isCollidingWithEnemy(Enemy enemy) {
        double playerLeft = x;
        double playerRight = x + cellSize;
        double playerTop = y;
        double playerBottom = y + cellSize;

        double enemyLeft = enemy.getX();
        double enemyRight = enemy.getX() + enemy.getCellSize();
        double enemyTop = enemy.getY();
        double enemyBottom = enemy.getY() + enemy.getCellSize();

        return playerRight > enemyLeft && playerLeft < enemyRight &&
                playerBottom > enemyTop && playerTop < enemyBottom;
    }

    public boolean isAtExit(Map map) {
        int tileX = x / cellSize;
        int tileY = y / cellSize;
        return map.isExit(tileY, tileX);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
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

    public int getCellSize() {
        return cellSize;
    }
}