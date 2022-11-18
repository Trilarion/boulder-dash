package boulderdash.views;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


/**
 * MenuImage
 * <p>
 * Specifies the menu image
 *
 * @author Valerian Saliou <valerian@valeriansaliou.name>
 * @since 2015-06-23
 */
public class MenuImage extends JPanel {
    private BufferedImage imageFile;


    public MenuImage() {
        try {
            InputStream imagePath = MenuImage.class.getResourceAsStream("/drawable/app/menu_actual.jpg");
            imageFile = ImageIO.read(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Paints the component itself
     *
     * @param g Graphics element
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(imageFile, 0, 0, null);
    }
}