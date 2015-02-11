package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.DrawBatch;
import net.wintastic.lwjgl.Drawable;
import net.wintastic.lwjgl.Input;
import net.wintastic.lwjgl.Pair;
import net.wintastic.util.math.MathHelper;
import net.wintastic.wincremental.GameManager;
import net.wintastic.wincremental.gui.MenuBar;
import net.wintastic.wincremental.tiles.Tile.TileType;

public class Board implements Drawable {
    private TileType[][] tiles;
    private final int width, height;
    private Pair<Integer> selectedTilePosition;
    private boolean visible;
    private float layerDepth;

    public Board() {
        this.width = GameManager.mapWidth;
        this.height = GameManager.mapHeight;
        initializeTiles();
        this.visible = true;
        this.layerDepth = 0.2f;

        DrawBatch.add(this);
    }

    private void initializeTiles() {
        tiles = new TileType[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (MathHelper.randomChance(0.2f))
                    tiles[i][j] = TileType.GOLD;
                else if (MathHelper.randomChance(4))
                    tiles[i][j] = TileType.WOOD;
                else
                    tiles[i][j] = TileType.EMPTY;
            }
        }

        placeTownCenter();
    }

    private void placeTownCenter() {
        setTile(new Pair<Integer>(width / 2, height / 2), TileType.TOWN_CENTER);
    }

    public TileType getTile(Pair<Integer> position) {
        return tiles[position.first][position.second];
    }

    public void setTile(Pair<Integer> position, TileType newTile) {
        tiles[position.first][position.second] = newTile;
    }

    public void setSelectedTile(Pair<Integer> position) {
        this.selectedTilePosition = position;
    }

    public void update() {
        if (mouseInBoard() && Input.isButtonPressed(0)) {
//            if (selectedTilePosition != null) {
//                ((BuildingTile) selectedTile).toggleSelected();
//            }

            Pair<Integer> p = new Pair<Integer>((int) ((Input.mousePosition().x - GameManager.menuBarWidth + GameManager.camera.getPosition().x) / GameManager.tileSize),
                    (int) ((Input.mousePosition().y - GameManager.toolbarHeight + GameManager.camera.getPosition().y) / GameManager.tileSize));
            TileType type = tiles[p.first][p.second];
            type.clickAction();
            if (type == TileType.EMPTY && MenuBar.selectedIcon != null && MenuBar.selectedIcon.selected) {
                setTile(p, MenuBar.selectedIcon.type.getBuildingTileType());
            }
        }
    }

    public boolean mouseInBoard() {
        return Input.mousePosition().x >= GameManager.menuBarWidth && Input.mousePosition().x < GameManager.resX && Input.mousePosition().y >= GameManager.toolbarHeight && Input.mousePosition().y < GameManager.resY;
    }

    @Override
    public void setLayerDepth(float layerDepth) {
        this.layerDepth = layerDepth;
    }

    @Override
    public float getLayerDepth() {
        return this.layerDepth;
    }

    @Override
    public void draw() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Pair<Integer> p = new Pair<Integer>(i, j);
                if (Tile.isVisible(p))
                    Tile.drawTile(tiles[i][j], p);
            }
        }
    }

    @Override
    public boolean isVisible() {
        return this.visible;
    }

    @Override
    public int compareTo(Drawable o) {
        if (this.layerDepth < o.getLayerDepth())
            return -1;
        else if (this.layerDepth > o.getLayerDepth())
            return 1;
        return 0;
    }
}
