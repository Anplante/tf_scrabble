package ca.qc.bdeb.p56.scrabble.view;

import javax.swing.*;

/**
 * Created by Antoine on 11/27/2016.
 */
public class PanelSearchBar extends JPanel {

    private JTextField searchbar;
    private JLabel lblTitle;

    public PanelSearchBar() {
        initComponents();
    }

    private void initComponents() {
        searchbar = new JTextField("", 30);
        searchbar.setToolTipText("test");
    }
}
