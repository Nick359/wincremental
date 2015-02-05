package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.DrawBatch;
import net.wintastic.lwjgl.Drawable;
import net.wintastic.lwjgl.Tuple;
import net.wintastic.wincremental.AssetLibrary;
import net.wintastic.wincremental.GameManager;
import org.lwjgl.util.vector.Vector2f;

public class TileRadiusIndicator implements Drawable {

    int radius;
    float layerDepth;
    boolean visible;
    BuildingTile buildingTile;

    public TileRadiusIndicator(int radius, BuildingTile buildingTile) {
        this.radius = radius;
        this.buildingTile = buildingTile;
        this.layerDepth = 0.3f;
        visible = false;

        DrawBatch.add(this);
    }

    @Override
    public void setLayerDepth(float layerDepth) {
        this.layerDepth = layerDepth;
    }

    @Override
    public float getLayerDepth() {
        return layerDepth;
    }

    @Override
    public void draw() {
        Tuple<Integer> position = buildingTile.position;

        int x0 = position.first;
        int y0 = position.second;

        for (int y = -radius; y <= radius; y++) {
            for (int x = -radius; x <= radius; x++) {
                if (x * x + y * y <= radius * radius) {
                    drawTile(x0 + x, y0 + y);
                }
            }
        }
    }

    private void drawTile(int x, int y) {
        float _x = x * GameManager.tileSize + GameManager.menuBarWidth - GameManager.camera.position.x;
        float _y = y * GameManager.tileSize + GameManager.toolbarHeight - GameManager.camera.position.y;

        AssetLibrary.radiusIndicatorSprite.position = new Vector2f(_x, _y);
        AssetLibrary.radiusIndicatorSprite.draw();
    }

    @Override
    public boolean isVisible() {
        return visible;
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
