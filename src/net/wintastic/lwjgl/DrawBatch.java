package net.wintastic.lwjgl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class DrawBatch {
    private static List<Drawable> l = new ArrayList<Drawable>();

    public static void add(Drawable d) {
        if (l.contains(d)) return;
        l.add(d);
    }

    public static void remove(Drawable d) {
        l.remove(d);
    }

    public static void draw() {
        Collections.sort(l);
        for (Drawable d : l) {
            if (d.isVisible())
                d.draw();
        }
    }
}
