package net.wintastic.wincremental.gui;

import net.wintastic.lwjgl.Shape2D;
import net.wintastic.lwjgl.Text;
import net.wintastic.wincremental.GameManager;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class MenuBar {

    List<IconGrid> iconGrids;
    Text buildingLabel;

    public MenuBar() {
        iconGrids = new ArrayList<IconGrid>();
        init();
    }

    private void init() {
        IconGrid buildingGrid = new IconGrid(IconGrid.GridType.BUILDING, new Vector2f(10f, 32f), 4, 4, 6);
        iconGrids.add(buildingGrid);
        buildingLabel = new Text(new Vector2f(32f, 40f), "Buildings", "Arial", 1, 16, org.newdawn.slick.Color.black, 300, 0f);
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

        buildingLabel.draw();
    }

}
