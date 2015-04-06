package net.wintastic.wincremental.gui;

import net.wintastic.lwjgl.DrawBatch;
import net.wintastic.lwjgl.Drawable;
import net.wintastic.lwjgl.Input;
import net.wintastic.lwjgl.Sprite;
import net.wintastic.wincremental.AssetLibrary;
import net.wintastic.wincremental.GameManager;
import net.wintastic.wincremental.tiles.Tile;
import org.lwjgl.util.vector.Vector2f;

public class Icon implements Drawable {

    public static enum IconType {
        TEST(AssetLibrary.testIconSprite),
        TENT(AssetLibrary.tentIconSprite),
        STORAGE_SHED(AssetLibrary.storageShedIconSprite),
        OUTPOST(AssetLibrary.outpostIconSprite),
        GOLD(AssetLibrary.goldIconSprite),
        WOOD(AssetLibrary.woodIconSprite);

        Sprite sprite;
        Sprite selectedSprite;

        IconType(Sprite sprite) {
            this.sprite = sprite;
            this.selectedSprite = AssetLibrary.iconSelectedMarkerSprite; //TODO: Either remove selectedSprite as it is always constant, or add new types of markers
        }

        public Tile.TileType getBuildingTileType() {
            switch (this) {
                case TENT:
                    return Tile.TileType.TENT;
                case STORAGE_SHED:
                    return Tile.TileType.STORAGE_SHED;
                case OUTPOST:
                    return Tile.TileType.OUTPOST;
            }
            return null;
        }

        //TODO Add a click action for icons
        //void clickAction() {
        //
        //  }
    }

    public final IconType type;
    private Vector2f position;
    private final Tooltip tooltip;
    public boolean selected;
    private float layerDepth;
    private boolean visible;

    public Icon(IconType type) {
        this(type, new Vector2f(0f, 0f));
    }

    public Icon(IconType type, Vector2f position) {
        this.type = type;
        this.position = position;
        this.layerDepth = 0.6f;
        this.selected = false;
        this.visible = true;
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
        tooltip.visible = contains(Input.mousePosition());
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
        return 0;
    }

}
