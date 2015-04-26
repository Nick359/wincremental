package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.Sprite;
import net.wintastic.wincremental.AssetLibrary;
import net.wintastic.wincremental.GameManager;

public class ResourceTile extends Tile {

    public enum Type {
        WOOD(20, 0.04f, AssetLibrary.woodTileSprite),
        GOLD(10, 0.002f, AssetLibrary.goldTileSprite);

        private int initialSize;
        private float probability;
        private Sprite sprite;

        Type(int initialSize, float probability, Sprite sprite) {
            this.initialSize = initialSize;
            this.probability = probability;
            this.sprite = sprite;
        }

        public int getInitialSize() {
            return initialSize;
        }

        public float getProbability() {
            return probability;
        }

        public Sprite getSprite() {
            return sprite;
        }
    }

    private Type type;
    private int size;

    public ResourceTile(Type type, Position position) {
        super(position);
        this.type = type;
        this.size = type.getInitialSize();
    }

    public Type getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public void clickAction() {
        GameManager.board.gatherResource(getPosition());
    }

    @Override
    public Sprite getSprite() {
        return type.getSprite();
    }
}
