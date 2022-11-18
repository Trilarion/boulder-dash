package boulderdash.helpers;

import boulderdash.bridges.SoundJLayerBridge;

import java.io.InputStream;
import java.util.HashMap;

/**
 * AudioLoadHelper
 * <p>
 * Manages audio
 */
public class AudioLoadHelper {

    private SoundJLayerBridge musicToPlay;
    private HashMap<String, SoundJLayerBridge> preloadedSounds;

    public AudioLoadHelper() {
        preloadSounds();
    }

    /**
     * Gets music storage path
     *
     * @param musicId Music identifier
     * @return Music path, with file extension
     */
    private static InputStream getMusicPathInAudioStore(String musicId) {
        return AudioLoadHelper.class.getResourceAsStream("/audio/music/" + musicId + ".mp3");
    }

    /**
     * Starts game music
     *
     * @param musicId Music identifier
     */
    public void startMusic(String musicId) {
        if (musicToPlay != null) {
            stopMusic();
        }

        musicToPlay = new SoundJLayerBridge(getMusicPathInAudioStore(musicId));
        musicToPlay.play();
    }

    /**
     * Stops game music
     */
    public void stopMusic() {
        musicToPlay.stop();
    }

    /**
     * Preloads available sounds
     */
    private void preloadSounds() {
        final String[] sounds = {"coin", "die", "new", "touch"};
        preloadedSounds = new HashMap<>();

        for (String sound : sounds) {
            InputStream inputStream = AudioLoadHelper.class.getResourceAsStream("/audio/sounds/" + sound + ".mp3");
            preloadedSounds.put(sound, new SoundJLayerBridge(inputStream));
        }
    }

    /**
     * Gets a preloaded sound
     *
     * @param soundId Sound identifier
     * @return Preloaded sound instance
     */
    private SoundJLayerBridge getPreloadedSound(String soundId) {
        return preloadedSounds.get(soundId);
    }

    /**
     * Plays a sound
     *
     * @param soundId Sound identifier
     */
    public void playSound(String soundId) {
        getPreloadedSound(soundId).play();
    }
}
