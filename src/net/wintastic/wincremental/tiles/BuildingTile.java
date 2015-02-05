package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.Sprite;
import net.wintastic.lwjgl.Tuple;
import net.wintastic.wincremental.AssetLibrary;

public class BuildingTile extends Tile {

    public static enum BuildingTileType {
        TENT(AssetLibrary.tentTileSprite),
        STORAGE_SHED(AssetLibrary.storageShedTileSprite);

        Sprite sprite;

        BuildingTileType(Sprite sprite) {
            this.sprite = sprite;
        }

//        void clickAction() {
//
//        }
    }

    BuildingTileType type;

    public BuildingTile(Tuple<Integer> position, BuildingTileType type) {
        super(position);
        this.type = type;
    }

    @Override
    public void clickAction() {

    }

    @Override
    protected Sprite getSprite() {
        return type.sprite;
    }

}
