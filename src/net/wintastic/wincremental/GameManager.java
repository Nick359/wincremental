package net.wintastic.wincremental;

import net.wintastic.Game;
import net.wintastic.lwjgl.Input;
import org.lwjgl.util.ReadableColor;

public class GameManager {

    Canvas canvas;
    int i = 0;

    public void init() {
        canvas = new Canvas(Game.resX, Game.resY);

        for (int j = 0; j < 1000; j++) {
            canvas.updatePixel(j, 100, ReadableColor.GREEN);
        }
    }

    public void update() {
        if (i < Game.resX) {
            for (int j = 0; j < Game.resY; j++) {
                canvas.updatePixel(i, j, ReadableColor.YELLOW);
            }
            i++;
        }

        if (Input.isButtonDown(0)) {
            canvas.updatePixel((int) Input.mousePosition().x, (int) Input.mousePosition().y, ReadableColor.GREEN);
        }
    }

    public void draw() {
        canvas.draw();
    }

}
