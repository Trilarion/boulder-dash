package fr.enssat.BoulderDash.views;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


/**
 * MenuImage
 *
 * Specifies the menu image
 *
 * @author      Valerian Saliou <valerian@valeriansaliou.name>
 * @since       2015-06-23
 */
public class MenuImage extends JPanel {
    private BufferedImage imageFile;
    private InputStream imagePath = MenuImage.class.getResourceAsStream("/drawable/app/menu_actual.jpg");

    /**
     * Class constructor
     */
    public MenuImage() {
        try {
            this.imageFile = ImageIO.read(this.imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Paints the component itself
     *
     * @param  g  Graphics element
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(this.imageFile, 0, 0, null);
    }
}