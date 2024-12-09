package coursework;

public class LaserFactory {
    public static Laser createLaser(GameCharacter source, double directionX, double directionY, int cellSize) {
        if (source instanceof Player) {
            return new PlayerLaser(source.getX(), source.getY(), directionX, directionY, cellSize);
        }
        else if (source instanceof Enemy) {
            return new EnemyLaser(source.getX(), source.getY(), directionX, directionY, cellSize);
        }
        throw new IllegalArgumentException("Unknown laser source");
    }
}
