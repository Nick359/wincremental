package net.wintastic.wincremental.gui;

import net.wintastic.wincremental.AssetLibrary;
import org.lwjgl.util.vector.Vector2f;

public class Icon {

    Vector2f position;

    public Icon(){

    }

    public Icon(Vector2f position){
        this.position = position;
    }

    public void draw(){
        AssetLibrary.testIconSprite.position = position;
        AssetLibrary.testIconSprite.draw();
    }
}
