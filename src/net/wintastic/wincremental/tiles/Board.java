package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.Input;
import net.wintastic.util.math.MathHelper;
import net.wintastic.wincremental.GameManager;
import net.wintastic.wincremental.gui.MenuBar;
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
                    tiles[i][j] = new ResourceTile(new Vector2f(i * GameManager.tileSize, j * GameManager.tileSize), ResourceTile.ResourceTileType.GOLD);
                else if (MathHelper.randomChance(4))
                    tiles[i][j] = new ResourceTile(new Vector2f(i * GameManager.tileSize, j * GameManager.tileSize), ResourceTile.ResourceTileType.WOOD);
                else
                    tiles[i][j] = new ResourceTile(new Vector2f(i * GameManager.tileSize, j * GameManager.tileSize), ResourceTile.ResourceTileType.GRASS);
            }
        }
    }

    public void setTile(Vector2f position, Tile newTile) {
        tiles[(int) position.x][(int) position.y] = newTile;
    }

    public void update() {
        if (mouseInBoard() && Input.isButtonPressed(0)) {
            Vector2f p = new Vector2f((Input.mousePosition().x - GameManager.menuBarWidth + GameManager.camera.position.x) / GameManager.tileSize, (Input.mousePosition().y - GameManager.toolbarHeight + GameManager.camera.position.y) / GameManager.tileSize);
            Tile t = tiles[((int) p.x)][((int) p.y)];
            t.clickAction();
            if (t instanceof ResourceTile && ((ResourceTile) t).type == ResourceTile.ResourceTileType.GRASS && MenuBar.selectedIcon != null && MenuBar.selectedIcon.selected) {
                setTile(p, new BuildingTile(t.position, MenuBar.selectedIcon.type.getBuildingTileType()));
            }
        }
    }

    private boolean mouseInBoard() {
        return Input.mousePosition().x >= GameManager.menuBarWidth && Input.mousePosition().x < GameManager.resX && Input.mousePosition().y >= GameManager.toolbarHeight && Input.mousePosition().y < GameManager.resY;
    }
}
