package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.Pair;
import net.wintastic.wincremental.GameManager;

public class FogOfWar {
    private float[][] fogOfWar;

    public FogOfWar() {
        this.fogOfWar = new float[GameManager.mapWidth][GameManager.mapHeight];
    }

    public float get(Pair<Integer> position) {
        if (position.first >= 0 && position.first < GameManager.mapWidth && position.second >= 0 && position.second < GameManager.mapHeight) {
            return fogOfWar[position.first][position.second];
        }
        return 0;
    }

    public void set(Pair<Integer> position, float value) {
        if (position.first >= 0 && position.first < GameManager.mapWidth && position.second >= 0 && position.second < GameManager.mapHeight) {
            fogOfWar[position.first][position.second] = value;
        }
    }
}
