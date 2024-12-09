package coursework;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player extends GameCharacter {
    private int dx, dy;
    private final int speed;

    public Player(double startX, double startY, int directionX, int directionY, int speed, int cellSize, int lives) {
        super(startX, startY, cellSize, lives);
        this.dx = directionX;
        this.dy = directionY;
        this.speed = speed;
    }

    public void moveUp() {
        move(0, -1);
    }

    public void moveDown() {
        move(0, 1);
    }

    public void moveLeft() {
        move(-1, 0);
    }

    public void moveRight() {
        move(1, 0);
    }

    private void move(int dx, int dy) {
        if (!isAlive) return;
        updateDirection(dx, dy);
        double newX = x + dx * speed;
        double newY = y + dy * speed;
        setX(newX);
        setY(newY);
    }

    private void updateDirection(int directionX, int directionY) {
        setDx(directionX);
        setDy(directionY);
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!isAlive) {
            return;
        }
        gc.setFill(Color.GREEN);
        gc.fillOval(x, y, cellSize, cellSize);
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }
}