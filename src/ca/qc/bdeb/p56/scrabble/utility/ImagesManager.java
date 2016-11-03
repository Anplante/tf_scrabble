package ca.qc.bdeb.p56.scrabble.utility;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

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
}
