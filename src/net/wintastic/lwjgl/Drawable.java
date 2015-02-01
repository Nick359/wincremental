package net.wintastic.lwjgl;

public interface Drawable extends Comparable<Drawable> {
    public void setLayerDepth(float layerDepth);
    /**
     * The layer to draw the object, between 0 and 1, 0 being the back layer and 1 the front layer
     */
    public float getLayerDepth();
    public void draw();
    public boolean isVisible();
}
