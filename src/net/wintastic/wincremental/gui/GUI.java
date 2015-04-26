package net.wintastic.wincremental.gui;

public class GUI {

    private final Toolbar toolbar;
    private final MenuBar menuBar;

    public GUI() {
        toolbar = new Toolbar();
        menuBar = new MenuBar();
    }

    public void update() {
        toolbar.update();
        menuBar.update();
    }

    public void draw() {
        menuBar.draw();
        toolbar.draw();
    }
}
