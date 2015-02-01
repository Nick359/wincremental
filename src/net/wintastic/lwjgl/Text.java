package net.wintastic.lwjgl;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

public class Text extends GameObject implements Drawable {
    public Font awtFont;
    public TrueTypeFont font;
    public String text;
    public Color color;
    public float layerDepth;
    public boolean visible;

    //TODO: Maximum font size 64??
    public Text(Vector2f position, String text) {
        this(position, text, "Times New Roman", Font.PLAIN, 64);
    }

    public Text(Vector2f position, String text, String fontName, int fontStyle, int fontSize) {
        this(position, text, fontName, fontStyle, fontSize, Color.white);
    }

    public Text(Vector2f position, String text, String fontName, int fontStyle, int fontSize, Color color) {
        this(position, text, fontName, fontStyle, fontSize, color, 1);
    }

    public Text(Vector2f position, String text, String fontName, int fontStyle, int fontSize, Color color, float layerDepth) {
        this.position = position;
        this.text = text;
        this.awtFont = new Font(fontName, fontStyle, fontSize);
        this.font = new TrueTypeFont(awtFont, true);
        this.color = color;
        this.layerDepth = layerDepth;
        this.visible = true;

        DrawBatch.add(this);
    }

    public int getWidth() {
        return font.getWidth(text);
    }

    public int getHeight() {
        return font.getHeight();
    }

    public void setFontSize(int s) {
        this.awtFont = new Font(awtFont.getName(), awtFont.getStyle(), s);
        this.font = new TrueTypeFont(awtFont, true);
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
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        this.font.drawString(this.position.x, this.position.y, this.text, this.color);
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

    @Override
    public boolean isVisible() {
        return visible;
    }
}
