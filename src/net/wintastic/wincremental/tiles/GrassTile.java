package net.wintastic.wincremental.tiles;

import net.wintastic.wincremental.AssetLibrary;
import net.wintastic.wincremental.GameManager;
import org.lwjgl.util.vector.Vector2f;

public class GrassTile extends Tile {

    public GrassTile(Vector2f position) {
        this.position = position;
    }

    @Override
    public void clickAction() {

    }

    @Override
    public void draw() {
        AssetLibrary.grassTileSprite.position = new Vector2f(position.x + GameManager.leftMenuWidth, position.y + GameManager.topBarHeight);
        AssetLibrary.grassTileSprite.draw();
    }
}
