package net.wintastic.lwjgl;

import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.vector.Vector2f;

public class BoundingBox {

    public Vector2f position;
    public int width;
    public int height;

    public BoundingBox(Vector2f position, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public boolean contains(Vector2f point) {
        return point.x >= this.position.x && point.x <= this.position.x + this.width &&
                point.y >= this.position.y && point.y <= this.position.y + this.height;
    }

    public boolean intersects(BoundingBox other) {
        return !(other.position.x > this.position.x + this.width
                || other.position.x + other.width < this.position.x
                || other.position.y > this.position.y + this.height
                || other.position.y + other.height < this.position.y);
    }

    public void draw(ReadableColor color) {
        Shape2D.drawRectangle(position, width, height, 0.0f, color, false);
    }

    public static BoundingBox generateBoundingBox(Sprite sprite) {
        return generateBoundingBox(new Sprite[]{sprite});
    }

    public static BoundingBox generateBoundingBox(Sprite[] sprites) {
        BoundingBox box = new BoundingBox(sprites[sprites.length - 1].position, (int) sprites[sprites.length - 1].getWidth(), (int) sprites[sprites.length - 1].getHeight());
        box.height = (int) (sprites[0].position.y + sprites[0].getHeight() - sprites[sprites.length - 1].position.y);
        return box;
    }


}
