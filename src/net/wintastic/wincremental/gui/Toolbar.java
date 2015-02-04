package net.wintastic.wincremental.gui;

import net.wintastic.lwjgl.DrawBatch;
import net.wintastic.lwjgl.Drawable;
import net.wintastic.lwjgl.Shape2D;
import net.wintastic.wincremental.GameManager;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Toolbar implements Drawable {

    List<ResourceDisplay> resourceDisplays;
    float layerDepth;
    boolean visible;

    public Toolbar() {
        resourceDisplays = new ArrayList<ResourceDisplay>();
        layerDepth = 0;
        visible = true;
        init();

        DrawBatch.add(this);
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

    @Override
    public void setLayerDepth(float layerDepth) {
        this.layerDepth = layerDepth;
    }

    @Override
    public float getLayerDepth() {
        return layerDepth;
    }

    public void draw() {
        Shape2D.drawRectangle(new Vector2f(0f, 0f), GameManager.resX, GameManager.toolbarHeight, 0f, new Color(255, 222, 173), true);
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
