package net.wintastic.wincremental.tiles;

import net.wintastic.lwjgl.Sprite;
import net.wintastic.wincremental.AssetLibrary;
import net.wintastic.wincremental.GameManager;
import net.wintastic.wincremental.Player;
import org.lwjgl.util.vector.Vector2f;

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

    public BuildingTile(Vector2f position, BuildingTileType type) {
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
