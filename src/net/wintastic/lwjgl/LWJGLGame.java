package net.wintastic.lwjgl;

public interface LWJGLGame {
    void start();
    void init();
    void initGL(int width, int height, boolean fullscreen);
    void update();
    void render();
}
