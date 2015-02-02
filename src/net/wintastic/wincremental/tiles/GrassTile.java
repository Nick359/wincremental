package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.Sprite;
import net.wintastic.wincremental.AssetLibrary;
import net.wintastic.wincremental.GameManager;
import org.lwjgl.util.vector.Vector2f;

public class GrassTile extends Tile {

    public GrassTile(Vector2f position) {
        super(position);
    }

    @Override
    public void clickAction() {

    }

    @Override
    protected Sprite getSprite() {
        return AssetLibrary.grassTileSprite;
    }

}
