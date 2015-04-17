package net.wintastic.wincremental.tiles;

import net.wintastic.wincremental.GameManager;

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
}
