package net.wintastic.lwjgl;

import net.wintastic.lwjgl.math.GameMathHelper;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import java.util.List;

public class Circle extends Shape2D {
    public float radius;
    public boolean filled;

    /**
     * A Circle object is used for a simple reusable circle
     *
     * @param position The position of the center of the circle
     * @param radius   The radius of the circle
     * @param filled   Indicates if the circle should be filled or only an outline
     */
    public Circle(Vector2f position, float radius, boolean filled) {
        this(position, radius, filled, 0);
    }

    /**
     * A Circle object is used for a simple reusable circle
     *
     * @param position   The position of the center of the circle
     * @param radius     The radius of the circle
     * @param filled     Indicates if the circle should be filled or only an outline
     * @param layerDepth The layer at which to draw the circle
     */
    public Circle(Vector2f position, float radius, boolean filled, float layerDepth) {
        this.position = position;
        this.radius = radius;
        this.filled = filled;
        this.layerDepth = layerDepth;
        this.color = Color.WHITE;
    }

    @Override
    public float getArea() {
        return (float) (Math.PI * radius * radius);
    }

    @Override
    public boolean contains(Vector2f point) {
        return GameMathHelper.getDistance(position, point) < radius;
    }

    @Override
    public void draw() {
        Shape2D.drawCircle(this.position, this.radius, this.color, this.filled);
    }


    @Override
    public List<Vector2f> getVertices() {
        return null;
    }
}
