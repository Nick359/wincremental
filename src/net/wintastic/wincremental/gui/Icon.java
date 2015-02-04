package net.wintastic.wincremental.gui;

import net.wintastic.lwjgl.Input;
import net.wintastic.lwjgl.Sprite;
import net.wintastic.wincremental.AssetLibrary;
import net.wintastic.wincremental.GameManager;
import org.lwjgl.util.vector.Vector2f;

public class Icon {

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

    public Icon() {
        this(IconType.TEST, new Vector2f(0f, 0f));
    }

    public Icon(IconType type) {
        this(type, new Vector2f(0f, 0f));
    }

    public Icon(IconType type, Vector2f position) {
        this.type = type;
        this.position = position;
        this.selected = false;
        tooltip = new Tooltip(this);
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

    public void draw() {
        if (selected) {
            type.selectedSprite.position = new Vector2f(position.x - 2, position.y - 2);
            type.selectedSprite.draw();
        }
        type.sprite.position = position;
        type.sprite.draw();
        if (contains(Input.mousePosition())) {
            tooltip.draw();
        }
    }

}
