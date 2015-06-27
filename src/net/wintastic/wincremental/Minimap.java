package net.wintastic.wincremental;

import net.wintastic.lwjgl.DrawBatch;
import net.wintastic.lwjgl.Drawable;
import net.wintastic.lwjgl.Input;
import net.wintastic.lwjgl.Shape2D;
import net.wintastic.util.math.MathHelper;
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
        this.pixels = new ReadableColor[actualWidth][actualHeight];
        this.prevTime = System.currentTimeMillis();
        this.update(true);

        this.layerDepth = 0.5f;
        this.visible = true;
        DrawBatch.add(this);
    }

    private Position getInitialTopLeftTile() {
        int x = (int) MathHelper.clamp(GameManager.camera.getPosition().x / GameManager.tileSize + (GameManager.viewportWidth - width) / 2, 0, GameManager.mapWidth);
        int y = (int) MathHelper.clamp(GameManager.camera.getPosition().y / GameManager.tileSize + (GameManager.viewportHeight - height) / 2, 0, GameManager.mapHeight);
        return new Position(x, y);
    }

    public void update(boolean force) {
        boolean rectChanged = updateRectPosition();
        if (force || rectChanged || System.currentTimeMillis() - prevTime > 2000) {
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
                    } else {
                        pixels[i][j] = null;
                    }
                }
            }
        }
    }

    public boolean mouseInMinimap() {
        return Input.mousePosition().x >= position.x + borderWidth && Input.mousePosition().x < position.x + width - borderWidth &&
                Input.mousePosition().y >= position.y + borderWidth && Input.mousePosition().y < position.y + height - borderWidth;
    }

    public boolean mouseInRect() {
        Vector2f m = Input.mousePosition();
        Vector2f p = getRectPosition();
        return m.x >= p.x && m.x < p.x + GameManager.viewportWidth && m.y >= p.y && m.y < p.y + GameManager.viewportHeight;
    }

    public Position getHoveredTile() {
        if (mouseInMinimap()) {
            return new Position(topLeftTile.x + (int) (Input.mousePosition().x - borderWidth - position.x),
                    topLeftTile.y + (int) (Input.mousePosition().y - borderWidth - position.y));
        }
        return null;
    }

    //TODO: if dragging minimap rectangle, move mouse to keep up with rectangle
    private boolean updateRectPosition() {
        Position prevTopLeftTile = topLeftTile;
        if (GameManager.camera.getPosition().x / GameManager.tileSize < topLeftTile.x) {
            topLeftTile.x -= (topLeftTile.x - GameManager.camera.getPosition().x / GameManager.tileSize);
        }
        if (GameManager.camera.getPosition().x / GameManager.tileSize + GameManager.viewportWidth > topLeftTile.x + actualWidth) {
            topLeftTile.x -= (topLeftTile.x + actualWidth - GameManager.viewportWidth - GameManager.camera.getPosition().x / GameManager.tileSize);
        }
        if (GameManager.camera.getPosition().y / GameManager.tileSize < topLeftTile.y) {
            topLeftTile.y -= (topLeftTile.y - GameManager.camera.getPosition().y / GameManager.tileSize);
        }
        if (GameManager.camera.getPosition().y / GameManager.tileSize + GameManager.viewportHeight > topLeftTile.y + actualHeight) {
            topLeftTile.y -= (topLeftTile.y + actualHeight - GameManager.viewportHeight - GameManager.camera.getPosition().y / GameManager.tileSize);
        }
        return topLeftTile.equals(prevTopLeftTile);
    }

    private Vector2f getRectPosition() {
        return new Vector2f(position.x + borderWidth + (GameManager.camera.getPosition().x / GameManager.tileSize - topLeftTile.x),
                position.y + borderWidth + (GameManager.camera.getPosition().y / GameManager.tileSize - topLeftTile.y));
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
        Shape2D.drawRectangle(getRectPosition(), GameManager.viewportWidth, GameManager.viewportHeight, 0, ReadableColor.WHITE, false);
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
