package net.wintastic.wincremental;

import net.wintastic.lwjgl.Input;

public abstract class GameHelper {

    public static boolean mouseInBoard() {
        return Input.mousePosition().x >= GameManager.menuBarWidth && Input.mousePosition().x < GameManager.resX &&
                Input.mousePosition().y >= GameManager.toolbarHeight && Input.mousePosition().y < GameManager.resY;
    }

    public static boolean mouseInMinimap() {
        return GameManager.minimap.mouseInMinimap();
    }
}
