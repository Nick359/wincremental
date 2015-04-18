package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.Shape2D;
import net.wintastic.wincremental.GameManager;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

public class FogOfWar {
    private float[][] fogOfWar;

    public FogOfWar() {
        this.fogOfWar = new float[GameManager.mapWidth][GameManager.mapHeight];
    }

    public float get(Position position) {
        if (position.x >= 0 && position.x < GameManager.mapWidth && position.y >= 0 && position.y < GameManager.mapHeight) {
            return fogOfWar[position.x][position.y];
        }
        return 0;
    }

    public void set(Position position, float value) {
        if (position.x >= 0 && position.x < GameManager.mapWidth && position.y >= 0 && position.y < GameManager.mapHeight) {
            fogOfWar[position.x][position.y] = value;
        }
    }

    public void updateAtPosition(Position position, int radius) {
        if (radius <= 0) return;
        int x0 = position.x;
        int y0 = position.y;
        for (int y = -radius; y <= radius; y++) {
            for (int x = -radius; x <= radius; x++) {
                if (x * x + y * y <= radius * radius) {
                    set(new Position(x0 + x, y0 + y), 1);
                }
            }
        }
        int gradientRadius = 32;
        x0 = position.x;
        y0 = position.y;
        for (int y = -gradientRadius; y <= gradientRadius; y++) {
            for (int x = -gradientRadius; x <= gradientRadius; x++) {
                if (x * x + y * y <= gradientRadius * gradientRadius) {
                    Position p = new Position(x0 + x, y0 + y);
                    if (get(p) < 1) {
                        set(p, ((float) (numberOfNearVisibleTiles(p, gradientRadius / 2))) / (gradientRadius * gradientRadius));
                    }
                }
            }
        }
    }

    private int numberOfNearVisibleTiles(Position position, int radius) {
        int total = 0;
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                if (i * i + j * j <= radius * radius && get(new Position(position.x + i, position.y + j)) == 1)
                    total++;
            }
        }
        return total;
    }

    public void draw() {
        float dx = GameManager.camera.getPosition().x % GameManager.tileSize;
        float dy = GameManager.camera.getPosition().y % GameManager.tileSize;
        for (int i = 0; i <= GameManager.viewportWidth + 1; i++) {
            for (int j = 0; j <= GameManager.viewportHeight + 1; j++) {
                Position tilePosition = new Position((int) (GameManager.camera.getPosition().x / GameManager.tileSize + i), (int) (GameManager.camera.getPosition().y / GameManager.tileSize + j));
                if (get(tilePosition) < 1) {
                    Vector2f p = new Vector2f(i * GameManager.tileSize - dx + GameManager.menuBarWidth, j * GameManager.tileSize - dy + GameManager.toolbarHeight);
                    int a = (int) (255 * get(tilePosition));
                    Color c = new Color(a, a, a, 255 - a);
                    Shape2D.drawRectangle(p, GameManager.tileSize, GameManager.tileSize, 0, c, true);
                }
            }
        }
    }
}
