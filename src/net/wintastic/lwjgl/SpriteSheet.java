package net.wintastic.lwjgl;

import org.lwjgl.opengl.GL11;

public class SpriteSheet implements Drawable {
    public final Sprite sprite;
    private final int frameWidth;
    private final int frameHeight;
    private final int frameDuration;
    private float currentFrame;
    private float frameCount;
    private int frameTimer;
    private float layerDepth;
    private boolean visible;


    public SpriteSheet(Sprite sprite, int frameWidth, int frameHeight, int frameDuration, float layerDepth) {
        this.sprite = sprite;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameDuration = frameDuration;
        this.currentFrame = 0;
        this.frameCount = sprite.getWidth() / frameWidth;
        this.frameTimer = 0;
        this.layerDepth = layerDepth;
        this.visible = true;
        //   DrawBatch.add(this);
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    public void draw() {
        if (frameTimer > frameDuration) {
            currentFrame = (currentFrame + 1) % frameCount;
            frameTimer = 0;
        }
        frameTimer++;

        sprite.color.bind();
        sprite.texture.bind();

        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glPushMatrix();

        GL11.glTranslatef(sprite.position.x + frameWidth * (currentFrame + 1f / 2), sprite.position.y + frameHeight / 2, 0);
        GL11.glRotatef((float) Math.toDegrees(sprite.rotation), 0f, 0f, 1f);
        GL11.glTranslatef(-sprite.position.x - frameWidth * (currentFrame + 1f / 2), -sprite.position.y - frameHeight / 2, 0);

        GL11.glBegin(GL11.GL_QUADS);

        float f = currentFrame / frameCount;
        float f2 = (currentFrame + 1) / frameCount;
        GL11.glTexCoord2f(sprite.getWidth() * f / Sprite.nextPowerOf2(sprite.getWidth()), 0);
        GL11.glVertex2f(sprite.position.x, sprite.position.y);

        GL11.glTexCoord2f(sprite.getWidth() * f2 / Sprite.nextPowerOf2(sprite.getWidth()), 0);
        GL11.glVertex2f(sprite.position.x + frameWidth, sprite.position.y);

        GL11.glTexCoord2f(sprite.getWidth() * f2 / Sprite.nextPowerOf2(sprite.getWidth()), sprite.getHeight() / Sprite.nextPowerOf2(sprite.getHeight()));
        GL11.glVertex2f(sprite.position.x + frameWidth, sprite.position.y + frameHeight);

        GL11.glTexCoord2f(sprite.getWidth() * f / Sprite.nextPowerOf2(sprite.getWidth()), sprite.getHeight() / Sprite.nextPowerOf2(sprite.getHeight()));
        GL11.glVertex2f(sprite.position.x, sprite.position.y + frameHeight);

        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    @Override
    public void setLayerDepth(float layerDepth) {
        this.layerDepth = layerDepth;
    }

    @Override
    public float getLayerDepth() {
        return this.layerDepth;
    }

    @Override
    public int compareTo(Drawable o) {
        if (this.layerDepth < o.getLayerDepth())
            return -1;
        else if (this.layerDepth > o.getLayerDepth())
            return 1;
        else
            return 0;
    }
}
