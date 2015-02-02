package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.Sprite;
import net.wintastic.wincremental.AssetLibrary;
import net.wintastic.wincremental.GameManager;
import org.lwjgl.util.vector.Vector2f;

public class WoodTile extends Tile {

    public WoodTile(Vector2f position) {
        super(position);
    }

    @Override
    public void clickAction() {
        // TODO: change 1 to something dynamic
        GameManager.player.changeWood(1);
    }

    @Override
    protected Sprite getSprite() {
        return AssetLibrary.woodTileSprite;
    }
}
