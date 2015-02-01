package net.wintastic.wincremental.gui;

import net.wintastic.wincremental.AssetLibrary;
import org.lwjgl.util.vector.Vector2f;

public class Icon {

    Vector2f position;
    Tooltip tooltip;

    public Icon(){
        this(new Vector2f(0f,0f));
    }

    public Icon(Vector2f position){
        this.position = position;
        tooltip = new Tooltip(position);
    }

    public void setPosition(Vector2f position){
        this.position = position;
        tooltip.setPosition(position);
    }

    public boolean contains(Vector2f other){
        //TODO change magic numbers 16f
        return (other.x >= position.x && other.x <= position.x+16f && other.y >= position.y && other.y <= position.y+16f);
    }

    public Tooltip getTooltip(){
        return tooltip;
    }

    public void draw(){
        AssetLibrary.testIconSprite.position = position;
        AssetLibrary.testIconSprite.draw();
    }

}
