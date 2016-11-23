package ca.qc.bdeb.p56.scrabble.utility;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.net.URL;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

/**
 * Classe utilisée pour la génération des images/icons du jeu.
 *
 * Created by Louis Luu Lim on 11/3/2016.
 */
public class ImagesManager {

    public static ImageIcon getIcon(URL path, int width, int height)
    {
        ImageIcon fillingIcon = new ImageIcon(path);
        Image img = fillingIcon.getImage();
        Image newImage = img.getScaledInstance(width,height, java.awt.Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(newImage);
        return icon;
    }

    public static BufferedImage createPlayerIcon(BufferedImage imageToResize) {
        BufferedImage playerIcon = null;
        if (imageToResize != null) {
            playerIcon = new BufferedImage(50,50, TYPE_INT_ARGB);
            Graphics2D graphics = playerIcon.createGraphics();
            AffineTransform transform = AffineTransform.getScaleInstance(0.2, 0.2);
            graphics.drawRenderedImage(imageToResize, transform);
        }
        return playerIcon;
    }
}
