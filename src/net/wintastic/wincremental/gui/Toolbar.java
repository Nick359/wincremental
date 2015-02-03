package net.wintastic.wincremental.gui;

import net.wintastic.lwjgl.Shape2D;
import net.wintastic.wincremental.GameManager;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Toolbar {

    List<ResourceDisplay> resourceDisplays;

    public Toolbar() {
        resourceDisplays = new ArrayList<ResourceDisplay>();
        init();
    }

    private void init() {
        ResourceDisplay woodDisplay = new ResourceDisplay(Icon.IconType.WOOD, new Vector2f(0f, 0f));
        resourceDisplays.add(woodDisplay);

        ResourceDisplay goldDisplay = new ResourceDisplay(Icon.IconType.GOLD, new Vector2f(92f, 0f));
        resourceDisplays.add(goldDisplay);

    }

    public void update() {
        for (int i = 0; i < resourceDisplays.size(); i++) {
            resourceDisplays.get(i).update();
        }
    }

    public void draw() {
        Shape2D.drawRectangle(new Vector2f(0f, 0f), GameManager.resX, GameManager.toolbarHeight, 0f, new Color(255, 222, 173), true);

        for (int i = 0; i < resourceDisplays.size(); i++) {
            resourceDisplays.get(i).draw();
        }

        for (int i = 0; i < resourceDisplays.size(); i++) {
            resourceDisplays.get(i).checkIconHovering();
        }
    }
}
