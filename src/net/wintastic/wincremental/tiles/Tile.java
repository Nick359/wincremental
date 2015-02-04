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
        this(position, true);
    }

    public Tile(Vector2f position, boolean visible) {
        this.position = position;
        this.layerDepth = 0.2f;
        this.visible = visible;
        DrawBatch.add(this);
    }

    public abstract void clickAction();

    protected abstract Sprite getSprite();

    public Vector2f getCameraPosition() {
        float x = position.x + GameManager.menuBarWidth - GameManager.camera.position.x;
        float y = position.y + GameManager.toolbarHeight - GameManager.camera.position.y;
        return new Vector2f(x,y);
    }

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
        getSprite().position = getCameraPosition();
        getSprite().draw();
    }

    @Override
    public boolean isVisible() {
        Vector2f p = getCameraPosition();
        return p.x >= GameManager.menuBarWidth - GameManager.tileSize && p.x <= GameManager.resX && p.y >= GameManager.toolbarHeight - GameManager.tileSize && p.y <= GameManager.resY;
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
