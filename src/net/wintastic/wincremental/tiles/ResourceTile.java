package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.Sprite;
import net.wintastic.lwjgl.Tuple;
import net.wintastic.wincremental.AssetLibrary;
import net.wintastic.wincremental.GameManager;
import net.wintastic.wincremental.Player;

import java.math.BigInteger;

public class ResourceTile extends Tile {

    public static enum ResourceTileType {
        GRASS(AssetLibrary.grassTileSprite, 0),
        GOLD(AssetLibrary.goldTileSprite, 10),
        WOOD(AssetLibrary.woodTileSprite, 20);

        Sprite sprite;
        int initialSize;

        ResourceTileType(Sprite sprite, int initialSize) {
            this.sprite = sprite;
            this.initialSize = initialSize;
        }

        void clickAction() {
            switch (this) {
                case GOLD:
                    GameManager.player.changeResource(Player.ResourceType.GOLD, BigInteger.ONE);
                    break;
                case WOOD:
                    GameManager.player.changeResource(Player.ResourceType.WOOD, BigInteger.ONE);
                    break;
            }
        }
    }

    ResourceTileType type;
    int currentSize;

    public ResourceTile(Tuple<Integer> position) {
        super(position);
        this.type = ResourceTileType.GRASS;
        this.currentSize = this.type.initialSize;
    }

    public ResourceTile(Tuple<Integer> position, ResourceTileType type) {
        super(position);
        this.type = type;
        this.currentSize = this.type.initialSize;
    }

    @Override
    public void clickAction() {
        type.clickAction();
        this.currentSize--;
        if (currentSize == 0)
            this.type = ResourceTileType.GRASS;
    }

    @Override
    protected Sprite getSprite() {
        return type.sprite;
    }

}
