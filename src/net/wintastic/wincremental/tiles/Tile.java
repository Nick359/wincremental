package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.Pair;
import net.wintastic.lwjgl.Sprite;
import net.wintastic.wincremental.AssetLibrary;
import net.wintastic.wincremental.GameManager;
import net.wintastic.wincremental.Player;
import org.lwjgl.util.vector.Vector2f;

import java.math.BigInteger;

public abstract class Tile {
    public enum TileType {
        EMPTY(AssetLibrary.grassTileSprite, 0, 0),

        TOWN_CENTER(AssetLibrary.townCenterSprite, 0, 16),
        TENT(AssetLibrary.tentTileSprite, 0, 0),
        STORAGE_SHED(AssetLibrary.storageShedTileSprite, 0, 0),

        WOOD(AssetLibrary.woodTileSprite, 20, 0),
        GOLD(AssetLibrary.goldTileSprite, 10, 0);

        private final Sprite sprite;
        private final int initialSize;
        private int radiusSize;

        TileType(Sprite sprite, int initialSize, int radiusSize) {
            this.sprite = sprite;
            this.initialSize = initialSize;
            this.radiusSize = radiusSize;
        }

        public Sprite getSprite() {
            return this.sprite;
        }

        public void clickAction() {
            switch (this) {
                case WOOD:
                    GameManager.player.changeResource(Player.ResourceType.WOOD, BigInteger.ONE);
                    break;
                case GOLD:
                    GameManager.player.changeResource(Player.ResourceType.GOLD, BigInteger.ONE);
                    break;
            }
        }
    }

    public static void drawTile(TileType type, Pair<Integer> position) {
        type.getSprite().position = getScreenPosition(position);
        type.getSprite().draw();
    }

    private static Vector2f getScreenPosition(Pair<Integer> position) {
        float x = position.first * GameManager.tileSize + GameManager.menuBarWidth - GameManager.camera.getPosition().x;
        float y = position.second * GameManager.tileSize + GameManager.toolbarHeight - GameManager.camera.getPosition().y;
        return new Vector2f(x, y);
    }

    public static boolean isVisible(Pair<Integer> position) {
        int minX = (int) (GameManager.camera.getPosition().x / GameManager.tileSize - 1);
        int maxX = (int) (GameManager.camera.getPosition().x / GameManager.tileSize + GameManager.viewportWidth + 1);
        int minY = (int) (GameManager.camera.getPosition().y / GameManager.tileSize - 1);
        int maxY = (int) (GameManager.camera.getPosition().y / GameManager.tileSize + GameManager.viewportHeight + 1);

        return position.first >= minX && position.first < maxX && position.second >= minY && position.second < maxY;
    }
}
