package net.wintastic.lwjgl;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Text extends GameObject implements Drawable {
    public Font awtFont;
    public TrueTypeFont font;
    public String text;
    public Color color;
    public int maxWidth; //TODO: Implement
    public float layerDepth;
    public boolean visible;

    private List<String> lines;

    //TODO: Maximum font size 64??
    public Text(Vector2f position, String text) {
        this(position, text, "Times New Roman", Font.PLAIN, 64);
    }

    public Text(Vector2f position, String text, String fontName, int fontStyle, int fontSize) {
        this(position, text, fontName, fontStyle, fontSize, Color.white);
    }

    public Text(Vector2f position, String text, String fontName, int fontStyle, int fontSize, Color color) {
        this(position, text, fontName, fontStyle, fontSize, color, 0, 1);
    }

    /**
     * @param position   The position of the top left corner of the text
     * @param text       The text contents
     * @param fontName   The font name of the text (ex. "Times New Roman")
     * @param fontStyle  The font style (ex. Font.PLAIN)
     * @param fontSize   The size of the text
     * @param color      The color of the text (ex. Color.white)
     * @param maxWidth   The max width the text is allowed to go before breaking on a new line
     * @param layerDepth The depth at which the text will be drawn, between 0 and 1
     */
    public Text(Vector2f position, String text, String fontName, int fontStyle, int fontSize, Color color, int maxWidth, float layerDepth) {
        this.position = position;
        this.text = text;
        this.awtFont = new Font(fontName, fontStyle, fontSize);
        this.font = new TrueTypeFont(awtFont, true);
        this.color = color;
        this.maxWidth = maxWidth;
        this.layerDepth = layerDepth;
        this.visible = true;

        this.lines = calculateLines(this.text);

        DrawBatch.add(this);
    }

    private List<String> calculateLines(String text) {
        List<String> lines = new ArrayList<String>();
        if (maxWidth == 0 || font.getWidth(text) <= maxWidth) {
            lines.add(text);
        } else {
            String[] words = text.split(" ");
            for (int i = 0; i < words.length; ) {
                String line = "";
                while (i < words.length && font.getWidth(line + words[i]) <= maxWidth) {
                    line += words[i++] + " ";
                }
                lines.add(line);
            }
        }
        return lines;
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
        for (int i = 0; i < lines.size(); i++) {
            this.font.drawString(this.position.x, this.position.y + this.getHeight() * i, this.lines.get(i), this.color);

        }
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
