package boulderdash.bridges;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.InputStream;

/**
 * Sound bridge to the JLayer library.
 */
public class SoundJLayerBridge extends PlaybackListener implements Runnable {
    private final InputStream inputStream;
    private AdvancedPlayer player;
    private Thread playerThread;

    public SoundJLayerBridge(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * Play the target sound
     */
    public void play() {
        try {
            player = new AdvancedPlayer(inputStream, FactoryRegistry.systemRegistry().createAudioDevice());

            player.setPlayBackListener(this);

            playerThread = new Thread(this, "AudioPlayerThread");
            playerThread.start();
        } catch (Exception ex) {
            ex.printStackTrace(); // TODO rethrow exception
        }
    }

    /**
     * Stops the target sound
     */
    public void stop() {
        try {
            playerThread.stop();
        } catch (Exception ex) {
            ex.printStackTrace(); // TODO rethrow exception
        }
    }

    /**
     * Runs the player thread
     */
    public void run() {
        try {
            player.play();
        } catch (JavaLayerException ex) {
            ex.printStackTrace(); // TODO rethrow exception
        }
    }
}