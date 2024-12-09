package coursework;

import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LaserManager {
    private final List<Laser> lasers = new ArrayList<>();

    public void shootLaser(GameCharacter shooter, double directionX, double directionY) {
        Laser laser = LaserFactory.createLaser(shooter, directionX, directionY, shooter.getCellSize());
        lasers.add(laser);
    }

    public void update(Map map, Player player, List<Enemy> enemies) {
        Iterator<Laser> iterator = lasers.iterator();
        while (iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.update();
            if (CollisionManager.isLaserCollidingWithMap(laser, map)) {
                laser.deactivate();
                iterator.remove();
            }
            if (laser.isActive()) {
                handleActiveLaser(laser, player, enemies);
            }
        }
    }

    private void handleActiveLaser(Laser laser, Player player, List<Enemy> enemies) {
        if (laser instanceof PlayerLaser) {
            handlePlayerLaserCollision((PlayerLaser) laser, enemies);
        } else if (laser instanceof EnemyLaser) {
            handleEnemyLaserCollision((EnemyLaser) laser, player);
        }
    }

    private void handlePlayerLaserCollision(PlayerLaser laser, List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (enemy.isAlive() && CollisionManager.isLaserCollidingWithCharacter(enemy, laser)) {
                laser.handleCollision(enemy);
                break;
            }
        }
    }

    private void handleEnemyLaserCollision(EnemyLaser laser, Player player) {
        if (CollisionManager.isLaserCollidingWithCharacter(player, laser)) {
            laser.handleCollision(player);
        }
    }

    public void render(GraphicsContext gc) {
        for (Laser laser : lasers) {
            laser.render(gc);
        }
    }
}

