package net.wintastic.wincremental.gui;

public class GUI {

    Toolbar toolbar;
    MenuBar menuBar;

    public GUI() {
        toolbar = new Toolbar();
        menuBar = new MenuBar();
    }

    public void update() {
        toolbar.update();
    }

    public void draw() {
        menuBar.draw();
        toolbar.draw();
    }
}
