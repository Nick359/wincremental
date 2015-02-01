package net.wintastic.wincremental.gui;

import java.util.ArrayList;
import java.util.List;

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
