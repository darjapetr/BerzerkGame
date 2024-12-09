package coursework;

public class EnemyLaser extends Laser {
    public EnemyLaser(double startX, double startY, double directionX, double directionY, int cellSize) {
        super(startX, startY, directionX, directionY, cellSize);
    }

    @Override
    public void handleCollision(GameCharacter target) {
        if (target instanceof Player player && player.isAlive()) {
            player.loseLife();
            deactivate();
        }
    }
}
