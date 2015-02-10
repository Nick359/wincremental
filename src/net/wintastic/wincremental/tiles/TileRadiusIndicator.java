package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.DrawBatch;
import net.wintastic.lwjgl.Drawable;
import net.wintastic.lwjgl.Pair;
import net.wintastic.wincremental.AssetLibrary;
import net.wintastic.wincremental.GameManager;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class TileRadiusIndicator implements Drawable {

    int radius;
    float layerDepth;
    boolean visible;
    BuildingTile buildingTile;
    List<Vector2f> tiles;

    public TileRadiusIndicator(int radius, BuildingTile buildingTile) {
        this.radius = radius;
        this.buildingTile = buildingTile;
        this.layerDepth = 0.3f;
        visible = false;

        getTiles();
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
        for (int i = 0; i < tiles.size(); i++){
            AssetLibrary.radiusIndicatorSprite.position = tiles.get(i);
            AssetLibrary.radiusIndicatorSprite.draw();
        }
    }

    private void getTiles() {
        tiles = new ArrayList<Vector2f>();

        Pair<Integer> position = buildingTile.position;

        int x0 = position.first;
        int y0 = position.second;

        for (int y = -radius; y <= radius; y++) {
            for (int x = -radius; x <= radius; x++) {
                if (x * x + y * y <= radius * radius) {
                    tiles.add(new Vector2f((x0 + x) * GameManager.tileSize + GameManager.menuBarWidth - GameManager.camera.position.x
                            , (y0 + y) * GameManager.tileSize + GameManager.toolbarHeight - GameManager.camera.position.y));
                }
            }
        }
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
