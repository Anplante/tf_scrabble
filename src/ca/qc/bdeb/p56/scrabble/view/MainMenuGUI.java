package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.ai.AiPlayer;
import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.GameManager;
import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteTestName;
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
 * Menu de démarrage de notre scrabble, on demande d'entrer un nom utilisateur , en y incluant le nombre d'adversaire.
 * Il peut aussi changer son arrière-plan de scrabble s'il veut.
 *
 * Created by Antoine on 9/12/2016.
 */
public class MainMenuGUI extends JDialog implements ConstanteTestName, ConstanteComponentMessage{


    private JPanel panelMenu;
    private JTextField txtName;
    private JButton btnConfirm;
    private JLabel lblName;
    private JButton btnCancel;
    private JLabel lblNumberOfAi;
    private String[] nombreDeAi = {"1", "2", "3"};
    private JComboBox cmbNumberOfAi = new JComboBox();
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

    public static final URL PATH_TO_FILE = Launcher.class.getResource("/files/ListOfName.xml");

    public MainMenuGUI(ScrabbleGUI parent) {
        super();
        this.parent = parent;
        initializeFrame();
        initializeComponents();
        initializeSize();

    }

    private void initializeFrame() {
        setLayout(null);
        JFrame fenetre = new JFrame();
        this.setTitle(TITLE_MENU);
        fenetre.pack();
        Insets insets = fenetre.getInsets();
        setSize(new Dimension(insets.left + insets.right + 400,
                insets.top + insets.bottom + 400));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initializeSize() {
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
        addFileChooser();
        addTextBox();
        addLabels();
        addButtons();
        addComboBox();
        add(panelMenu);
    }

    private void addComboBox() {

        cmbNumberOfAi = new JComboBox();
        cmbNumberOfAi.setName(QTE_AI_NAME);
        for (int i = 0; i < nombreDeAi.length; i++) {
            cmbNumberOfAi.addItem(nombreDeAi[i]);
        }
        cmbNumberOfAi.setVisible(true);
        cmbNumberOfAi.setLocation(180, 120);
        cmbNumberOfAi.setSize(100, 25);

        cmbBackgroundScrabble = new JComboBox<>();

        cmbBackgroundScrabble.setName(BACKGROUND_NAME);
        addImageFile();
        cmbBackgroundScrabble.setVisible(true);
        cmbBackgroundScrabble.setLocation(180, 220);
        cmbBackgroundScrabble.setSize(180,25);


        panelMenu.add(cmbNumberOfAi);
        panelMenu.add(cmbBackgroundScrabble);
    }

    private void addButtons() {

        btnCancel = new JButton();
        btnConfirm = new JButton();
        btnOpenDialog = new JButton("...");
        btnCancel.setVisible(true);
        btnOpenDialog.setVisible(true);
        btnConfirm.setVisible(true);
        btnCancel.setSize(100, 50);
        btnOpenDialog.setSize(25,25);
        btnConfirm.setSize(100, 50);
        btnCancel.setText(MESS_CANCEL);
        btnConfirm.setText(MESS_CONFIRM);
        btnCancel.setLocation(250, 325);
        btnOpenDialog.setLocation(365,220);
        btnConfirm.setLocation(50, 325);
        btnConfirm.setName(CONFIRM_NAME);
        btnCancel.setName(CANCEL_NAME);


        btnConfirm.addActionListener(e -> {
            setPlayer();
            initializeGame();
        });

        btnCancel.addActionListener(e -> System.exit(0));
        btnOpenDialog.addActionListener(e -> {
            int returnValue = fileImage.showOpenDialog(panelMenu);
            receiveBackground(returnValue);
        });
        panelMenu.add(btnOpenDialog);
        panelMenu.add(btnConfirm);
        panelMenu.add(btnCancel);
    }

    private void addFileChooser() {
        fileImage = new JFileChooser();
    }

    private void setPlayer() {

        players = new ArrayList<>();
        listName = readXMLFiles();
        player = new Player(txtName.getText());
        players.add(player);

        int limit = cmbNumberOfAi.getSelectedIndex();
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
    }

    private void addLabels() {
        lblName = new JLabel();
        lblName.setText(MESS_PLAYER_NAME);
        lblName.setLocation(25, 25);
        lblName.setSize(lblName.getPreferredSize());
        lblName.setVisible(true);

        lblNumberOfAi = new JLabel();
        lblNumberOfAi.setText(MESS_NUMBER_OF_AI);
        lblNumberOfAi.setLocation(25, 125);
        lblNumberOfAi.setSize(lblNumberOfAi.getPreferredSize());
        lblNumberOfAi.setVisible(true);

        lblBackground = new JLabel();
        lblBackground.setText(MESS_BACKGROUND);
        lblBackground.setLocation(25, 225);
        lblBackground.setSize(lblBackground.getPreferredSize());
        lblBackground.setVisible(true);


        panelMenu.add(lblName);
        panelMenu.add(lblNumberOfAi);
        panelMenu.add(lblBackground);
    }

    private void addTextBox() {
        panelMenu.setLocation(0, 0);
        panelMenu.setSize(new Dimension(400, 400));
        txtName = new JTextField("", 30);
        txtName.setName(PLAYER_NAME);
        txtName.setBounds(150, 20, 180, 30);

        txtName.addActionListener(e -> {
            String input = txtName.getText();
            txtName.setText(input);
        });
        panelMenu.add(txtName);
    }

    private void addImageFile() {
        File theFiles = null;

        URL url = Launcher.class.getResource(PATH_BACKGROUND_RES);
        try {
            theFiles = new File(url.toURI());
        } catch (URISyntaxException ex) {
            Logger.getLogger(url.toString()).log(Level.SEVERE, null, ex);
        }

        for (File file : theFiles.listFiles()) {
            cmbBackgroundScrabble.addItem(file.getName());
        }
        cmbBackgroundScrabble.setSelectedIndex(2);
    }

    /**
     * Antoine : Future implémentation?
     * @return
     */
   /* public static String removeExtension(String filenameWithExtension) {

        String separator = System.getProperty(FILE_SEPARATOR);
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
    }*/


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
            NodeList nodeOfName = documentOfName.getElementsByTagName(TAG_ITEM);

            for (int i = 0; i < nodeOfName.getLength(); i++) {
                Node itemName = nodeOfName.item(i);
                if (itemName.getNodeType() == Node.ELEMENT_NODE) {
                    Element aiName = (Element) itemName;
                    listOfName.add(aiName.getElementsByTagName(TAG_NAME).item(0).getTextContent());
                }
            }
            // TODO Antoine : donner du feedback à l'utilisateur
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

    private void receiveBackground(int returnValue) {
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File fichier = fileImage.getSelectedFile();
            // TODO: filter
            if (!fichier.getName().endsWith(EXT_JPG) || !fichier.getName().endsWith(EXT_PNG)
                    || !fichier.getName().endsWith(EXT_JPG)) {
                fichier = new File(fichier.getAbsolutePath());
                Path path = Paths.get(fichier.getAbsolutePath());
                cmbBackgroundScrabble.addItem(path.getFileName().toString());
            }
            else {
                JOptionPane.showMessageDialog(panelMenu, MESS_ERROR_LOADING_FILE, MESS_ERROR,
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}