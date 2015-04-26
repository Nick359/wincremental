package net.wintastic.wincremental.gui;

import net.wintastic.lwjgl.DrawBatch;
import net.wintastic.lwjgl.Drawable;
import net.wintastic.lwjgl.Text;
import net.wintastic.wincremental.GameManager;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import java.awt.*;

public class Clock implements Drawable {
    private boolean visible;
    private float layerDepth;
    private long startTime;
    private final Text timeText;

    public Clock(Vector2f position) {
        this.visible = true;
        this.layerDepth = 0.5f;
        this.startTime = System.currentTimeMillis();
        this.timeText = new Text(position, "", "Times New Roman", Font.PLAIN, 18, Color.black);

        DrawBatch.add(this);
    }

    private String formatTime(long time) {
        long ms = time % 1000;
        long s = (time / 1000) % 60;
        long m = (time / 1000 / 60) % 60;
        long h = (time / 1000 / 60 / 60) % 24;
        long d = (time / 1000 / 60 / 60 / 24);
        String daysString = d > 1 ? "%1$d days, " : (d > 0 ? "%1$d day, " : "");
        return String.format(daysString + "%2$d:%3$02d:%4$02d.%5$03d", d, h, m, s, ms);
    }

    @Override
    public void setLayerDepth(float layerDepth) {
        this.layerDepth = layerDepth;
    }

    @Override
    public float getLayerDepth() {
        return this.layerDepth;
    }

    @Override
    public void draw() {
        this.timeText.setText(formatTime(System.currentTimeMillis() - startTime));
        this.timeText.position = new Vector2f(GameManager.resX - timeText.getWidth() - 4, (GameManager.toolbarHeight - timeText.getHeight()) / 2);
        this.timeText.draw();
    }

    @Override
    public boolean isVisible() {
        return this.visible;
    }

    @Override
    public int compareTo(Drawable o) {
        if (this.layerDepth < o.getLayerDepth())
            return -1;
        else if (this.layerDepth > o.getLayerDepth())
            return 1;
        else
            return 0;
    }
}
