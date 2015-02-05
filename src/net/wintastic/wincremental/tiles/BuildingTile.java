package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.Sprite;
import net.wintastic.lwjgl.Tuple;
import net.wintastic.wincremental.AssetLibrary;

public class BuildingTile extends Tile {

    public static enum BuildingTileType {
        TOWN_CENTER(AssetLibrary.townCenterSprite, 16),
        TENT(AssetLibrary.tentTileSprite, 0),
        STORAGE_SHED(AssetLibrary.storageShedTileSprite, 0);

        Sprite sprite;
        int radiusSize;

        BuildingTileType(Sprite sprite, int radiusSize) {
            this.sprite = sprite;
            this.radiusSize = radiusSize;
        }
    }

    BuildingTileType type;
    int radiusSize;
    public boolean selected;
    TileRadiusIndicator radiusIndicator;

    public BuildingTile(Tuple<Integer> position, BuildingTileType type) {
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

}
