package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.Sprite;
import net.wintastic.wincremental.AssetLibrary;
import net.wintastic.wincremental.GameManager;
import net.wintastic.wincremental.Player;
import org.lwjgl.util.vector.Vector2f;

public class ResourceTile extends Tile {

    public static enum TileType {
        GRASS(AssetLibrary.grassTileSprite, 0),
        GOLD(AssetLibrary.goldTileSprite, 10),
        WOOD(AssetLibrary.woodTileSprite, 20);

        Sprite sprite;
        int initialSize;

        TileType(Sprite sprite, int initialSize) {
            this.sprite = sprite;
            this.initialSize = initialSize;
        }

        void clickAction() {
            switch (this) {
                case GOLD:
                    GameManager.player.changeResource(Player.ResourceType.GOLD, 1);
                    break;
                case WOOD:
                    GameManager.player.changeResource(Player.ResourceType.WOOD, 1);
                    break;
            }
        }
    }

    TileType type;
    int currentSize;

    public ResourceTile(Vector2f position) {
        super(position);
        this.type = TileType.GRASS;
        this.currentSize = this.type.initialSize;
    }

    public ResourceTile(Vector2f position, TileType type) {
        super(position);
        this.type = type;
        this.currentSize = this.type.initialSize;
    }

    @Override
    public void clickAction() {
        type.clickAction();
        this.currentSize--;
        if (currentSize == 0)
            this.type = TileType.GRASS;
    }

    @Override
    protected Sprite getSprite() {
        return type.sprite;
    }

}
