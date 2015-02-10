package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.Pair;
import net.wintastic.lwjgl.Sprite;
import net.wintastic.wincremental.AssetLibrary;

public class BuildingTile extends Tile {

    public static enum BuildingTileType {
        TOWN_CENTER(AssetLibrary.townCenterSprite, 16),
        TENT(AssetLibrary.tentTileSprite, 0),
        STORAGE_SHED(AssetLibrary.storageShedTileSprite, 0);

        private final Sprite sprite;
        private int radiusSize;

        BuildingTileType(Sprite sprite, int radiusSize) {
            this.sprite = sprite;
            this.radiusSize = radiusSize;
        }
    }

    private BuildingTileType type;
    private boolean selected;
    private TileRadiusIndicator radiusIndicator;

    public BuildingTile(Pair<Integer> position, BuildingTileType type) {
        super(position);
        this.type = type;
        this.selected = false;
        radiusIndicator = new TileRadiusIndicator(type.radiusSize, this);
    }

    @Override
    public void clickAction() {
        selected = true;
        Board.setSelectedTile(this);

        if (type.radiusSize > 0)
            radiusIndicator.visible = true;
    }

    public void toggleSelected() {
        selected = !selected;
        radiusIndicator.visible = false;
    }

    @Override
    protected Sprite getSprite() {
        return type.sprite;
    }

}
