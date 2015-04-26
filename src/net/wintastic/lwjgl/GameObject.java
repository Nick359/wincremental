package net.wintastic.lwjgl;

import org.lwjgl.util.vector.Vector2f;

public class GameObject {
    public Vector2f position;
    public float rotation;

    public GameObject() {
        this.position = new Vector2f(0,0);
        this.rotation = 0.0f;
    }
}
