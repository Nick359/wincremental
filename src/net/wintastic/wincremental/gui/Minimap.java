package net.wintastic.wincremental.gui;

import net.wintastic.lwjgl.DrawBatch;
import net.wintastic.lwjgl.Drawable;
import net.wintastic.lwjgl.Shape2D;
import net.wintastic.util.math.MathHelper;
import net.wintastic.wincremental.GameManager;
import net.wintastic.wincremental.tiles.Board;
import net.wintastic.wincremental.tiles.Position;
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
    Position topLeftTile;
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
        this.update(true);

        DrawBatch.add(this);
    }

    private Position getInitialTopLeftTile() {
        int x = (int) MathHelper.clamp(GameManager.camera.getPosition().x / GameManager.tileSize - width / 2, 0, GameManager.mapWidth);
        int y = (int) MathHelper.clamp(GameManager.camera.getPosition().y / GameManager.tileSize - height / 2, 0, GameManager.mapHeight);
        return new Position(x, y);
    }

    public void update(boolean force) {
        if (force || System.currentTimeMillis() - prevTime > 2000) {
            prevTime = System.currentTimeMillis();
            for (int i = 0; i < actualWidth; i++) {
                for (int j = 0; j < actualHeight; j++) {
                    Position p = new Position(topLeftTile.x + i, topLeftTile.y + j);
                    if (board.fogOfWar.get(p) == 1) {
                        Tile t = board.getTile(p);
                        if (t == null) {
                            pixels[i][j] = ReadableColor.GREEN;
                        } else {
                            pixels[i][j] = board.getTile(p).getColor();
                        }
                    }
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
                width - borderWidth * 2, height - borderWidth * 2, 0f, ReadableColor.BLACK, true);
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