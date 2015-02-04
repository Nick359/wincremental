package net.wintastic.wincremental;

import net.wintastic.lwjgl.DrawBatch;
import net.wintastic.wincremental.gui.GUI;
import net.wintastic.wincremental.tiles.Board;
import org.lwjgl.util.vector.Vector2f;

public class GameManager {

    public static final int tileSize = 16;
    public static final int iconSize = 32;
    public static final int menuBarWidth = 320;
    public static final int toolbarHeight = 32;
    public static final int resX = 1280;
    public static final int resY = 720;

    public static Player player;
    public static Camera camera;
    GUI gui;
    Board board;


    public void init() {
        player = new Player();
        camera = new Camera(new Vector2f(resX / 2, resY / 2));
        gui = new GUI();
        board = new Board((resX - menuBarWidth) / tileSize, (resY - toolbarHeight) / tileSize);
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
