package coursework;

public class CollisionManager {

    public static boolean isCharactersColliding(GameCharacter character1, GameCharacter character2) {
        double[] box1 = getBoundingBoxCoordinates(character1);
        double[] box2 = getBoundingBoxCoordinates(character2);
        return isIntersecting(box1, box2);
    }

    public static boolean isCharacterCollidingWithMap(GameCharacter character, Map map) {
        int leftCellX = (int) character.getX() / map.getCellSize();
        int rightCellX = (int) (character.getX() + character.getCellSize() - 1) / map.getCellSize();
        int topCellY = (int) character.getY() / map.getCellSize();
        int bottomCellY = (int) (character.getY() + character.getCellSize() - 1) / map.getCellSize();
        return map.isWall(topCellY, leftCellX) || map.isWall(topCellY, rightCellX) ||
                map.isWall(bottomCellY, leftCellX) || map.isWall(bottomCellY, rightCellX);
    }

    public static boolean isLaserCollidingWithCharacter(GameCharacter character, Laser laser) {
        double[] box1 = getBoundingBoxCoordinates(character);
        double[] box2 = getBoundingBoxCoordinates(laser);
        return isIntersecting(box1, box2);
    }

    public static boolean isLaserCollidingWithMap(Laser laser, Map map) {
        int tileX = laser.getX() / map.getCellSize();
        int tileY = laser.getY() / map.getCellSize();
        return isLaserOutOfMapBounds(laser, map) || map.isWall(tileY, tileX);
    }

    public static boolean isCharacterCollidingWithExit(GameCharacter character, Map map) {
        double characterX = character.getX();
        double characterY = character.getY();
        int cellSize = character.getCellSize();
        int tileX = (int) (characterX / cellSize);
        int tileY = (int) (characterY / cellSize);
        return map.isExit(tileY, tileX);
    }

    private static boolean isLaserOutOfMapBounds(Laser laser, Map map) {
        return laser.getX() < 0 || laser.getX() > map.getWidth() * map.getCellSize() ||
                laser.getY() < 0 || laser.getY() > map.getHeight() * map.getCellSize();
    }

    private static double[] getBoundingBoxCoordinates(GameCharacter character) {
        double left = character.getX();
        double right = character.getX() + character.getCellSize();
        double top = character.getY();
        double bottom = character.getY() + character.getCellSize();
        return new double[]{left, right, top, bottom};
    }

    private static double[] getBoundingBoxCoordinates(Laser laser) {
        int size = laser.getSize();
        double left = laser.getX() - (double) size / 2;
        double right = laser.getX() + (double) size / 2;
        double top = laser.getY() - (double) size / 2;
        double bottom = laser.getY() + (double) size / 2;
        return new double[]{left, right, top, bottom};
    }

    private static boolean isIntersecting(double[] box1, double[] box2) {
        return box1[1] > box2[0] && box1[0] < box2[1] &&
                box1[3] > box2[2] && box1[2] < box2[3];
    }
}