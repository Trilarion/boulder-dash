package boulderdash.views;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Specifies the menu image
 */
public class MenuImage extends JPanel { // TODO use composition instead and inline
    private BufferedImage imageFile;

    public MenuImage() {
        try {
            InputStream imagePath = MenuImage.class.getResourceAsStream("/drawable/app/menu_actual.jpg"); // TODO replace with constant
            imageFile = ImageIO.read(imagePath);
        } catch (IOException e) {
            e.printStackTrace(); // TODO rethrow exception
        }
    }

    /**
     * Paints the component itself
     *
     * @param g Graphics element
     */
    @Override
    protected void paintComponent(@NotNull Graphics g) {
        super.paintComponent(g);

        g.drawImage(imageFile, 0, 0, null);
    }
}