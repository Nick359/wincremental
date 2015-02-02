package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.Sprite;
import net.wintastic.wincremental.AssetLibrary;
import net.wintastic.wincremental.GameManager;
import net.wintastic.wincremental.Player;
import org.lwjgl.util.vector.Vector2f;

public class ResourceTile extends Tile {

    public static enum TileType {
        Grass(AssetLibrary.grassTileSprite),
        Gold(AssetLibrary.goldTileSprite),
        Wood(AssetLibrary.woodTileSprite);

        Sprite sprite;

        TileType(Sprite sprite) {
            this.sprite = sprite;
        }

        void clickAction() {
            if (this == Gold)
                GameManager.player.changeResource(Player.ResourceType.Gold, 1);
            else if (this == Wood)
                GameManager.player.changeResource(Player.ResourceType.Wood, 1);
        }
    }

    TileType type;

    public ResourceTile(Vector2f position) {
        super(position);
        this.type = TileType.Grass;
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
