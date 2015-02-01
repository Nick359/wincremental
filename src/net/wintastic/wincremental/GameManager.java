package net.wintastic.wincremental;

import net.wintastic.Game;

public class GameManager {

    public static final int tileSize = 16;
    public static final int leftMenuWidth = 320;
    public static final int topBarHeight = 16;


    Board board;


    public void init() {
        board = new Board((Game.resX - leftMenuWidth) / tileSize, (Game.resY - topBarHeight) / tileSize);
    }

    public void update() {
        board.update();
    }

    public void draw() {
        board.draw();
    }

}
