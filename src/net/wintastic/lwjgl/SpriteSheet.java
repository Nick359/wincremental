package net.wintastic.lwjgl;

import org.lwjgl.opengl.GL11;

public class SpriteSheet implements Drawable {
    public final Sprite sprite;
    private final int frameWidth;
    private final int frameHeight;
    private final int framesCount;
    private final int frameDuration;
    private int currentFrame;
    private int frameTimer;
    private float layerDepth;
    private boolean visible;


    public SpriteSheet(Sprite sprite, int frameWidth, int frameHeight, int framesCount, int frameDuration, float layerDepth) {
        this.sprite = sprite;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.framesCount = framesCount;
        this.frameDuration = frameDuration;
        this.currentFrame = 0;
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
            currentFrame = (currentFrame + 1) % framesCount;
            frameTimer = 0;
        }
        frameTimer++;

        sprite.color.bind();
        sprite.texture.bind();

        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glPushMatrix();

        GL11.glTranslatef(sprite.position.x + frameWidth / 2f, sprite.position.y + frameHeight / 2f, 0);
        GL11.glRotatef((float) Math.toDegrees(sprite.rotation), 0f, 0f, 1f);
        GL11.glTranslatef(-sprite.position.x - frameWidth / 2f, -sprite.position.y - frameHeight / 2f, 0);

        GL11.glBegin(GL11.GL_QUADS);

        float fH = (currentFrame % ((int) sprite.getWidth() / frameWidth)) / (sprite.getWidth() / frameWidth);
        float fH2 = fH + 1 / (sprite.getWidth() / frameWidth);
        float fV = (currentFrame / ((int) sprite.getWidth() / frameHeight)) / (sprite.getHeight() / frameHeight);
        float fV2 = fV + 1 / (sprite.getHeight() / frameHeight);
        System.out.println(fV);

        GL11.glTexCoord2f(sprite.getWidth() * fH / Sprite.nextPowerOf2(sprite.getWidth()), sprite.getHeight() * fV / Sprite.nextPowerOf2(sprite.getHeight()));
        GL11.glVertex2f(sprite.position.x, sprite.position.y);

        GL11.glTexCoord2f(sprite.getWidth() * fH2 / Sprite.nextPowerOf2(sprite.getWidth()), sprite.getHeight() * fV / Sprite.nextPowerOf2(sprite.getHeight()));
        GL11.glVertex2f(sprite.position.x + frameWidth, sprite.position.y);

        GL11.glTexCoord2f(sprite.getWidth() * fH2 / Sprite.nextPowerOf2(sprite.getWidth()), sprite.getHeight() * fV2 / Sprite.nextPowerOf2(sprite.getHeight()));
        GL11.glVertex2f(sprite.position.x + frameWidth, sprite.position.y + frameHeight);

        GL11.glTexCoord2f(sprite.getWidth() * fH / Sprite.nextPowerOf2(sprite.getWidth()), sprite.getHeight() * fV2 / Sprite.nextPowerOf2(sprite.getHeight()));
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
