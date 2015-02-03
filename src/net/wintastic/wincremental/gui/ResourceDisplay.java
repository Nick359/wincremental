package net.wintastic.wincremental.gui;

import net.wintastic.lwjgl.Text;
import net.wintastic.wincremental.GameManager;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

public class ResourceDisplay {

    Icon icon;
    Text text;
    Vector2f position;

    public ResourceDisplay(Icon.IconType type, Vector2f position) {
        this.position = position;
        icon = new Icon(type, position);
        text = new Text(new Vector2f(position.x + GameManager.iconSize + 6f, position.y + (GameManager.iconSize / 4)), "Resource_Display", "Arial", 1, 16);
        text.color = Color.black;
    }

    public void update() {
        switch (icon.type){
            case TEST:
                break;
            case GOLD:
                text.text = Integer.toString(GameManager.player.getGold());
                break;
            case WOOD:
                text.text = Integer.toString(GameManager.player.getWood());
                break;
        }
    }

    public void draw() {
        icon.draw();
        text.draw();
    }
}
