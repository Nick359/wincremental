package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.DrawBatch;
import net.wintastic.lwjgl.Drawable;
import net.wintastic.lwjgl.Sprite;
import net.wintastic.wincremental.GameManager;
import org.lwjgl.util.vector.Vector2f;

public abstract class Tile implements Drawable {
    Vector2f position;
    float layerDepth;
    boolean visible;

    public Tile(Vector2f position) {
        this(position, 0, true);
    }

    public Tile(Vector2f position, float layerDepth, boolean visible) {
        this.position = position;
        this.layerDepth = layerDepth;
        this.visible = visible;
        DrawBatch.add(this);
    }

    public abstract void clickAction();

    protected abstract Sprite getSprite();

    @Override
    public void setLayerDepth(float layerDepth) {
        this.layerDepth = layerDepth;
    }

    @Override
    public float getLayerDepth() {
        return this.layerDepth;
    }

    @Override
    public void draw() {
        getSprite().position = new Vector2f(position.x + GameManager.menuBarWidth, position.y + GameManager.toolbarHeight);
        getSprite().draw();
    }

    @Override
    public boolean isVisible() {
        return this.visible;
    }

    @Override
    public int compareTo(Drawable o) {
        if (this.layerDepth < o.getLayerDepth())
            return -1;
        else if (this.layerDepth > o.getLayerDepth())
            return 1;
        else
            return 0;
    }
}
