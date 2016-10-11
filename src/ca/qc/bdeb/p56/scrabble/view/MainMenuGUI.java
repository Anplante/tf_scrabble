package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.ai.AiPlayer;
import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.GameManager;
import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;
import sun.misc.Launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antoine on 9/12/2016.
 */
public class MainMenuGUI extends JDialog {

    private JPanel panelMenu;
    private JTextField txtNom;
    private JButton btnConfirm;
    private JLabel lblNom;
    private JButton btnCancel;
    private JLabel lblNombreAI;
    private String[] nombreDeAi = {"1", "2", "3"};
    private JComboBox cmbNombreAi = new JComboBox();
    private GameManager gameManager;
    private JComboBox<String> cmbBackgroundScrabble;
    private JLabel lblBackground;

    private Player player;
    private List<Player> players;
    private Game game;
    ScrabbleGUI parent;

    public MainMenuGUI(ScrabbleGUI parent) {

        super();

        this.parent = parent;
        setLayout(null);
        players = new ArrayList<>();

        JFrame fenetre = new JFrame();
        this.setTitle("Menu");
        fenetre.pack();
        Insets insets = fenetre.getInsets();
        setSize(new Dimension(insets.left + insets.right + 400,
                insets.top + insets.bottom + 400));

        initializeComponents();

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
        setVisible(true);
        setResizable(false);

    }


    private void initializeComponents() {
        panelMenu = new JPanel();
        panelMenu.setLayout(null);
        ajouterTextBox();
        ajouterLesLabels();
        ajouterBoutons();
        addComboBox();

        add(panelMenu);
    }

    private void addComboBox() {
        cmbNombreAi = new JComboBox();
        cmbNombreAi.setName("choix");
        for (int i = 0; i < nombreDeAi.length; i++) {
            cmbNombreAi.addItem(nombreDeAi[i]);
        }
        cmbNombreAi.setVisible(true);
        cmbNombreAi.setLocation(180, 120);
        cmbNombreAi.setSize(100, 25);

        cmbBackgroundScrabble = new JComboBox<>();

        cmbBackgroundScrabble.setName("background");
        addImageFile();
        cmbBackgroundScrabble.setVisible(true);
        cmbBackgroundScrabble.setLocation(180, 220);
        cmbBackgroundScrabble.setSize(200,25);


        panelMenu.add(cmbNombreAi);
        panelMenu.add(cmbBackgroundScrabble);
    }

    private void ajouterBoutons() {
        btnCancel = new JButton();
        btnConfirm = new JButton();
        btnCancel.setVisible(true);
        btnConfirm.setVisible(true);
        btnCancel.setSize(100, 50);
        btnConfirm.setSize(100, 50);
        btnCancel.setText("Annuler");
        btnConfirm.setText("Confirmer");
        btnCancel.setLocation(250, 325);
        btnConfirm.setLocation(50, 325);
        btnConfirm.setName("Confirm");
        btnCancel.setName("Cancel");
        btnConfirm.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                setPlayer();
                initializeGame();
            }
        });
        btnCancel.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
        panelMenu.add(btnConfirm);
        panelMenu.add(btnCancel);
    }

    private void setPlayer() {

        players = new ArrayList<Player>();
        player = new Player(txtNom.getText());
        players.add(player);

        int limit = (int) cmbNombreAi.getSelectedIndex();
        ++limit;
        for (int i = 0; i < limit; i++) {
            players.add(new AiPlayer());
        }
    }

    private void initializeGame() {

        gameManager = new GameManager();
        game = gameManager.createNewGame(players);
        parent.changeBackground(cmbBackgroundScrabble.getSelectedItem().toString());
        parent.createScrabbleGame(game);
        setVisible(false);
        //dispose();
    }

    private void ajouterLesLabels() {
        lblNom = new JLabel();
        lblNom.setText("Nom du Joueur : ");
        lblNom.setLocation(25, 25);
        lblNom.setSize(lblNom.getPreferredSize());
        lblNom.setVisible(true);

        lblNombreAI = new JLabel();
        lblNombreAI.setText("Nombre d'ordinateurs :");
        lblNombreAI.setLocation(25, 125);
        lblNombreAI.setSize(lblNombreAI.getPreferredSize());
        lblNombreAI.setVisible(true);

        lblBackground = new JLabel();
        lblBackground.setText("Background : ");
        lblBackground.setLocation(25, 225);
        lblBackground.setSize(lblBackground.getPreferredSize());
        lblBackground.setVisible(true);


        panelMenu.add(lblNom);
        panelMenu.add(lblNombreAI);
        panelMenu.add(lblBackground);
    }

    private void ajouterTextBox() {
        panelMenu.setLocation(0, 0);
        panelMenu.setSize(new Dimension(400, 400));
        txtNom = new JTextField("", 30);
        txtNom.setName("textBox");
        txtNom.setBounds(150, 20, 180, 30);

        txtNom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = txtNom.getText();
                txtNom.setText(input);
            }
        });
        panelMenu.add(txtNom);
    }

    private void addImageFile() {
        File lesFichiers = null;

        URL url = Launcher.class.getResource("/background/");
        try {
            lesFichiers = new File(url.toURI());
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        File[] files =  null;
        for (File file : lesFichiers.listFiles()) {
            cmbBackgroundScrabble.addItem(file.getName());
        }
    }

    public static String removeExtension(String filenameWithExtension) {

        String separator = System.getProperty("file.separator");
        String filename;

        // Remove the path up to the filename.
        int lastSeparatorIndex = filenameWithExtension.lastIndexOf(separator);
        if (lastSeparatorIndex == -1) {
            filename = filenameWithExtension;
        } else {
            filename = filenameWithExtension.substring(lastSeparatorIndex + 1);
        }

        // Remove the extension.
        int extensionIndex = filename.lastIndexOf(".");
        if (extensionIndex == -1)
            return filename;

        return filename.substring(0, extensionIndex);
    }

    public Player getPlayer() {
        return player;
    }

    public int getLenghtPlayers() {
        return game.getPlayers().size();
    }

}