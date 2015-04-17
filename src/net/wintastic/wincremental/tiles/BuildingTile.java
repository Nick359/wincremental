package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.Pair;
import net.wintastic.lwjgl.Sprite;
import net.wintastic.wincremental.AssetLibrary;
import net.wintastic.wincremental.GameManager;
import net.wintastic.wincremental.ResourceCost;

import java.math.BigInteger;

public class BuildingTile extends Tile {

    public enum Type {
        TOWN_CENTER(16, new ResourceCost(BigInteger.valueOf(200), BigInteger.valueOf(200)), AssetLibrary.townCenterSprite),
        TENT(0, new ResourceCost(BigInteger.valueOf(10), BigInteger.ZERO), AssetLibrary.tentTileSprite),
        STORAGE_SHED(0, new ResourceCost(BigInteger.valueOf(20), BigInteger.ZERO), AssetLibrary.storageShedTileSprite),
        OUTPOST(8, new ResourceCost(BigInteger.valueOf(40), BigInteger.valueOf(5)), AssetLibrary.outpostTileSprite);

        private int radius;
        private ResourceCost cost;
        private Sprite sprite;

        Type(int radius, ResourceCost cost, Sprite sprite) {
            this.radius = radius;
            this.cost = cost;
            this.sprite = sprite;
        }

        public int getRadius() {
            return radius;
        }

        public ResourceCost getCost() {
            return cost;
        }

        public Sprite getSprite() {
            return sprite;
        }
    }

    private Type type;

    public BuildingTile(Type type, Pair<Integer> position) {
        super(position);
        this.type = type;
    }

    @Override
    public int getRadius() {
        return type.getRadius();
    }

    @Override
    public void clickAction() {
        GameManager.board.selectedTilePosition = getPosition();
    }

    @Override
    public Sprite getSprite() {
        return type.getSprite();
    }
}
