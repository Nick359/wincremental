package net.wintastic.lwjgl;

import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Rectangle extends Shape2D {

    public int width;
    public int height;

    /**
     * A Rectangle object is used for a simple reusable rectangle.
     *
     * @param position The position of the top left corner
     * @param width    The width of the rectangle
     * @param height   The height of the rectangle
     */
    public Rectangle(Vector2f position, int width, int height) {
        this(position, width, height, 0);
    }

    /**
     * A Rectangle object is used for a simple reusable rectangle.
     *
     * @param position   The position of the top left corner
     * @param width      The width of the rectangle
     * @param height     The height of the rectangle
     * @param layerDepth The layer at which to draw the rectangle
     */
    public Rectangle(Vector2f position, int width, int height, float layerDepth) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.layerDepth = layerDepth;
        this.color = Color.WHITE;
    }

    @Override
    public void draw() {
        Shape2D.drawRectangle(this.position, this.width, this.height, this.rotation, this.color, true);
    }

    @Override
    public float getArea() {
        return this.width * this.height;
    }

    @Override
    public boolean contains(Vector2f point) {
        return point.x >= this.position.x && point.x <= this.position.x + this.width &&
                point.y >= this.position.y && point.y <= this.position.y + this.height;
    }

    @Override
    public List<Vector2f> getVertices() {
        List<Vector2f> v = new ArrayList<Vector2f>();
        Vector2f center = new Vector2f(this.position.x + this.width / 2, this.position.y + this.height / 2);
        Vector2f point = this.position;
        v.add(new Vector2f((float) ((point.x - center.x) * Math.cos(this.rotation) - (point.y - center.y) * Math.sin(this.rotation) + center.x),
                (float) ((point.y - center.y) * Math.cos(this.rotation) + (point.x - center.x) * Math.sin(this.rotation) + center.y)));
        point = new Vector2f(this.position.x + this.width, this.position.y);
        v.add(new Vector2f((float) ((point.x - center.x) * Math.cos(this.rotation) - (point.y - center.y) * Math.sin(this.rotation) + center.x),
                (float) ((point.y - center.y) * Math.cos(this.rotation) + (point.x - center.x) * Math.sin(this.rotation) + center.y)));
        point = new Vector2f(this.position.x + this.width, this.position.y + this.height);
        v.add(new Vector2f((float) ((point.x - center.x) * Math.cos(this.rotation) - (point.y - center.y) * Math.sin(this.rotation) + center.x),
                (float) ((point.y - center.y) * Math.cos(this.rotation) + (point.x - center.x) * Math.sin(this.rotation) + center.y)));
        point = new Vector2f(this.position.x, this.position.y + this.height);
        v.add(new Vector2f((float) ((point.x - center.x) * Math.cos(this.rotation) - (point.y - center.y) * Math.sin(this.rotation) + center.x),
                (float) ((point.y - center.y) * Math.cos(this.rotation) + (point.x - center.x) * Math.sin(this.rotation) + center.y)));
        return v;
    }
}
