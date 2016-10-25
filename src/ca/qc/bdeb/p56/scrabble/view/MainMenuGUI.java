package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.ai.AiPlayer;
import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.GameManager;
import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;
import javafx.stage.FileChooser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import sun.misc.Launcher;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private JFileChooser fileImage;
    private JButton btnOpenDialog;
    private ArrayList<String> listName;

    private Player player;
    private List<Player> players;
    private Game game;
    ScrabbleGUI parent;

    public static final URL PATH_TO_FILE = Launcher.class.getResource("/fichiers/ListOfName.xml");

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

        listName = readXMLFiles();

    }


    private void initializeComponents() {
        panelMenu = new JPanel();
        panelMenu.setLayout(null);
        addFileChooser();
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
        cmbBackgroundScrabble.setSize(180,25);


        panelMenu.add(cmbNombreAi);
        panelMenu.add(cmbBackgroundScrabble);
    }

    private void ajouterBoutons() {
        btnCancel = new JButton();
        btnConfirm = new JButton();
        btnOpenDialog = new JButton("...");
        btnCancel.setVisible(true);
        btnOpenDialog.setVisible(true);
        btnConfirm.setVisible(true);
        btnCancel.setSize(100, 50);
        btnOpenDialog.setSize(25,25);
        btnConfirm.setSize(100, 50);
        btnCancel.setText("Annuler");
        btnConfirm.setText("Confirmer");
        btnCancel.setLocation(250, 325);
        btnOpenDialog.setLocation(365,220);
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
        btnOpenDialog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnValue = fileImage.showOpenDialog(panelMenu);
                receiveBackground(returnValue);
            }});
        panelMenu.add(btnOpenDialog);
        panelMenu.add(btnConfirm);
        panelMenu.add(btnCancel);
    }

    private void addFileChooser() {
        fileImage = new JFileChooser();
    }

    private void setPlayer() {

        players = new ArrayList<Player>();
        player = new Player(txtNom.getText());
        players.add(player);

        int limit = (int) cmbNombreAi.getSelectedIndex();
        ++limit;
        for (int i = 0; i < limit; i++) {
            players.add(new AiPlayer(listName));
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
        cmbBackgroundScrabble.setSelectedIndex(2);
    }

    private void receiveBackground(int returnValue) {
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File fichier = fileImage.getSelectedFile();
            // TODO: filter
            if (!fichier.getName().endsWith(".jpg") || !fichier.getName().endsWith(".png")
                    || !fichier.getName().endsWith(".jpeg")) {
                fichier = new File(fichier.getAbsolutePath());
                Path path = Paths.get(fichier.getAbsolutePath());
                cmbBackgroundScrabble.addItem(path.getFileName().toString());
            }
            else {
                JOptionPane.showMessageDialog(panelMenu, "Problème de chargement de fichier", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
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

    public ArrayList<String> readXMLFiles() {
        ArrayList<String> listOfName = new ArrayList<>();
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document documentOfName = docBuilder.parse(new File(PATH_TO_FILE.toURI()));
            documentOfName.getDocumentElement().normalize();
            NodeList nodeOfName = documentOfName.getElementsByTagName("item");

            for (int i = 0; i < nodeOfName.getLength(); i++) {
                Node itemName = nodeOfName.item(i);
                if (itemName.getNodeType() == Node.ELEMENT_NODE) {
                    Element aiName = (Element) itemName;
                    listOfName.add(aiName.getElementsByTagName("name").item(0).getTextContent());
                }
            }
            // TODO : donner du feedback à l'utilisateur
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(docBuilderFactory.toString()).log(Level.SEVERE, null, ex);
        } catch (SAXParseException ex) {
            Logger.getLogger(PATH_TO_FILE.toString()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(PATH_TO_FILE.toString()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(PATH_TO_FILE.toString()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(docBuilderFactory.toString()).log(Level.SEVERE, null, ex);
        }
        return listOfName;
    }

}