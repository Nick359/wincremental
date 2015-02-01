package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.Sprite;
import org.lwjgl.util.vector.Vector2f;

public abstract class Tile {
    Vector2f position;

    public Tile(Vector2f position) {
        this.position = position;
    }

    public abstract void clickAction();

    protected abstract Sprite getSprite();

    public abstract void draw();
}
