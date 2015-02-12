package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.DrawBatch;
import net.wintastic.lwjgl.Drawable;
import net.wintastic.lwjgl.Input;
import net.wintastic.lwjgl.Pair;
import net.wintastic.util.math.MathHelper;
import net.wintastic.wincremental.AssetLibrary;
import net.wintastic.wincremental.GameManager;
import net.wintastic.wincremental.gui.MenuBar;
import net.wintastic.wincremental.tiles.Tile.TileType;
import org.lwjgl.util.vector.Vector2f;

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
        for (TileType type : TileType.values()) {
            if (type.getCategory() == Tile.TileCategory.RESOURCE) {
                int nTiles = (int) (width * height * type.getProbability() * MathHelper.randomFloat(0.9f, 1.1f));
                int[] x = MathHelper.randomInts(nTiles, 0, width);
                int[] y = MathHelper.randomInts(nTiles, 0, height);
                for (int i = 0; i < nTiles; i++) {
                    tiles[x[i]][y[i]] = type;
                }
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

    public void update() {
        if (mouseInBoard() && Input.isButtonPressed(0)) {
            selectedTilePosition = null;
            Pair<Integer> p = getMouseTilePosition();
            TileType type = tiles[p.first][p.second];
            if (type == null && MenuBar.selectedIcon != null && MenuBar.selectedIcon.selected) {
                setTile(p, MenuBar.selectedIcon.type.getBuildingTileType());
            } else if (type != null) {
                if (type.clickAction()) {
                    tiles[p.first][p.second] = null;
                }
                if (type.getCategory() == Tile.TileCategory.BUILDING) {
                    selectedTilePosition = p;
                }
            }
        }
    }

    private Pair<Integer> getMouseTilePosition() {
        return new Pair<Integer>((int) ((Input.mousePosition().x - GameManager.menuBarWidth + GameManager.camera.getPosition().x) / GameManager.tileSize),
                (int) ((Input.mousePosition().y - GameManager.toolbarHeight + GameManager.camera.getPosition().y) / GameManager.tileSize));
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
        drawBoardBackground();
        drawTiles();
        if (selectedTilePosition != null) {
            drawTileRadiusIndicator();
        }
    }

    private void drawBoardBackground() {
        float dx = GameManager.camera.getPosition().x % GameManager.tileSize;
        float dy = GameManager.camera.getPosition().y % GameManager.tileSize;
        for (int i = 0; i <= GameManager.viewportWidth; i++) {
            for (int j = 0; j <= GameManager.viewportHeight; j++) {
                AssetLibrary.grassTileSprite.position = new Vector2f(i * GameManager.tileSize - dx + GameManager.menuBarWidth, j * GameManager.tileSize - dy + GameManager.toolbarHeight);
                AssetLibrary.grassTileSprite.draw();
            }
        }
    }

    private void drawTiles() {
        int minX = (int) (GameManager.camera.getPosition().x / GameManager.tileSize - 1);
        int maxX = (int) (GameManager.camera.getPosition().x / GameManager.tileSize + GameManager.viewportWidth + 1);
        int minY = (int) (GameManager.camera.getPosition().y / GameManager.tileSize - 1);
        int maxY = (int) (GameManager.camera.getPosition().y / GameManager.tileSize + GameManager.viewportHeight + 1);
        for (int i = minX; i < maxX; i++) {
            for (int j = minY; j < maxY; j++) {
                if (tiles[i][j] != null) {
                    Pair<Integer> p = new Pair<Integer>(i, j);
                    Tile.drawTile(p, tiles[i][j]);
                }
            }
        }
    }

    private void drawTileRadiusIndicator() {
        int radius = getTile(selectedTilePosition).getRadius();
        int x0 = selectedTilePosition.first;
        int y0 = selectedTilePosition.second;
        for (int y = -radius; y <= radius; y++) {
            for (int x = -radius; x <= radius; x++) {
                if (x * x + y * y <= radius * radius) {
                    AssetLibrary.radiusIndicatorSprite.position = new Vector2f(
                            (x0 + x) * GameManager.tileSize + GameManager.menuBarWidth - GameManager.camera.getPosition().x,
                            (y0 + y) * GameManager.tileSize + GameManager.toolbarHeight - GameManager.camera.getPosition().y);
                    AssetLibrary.radiusIndicatorSprite.draw();
                }
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
