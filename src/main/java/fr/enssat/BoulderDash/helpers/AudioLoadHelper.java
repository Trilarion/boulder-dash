package fr.enssat.BoulderDash.helpers;

import fr.enssat.BoulderDash.bridges.SoundJLayerBridge;

import java.io.InputStream;
import java.util.HashMap;

/**
 * AudioLoadHelper
 * <p>
 * Manages audio
 *
 * @author Valerian Saliou <valerian@valeriansaliou.name>
 * @since 2015-06-19
 */
public class AudioLoadHelper {

    private SoundJLayerBridge musicToPlay;
    private HashMap<String, SoundJLayerBridge> preloadedSounds;

    /**
     * Class constructor
     */
    public AudioLoadHelper() {
        this.preloadSounds();
    }

    /**
     * Gets music storage path
     *
     * @param musicId Music identifier
     * @return Music path, with file extension
     */
    private InputStream getMusicPathInAudioStore(String musicId) {
        return AudioLoadHelper.class.getResourceAsStream("/audio/music/" + musicId + ".mp3");
    }

    /**
     * Starts game music
     *
     * @param musicId Music identifier
     */
    public void startMusic(String musicId) {
        if (this.musicToPlay != null) {
            this.stopMusic();
        }

        this.musicToPlay = new SoundJLayerBridge(this.getMusicPathInAudioStore(musicId));
        this.musicToPlay.play();
    }

    /**
     * Stops game music
     */
    public void stopMusic() {
        this.musicToPlay.stop();
    }

    /**
     * Preloads available sounds
     */
    private void preloadSounds() {
        final String[] sounds = {"coin", "die", "new", "touch"};
        this.preloadedSounds = new HashMap<>();

        for (String sound : sounds) {
            InputStream inputStream = AudioLoadHelper.class.getResourceAsStream("/audio/sounds/" + sound + ".mp3");
            this.preloadedSounds.put(sound, new SoundJLayerBridge(inputStream));
        }
    }

    /**
     * Gets a preloaded sound
     *
     * @param soundId Sound identifier
     * @return Preloaded sound instance
     */
    private SoundJLayerBridge getPreloadedSound(String soundId) {
        return this.preloadedSounds.get(soundId);
    }

    /**
     * Plays a sound
     *
     * @param soundId Sound identifier
     */
    public void playSound(String soundId) {
        this.getPreloadedSound(soundId).play();
    }
}
