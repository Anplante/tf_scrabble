package ca.qc.bdeb.p56.scrabble.utility;

import javax.swing.*;
import java.awt.*;

/**
 * Adapté de Sherif S. Mohammed
 * @author shsaad@ece.uvic.ca
 * @see //www.ece.uvic.ca/~shsaad/seng426/resources/Lab%20Slides/Lab4-SENG09.pdf
 */
public class TestUtils {
    private static int counter;

    public static Component getChildNamed(Component parent, String name) {
        if (name.equals(parent.getName())) {
            return parent;
        } else if (parent instanceof Container) {
            Component[] children;
            if (parent instanceof JMenu) {
                children = ((JMenu)parent).getMenuComponents();
            } else {
                children = ((Container)parent).getComponents();
            }

            for (int i = 0; i < children.length; i++) {
                Component child = getChildNamed(children[i],name);
                if (child != null) {
                    return child;
                }
            }
        }

        return null;
    }
}
