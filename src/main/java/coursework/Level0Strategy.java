package coursework;

public class Level0Strategy implements LevelStrategy {
    @Override
    public void initialize(GameRules gameRules) {
        gameRules.setPlayer(new Player(CELL_SIZE, 7 * CELL_SIZE, 1, 0, CELL_SIZE / 4, CELL_SIZE, 3));
        gameRules.getEnemies().clear();
        gameRules.getEnemies().add(new Enemy(8 * CELL_SIZE, 2 * CELL_SIZE, 0, 1, CELL_SIZE, 15));
        gameRules.getEnemies().add(new Enemy(15 * CELL_SIZE, 12 * CELL_SIZE, 0, -1, CELL_SIZE, 15));
        gameRules.getEnemies().add(new Enemy(22 * CELL_SIZE, 2 * CELL_SIZE, 0, 1, CELL_SIZE, 15));
    }
}
