package net.wintastic.wincremental.gui;


import net.wintastic.wincremental.AssetLibrary;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 31/01/2015.
 */
public class GUI {

    List<IconGrid> iconGrids;

    public GUI() {
        iconGrids = new ArrayList<IconGrid>();
        testInit();
    }

    private void testInit(){
        IconGrid testGrid = new IconGrid("test_grid");
        iconGrids.add(testGrid);
    }

    public void draw() {

        //Draw Icon Grids
        for (int i = 0; i < iconGrids.size(); i++) {
            iconGrids.get(i).draw();
        }
    }
}
