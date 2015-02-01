package net.wintastic.wincremental;

import net.wintastic.wincremental.tiles.GrassTile;
import net.wintastic.wincremental.tiles.Tile;
import org.lwjgl.util.vector.Vector2f;

public class Board {
    Tile[][] tiles;
    int width, height;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        initializeTiles();
    }

    private void initializeTiles() {
        tiles = new Tile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j] = new GrassTile(new Vector2f(i * GameManager.tileSize, j * GameManager.tileSize));
            }
        }
    }

    public void update() {

    }

    public void draw() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j].draw();
            }
        }
    }
}
