package net.wintastic.lwjgl;

import org.lwjgl.util.vector.Vector2f;

public class GameObject {
    public Vector2f position;
    public float rotation;
    public Vector2f velocity;

    public GameObject() {
        this.position = new Vector2f(0,0);
        this.rotation = 0.0f;
        this.velocity = new Vector2f(0,0);
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }
}
