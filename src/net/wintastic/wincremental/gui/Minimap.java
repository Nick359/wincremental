package net.wintastic.wincremental.gui;

import net.wintastic.lwjgl.DrawBatch;
import net.wintastic.lwjgl.Drawable;
import net.wintastic.lwjgl.Pair;
import net.wintastic.lwjgl.Shape2D;
import net.wintastic.util.math.MathHelper;
import net.wintastic.wincremental.GameManager;
import net.wintastic.wincremental.tiles.Board;
import net.wintastic.wincremental.tiles.ResourceTile;
import net.wintastic.wincremental.tiles.Tile;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.vector.Vector2f;

public class Minimap implements Drawable {
    Board board;
    Vector2f position;
    int width;
    int height;
    int actualWidth;
    int actualHeight;
    Pair<Integer> topLeftTile;
    int borderWidth;
    float layerDepth;
    boolean visible;

    ReadableColor[][] pixels;
    long prevTime;

    public Minimap(Board board, int width, int height) {
        this.board = board;
        this.width = width;
        this.height = height;
        this.topLeftTile = getInitialTopLeftTile();
        this.borderWidth = 2;
        this.actualWidth = Math.min(this.width, GameManager.mapWidth) - this.borderWidth * 2;
        this.actualHeight = Math.min(this.height, GameManager.mapHeight) - this.borderWidth * 2;
        this.position = new Vector2f(0, GameManager.resY - height);
        this.layerDepth = 0.5f;
        this.visible = true;

        this.pixels = new ReadableColor[actualWidth][actualHeight];
        this.prevTime = System.currentTimeMillis();

        DrawBatch.add(this);
    }

    private Pair<Integer> getInitialTopLeftTile() {
        int x = (int) MathHelper.clamp(GameManager.camera.position.x / GameManager.tileSize - width / 2, 0, GameManager.mapWidth);
        int y = (int) MathHelper.clamp(GameManager.camera.position.y / GameManager.tileSize - height / 2, 0, GameManager.mapHeight);
        return new Pair<Integer>(x, y);
    }

    public void update() {
        if (System.currentTimeMillis() - prevTime > 500) {
            System.out.println("!");
            prevTime = System.currentTimeMillis();
            for (int i = 0; i < actualWidth; i++) {
                for (int j = 0; j < actualHeight; j++) {
                    Tile t = GameManager.board.getTile(new Pair<Integer>(topLeftTile.first + i, topLeftTile.second + j));
                    if (!(t instanceof ResourceTile && ((ResourceTile) t).type == ResourceTile.ResourceTileType.GRASS))
                        pixels[i][j] = t.getColor();
                }
            }
        }
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
        Shape2D.drawRectangle(position, width, height, 0f, ReadableColor.LTGREY, true);
        Shape2D.drawRectangle(new Vector2f(position.x + borderWidth, position.y + borderWidth),
                width - borderWidth * 2, height - borderWidth * 2, 0f, ReadableColor.GREEN, true);
        drawMinimapContents();
    }

    private void drawMinimapContents() {
        for (int i = 0; i < actualWidth; i++) {
            for (int j = 0; j < actualHeight; j++) {
                if (pixels[i][j] != null)
                    Shape2D.drawPixel(new Vector2f(position.x + borderWidth + i, position.y + borderWidth + j), pixels[i][j]);

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
