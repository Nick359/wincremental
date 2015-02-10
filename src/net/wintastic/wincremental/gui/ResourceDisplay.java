package net.wintastic.wincremental.gui;

import net.wintastic.lwjgl.DrawBatch;
import net.wintastic.lwjgl.Drawable;
import net.wintastic.lwjgl.Text;
import net.wintastic.wincremental.GameManager;
import net.wintastic.wincremental.gui.util.GUIHelper;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

public class ResourceDisplay implements Drawable {

    private Icon icon;
    private Text text;
    private Vector2f position;
    private float layerDepth;
    private boolean visible;

    public ResourceDisplay(Icon.IconType type, Vector2f position) {
        this.position = position;
        icon = new Icon(type, position);
        text = new Text(new Vector2f(position.x + GameManager.iconSize + 6f, position.y + (GameManager.iconSize / 4)), "Resource_Display", "Arial", 1, 16);
        text.color = Color.black;
        layerDepth = 0.6f;
        visible = true;

        DrawBatch.add(this);
    }

    public void update() {
        switch (icon.type) {
            case TEST:
                break;
            case GOLD:
                String goldText = GUIHelper.formatNumber(GameManager.player.getGold());
                text.setText(goldText);
                icon.getTooltip().update();
                break;
            case WOOD:
                String woodText = GUIHelper.formatNumber(GameManager.player.getWood());
                text.setText(woodText);
                icon.getTooltip().update();
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

    public void draw() {
        text.draw();
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
