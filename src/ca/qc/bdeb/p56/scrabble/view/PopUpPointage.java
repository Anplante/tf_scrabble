package ca.qc.bdeb.p56.scrabble.view;

import javax.swing.*;

/**
 * Created by 1468636 on 2016-12-01.
 */
public class PopUpPointage extends JPopupMenu {
    JMenuItem anItem;
    public PopUpPointage(String message){
        anItem = new JMenuItem(message);
        add(anItem);
    }
}
