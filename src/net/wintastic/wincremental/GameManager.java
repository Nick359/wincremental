package net.wintastic.wincremental;

import net.wintastic.lwjgl.DrawBatch;
import net.wintastic.wincremental.gui.GUI;

public class GameManager {

    public static final int tileSize = 16;
    public static final int iconSize = 32;
    public static final int menuBarWidth = 320;
    public static final int toolbarHeight = 32;
    public static final int resX = 1280;
    public static final int resY = 720;

    public static Player player;
    GUI gui;
    Board board;


    public void init() {
        player = new Player();
        gui = new GUI();
        board = new Board((resX - menuBarWidth) / tileSize, (resY - toolbarHeight) / tileSize);
    }

    public void update() {
        gui.update();
        board.update();
    }

    public void draw() {
        DrawBatch.draw();
    }

}
