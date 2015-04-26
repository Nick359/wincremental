package net.wintastic.lwjgl;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.vector.Vector2f;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public abstract class Shape2D extends GameObject implements Drawable {
    public ReadableColor color;
    public float layerDepth;
    public boolean visible;

    public Shape2D() {
//        DrawBatch.add(this);
        visible = true;
    }

    public abstract float getArea();

    public abstract boolean contains(Vector2f point);

    public ReadableColor getColor() {
        return color;
    }

    public void setColor(ReadableColor color) {
        this.color = color;
    }

    public abstract List<Vector2f> getVertices();

    public float getLayerDepth() {
        return this.layerDepth;
    }

    public void setLayerDepth(float layerDepth) {
        this.layerDepth = layerDepth;
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

    public List<Vector2f> getAxes() {
        List<Vector2f> axes = new ArrayList<Vector2f>();
        List<Vector2f> vertices = getVertices();
        for (int i = 0; i < vertices.size(); i++) {
            Vector2f p1 = vertices.get(i);
            Vector2f p2 = vertices.get(i + 1 == vertices.size() ? 0 : i + 1);
            Vector2f edge = new Vector2f();
            Vector2f.sub(p1, p2, edge);
            edge = new Vector2f(edge.y + 0f, -edge.x + 0f);
            // TODO:fix
            //boolean flag = false;
            //for (Vector2f e : axes)
            //    if ((Math.abs(e.x) - Math.abs(edge.x) < 0.000001f && Math.abs(e.y) - Math.abs(edge.y) < 0.000001f) || (Math.abs(e.x) + Math.abs(edge.x) < 0.000001f && Math.abs(e.x) + Math.abs(edge.x) < 0.000001f))
            //        flag = true;
            //if (!flag) {
            edge.normalise(edge);
            axes.add(edge);
            //}
        }
        return axes;
    }

    public Vector2f projection(Vector2f axis) {
        float min = Vector2f.dot(axis, getVertices().get(0));
        float max = min;
        for (Vector2f v : getVertices()) {
            float p = Vector2f.dot(axis, v);
            if (p < min)
                min = p;
            else if (p > max)
                max = p;
        }
        return new Vector2f(min, max);
    }

    public static boolean collides(Shape2D s1, Shape2D s2) {
        for (Vector2f a : s1.getAxes()) {
            Vector2f p1 = s1.projection(a);
            Vector2f p2 = s2.projection(a);
            if (!(p1.x < p2.y && p1.y > p2.x))
                return false;
        }
        for (Vector2f a : s2.getAxes()) {
            Vector2f p1 = s1.projection(a);
            Vector2f p2 = s2.projection(a);
            if (!(p1.x < p2.y && p1.y > p2.x))
                return false;
        }
        return true;
    }

    public static void drawPixel(Vector2f position, ReadableColor color) {
        // set the color of the pixel (R,G,B,A)
        GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // draw pixel
        GL11.glBegin(GL11.GL_LINE_STRIP);

        GL11.glVertex2f(position.x, position.y);
        GL11.glVertex2f(position.x + 1, position.y + 1);

        GL11.glEnd();
    }

    public static void drawCircle(Vector2f position, float radius, ReadableColor color, boolean filled) {
        // set the color of the circle (R,G,B,A)
        GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glBegin(filled ? GL11.GL_TRIANGLE_FAN : GL11.GL_LINE_LOOP);

        int numSegments = (int) (360 * radius / 100);
        for (int i = 0; i < numSegments; i++) {
            float degInRad = (float) (2 * i * Math.PI / numSegments);
            GL11.glVertex2f((float) Math.cos(degInRad) * radius + position.x, (float) Math.sin(degInRad) * radius + position.y);
        }

        GL11.glEnd();
    }

    public static void drawRectangle(Vector2f position, int width, int height, float rotation, ReadableColor color, boolean filled) {
        // set the color of the quad (R,G,B,A)
        GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // draw quad
        GL11.glPushMatrix();

        GL11.glTranslatef(position.x + width / 2, position.y + height / 2, 0);
        GL11.glRotatef((float) Math.toDegrees(rotation), 0f, 0f, 1f);
        GL11.glTranslatef(-position.x - width / 2, -position.y - height / 2, 0);

        GL11.glBegin(filled ? GL11.GL_QUADS : GL11.GL_LINE_LOOP);

        GL11.glVertex2f(position.x, position.y);
        GL11.glVertex2f(position.x, position.y + height);
        GL11.glVertex2f(position.x + width, position.y + height);
        GL11.glVertex2f(position.x + width, position.y);

        GL11.glEnd();
        GL11.glPopMatrix();
    }

    public static void drawLine(Vector2f p1, Vector2f p2, ReadableColor color) {
        // set the color of the line (R,G,B,A)
        GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // draw pixel
        GL11.glBegin(GL11.GL_LINE_STRIP);

        GL11.glVertex2f(p1.x, p1.y);
        GL11.glVertex2f(p2.x + 1, p2.y + 1);

        GL11.glEnd();
    }

}
