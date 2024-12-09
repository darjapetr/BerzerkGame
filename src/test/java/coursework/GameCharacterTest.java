package coursework;

import junit.framework.TestCase;

public class GameCharacterTest extends TestCase {
    Player $player;

    public void testLoseLife() {
        $player = new Player(0,0,0, 1, 20, 20, 5);
        $player.loseLife();
        int $result = $player.getLives();
        assertEquals(4, $result);
    }
}