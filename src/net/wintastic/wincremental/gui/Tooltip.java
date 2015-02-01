package net.wintastic.wincremental.gui;


import net.wintastic.lwjgl.Input;
import net.wintastic.lwjgl.Shape2D;
import net.wintastic.lwjgl.Text;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

public class Tooltip {

    Text headerText;
    Text bodyText;
    Text footerText;

    public Tooltip(){
        headerText = new Text(new Vector2f(0f, 0f), "Test Header", "Arial", 1, 16);
        bodyText = new Text(new Vector2f(0f, 0f), "Test Body", "Arial", 0, 12);
        footerText = new Text(new Vector2f(0f, 0f), "Test Footer", "Arial", 2, 12);
    }

    public void draw(){
        Shape2D.drawRectangle(Input.mousePosition(), 300, 200, 0f, new Color(200, 200, 200), true);
        headerText.position =new Vector2f(Input.mousePosition().x + 12f, Input.mousePosition().y+6f );
        bodyText.position =new Vector2f(Input.mousePosition().x + 12f, Input.mousePosition().y+32f );
        footerText.position =new Vector2f(Input.mousePosition().x + 12f, Input.mousePosition().y+128f );
        headerText.draw();
        bodyText.draw();
        footerText.draw();
    }

}