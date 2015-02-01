package net.wintastic.wincremental.gui;


import net.wintastic.lwjgl.Input;
import net.wintastic.lwjgl.Shape2D;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

public class Tooltip {

    Vector2f position;
    String headerText;
    String bodyText;
    String footerText;

    public Tooltip(){
        this(new Vector2f(0f,0f));
    }

    public Tooltip(Vector2f position){
        this.position = position;
    }

    public void setPosition(Vector2f position){
        this.position = position;
    }

    public void draw(){
        Shape2D.drawRectangle(Input.mousePosition(), 300, 200, 0f, new Color(200, 200, 200), true);
    }

}
