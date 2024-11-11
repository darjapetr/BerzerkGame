package coursework;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Map {
    private int width;
    private int height;
    private int[][] map;
    private int cellSize;
    private Image borderImage;

    public Map(String file, int cellSize) {
        loadMapFromFile(file);
        this.cellSize = cellSize;
        borderImage = new Image(getClass().getResource("/border.png").toExternalForm());
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
                    switch (cell) {
                        case '1' -> map[rowCount][colCount] = 1; // wall
                        case '0' -> map[rowCount][colCount] = 0; // empty space
                        case 'E' -> map[rowCount][colCount] = 2; // exit
                        default -> throw new IllegalArgumentException("Unknown map character: " + cell);
                    }
                }
                rowCount++;
            }
        } catch (IOException e) {
            System.err.println("Error reading map file: " + e.getMessage());
        }
    }

    public boolean isWall(int x, int y) {
        return map[x][y] == 1;
    }

    public boolean isExit(int x, int y) {
        return map[x][y] == 2;
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

    public void render(GraphicsContext gc) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (map[row][col] == 1) {
                    gc.drawImage(borderImage, col * cellSize, row * cellSize, cellSize, cellSize);
                } else if (map[row][col] == 2) {
                    gc.setFill(Color.BLACK);
                    gc.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                } else if (map[row][col] == 0) {
                    gc.setFill(Color.BLACK);
                    gc.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);

                }
            }
        }
    }
}