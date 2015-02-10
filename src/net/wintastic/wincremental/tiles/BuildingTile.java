package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.Sprite;
import net.wintastic.lwjgl.Pair;
import net.wintastic.wincremental.AssetLibrary;
import org.lwjgl.util.ReadableColor;

public class BuildingTile extends Tile {

    public static enum BuildingTileType {
        TOWN_CENTER(AssetLibrary.townCenterSprite, ReadableColor.BLACK, 16),
        TENT(AssetLibrary.tentTileSprite, ReadableColor.BLACK, 0),
        STORAGE_SHED(AssetLibrary.storageShedTileSprite, ReadableColor.BLACK, 0);

        Sprite sprite;
        ReadableColor color;
        int radiusSize;

        BuildingTileType(Sprite sprite, ReadableColor color, int radiusSize) {
            this.sprite = sprite;
            this.color = color;
            this.radiusSize = radiusSize;
        }
    }

    public BuildingTileType type;
    int radiusSize;
    public boolean selected;
    TileRadiusIndicator radiusIndicator;

    public BuildingTile(Pair<Integer> position, BuildingTileType type) {
        super(position);
        this.type = type;
        this.radiusSize = type.radiusSize;
        this.selected = false;
        radiusIndicator = new TileRadiusIndicator(radiusSize, this);
    }

    @Override
    public void clickAction() {
        selected = true;
        Board.selectedTile = this;

        if (radiusSize > 0)
        radiusIndicator.visible = true;
    }

    public void toggleSelected(){
        selected = !selected;
        radiusIndicator.visible = false;
    }

    @Override
    protected Sprite getSprite() {
        return type.sprite;
    }

    @Override
    public ReadableColor getColor() {
        return type.color;
    }

}
