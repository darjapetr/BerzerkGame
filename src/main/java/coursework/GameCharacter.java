package coursework;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameCharacter {
    protected double x, y;
    protected int cellSize;
    protected int lives;
    protected boolean isAlive;

    public GameCharacter(double x, double y, int cellSize, int lives) {
        this.x = x;
        this.y = y;
        this.cellSize = cellSize;
        this.lives = lives;
        this.isAlive = true;
    }

    public abstract void render(GraphicsContext gc);

    public void loseLife() {
        lives--;
        if (lives <= 0) {
            isAlive = false;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getLives() {
        return lives;
    }

    public int getCellSize() {
        return cellSize;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
