package coursework;

import junit.framework.TestCase;

public class PlayerTest extends TestCase {
    Player $player;

    public void testMoveUp() {
        $player = new Player(0,0,0, 1, 20, 20, 5);
        $player.moveUp();
        int $result = (int) $player.getY();
        assertEquals(-20, $result);
    }

    public void testMoveDown() {
        $player = new Player(0,0,0, 1, 20, 20, 5);
        $player.moveDown();
        int $result = (int) $player.getY();
        assertEquals(20, $result);
    }

    public void testMoveLeft() {
        $player = new Player(0,0,0, 1, 20, 20, 5);
        $player.moveLeft();
        int $result = (int) $player.getX();
        assertEquals(-20, $result);
    }

    public void testMoveRight() {
        $player = new Player(0,0,0, 1, 20, 20, 5);
        $player.moveRight();
        int $result = (int) $player.getX();
        assertEquals(20, $result);
    }
}