package net.wintastic.wincremental.gui;

import net.wintastic.lwjgl.Shape2D;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Toolbar {

    List<ResourceDisplay> resourceDisplays;

    public Toolbar(){
        resourceDisplays = new ArrayList<ResourceDisplay>();
        testInit();
    }

    private void testInit(){
        ResourceDisplay resDisplay1 = new ResourceDisplay(new Vector2f(0f,0f));
        resourceDisplays.add(resDisplay1);
    }

    public void update(){
        for (int i = 0; i < resourceDisplays.size(); i++){
            resourceDisplays.get(i).update();
        }
    }

    public void draw(){
        Shape2D.drawRectangle(new Vector2f(0f, 0f), 1280, 16, 0f, new Color(255, 222, 173), true);

        for (int i = 0; i < resourceDisplays.size(); i++){
            resourceDisplays.get(i).draw();
        }
    }
}
