package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.Pair;
import net.wintastic.lwjgl.Sprite;
import net.wintastic.wincremental.AssetLibrary;
import net.wintastic.wincremental.GameManager;
import net.wintastic.wincremental.Player;
import org.lwjgl.util.vector.Vector2f;

import java.math.BigInteger;

public abstract class Tile {

    public enum TileCategory {
        EMPTY, RESOURCE, BUILDING
    }

    public enum TileType {
        TOWN_CENTER(AssetLibrary.townCenterSprite, 0, 16, TileCategory.BUILDING, 0),
        TENT(AssetLibrary.tentTileSprite, 0, 0, TileCategory.BUILDING, 0),
        STORAGE_SHED(AssetLibrary.storageShedTileSprite, 0, 0, TileCategory.BUILDING, 0),

        WOOD(AssetLibrary.woodTileSprite, 20, 0, TileCategory.RESOURCE, 0.04f),
        GOLD(AssetLibrary.goldTileSprite, 10, 0, TileCategory.RESOURCE, 0.002f);

        private final Sprite sprite;
        private int size;
        private int radius;
        private TileCategory category;
        private float probability;

        TileType(Sprite sprite, int initialSize, int radius, TileCategory category, float probability) {
            this.sprite = sprite;
            this.size = initialSize;
            this.radius = radius;
            this.category = category;
            this.probability = probability;
        }

        public Sprite getSprite() {
            return this.sprite;
        }

        public int getRadius() {
            return this.radius;
        }

        public TileCategory getCategory() {
            return this.category;
        }

        public float getProbability() {
            return this.probability;
        }

        public boolean clickAction() {
            if (this.getCategory() == TileCategory.RESOURCE) {
                switch (this) {
                    case WOOD:
                        GameManager.player.changeResource(Player.ResourceType.WOOD, BigInteger.ONE);
                        break;
                    case GOLD:
                        GameManager.player.changeResource(Player.ResourceType.GOLD, BigInteger.ONE);
                        break;
                }
                this.size--;
                if (this.size == 0)
                    return true;
            }
            return false;
        }
    }

    public static void drawTile(Pair<Integer> position, TileType type) {
        type.getSprite().position = getScreenPosition(position);
        type.getSprite().draw();
    }

    private static Vector2f getScreenPosition(Pair<Integer> position) {
        float x = position.first * GameManager.tileSize + GameManager.menuBarWidth - GameManager.camera.getPosition().x;
        float y = position.second * GameManager.tileSize + GameManager.toolbarHeight - GameManager.camera.getPosition().y;
        return new Vector2f(x, y);
    }
}
