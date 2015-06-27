package net.wintastic.wincremental;

import net.wintastic.lwjgl.DrawBatch;
import net.wintastic.wincremental.gui.GUI;
import net.wintastic.wincremental.tiles.Board;
import org.lwjgl.util.vector.Vector2f;

public class GameManager {

    public static final int resX = 1280;
    public static final int resY = 720;
    public static boolean fullscreen = false;

    public static boolean useFogOfWar = true;
    public static boolean useBuildingCost = false;

    public static int tileSize = 16;
    public static final int defaultTileSize = 16;
    public static final int iconSize = 32;
    public static final int menuBarWidth = 320;
    public static final int toolbarHeight = 32;
    public static final int mapWidth = 1000;    // WARNING: Values over ~256M tiles causes OutOfMemoryError: Java heap space! Woohoo!
    public static final int mapHeight = 1000;
    public static int viewportWidth = (resX - menuBarWidth) / tileSize;
    public static int viewportHeight = (resY - toolbarHeight) / tileSize;

    public static Player player;
    public static Camera camera;
    public static GUI gui;
    public static Board board;
    public static Minimap minimap;

    public void init() {
        player = new Player();
        camera = new Camera(new Vector2f((mapWidth - viewportWidth) * tileSize / 2, (mapHeight - viewportHeight) * tileSize / 2));
        gui = new GUI();
        board = new Board();
        minimap = new Minimap(GameManager.board, GameManager.menuBarWidth, GameManager.menuBarWidth * 2 / 3); //TODO: change hardcoded to better version
    }

    public void update() {
        camera.update();
        gui.update();
        board.update();
        minimap.update(false);
    }

    public void draw() {
        DrawBatch.draw();
    }

}
