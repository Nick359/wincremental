package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.*;
import net.wintastic.util.math.MathHelper;
import net.wintastic.wincremental.AssetLibrary;
import net.wintastic.wincremental.GameManager;
import net.wintastic.wincremental.Player;
import net.wintastic.wincremental.gui.MenuBar;
import net.wintastic.wincremental.tiles.Tile.TileType;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import java.math.BigInteger;

public class Board implements Drawable {

    private TileType[][] tiles;
    private int[][] resourceQuantities;
    private float[][] fogOfWar;
    private final int width, height;
    public Pair<Integer> selectedTilePosition;
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
        resourceQuantities = new int[width][height];
        fogOfWar = new float[width][height];
        for (TileType type : TileType.values()) {
            if (type.getCategory() == Tile.TileCategory.RESOURCE) {
                int nTiles = (int) (width * height * type.getProbability() * MathHelper.randomFloat(0.9f, 1.1f));
                int[] x = MathHelper.randomInts(nTiles, 0, width);
                int[] y = MathHelper.randomInts(nTiles, 0, height);
                for (int i = 0; i < nTiles; i++) {
                    tiles[x[i]][y[i]] = type;
                    resourceQuantities[x[i]][y[i]] = type.getSize();
                }
            }
        }
        setTile(new Pair<Integer>(width / 2, height / 2), TileType.TOWN_CENTER);
        updateFogOfWar(new Pair<Integer>(width / 2, height / 2));
    }

    private void updateFogOfWar(Pair<Integer> position) {
        int radius = getTile(position).getRadius();
        if (radius <= 0)
            return;
        int x0 = position.first;
        int y0 = position.second;
        for (int y = -radius; y <= radius; y++) {
            for (int x = -radius; x <= radius; x++) {
                if (x * x + y * y <= radius * radius) {
                    fogOfWar[x0 + x][y0 + y] = 1;
                }
            }
        }
        int gradientRadius = 64;
        x0 = position.first;
        y0 = position.second;
        for (int y = -gradientRadius; y <= gradientRadius; y++) {
            for (int x = -gradientRadius; x <= gradientRadius; x++) {
                if (x * x + y * y <= gradientRadius * gradientRadius) {
                    Pair<Integer> p = new Pair<Integer>(x0 + x, y0 + y);
                    if (fogOfWar[p.first][p.second] < 1) {
                        fogOfWar[p.first][p.second] = ((float) (numberOfNearVisibleTiles(p, gradientRadius / 2))) / (gradientRadius * gradientRadius);
                    }
                }
            }
        }
    }

    private int numberOfNearVisibleTiles(Pair<Integer> position, int radius) {
        int total = 0;
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                if (i * i + j * j <= radius * radius && fogOfWar[position.first + i][position.second + j] == 1)
                    total++;
            }
        }
        return total;
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
                placeBuilding(p, MenuBar.selectedIcon.type.getBuildingTileType());
            } else if (type != null) {
                type.clickAction(p);
            }
        }
    }

    private void placeBuilding(Pair<Integer> position, TileType type) {
        if (GameManager.player.hasEnoughResources(type.getResourceCost())) {
            GameManager.player.applyResourceCost(type.getResourceCost());
            setTile(position, type);
            updateFogOfWar(position);
        }
    }

    public static Pair<Integer> getMouseTilePosition() {
        return new Pair<Integer>((int) ((Input.mousePosition().x - GameManager.menuBarWidth + GameManager.camera.getPosition().x) / GameManager.tileSize),
                (int) ((Input.mousePosition().y - GameManager.toolbarHeight + GameManager.camera.getPosition().y) / GameManager.tileSize));
    }

    public boolean mouseInBoard() {
        return Input.mousePosition().x >= GameManager.menuBarWidth && Input.mousePosition().x < GameManager.resX && Input.mousePosition().y >= GameManager.toolbarHeight && Input.mousePosition().y < GameManager.resY;
    }

    public void gatherResource(Pair<Integer> p) {
        TileType t = getTile(p);
        if (t.getCategory() == Tile.TileCategory.RESOURCE) {
            switch (t) {
                case WOOD:
                    GameManager.player.changeResource(Player.ResourceType.WOOD, BigInteger.ONE);
                    break;
                case GOLD:
                    GameManager.player.changeResource(Player.ResourceType.GOLD, BigInteger.ONE);
                    break;
            }
            resourceQuantities[p.first][p.second]--;
            if (resourceQuantities[p.first][p.second] == 0)
                tiles[p.first][p.second] = null;
        }
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
        if (GameManager.useFogOfWar)
            drawFogOfWar();
    }

    private void drawBoardBackground() {
        float dx = GameManager.camera.getPosition().x % GameManager.tileSize;
        float dy = GameManager.camera.getPosition().y % GameManager.tileSize;
        for (int i = 0; i <= GameManager.viewportWidth + 1; i++) {
            for (int j = 0; j <= GameManager.viewportHeight + 1; j++) {
                Pair<Integer> tilePosition = new Pair<Integer>((int) (GameManager.camera.getPosition().x / GameManager.tileSize + i), (int) (GameManager.camera.getPosition().y / GameManager.tileSize + j));
                if (!GameManager.useFogOfWar || fogOfWar[tilePosition.first][tilePosition.second] == 1) {
                    AssetLibrary.grassTileSprite.position = new Vector2f(i * GameManager.tileSize - dx + GameManager.menuBarWidth, j * GameManager.tileSize - dy + GameManager.toolbarHeight);
                    AssetLibrary.grassTileSprite.scaleX = Tile.scaleX;
                    AssetLibrary.grassTileSprite.scaleY = Tile.scaleY;
                    AssetLibrary.grassTileSprite.draw();
                }
            }
        }
    }

    private void drawTiles() {
        int minX = (int) MathHelper.clamp(GameManager.camera.getPosition().x / GameManager.tileSize - 1, 0, width);
        int maxX = (int) MathHelper.clamp(GameManager.camera.getPosition().x / GameManager.tileSize + GameManager.viewportWidth + 2, 0, width);
        int minY = (int) MathHelper.clamp(GameManager.camera.getPosition().y / GameManager.tileSize - 1, 0, height);
        int maxY = (int) MathHelper.clamp(GameManager.camera.getPosition().y / GameManager.tileSize + GameManager.viewportHeight + 2, 0, height);
        for (int i = minX; i < maxX; i++) {
            for (int j = minY; j < maxY; j++) {
                Pair<Integer> p = new Pair<Integer>(i, j);
                if (tiles[i][j] != null && (!GameManager.useFogOfWar || fogOfWar[i][j] == 1)) {
                    Tile.drawTile(p, tiles[i][j]);
                }
            }
        }
    }

    private void drawFogOfWar() {
        float dx = GameManager.camera.getPosition().x % GameManager.tileSize;
        float dy = GameManager.camera.getPosition().y % GameManager.tileSize;
        for (int i = 0; i <= GameManager.viewportWidth + 1; i++) {
            for (int j = 0; j <= GameManager.viewportHeight + 1; j++) {
                Pair<Integer> tilePosition = new Pair<Integer>((int) (GameManager.camera.getPosition().x / GameManager.tileSize + i), (int) (GameManager.camera.getPosition().y / GameManager.tileSize + j));
                if (fogOfWar[tilePosition.first][tilePosition.second] < 1) {
                    Vector2f p = new Vector2f(i * GameManager.tileSize - dx + GameManager.menuBarWidth, j * GameManager.tileSize - dy + GameManager.toolbarHeight);
                    int a = (int) (255 * fogOfWar[tilePosition.first][tilePosition.second]);
                    Color c = new Color(a, a, a, 255 - a);
                    Shape2D.drawRectangle(p, GameManager.tileSize, GameManager.tileSize, 0, c, true);
                }
            }
        }
    }

    private void drawTileRadiusIndicator() {
        int radius = getTile(selectedTilePosition).getRadius();
        if (radius <= 0)
            return;
        int x0 = selectedTilePosition.first;
        int y0 = selectedTilePosition.second;
        for (int y = -radius; y <= radius; y++) {
            for (int x = -radius; x <= radius; x++) {
                if (x * x + y * y <= radius * radius) {
                    AssetLibrary.radiusIndicatorSprite.position = new Vector2f(
                            (x0 + x) * GameManager.tileSize + GameManager.menuBarWidth - GameManager.camera.getPosition().x,
                            (y0 + y) * GameManager.tileSize + GameManager.toolbarHeight - GameManager.camera.getPosition().y);
                    AssetLibrary.radiusIndicatorSprite.scaleX = Tile.scaleX;
                    AssetLibrary.radiusIndicatorSprite.scaleY = Tile.scaleY;
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
