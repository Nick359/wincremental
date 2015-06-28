package net.wintastic.lwjgl;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public abstract class Input {
    private static final List<Integer> pressedKeys = new ArrayList<Integer>();
    private static final List<Integer> pressedButtons = new ArrayList<Integer>();

    public static boolean isKeyDown(int key) {
        return Keyboard.isKeyDown(key);
    }

    public static boolean isKeyUp(int key) {
        return !Keyboard.isKeyDown(key);
    }

    public static boolean isKeyPressed(int key) {
        if (isKeyUp(key)) {
            pressedKeys.remove((Integer) key);
            return false;
        }
        if (pressedKeys.contains(key)) {
            return false;
        }
        pressedKeys.add(key);
        return true;
    }

    public static boolean wasKeyDown(int key) {
        return pressedKeys.contains(key);
    }

    public static boolean isButtonDown(int button) {
        return Mouse.isButtonDown(button);
    }

    public static boolean isButtonUp(int button) {
        return !Mouse.isButtonDown(button);
    }

    public static boolean isButtonPressed(int button) {
        if (isButtonUp(button)) {
            pressedButtons.remove((Integer) button);
            return false;
        }
        if (pressedButtons.contains(button)) {
            return false;
        }
        pressedButtons.add(button);
        return true;
    }

    public static boolean wasButtonDown(int button) {
        return pressedButtons.contains(button);
    }

    public static int scrollWheel() {
        int value = Mouse.getDWheel();
        return value > 0 ? 1 : value < 0 ? -1 : 0;
    }

    public static Vector2f mousePosition() {
        return new Vector2f(Mouse.getX(), Display.getHeight() - Mouse.getY());
    }

    public static void setMousePosition(int x, int y) {
        Mouse.setCursorPosition(x, Display.getHeight() - y);
    }

    public static void moveMousePosition(int x, int y) {
        Mouse.setCursorPosition(Mouse.getX() + x, Mouse.getY() - y);
    }

    public static void setMouseVisible(boolean showMouse) {
        Mouse.setGrabbed(!showMouse);
    }
}
