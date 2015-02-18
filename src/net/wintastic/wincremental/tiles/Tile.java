package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.Pair;
import net.wintastic.lwjgl.Sprite;
import net.wintastic.wincremental.AssetLibrary;
import net.wintastic.wincremental.GameManager;
import org.lwjgl.util.vector.Vector2f;

public abstract class Tile {

    public enum TileCategory {
        RESOURCE, BUILDING
    }

    public enum TileType {
        EMPTY(AssetLibrary.grassTileSprite, 0, 0, null, 0),

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

        public int getSize() {
            return this.size;
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

        public void clickAction(Pair<Integer> p) {
            switch (this.getCategory()) {
                case RESOURCE:
                    GameManager.board.gatherResource(p);
                    break;
                case BUILDING:
                    GameManager.board.selectedTilePosition = p;
                    break;
            }
        }
    }

    public static float scaleX = 1;
    public static float scaleY = 1;

    public static void drawTile(Pair<Integer> position, TileType type) {
        type.getSprite().position = getScreenPosition(position);
        type.getSprite().scaleX = scaleX;
        type.getSprite().scaleY = scaleY;
        type.getSprite().draw();
    }

    private static Vector2f getScreenPosition(Pair<Integer> position) {
        float x = position.first * GameManager.tileSize + GameManager.menuBarWidth - GameManager.camera.getPosition().x;
        float y = position.second * GameManager.tileSize + GameManager.toolbarHeight - GameManager.camera.getPosition().y;
        return new Vector2f(x, y);
    }
}
