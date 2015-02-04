package net.wintastic.wincremental.gui;

import net.wintastic.lwjgl.DrawBatch;
import net.wintastic.lwjgl.Drawable;
import net.wintastic.lwjgl.Input;
import net.wintastic.lwjgl.Sprite;
import net.wintastic.wincremental.AssetLibrary;
import net.wintastic.wincremental.GameManager;
import org.lwjgl.util.vector.Vector2f;

public class Icon implements Drawable {

    public static enum IconType {
        TEST(AssetLibrary.testIconSprite),
        TENT(AssetLibrary.tentIconSprite),
        STORAGE_SHED(AssetLibrary.storageShedIconSprite),
        GOLD(AssetLibrary.goldIconSprite),
        WOOD(AssetLibrary.woodIconSprite);

        Sprite sprite;
        Sprite selectedSprite;

        IconType(Sprite sprite) {
            this.sprite = sprite;
            this.selectedSprite = AssetLibrary.iconSelectedMarkerSprite;
        }

        //TODO Add a click action for icons
        //void clickAction() {
        //
        //  }
    }

    IconType type;
    Vector2f position;
    Tooltip tooltip;
    boolean selected;
    float layerDepth;
    boolean visible;

    public Icon() {
        this(IconType.TEST, new Vector2f(0f, 0f), 0f, false);
    }

    public Icon(IconType type, float layerDepth, boolean visible) {
        this(type, new Vector2f(0f, 0f), layerDepth, visible);
    }

    public Icon(IconType type, Vector2f position, float layerDepth, boolean visible) {
        this.type = type;
        this.position = position;
        this.layerDepth = layerDepth;
        this.selected = false;
        this.visible = visible;
        tooltip = new Tooltip(this);

        DrawBatch.add(this);
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public boolean contains(Vector2f other) {
        return (other.x >= position.x && other.x <= position.x + GameManager.iconSize && other.y >= position.y && other.y <= position.y + GameManager.iconSize);
    }

    public Tooltip getTooltip() {
        return tooltip;
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
        if (selected) {
            type.selectedSprite.position = new Vector2f(position.x - 2, position.y - 2);
            type.selectedSprite.draw();
        }
        type.sprite.position = position;
        type.sprite.draw();
        if (contains(Input.mousePosition())) {
            tooltip.visible = true;
        } else {
            tooltip.visible = false;
        }
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
