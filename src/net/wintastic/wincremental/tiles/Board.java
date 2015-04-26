package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.DrawBatch;
import net.wintastic.lwjgl.Drawable;
import net.wintastic.lwjgl.Input;
import net.wintastic.util.math.MathHelper;
import net.wintastic.wincremental.AssetLibrary;
import net.wintastic.wincremental.GameManager;
import net.wintastic.wincremental.Player;
import net.wintastic.wincremental.gui.MenuBar;
import org.lwjgl.util.vector.Vector2f;

import java.math.BigInteger;

public class Board implements Drawable {

    private Tile[][] tiles;
    private FogOfWar fogOfWar;
    private final int width, height;
    public Position selectedTilePosition;
    private boolean visible;
    private float layerDepth;

    public Board() {
        this.width = GameManager.mapWidth;
        this.height = GameManager.mapHeight;
        this.fogOfWar = new FogOfWar();
        initializeTiles();

        this.visible = true;
        this.layerDepth = 0.2f;
        DrawBatch.add(this);
    }

    private void initializeTiles() {
        tiles = new Tile[width][height];
        for (ResourceTile.Type type : ResourceTile.Type.values()) {
            int nTiles = (int) (width * height * type.getProbability() * MathHelper.randomFloat(0.9f, 1.1f));
            int[] x = MathHelper.randomInts(nTiles, 0, width);
            int[] y = MathHelper.randomInts(nTiles, 0, height);
            for (int i = 0; i < nTiles; i++) {
                Position p = new Position(x[i], y[i]);
                setTile(p, new ResourceTile(type, p));
            }
        }
        Position p = new Position(width / 2, height / 2);
        setTile(p, new BuildingTile(BuildingTile.Type.TOWN_CENTER, p));
        fogOfWar.updateAtPosition(p, getTile(p).getRadius());
    }

    public Tile getTile(Position position) {
        return tiles[position.x][position.y];
    }

    public void setTile(Position position, Tile newTile) {
        tiles[position.x][position.y] = newTile;
    }

    public void update() {
        if (mouseInBoard() && Input.isButtonPressed(0)) {
            selectedTilePosition = null;
            Position p = getMouseTilePosition();
            Tile t = getTile(p);
            if (t == null && MenuBar.selectedIcon != null && MenuBar.selectedIcon.selected) {
                placeBuilding(p, MenuBar.selectedIcon.type.getBuildingTileType());
            } else if (t != null) {
                t.clickAction();
            }
        }
    }

    private void placeBuilding(Position position, BuildingTile.Type type) {
        if (GameManager.player.hasEnoughResources(type.getCost()) && fogOfWar.get(position) == 1) {
            GameManager.player.applyResourceCost(type.getCost());
            setTile(position, new BuildingTile(type, position));
            fogOfWar.updateAtPosition(position, getTile(position).getRadius());
        }
    }

    public static Position getMouseTilePosition() {
        return new Position((int) ((Input.mousePosition().x - GameManager.menuBarWidth + GameManager.camera.getPosition().x) / GameManager.tileSize),
                (int) ((Input.mousePosition().y - GameManager.toolbarHeight + GameManager.camera.getPosition().y) / GameManager.tileSize));
    }

    public boolean mouseInBoard() {
        return Input.mousePosition().x >= GameManager.menuBarWidth && Input.mousePosition().x < GameManager.resX && Input.mousePosition().y >= GameManager.toolbarHeight && Input.mousePosition().y < GameManager.resY;
    }

    public void gatherResource(Position position) {
        Tile tile = getTile(position);
        if (!(tile instanceof ResourceTile)) return;
        ResourceTile t = (ResourceTile) tile;
        switch (t.getType()) {
            case WOOD:
                GameManager.player.changeResource(Player.ResourceType.WOOD, BigInteger.ONE);
                break;
            case GOLD:
                GameManager.player.changeResource(Player.ResourceType.GOLD, BigInteger.ONE);
                break;
        }
        t.setSize(t.getSize() - 1);
        if (t.getSize() == 0) {
            setTile(position, null);
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
        if (GameManager.useFogOfWar) {
            fogOfWar.draw();
        }
    }

    private void drawBoardBackground() {
        float dx = GameManager.camera.getPosition().x % GameManager.tileSize;
        float dy = GameManager.camera.getPosition().y % GameManager.tileSize;
        for (int i = 0; i <= GameManager.viewportWidth + 1; i++) {
            for (int j = 0; j <= GameManager.viewportHeight + 1; j++) {
                Position tilePosition = new Position((int) (GameManager.camera.getPosition().x / GameManager.tileSize + i), (int) (GameManager.camera.getPosition().y / GameManager.tileSize + j));
                if (!GameManager.useFogOfWar || fogOfWar.get(tilePosition) == 1) {
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
                if (tiles[i][j] != null && (!GameManager.useFogOfWar || fogOfWar.get(tiles[i][j].getPosition()) == 1)) {
                    tiles[i][j].draw();
                }
            }
        }
    }

    private void drawTileRadiusIndicator() {
        int radius = getTile(selectedTilePosition).getRadius();
        if (radius <= 0)
            return;
        int x0 = selectedTilePosition.x;
        int y0 = selectedTilePosition.y;
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
