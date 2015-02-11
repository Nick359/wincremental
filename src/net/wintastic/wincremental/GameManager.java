package net.wintastic.wincremental;

import net.wintastic.lwjgl.DrawBatch;
import net.wintastic.wincremental.gui.GUI;
import net.wintastic.wincremental.tiles.Board;
import org.lwjgl.util.vector.Vector2f;

public class GameManager {

    public static final int resX = 1280;
    public static final int resY = 720;
    public static boolean fullscreen = false;

    public static final int tileSize = 16;
    public static final int iconSize = 32;
    public static final int menuBarWidth = 320;
    public static final int toolbarHeight = 32;
    public static final int mapWidth = 1500;
    public static final int mapHeight = 1500;
    public static final int viewportWidth = (resX - menuBarWidth) / tileSize;
    public static final int viewportHeight = (resY - toolbarHeight) / tileSize;

    public static Player player;
    public static Camera camera;
    private GUI gui;
    public static Board board;

    public void init() {
        player = new Player();
        camera = new Camera(new Vector2f((mapWidth - viewportWidth) * tileSize / 2, (mapHeight - viewportHeight) * tileSize / 2));
        gui = new GUI();
        board = new Board();
    }

    public void update() {
        camera.update();
        gui.update();
        board.update();
    }

    public void draw() {
        DrawBatch.draw();
    }

}
