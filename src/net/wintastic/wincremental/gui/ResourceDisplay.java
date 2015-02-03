package net.wintastic.wincremental.gui;

import net.wintastic.lwjgl.Text;
import net.wintastic.wincremental.GameManager;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

public class ResourceDisplay {

    Icon icon;
    Text text;
    Vector2f position;

    public ResourceDisplay(Vector2f position){
        this.position = position;
        icon = new Icon(position);
        text = new Text(new Vector2f(position.x + GameManager.iconSize + 6f, position.y + (GameManager.iconSize / 4) ), "Ressource Display", "Arial", 1, 16);
        text.color = Color.black;
    }

    public void update(){
        text.text = Integer.toString(GameManager.player.getGold());
    }

    public void draw(){
        icon.draw();
        text.draw();
    }
}
