package net.wintastic.wincremental.gui;

import net.wintastic.lwjgl.Input;
import net.wintastic.lwjgl.Shape2D;
import net.wintastic.lwjgl.Text;
import net.wintastic.wincremental.GameManager;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

public class Tooltip {

    Icon parentIcon;
    Text headerText;
    Text bodyText;
    Text footerText;

    public Tooltip(Icon parentIcon) {
        this.parentIcon = parentIcon;
        init();
    }

    private void init() {
        switch (parentIcon.type) {
            case TEST:
                headerText = new Text(new Vector2f(0f, 0f), "Test Header", "Arial", 1, 16);
                bodyText = new Text(new Vector2f(0f, 0f), "Test Body", "Arial", 0, 12);
                footerText = new Text(new Vector2f(0f, 0f), "Test Footer", "Arial", 2, 12);
                break;
            case TENT:
                headerText = new Text(new Vector2f(0f, 0f), "Simple Tent", "Arial", 1, 16);
                bodyText = new Text(new Vector2f(0f, 0f), "-10 WOOD     +3 POPULATION", "Arial", 0, 12);
                footerText = new Text(new Vector2f(0f, 0f), "There's nothing like a good tent to get naked in.", "Arial", 2, 12);
                break;
            case WOOD:
                headerText = new Text(new Vector2f(0f, 0f), "Wood", "Arial", 1, 16);
                bodyText = new Text(new Vector2f(0f, 0f), "You have " + GameManager.player.getWood() + " wood.", "Arial", 0, 12);
                footerText = new Text(new Vector2f(0f, 0f), "Named after suburban streets.", "Arial", 2, 12);
                break;
            case GOLD:
                headerText = new Text(new Vector2f(0f, 0f), "Gold", "Arial", 1, 16);
                bodyText = new Text(new Vector2f(0f, 0f), "You have " + GameManager.player.getGold() + " gold.", "Arial", 0, 12);
                footerText = new Text(new Vector2f(0f, 0f), "It is health that is real wealth and not pieces of gold and silver - Poor person", "Arial", 2, 12, org.newdawn.slick.Color.white, 256, 0f);
                break;
        }
    }

    public void update(){
        switch (parentIcon.type) {
            case TEST:
                break;
            case TENT:
                break;
            case WOOD:
                bodyText.setText("You have " + GameManager.player.getWood() + " wood.");
                break;
            case GOLD:
                bodyText.setText("You have " + GameManager.player.getGold() + " gold.");
                break;
        }
    }

    public void draw() {
        Shape2D.drawRectangle(Input.mousePosition(), 300, 200, 0f, new Color(200, 200, 200), true);
        headerText.position = new Vector2f(Input.mousePosition().x + 12f, Input.mousePosition().y + 6f);
        bodyText.position = new Vector2f(Input.mousePosition().x + 12f, Input.mousePosition().y + 32f);
        footerText.position = new Vector2f(Input.mousePosition().x + 12f, Input.mousePosition().y + 128f);
        headerText.draw();
        bodyText.draw();
        footerText.draw();
    }

}
