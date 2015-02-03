package net.wintastic.wincremental.gui;

import net.wintastic.wincremental.AssetLibrary;
import net.wintastic.wincremental.GameManager;
import org.lwjgl.util.vector.Vector2f;

public class Icon {

    Vector2f position;
    Tooltip tooltip;

    public Icon(){
        this(new Vector2f(0f,0f));
    }

    public Icon(Vector2f position){
        this.position = position;
        tooltip = new Tooltip();
    }

    public void setPosition(Vector2f position){
        this.position = position;
    }

    public boolean contains(Vector2f other){
        return (other.x >= position.x && other.x <= position.x+ GameManager.iconSize && other.y >= position.y && other.y <= position.y+GameManager.iconSize);
    }

    public Tooltip getTooltip(){
        return tooltip;
    }

    public void draw(){
        AssetLibrary.testIconSprite.position = position;
        AssetLibrary.testIconSprite.draw();
    }

}
