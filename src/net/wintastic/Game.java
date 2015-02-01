package net.wintastic;

import net.wintastic.lwjgl.Input;
import net.wintastic.lwjgl.LWJGLGame;
import net.wintastic.wincremental.GameManager;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Game implements LWJGLGame {
    public static int resX = 1280;
    public static int resY = 720;
    boolean fullscreen = false;

    long fps;
    long lastFPS;

    GameManager gameManager = new GameManager();

    @Override
    public void start() {
        lastFPS = getTime();
        initGL(resX, resY, fullscreen);
        init();

        while (true) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            update();
            render();

            Display.update();
            Display.sync(60);

            if (Display.isCloseRequested()) {
                Display.destroy();
                AL.destroy();
                System.exit(0);
            }
        }
    }

    @Override
    public void init() {
        gameManager.init();
    }

    @Override
    public void initGL(int width, int height, boolean fullscreen) {
        try {
            setDisplayMode(width, height, fullscreen);
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 1, -1);
//        GL11.glMatrixMode(GL11.GL_MODELVIEW);

//        GL11.glEnable(GL11.GL_POINT_SMOOTH);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void setDisplayMode(int width, int height, boolean fullscreen) {
        try {
            DisplayMode targetDisplayMode = null;

            if (fullscreen) {
                DisplayMode[] modes = Display.getAvailableDisplayModes();
                int freq = 0;

                for (int i = 0; i < modes.length; i++) {
                    DisplayMode current = modes[i];

                    if ((current.getWidth() == width) && (current.getHeight() == height)) {
                        if (targetDisplayMode == null || current.getFrequency() >= freq && current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel()) {
                            targetDisplayMode = current;
                            freq = targetDisplayMode.getFrequency();
                        }

                        if (current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel() && current.getFrequency() == Display.getDesktopDisplayMode().getFrequency()) {
                            targetDisplayMode = current;
                            break;
                        }
                    }
                }
            } else {
                targetDisplayMode = new DisplayMode(width, height);
            }

            if (targetDisplayMode == null) {
                System.out.println("Failed to find value mode: " + width + "x" + height + " fs=" + fullscreen);
                return;
            }

            Display.setDisplayMode(targetDisplayMode);
            Display.setFullscreen(fullscreen);
            Display.setVSyncEnabled(true);

        } catch (LWJGLException e) {
            System.out.println("Unable to setup mode " + width + "x" + height + " fullscreen=" + fullscreen + e);
        }
    }

    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    @Override
    public void update() {
        if (Input.isKeyPressed(Keyboard.KEY_ESCAPE)) {
            Display.destroy();
            AL.destroy();
            System.exit(0);
        }
        updateFPS();

        gameManager.update();
    }

    @Override
    public void render() {
        gameManager.draw();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
