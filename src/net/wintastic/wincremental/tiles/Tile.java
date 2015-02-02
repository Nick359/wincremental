package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.Sprite;
import net.wintastic.wincremental.GameManager;
import org.lwjgl.util.vector.Vector2f;

public abstract class Tile {
    Vector2f position;

    public Tile(Vector2f position) {
        this.position = position;
    }

    public abstract void clickAction();

    protected abstract Sprite getSprite();

    public void draw() {
        getSprite().position = new Vector2f(position.x + GameManager.leftMenuWidth, position.y + GameManager.topBarHeight);
        getSprite().draw();
    }
}
