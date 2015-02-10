package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.Pair;
import net.wintastic.lwjgl.Sprite;
import net.wintastic.wincremental.AssetLibrary;
import net.wintastic.wincremental.GameManager;
import net.wintastic.wincremental.Player;
import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;

import java.math.BigInteger;

public class ResourceTile extends Tile {

    public static enum ResourceTileType {
        GRASS(AssetLibrary.grassTileSprite, null, 0),
        GOLD(AssetLibrary.goldTileSprite, ReadableColor.ORANGE, 10),
        WOOD(AssetLibrary.woodTileSprite, new Color(129, 55, 6), 20);

        Sprite sprite;
        ReadableColor color;
        int initialSize;

        ResourceTileType(Sprite sprite, ReadableColor color, int initialSize) {
            this.sprite = sprite;
            this.color = color;
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

    public ResourceTileType type;
    int currentSize;

    public ResourceTile(Pair<Integer> position) {
        super(position);
        this.type = ResourceTileType.GRASS;
        this.currentSize = this.type.initialSize;
    }

    public ResourceTile(Pair<Integer> position, ResourceTileType type) {
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

    @Override
    public ReadableColor getColor() {
        return type.color;
    }

}
