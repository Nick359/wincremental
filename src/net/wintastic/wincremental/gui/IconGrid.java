package net.wintastic.wincremental.gui;

import net.wintastic.lwjgl.Input;
import net.wintastic.wincremental.GameManager;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class IconGrid {

    public static enum GridType {
        TEST, BUILDING
    }

    GridType type;
    Vector2f position;
    List<Icon> icons;
    int verticalPadding;
    int horizontalPadding;
    int iconsPerLine;

    public IconGrid() {
        this(GridType.TEST, new Vector2f(0f, 0f), 4, 4, 6);
    }

    public IconGrid(GridType type) {
        this(type, new Vector2f(0f, 0f), 4, 4, 6);
    }

    public IconGrid(GridType type, Vector2f position, int verticalPadding, int horizontalPadding, int iconsPerLine) {
        this.type = type;
        this.position = position;
        this.verticalPadding = verticalPadding;
        this.horizontalPadding = horizontalPadding;
        this.iconsPerLine = iconsPerLine;
        icons = new ArrayList<Icon>();

        init();
    }

    private void init() {

        switch (type) {
            case TEST:
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
                break;
            case BUILDING:
                Icon tentIcon = new Icon(Icon.IconType.TENT, 0.1f, true);
                icons.add(tentIcon);
                Icon storageShedIcon = new Icon(Icon.IconType.STORAGE_SHED, 0.1f, true);
                icons.add(storageShedIcon);
                break;
        }
        calculateIconPositions();
    }

    private void calculateIconPositions() {
        boolean iconsNotPlaced = true;
        int i = 0;
        int vertDisplacement = 0;
        int horizDisplacement = 0;
        while (iconsNotPlaced) {
            icons.get(i).setPosition(new Vector2f(position.x + (horizDisplacement + 1) * (horizontalPadding + GameManager.iconSize), position.y + (vertDisplacement + 1) * (verticalPadding + GameManager.iconSize)));

            if (i >= icons.size() - 1) {
                iconsNotPlaced = false;
            }

            i++;
            horizDisplacement++;
            if (i == iconsPerLine) {
                vertDisplacement++;
                horizDisplacement = 0;
            }
        }
    }

    public Icon getSelectedIcon() {
        for (int i = 0; i < icons.size(); i++) {
            if (icons.get(i).contains(Input.mousePosition())) {
                icons.get(i).selected = true;
                return icons.get(i);
            }
        }
        return null;
    }
}
