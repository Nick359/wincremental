package net.wintastic.lwjgl;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;

public class Sound {
    public Audio effect;
    public boolean loop;

    public Sound(String path) {
        try {
            this.effect = AudioLoader.getAudio(getFileType(path), ResourceLoader.getResourceAsStream(path));
            this.loop = false;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static String getFileType(String path) {
        return path.substring(path.length() - 3).toUpperCase();
    }

    public void play() {
        this.effect.playAsSoundEffect(1.0f, 0.5f, loop, 0, 0, 0);
    }

    public void play(Vector3f position) {
        this.effect.playAsSoundEffect(1.0f, 0.5f, loop, position.x, position.y, position.z);
    }

    public void play(Vector3f position, float pitch, float gain) {
        this.effect.playAsSoundEffect(pitch, gain, loop, position.x, position.y, position.z);
    }

    public boolean isPlaying() {
        return this.effect.isPlaying();
    }

    public void stop() {
        this.effect.stop();
    }
}
