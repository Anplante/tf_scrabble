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
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

/**
 * Classe utilisée pour la génération des images/icons du jeu.
 *
 * Created by Louis Luu Lim on 11/3/2016.
 */
public class ImagesManager {

    private  static final ResourceBundle messages = ResourceBundle.getBundle("strings", Locale.getDefault());
    private static final URL DEFAULT_PLAYER_ICON = Launcher.class.getResource("/images/default.png");

    public static ImageIcon getIcon(URL path, int width, int height)
    {

        ImageIcon fillingIcon = new ImageIcon(path);
        Image img = fillingIcon.getImage();
        Image newImage = img.getScaledInstance(width,height, java.awt.Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(newImage);
        return icon;
    }

    public static BufferedImage getImageFromURL (URL pathToIcon) {

        BufferedImage imgPlayer = null;
        try {
            imgPlayer = ImageIO.read(pathToIcon);
        } catch (IOException ex) {
            Logger.getLogger(messages.getString("Error_Finding_File") + " "
                    + String.valueOf(pathToIcon)).log(Level.SEVERE, null, ex);
        }

        return imgPlayer;
    }

    public static BufferedImage getImageFromFile(File fichier) {

        Path path = Paths.get(fichier.getAbsolutePath());
        BufferedImage imagePlayer;
        try {
            imagePlayer =  getImageFromURL(path.toUri().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            imagePlayer = getImageFromURL(DEFAULT_PLAYER_ICON);
        }
        return imagePlayer;
    }
}
