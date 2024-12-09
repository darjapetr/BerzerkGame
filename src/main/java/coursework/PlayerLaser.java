package coursework;

public class PlayerLaser extends Laser {
    public PlayerLaser(double startX, double startY, double directionX, double directionY, int cellSize) {
        super(startX, startY, directionX, directionY, cellSize);
    }

    @Override
    public void handleCollision (GameCharacter target) {
        if (target instanceof Enemy enemy && enemy.isAlive()) {
            enemy.loseLife();
            deactivate();
        }
    }
}
