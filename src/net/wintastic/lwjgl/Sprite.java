package net.wintastic.lwjgl;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;

public class Sprite implements Drawable {
    public Texture texture;
    public Vector2f position;
    public float rotation;
    public Vector2f center;
    public Color color;
    public float scaleX;
    public float scaleY;
    public float layerDepth;
    public boolean visible;

    public Sprite(String path) {
        this(path, new Vector2f(0, 0));
    }

    public Sprite(String path, Vector2f position) {
        this(path, position, null, 1.0f, 1.0f);
    }

    public Sprite(String path, Vector2f position, Vector2f center, float scaleX, float scaleY) {
        this(path, position, 0f, center, scaleX, scaleY, 0);
    }

    public Sprite(String path, Vector2f position, float rotation, Vector2f center, float scaleX, float scaleY, float layerDepth) {
        try {
            texture = TextureLoader.getTexture(getFileType(path), ResourceLoader.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        this.position = position;
        this.rotation = rotation;
        this.center = center;
        if (this.center == null)
            this.center = new Vector2f(texture.getImageWidth() / 2, texture.getImageHeight() / 2);
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.layerDepth = layerDepth;
        this.color = Color.white;
        this.visible = true;

     //   DrawBatch.add(this);
    }

    public static String getFileType(String path) {
        return path.substring(path.length() - 3);
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    public void draw() {
        color.bind();
        this.texture.bind();

        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glPushMatrix();

        GL11.glTranslatef(position.x + getWidth() / 2, position.y + getHeight() / 2, 0);
        GL11.glRotatef((float) Math.toDegrees(rotation), 0f, 0f, 1f);
        GL11.glTranslatef(-position.x - getWidth() / 2, -position.y - getHeight() / 2, 0);

        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(position.x, position.y);

        GL11.glTexCoord2f(texture.getImageWidth() / nextPowerOf2(texture.getImageWidth()), 0);
        GL11.glVertex2f(position.x + getWidth(), position.y);

        GL11.glTexCoord2f(texture.getImageWidth() / nextPowerOf2(texture.getImageWidth()), texture.getImageHeight() / nextPowerOf2(texture.getImageHeight()));
        GL11.glVertex2f(position.x + getWidth(), position.y + getHeight());

        GL11.glTexCoord2f(0, texture.getImageHeight() / nextPowerOf2(texture.getImageHeight()));
        GL11.glVertex2f(position.x, position.y + getHeight());

        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    public static float nextPowerOf2(float n) {
        return (float) Math.pow(2, 32 - Integer.numberOfLeadingZeros((int) n - 1));
    }

    public float getWidth() {
        return texture.getImageWidth() * scaleX;
    }

    public float getHeight() {
        return texture.getImageHeight() * scaleY;
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
    public int compareTo(Drawable o) {
        if (this.layerDepth < o.getLayerDepth())
            return -1;
        else if (this.layerDepth > o.getLayerDepth())
            return 1;
        else
            return 0;
    }
}
