package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.Input;
import net.wintastic.lwjgl.Pair;
import net.wintastic.util.math.MathHelper;
import net.wintastic.wincremental.GameManager;
import net.wintastic.wincremental.gui.MenuBar;

public class Board {
    private Tile[][] tiles;
    private final int width, height;
    private static Tile selectedTile;

    public Board() {
        this.width = GameManager.mapWidth;
        this.height = GameManager.mapHeight;
        initializeTiles();
    }

    private void initializeTiles() {
        tiles = new Tile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (MathHelper.randomChance(0.2f))
                    tiles[i][j] = new ResourceTile(new Pair<Integer>(i, j), ResourceTile.ResourceTileType.GOLD);
                else if (MathHelper.randomChance(4))
                    tiles[i][j] = new ResourceTile(new Pair<Integer>(i, j), ResourceTile.ResourceTileType.WOOD);
                else
                    tiles[i][j] = new ResourceTile(new Pair<Integer>(i, j), ResourceTile.ResourceTileType.GRASS);
            }
        }

        placeTownCenter();
    }

    private void placeTownCenter() {
        setTile(new Pair<Integer>(width / 2, height / 2), new BuildingTile(new Pair<Integer>(width / 2, height / 2), BuildingTile.BuildingTileType.TOWN_CENTER));
    }

    public Tile getTile(Pair<Integer> position) {
        return tiles[position.first][position.second];
    }

    public void setTile(Pair<Integer> position, Tile newTile) {
        tiles[position.first][position.second] = newTile;
    }

    public static void setSelectedTile(Tile selectedTile) {
        Board.selectedTile = selectedTile;
    }

    public void update() {
        if (mouseInBoard() && Input.isButtonPressed(0)) {
            if (selectedTile != null) {
                ((BuildingTile) selectedTile).toggleSelected();
            }

            Pair<Integer> p = new Pair<Integer>((int) ((Input.mousePosition().x - GameManager.menuBarWidth + GameManager.camera.getPosition().x) / GameManager.tileSize),
                    (int) ((Input.mousePosition().y - GameManager.toolbarHeight + GameManager.camera.getPosition().y) / GameManager.tileSize));
            Tile t = tiles[p.first][p.second];
            t.clickAction();
            if (t instanceof ResourceTile && ((ResourceTile) t).type == ResourceTile.ResourceTileType.GRASS && MenuBar.selectedIcon != null && MenuBar.selectedIcon.selected) {
                setTile(p, new BuildingTile(t.position, MenuBar.selectedIcon.type.getBuildingTileType()));
            }
        }
    }

    public boolean mouseInBoard() {
        return Input.mousePosition().x >= GameManager.menuBarWidth && Input.mousePosition().x < GameManager.resX && Input.mousePosition().y >= GameManager.toolbarHeight && Input.mousePosition().y < GameManager.resY;
    }
}
