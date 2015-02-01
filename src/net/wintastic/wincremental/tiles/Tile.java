package net.wintastic.wincremental.tiles;

import org.lwjgl.util.vector.Vector2f;

public abstract class Tile {
    Vector2f position;

    public abstract void clickAction();

    public abstract void draw();
}
