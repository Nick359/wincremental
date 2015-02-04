package net.wintastic.wincremental.gui;

import net.wintastic.lwjgl.Input;
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
    Icon selectedIcon;

    public MenuBar() {
        iconGrids = new ArrayList<IconGrid>();
        init();
    }

    private void init() {
        selectedIcon = new Icon();

        IconGrid buildingGrid = new IconGrid(IconGrid.GridType.BUILDING, new Vector2f(10f, 32f), 4, 4, 6);
        iconGrids.add(buildingGrid);
        buildingLabel = new Text(new Vector2f(32f, 40f), "Buildings", "Arial", 1, 16, org.newdawn.slick.Color.black, 300, 0f);
    }

    private boolean mouseInMenu() {
        return (Input.mousePosition().x < GameManager.menuBarWidth && Input.mousePosition().y > GameManager.toolbarHeight);
    }

    public void update() {
        if (mouseInMenu() && Input.isButtonPressed(0)) {
            for (int i = 0; i < iconGrids.size(); i++) {
                selectedIcon.selected = false;
                selectedIcon = iconGrids.get(i).getSelectedIcon();
            }
        }
    }

    public void draw() {
        Shape2D.drawRectangle(new Vector2f(0f, GameManager.toolbarHeight), GameManager.menuBarWidth, GameManager.resY, 0f, new Color(255, 255, 240), true);

        //Draw Icon Grids
        for (int i = 0; i < iconGrids.size(); i++) {
            iconGrids.get(i).draw();
        }

        buildingLabel.draw();
    }

}
