package net.wintastic.wincremental.gui;

import net.wintastic.lwjgl.*;
import net.wintastic.wincremental.GameManager;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

public class Tooltip implements Drawable {

    Icon parentIcon;
    Text headerText;
    Text bodyText;
    Text footerText;
    private float layerDepth;
    boolean visible;

    public Tooltip(Icon parentIcon) {
        this.parentIcon = parentIcon;
        layerDepth = 0.3f;
        visible = false;
        init();

        DrawBatch.add(this);
    }

    private void init() {
        switch (parentIcon.type) {
            case TEST:
                headerText = new Text(new Vector2f(0f, 0f), "Test Header", "Arial", 1, 16);
                bodyText = new Text(new Vector2f(0f, 0f), "Test Body", "Arial", 0, 12);
                footerText = new Text(new Vector2f(0f, 0f), "Test Footer", "Arial", 2, 12);
                break;
            case TENT:
                headerText = new Text(new Vector2f(0f, 0f), "Simple Tent", "Arial", 1, 16);
                bodyText = new Text(new Vector2f(0f, 0f), "-10 WOOD     +3 POPULATION", "Arial", 0, 12);
                footerText = new Text(new Vector2f(0f, 0f), "There's nothing like a good tent to get naked in.", "Arial", 2, 12);
                break;
            case STORAGE_SHED:
                headerText = new Text(new Vector2f(0f, 0f), "Storage Shed", "Arial", 1, 16);
                bodyText = new Text(new Vector2f(0f, 0f), "-20 WOOD     +100 CAPACITY", "Arial", 0, 12);
                footerText = new Text(new Vector2f(0f, 0f), "A humble place to put your crap in.", "Arial", 2, 12, org.newdawn.slick.Color.white, 256, 0f);
                break;
            case WOOD:
                headerText = new Text(new Vector2f(0f, 0f), "Wood", "Arial", 1, 16);
                bodyText = new Text(new Vector2f(0f, 0f), "You have " + GameManager.player.getWood() + " wood.", "Arial", 0, 12);
                footerText = new Text(new Vector2f(0f, 0f), "Named after suburban streets.", "Arial", 2, 12);
                break;
            case GOLD:
                headerText = new Text(new Vector2f(0f, 0f), "Gold", "Arial", 1, 16);
                bodyText = new Text(new Vector2f(0f, 0f), "You have " + GameManager.player.getGold() + " gold.", "Arial", 0, 12);
                footerText = new Text(new Vector2f(0f, 0f), "It is health that is real wealth and not pieces of gold and silver - Poor person", "Arial", 2, 12, org.newdawn.slick.Color.white, 256, 0f);
                break;
        }
    }

    public void update() {
        switch (parentIcon.type) {
            case TEST:
                break;
            case TENT:
                break;
            case WOOD:
                bodyText.setText("You have " + GameManager.player.getWood() + " wood.");
                break;
            case GOLD:
                bodyText.setText("You have " + GameManager.player.getGold() + " gold.");
                break;
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

    @Override
    public void draw() {
        Shape2D.drawRectangle(Input.mousePosition(), 300, 200, 0f, new Color(200, 200, 200), true);
        headerText.position = new Vector2f(Input.mousePosition().x + 12f, Input.mousePosition().y + 6f);
        bodyText.position = new Vector2f(Input.mousePosition().x + 12f, Input.mousePosition().y + 32f);
        footerText.position = new Vector2f(Input.mousePosition().x + 12f, Input.mousePosition().y + 128f);
        headerText.draw();
        bodyText.draw();
        footerText.draw();
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
