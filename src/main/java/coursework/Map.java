package coursework;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Map {
    private static final int WALL = 1;
    private static final int EMPTY = 0;
    private static final int EXIT = 2;
    private int width;
    private int height;
    private int[][] map;
    private final int cellSize;
    private final Image borderImage;

    public Map(String file, int cellSize) {
        this.cellSize = cellSize;
        this.borderImage = new Image(getClass().getResource("/border.png").toExternalForm());
        loadMapFromFile(file);
    }

    public boolean isWall(int x, int y) {
        return map[x][y] == WALL;
    }

    public boolean isExit(int x, int y) {
        return map[x][y] == EXIT;
    }

    private void loadMapFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String firstLine = reader.readLine();
            String[] dimensions = firstLine.split(" ");
            height = Integer.parseInt(dimensions[1]);
            width = Integer.parseInt(dimensions[0]);
            map = new int[height][width];
            String line;
            int rowCount = 0;
            while ((line = reader.readLine()) != null && rowCount < height) {
                for (int colCount = 0; colCount < line.length() && colCount < width; colCount++) {
                    char cell = line.charAt(colCount);
                    map[rowCount][colCount] = parseCell(cell);
                }
                rowCount++;
            }
        } catch (IOException e) {
            System.err.println("Error reading map file: " + e.getMessage());
        }
    }

    private int parseCell(char cell) {
        return switch (cell) {
            case '1' -> WALL;
            case '0' -> EMPTY;
            case 'E' -> EXIT;
            default -> {
                System.err.println("Unknown map character: " + cell);
                yield EMPTY;
            }
        };
    }

    public void render(GraphicsContext gc) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                renderCell(gc, map[row][col], col, row);
            }
        }
    }

    private void renderCell(GraphicsContext gc, int cellType, int col, int row) {
        switch (cellType) {
            case WALL:
                gc.drawImage(borderImage, col * cellSize, row * cellSize, cellSize, cellSize);
                break;
            case EXIT, EMPTY:
                gc.setFill(Color.BLACK);
                gc.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                break;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCellSize() {
        return cellSize;
    }
}