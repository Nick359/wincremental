package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.Input;
import net.wintastic.lwjgl.Tuple;
import net.wintastic.util.math.MathHelper;
import net.wintastic.wincremental.GameManager;
import net.wintastic.wincremental.gui.MenuBar;

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
                    tiles[i][j] = new ResourceTile(new Tuple<Integer>(i, j), ResourceTile.ResourceTileType.GOLD);
                else if (MathHelper.randomChance(4))
                    tiles[i][j] = new ResourceTile(new Tuple<Integer>(i, j), ResourceTile.ResourceTileType.WOOD);
                else
                    tiles[i][j] = new ResourceTile(new Tuple<Integer>(i, j), ResourceTile.ResourceTileType.GRASS);
            }
        }
    }

    public void setTile(Tuple<Integer> position, Tile newTile) {
        tiles[position.first][position.second] = newTile;
    }

    public void update() {
        if (mouseInBoard() && Input.isButtonPressed(0)) {
            Tuple<Integer> p = new Tuple<Integer>((int) ((Input.mousePosition().x - GameManager.menuBarWidth + GameManager.camera.position.x) / GameManager.tileSize),
                    (int) ((Input.mousePosition().y - GameManager.toolbarHeight + GameManager.camera.position.y) / GameManager.tileSize));
            Tile t = tiles[p.first][p.second];
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
