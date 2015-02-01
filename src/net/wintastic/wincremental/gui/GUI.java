package net.wintastic.wincremental.gui;

import net.wintastic.lwjgl.Rectangle;
import net.wintastic.lwjgl.Shape2D;
import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class GUI {

    List<IconGrid> iconGrids;

    public GUI() {
        iconGrids = new ArrayList<IconGrid>();
        testInit();
    }

    private void testInit(){
        IconGrid testGrid = new IconGrid("test_grid");
        iconGrids.add(testGrid);
    }

    public void draw() {
        Shape2D.drawRectangle(new Vector2f(0f, 16f), 320, 720, 0f, new Color(255,255,240), true);
        Shape2D.drawRectangle(new Vector2f(0f, 0f), 1280, 16, 0f, new Color(255,222,173), true);

        //Draw Icon Grids
        for (int i = 0; i < iconGrids.size(); i++) {
            iconGrids.get(i).draw();
        }

        for (int i = 0; i < iconGrids.size(); i++) {
            iconGrids.get(i).checkIconHovering();
        }
    }
}
