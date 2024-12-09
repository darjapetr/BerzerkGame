package coursework;

import junit.framework.TestCase;

public class CollisionManagerTest extends TestCase {
    Enemy $enemy;
    Player $player;
    PlayerLaser $laser;

    public void testIsCharactersColliding() {
        $enemy = new Enemy(20, 20, 1, 0, 20, 20);
        $player = new Player(20, 20, 1, 0, 20, 20, 5);
        boolean $result = CollisionManager.isCharactersColliding($enemy, $player);
        assertTrue($result);
    }

    public void testIsLaserCollidingWithCharacter() {
        $laser = new PlayerLaser(20, 20, 1, 0, 20);
        $enemy = new Enemy(20, 20, 1, 0, 20, 20);
        boolean $result = CollisionManager.isLaserCollidingWithCharacter($enemy, $laser);
        assertTrue($result);
    }
}