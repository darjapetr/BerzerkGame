package coursework;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Laser {
    protected int x, y;
    protected double dx, dy;
    protected static final double SPEED = 5.0;
    protected static final int SIZE = 5;
    protected boolean active;

    public Laser(double startX, double startY, double directionX, double directionY, int cellSize) {
        this.x = (int) startX + cellSize / 2;
        this.y = (int) startY + cellSize / 2;
        this.dx = directionX * SPEED;
        this.dy = directionY * SPEED;
        this.active = true;
    }

    public void update() {
        if (!active) return;
        x += (int) dx;
        y += (int) dy;
    }

    public void render(GraphicsContext gc) {
        if (active) {
            gc.setFill(Color.RED);
            gc.fillOval(x - (double) SIZE / 2, y - (double) SIZE / 2, SIZE, SIZE);
        }
    }

    public abstract void handleCollision(GameCharacter target);

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

    public int getSize() {
        return SIZE;
    }
}