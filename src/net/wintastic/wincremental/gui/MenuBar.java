package net.wintastic.wincremental.gui;

import net.wintastic.lwjgl.Shape2D;
import net.wintastic.wincremental.GameManager;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class MenuBar {

    List<IconGrid> iconGrids;

    public MenuBar() {
        iconGrids = new ArrayList<IconGrid>();
        testInit();
    }

    private void testInit() {
        IconGrid testGrid = new IconGrid("test_grid");
        iconGrids.add(testGrid);
    }

    public void draw() {
        Shape2D.drawRectangle(new Vector2f(0f, GameManager.toolbarHeight), GameManager.menuBarWidth, GameManager.resY, 0f, new Color(255, 255, 240), true);

        //Draw Icon Grids
        for (int i = 0; i < iconGrids.size(); i++) {
            iconGrids.get(i).draw();
        }

        for (int i = 0; i < iconGrids.size(); i++) {
            iconGrids.get(i).checkIconHovering();
        }

    }

}
