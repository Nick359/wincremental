package net.wintastic.wincremental;

import net.wintastic.wincremental.gui.GUI;

public class GameManager {

    public static final int tileSize = 16;
    public static final int iconSize = 32;
    public static final int leftMenuWidth = 320;
    public static final int toolbarHeight = 32;
    public static final int resX = 1280;
    public static final int resY = 720;

    GUI gui;
    Board board;
    public static Player player;

    public void init() {
        gui = new GUI();
        board = new Board((resX - leftMenuWidth) / tileSize, (resY - toolbarHeight) / tileSize);
        player = new Player();
    }

    public void update() {
        gui.update();
        board.update();
    }

    public void draw() {
        board.draw();
        gui.draw();
    }

}
