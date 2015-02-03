package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.Sprite;
import net.wintastic.wincremental.AssetLibrary;
import net.wintastic.wincremental.GameManager;
import net.wintastic.wincremental.Player;
import org.lwjgl.util.vector.Vector2f;

public class ResourceTile extends Tile {

    public static enum TileType {
        GRASS(AssetLibrary.grassTileSprite),
        GOLD(AssetLibrary.goldTileSprite),
        WOOD(AssetLibrary.woodTileSprite);

        Sprite sprite;

        TileType(Sprite sprite) {
            this.sprite = sprite;
        }

        void clickAction() {
            if (this == GOLD)
                GameManager.player.changeResource(Player.ResourceType.GOLD, 1);
            else if (this == WOOD)
                GameManager.player.changeResource(Player.ResourceType.WOOD, 1);
        }
    }

    TileType type;

    public ResourceTile(Vector2f position) {
        super(position);
        this.type = TileType.GRASS;
    }

    public ResourceTile(Vector2f position, TileType type) {
        super(position);
        this.type = type;

    }

    @Override
    public void clickAction() {
        type.clickAction();
    }

    @Override
    protected Sprite getSprite() {
        return type.sprite;
    }

}
