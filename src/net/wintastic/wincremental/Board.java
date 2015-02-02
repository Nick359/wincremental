package net.wintastic.wincremental;

import net.wintastic.lwjgl.Input;
import net.wintastic.util.math.MathHelper;
import net.wintastic.wincremental.tiles.ResourceTile;
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
                if (MathHelper.randomChance(0.2f))
                    tiles[i][j] = new ResourceTile(new Vector2f(i * GameManager.tileSize, j * GameManager.tileSize), ResourceTile.TileType.Gold);
                else if (MathHelper.randomChance(4))
                    tiles[i][j] = new ResourceTile(new Vector2f(i * GameManager.tileSize, j * GameManager.tileSize), ResourceTile.TileType.Wood);
                else
                    tiles[i][j] = new ResourceTile(new Vector2f(i * GameManager.tileSize, j * GameManager.tileSize), ResourceTile.TileType.Grass);
            }
        }
    }

    public void update() {
        if (Input.isButtonPressed(0) && mouseInBoard()) {
            Vector2f p = new Vector2f((Input.mousePosition().x - GameManager.leftMenuWidth) / GameManager.tileSize, (Input.mousePosition().y - GameManager.topBarHeight) / GameManager.tileSize);
            tiles[((int) p.x)][((int) p.y)].clickAction();
        }
    }

    private boolean mouseInBoard() {
        return Input.mousePosition().x >= GameManager.leftMenuWidth && Input.mousePosition().y >= GameManager.topBarHeight;
    }

    public void draw() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j].draw();
            }
        }
    }
}
