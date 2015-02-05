package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.DrawBatch;
import net.wintastic.lwjgl.Drawable;
import net.wintastic.lwjgl.Sprite;
import net.wintastic.lwjgl.Tuple;
import net.wintastic.wincremental.GameManager;
import org.lwjgl.util.vector.Vector2f;

public abstract class Tile implements Drawable {
    Tuple<Integer> position;
    float layerDepth;
    boolean visible;

    public Tile(Tuple<Integer> position) {
        this(position, true);
    }

    public Tile(Tuple<Integer> position, boolean visible) {
        this.position = position;
        this.layerDepth = 0.2f;
        this.visible = visible;
        DrawBatch.add(this);
    }

    public abstract void clickAction();

    protected abstract Sprite getSprite();

    public Vector2f getScreenPosition() {
        float x = position.first * GameManager.tileSize + GameManager.menuBarWidth - GameManager.camera.position.x;
        float y = position.second * GameManager.tileSize + GameManager.toolbarHeight - GameManager.camera.position.y;
        return new Vector2f(x, y);
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
        getSprite().position = getScreenPosition();
        getSprite().draw();
    }

    @Override
    public boolean isVisible() {
        int minX = (int) (GameManager.camera.position.x / GameManager.tileSize - 1);
        int maxX = (int) (GameManager.camera.position.x / GameManager.tileSize + GameManager.viewportWidth + 1);
        int minY = (int) (GameManager.camera.position.y / GameManager.tileSize - 1);
        int maxY = (int) (GameManager.camera.position.y / GameManager.tileSize + GameManager.viewportHeight + 1);

        return position.first >= minX && position.first < maxX && position.second >= minY && position.second < maxY;
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
