package net.wintastic.lwjgl;

import net.wintastic.lwjgl.math.GameMathHelper;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import java.util.List;

public class Pixel extends Shape2D {

    public Pixel(Vector2f position) {
        this(position, 0, new Color(1, 1, 1));
    }

    public Pixel(Vector2f position, float layerDepth, Color color) {
        this.position = position;
        this.layerDepth = layerDepth;
        this.color = color;
    }

    @Override
    public float getArea() {
        return 1;
    }

    @Override
    public boolean contains(Vector2f point) {
        return GameMathHelper.vector2fEquals(this.position, point);
    }

    @Override
    public void draw() {
        Shape2D.drawPixel(this.position, this.color);
    }

    @Override
    public List<Vector2f> getVertices() {
        return null;
    }
}
