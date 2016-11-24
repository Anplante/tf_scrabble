package ca.qc.bdeb.p56.scrabble.utility;

import sun.misc.Launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

/**
 * Classe utilisée pour la génération des images/icons du jeu.
 *
 * Created by Louis Luu Lim on 11/3/2016.
 */
public class ImagesManager {

    private static final URL DEFAULT_PLAYER_ICON = Launcher.class.getResource("/images/default.png");

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

    public static BufferedImage getImageFromURL (URL pathToIcon) {
        BufferedImage imgPlayer = null;
        try {
            imgPlayer = ImageIO.read(pathToIcon);
        } catch (IOException ex) {
            Logger.getLogger("Impossible de trouver l'image situé à : "
                    + String.valueOf(pathToIcon)).log(Level.SEVERE, null, ex);
        }
        // attention il peut retourner un null
        return imgPlayer;
    }

    public static BufferedImage getImageFromFile(File fichier) {
        Path path = Paths.get(fichier.getAbsolutePath());
        BufferedImage imagePlayer = null;
        try {
            imagePlayer =  getImageFromURL(path.toUri().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            imagePlayer = getImageFromURL(DEFAULT_PLAYER_ICON);
        }
        return imagePlayer;
    }
}
