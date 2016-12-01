package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteTestName;
import ca.qc.bdeb.p56.scrabble.utility.ImagesManager;
import sun.misc.Launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Antoine on 11/27/2016.
 */
public class PanelSearchBar extends JPanel {

    private static final Font ROBOTO_FONT = loadFont("Roboto-Bold.ttf").deriveFont(Font.BOLD, 25);
    private static final Font FONT_RESULT = loadFont("Roboto-Light.ttf").deriveFont(Font.PLAIN, 25);
    private static final URL DEFAULT_SEARCH_ICON = Launcher.class.getResource("/images/search.png");
    private static final int SEARCH_BAR_HEIGHT = 45;
    private static final int BTN_SEARCH_SIZE = SEARCH_BAR_HEIGHT - 1;
    private static final int SEARCH_BAR_WIDTH = 250;
    private Game gameModel;
    private JTextField searchBar;
    private JButton btnSearch;
    private JLabel lblTitle;
    private JLabel lblResult;

    public PanelSearchBar(Game gameModel) {
        this.gameModel = gameModel;
        setLayout(null);
        initComponents();
    }

    private void initComponents() {

        lblTitle = new JLabel();
        lblTitle.setText("Dictionnaire");
        lblTitle.setFont(ROBOTO_FONT);
        lblTitle.setSize(lblTitle.getPreferredSize());
        lblTitle.setLocation(0,0);

        searchBar = new JTextField("", 30);
        GhostText ghostText = new GhostText(searchBar, ConstanteComponentMessage.ENTER_WORD);
        searchBar.setLocation(0,lblTitle.getHeight());
        searchBar.setSize(SEARCH_BAR_WIDTH, SEARCH_BAR_HEIGHT);
        searchBar.setFont(ROBOTO_FONT);

        btnSearch = new JButton();
        btnSearch.setSize(BTN_SEARCH_SIZE, BTN_SEARCH_SIZE);
        btnSearch.setLocation(searchBar.getWidth(), lblTitle.getHeight());
        btnSearch.setName(ConstanteTestName.SEARCH_BUTTON);
        btnSearch.setIcon(ImagesManager.getIcon(DEFAULT_SEARCH_ICON, BTN_SEARCH_SIZE,BTN_SEARCH_SIZE));

        lblResult = new JLabel();
        lblResult.setText("");
        lblResult.setFont(FONT_RESULT);
        lblResult.setSize(lblResult.getPreferredSize());
        lblResult.setLocation(0,lblTitle.getHeight() + btnSearch.getHeight());

        initEvents();

        add(searchBar);
        add(btnSearch);
        add(lblTitle);
        add(lblResult);
    }

    private void initEvents() {
        searchBar.addActionListener(e -> {
            String input = searchBar.getText();
            searchBar.setText(input);
        });
        btnSearch.addActionListener(e -> {
            if (gameModel.isValidWord(searchBar.getText().toLowerCase()) &&
            !searchBar.getText().equals(ConstanteComponentMessage.ENTER_WORD)) {
                lblResult.setText(ConstanteComponentMessage.VALID_WORD);
                lblResult.setSize(lblResult.getPreferredSize());
            }
            else {
                lblResult.setText(ConstanteComponentMessage.INVALID_WORD);
                lblResult.setSize(lblResult.getPreferredSize());
            }
        });
    }

    private static Font loadFont(String resourceName) {
        try (InputStream inputStream = PanelSearchBar.class.getResourceAsStream("/fonts/" + resourceName)) {
            return Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException("Could not load " + resourceName, e);
        }
    }
}
