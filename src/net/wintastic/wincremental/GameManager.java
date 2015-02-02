package net.wintastic.wincremental;

import net.wintastic.Game;
import net.wintastic.wincremental.gui.GUI;

public class GameManager {

    public static final int tileSize = 16;
    public static final int leftMenuWidth = 320;
    public static final int topBarHeight = 16;

    GUI gui;
    Board board;
    public static Player player;

    public void init() {
        gui = new GUI();
        board = new Board((Game.resX - leftMenuWidth) / tileSize, (Game.resY - topBarHeight) / tileSize);
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
