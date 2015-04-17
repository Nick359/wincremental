package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.Pair;
import net.wintastic.lwjgl.Sprite;
import net.wintastic.wincremental.GameManager;
import org.lwjgl.util.vector.Vector2f;

public abstract class Tile {

    public static float scaleX = 1;
    public static float scaleY = 1;

    private static Vector2f getScreenPosition(Pair<Integer> position) {
        float x = position.first * GameManager.tileSize + GameManager.menuBarWidth - GameManager.camera.getPosition().x;
        float y = position.second * GameManager.tileSize + GameManager.toolbarHeight - GameManager.camera.getPosition().y;
        return new Vector2f(x, y);
    }


    private Pair<Integer> position;

    public Tile(Pair<Integer> position) {
        this.position = position;
    }

    public Pair<Integer> getPosition() {
        return position;
    }

    public int getRadius() {
        return 0;
    }

    public abstract void clickAction();

    public abstract Sprite getSprite();

    public void draw() {
        Sprite s = getSprite();
        s.position = getScreenPosition(position);
        s.scaleX = scaleX;
        s.scaleY = scaleY;
        s.draw();
    }
}
