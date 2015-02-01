package net.wintastic.wincremental.gui;

import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Nick on 01/02/2015.
 */
public class IconGrid {

    String name;
    Vector2f position;
    List<Icon> icons;
    int verticalPadding;
    int horizontalPadding;
    int iconsPerLine;

    public IconGrid() {
        this("icon_grid", new Vector2f(0f, 0f), 4, 4, 6);
    }

    public IconGrid(String name) {
        this(name, new Vector2f(0f, 0f), 4, 4, 6);
    }

    public IconGrid(String name, Vector2f position, int verticalPadding, int horizontalPadding, int iconsPerLine) {
        this.name = name;
        this.position = position;
        this.verticalPadding = verticalPadding;
        this.horizontalPadding = horizontalPadding;
        this.iconsPerLine = iconsPerLine;
        icons = new ArrayList<Icon>();

        testInit();
    }

    private void testInit() {
        Icon testIcon1 = new Icon();
        Icon testIcon2 = new Icon();
        Icon testIcon3 = new Icon();
        Icon testIcon4 = new Icon();
        Icon testIcon5 = new Icon();
        Icon testIcon6 = new Icon();
        Icon testIcon7 = new Icon();
        Icon testIcon8 = new Icon();
        Icon testIcon9 = new Icon();
        Icon testIcon10 = new Icon();

        icons.add(testIcon1);
        icons.add(testIcon2);
        icons.add(testIcon3);
        icons.add(testIcon4);
        icons.add(testIcon5);
        icons.add(testIcon6);
        icons.add(testIcon7);
        icons.add(testIcon8);
        icons.add(testIcon9);
        icons.add(testIcon10);

        calculateIconPositions();
    }

    private void calculateIconPositions() {
        int numLines = (int) Math.ceil(icons.size() / iconsPerLine);

        boolean iconsNotPlaced = true;
        int i = 0;
        int vertDisplacement = 0;
        int horizDisplacement = 0;
        while (iconsNotPlaced) {
            icons.get(i).position = new Vector2f(position.x + (horizDisplacement + 1) * (horizontalPadding + 16f), position.y + (vertDisplacement + 1) * (verticalPadding + 16f));

            if (i >= icons.size() - 1) {
                iconsNotPlaced = false;
            }

            i++;
            horizDisplacement++;
            if (i == iconsPerLine - 1) {
                vertDisplacement++;
                horizDisplacement = 0;
            }
        }
    }

    public void draw() {
        //Draw Icon Grids
        for (int i = 0; i < icons.size(); i++) {
            icons.get(i).draw();
        }
    }
}
