package net.wintastic.wincremental.gui;

import net.wintastic.lwjgl.*;
import net.wintastic.wincremental.GameManager;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class MenuBar implements Drawable {

    private final List<IconGrid> iconGrids;
    private Text buildingLabel;
    public static Icon selectedIcon;
    private float layerDepth;
    private boolean visible;

    public MenuBar() {
        iconGrids = new ArrayList<IconGrid>();
        layerDepth = 0.4f;
        visible = true;
        init();

        DrawBatch.add(this);
    }

    private void init() {
        IconGrid buildingGrid = new IconGrid(IconGrid.GridType.BUILDING, new Vector2f(10f, 32f), 4, 4, 6);
        iconGrids.add(buildingGrid);
        buildingLabel = new Text(new Vector2f(32f, 40f), "Buildings", "Arial", 1, 16, org.newdawn.slick.Color.black, 300, 0f);
    }

    private boolean mouseInMenu() {
        return Input.mousePosition().x > 0 && Input.mousePosition().x < GameManager.menuBarWidth && Input.mousePosition().y > GameManager.toolbarHeight && Input.mousePosition().y < GameManager.resY;
    }

    public void update() {
        if (mouseInMenu() && Input.isButtonPressed(0)) {
            if (selectedIcon != null)
                selectedIcon.selected = false;

            for (IconGrid iconGrid : iconGrids) {
                selectedIcon = iconGrid.getSelectedIcon();
            }
        }
    }

    @Override
    public void setLayerDepth(float layerDepth) {
        this.layerDepth = layerDepth;
    }

    @Override
    public float getLayerDepth() {
        return layerDepth;
    }

    public void draw() {
        Shape2D.drawRectangle(new Vector2f(0f, GameManager.toolbarHeight), GameManager.menuBarWidth, GameManager.resY, 0f, new Color(255, 255, 240), true);

        buildingLabel.draw();
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public int compareTo(Drawable o) {
        if (this.layerDepth < o.getLayerDepth())
            return -1;
        else if (this.layerDepth > o.getLayerDepth())
            return 1;
        else
            return 0;
    }
}
