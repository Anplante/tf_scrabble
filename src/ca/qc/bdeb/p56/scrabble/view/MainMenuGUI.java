package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.ai.AiPlayer;
import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.GameManager;
import ca.qc.bdeb.p56.scrabble.model.HumanPlayer;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
 * <p>
 * Created by Antoine on 9/12/2016.
 */
public class MainMenuGUI extends JDialog {

    private static final String[] nombreDeAi = {"1", "2", "3"};
    private JPanel panelMenu;
    private JTextField txtName;
    private JButton btnCreateGame;
    private JLabel lblName;
    private JButton btnExit;
    private JLabel lblNumberOfAi;
    private JComboBox cmbNumberOfAi = new JComboBox();
    private GameManager gameManager;
    private JComboBox<String> cmbBackgroundScrabble;
    private JLabel lblBackground;
    private JFileChooser fileImage;
    private JButton btnChooseBackgroundImg;
    private ArrayList<String> listName;

    private HumanPlayer player;
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

        addWindowListener(new WindowAdapter() {
            @Override public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });


    }

    private void initializeFrame() {
        setLayout(null);
        JFrame fenetre = new JFrame();
        this.setTitle(ConstanteComponentMessage.TITLE_MENU);

        Insets insets = fenetre.getInsets();
        setSize(new Dimension(insets.left + insets.right + 400,
                insets.top + insets.bottom + 400));

        fenetre.pack();
        // TODO Louis : Faire en sorte que lorsqu'on ferme le dialogue que le programme se termine.
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
        initMenuOptions();
        addComboBox();
        add(panelMenu);
    }

    private void addComboBox() {

        cmbNumberOfAi = new JComboBox();
        cmbNumberOfAi.setName(ConstanteTestName.QTE_AI_NAME);
        for (int i = 0; i < nombreDeAi.length; i++) {
            cmbNumberOfAi.addItem(nombreDeAi[i]);
        }
        cmbNumberOfAi.setVisible(true);
        cmbNumberOfAi.setLocation(180, 120);
        cmbNumberOfAi.setSize(100, 25);

        cmbBackgroundScrabble = new JComboBox<>();

        cmbBackgroundScrabble.setName(ConstanteTestName.BACKGROUND_NAME);
        addImageFile();
        cmbBackgroundScrabble.setVisible(true);
        cmbBackgroundScrabble.setLocation(180, 220);
        cmbBackgroundScrabble.setSize(180, 25);


        panelMenu.add(cmbNumberOfAi);
        panelMenu.add(cmbBackgroundScrabble);
    }

    private void initMenuOptions() {

        initBtnChooseBackgroundImg();
        initBtnExit();
        initBtnCreateGame();
    }

    private void initBtnChooseBackgroundImg()
    {
        btnChooseBackgroundImg = new JButton(ConstanteComponentMessage.ELLIPSIS);
        btnChooseBackgroundImg.setSize(25, 25);
        btnChooseBackgroundImg.setLocation(365, 220);
        panelMenu.add(btnChooseBackgroundImg);
        btnChooseBackgroundImg.addActionListener(e -> {
            int returnValue = fileImage.showOpenDialog(panelMenu);
            receiveBackground(returnValue);
        });
    }
    private void initBtnExit()
    {
        btnExit = new JButton();
        btnExit.setSize(100, 50);
        btnExit.setLocation(250, 325);
        btnExit.setText(ConstanteComponentMessage.MESS_CANCEL);
        btnExit.setName(ConstanteTestName.CANCEL_NAME);
        panelMenu.add(btnExit);
        btnExit.addActionListener(e -> System.exit(0));
    }

    private void initBtnCreateGame()
    {
        btnCreateGame = new JButton();
        btnCreateGame.setSize(100, 50);
        btnCreateGame.setText(ConstanteComponentMessage.MESS_CONFIRM);
        btnCreateGame.setLocation(50, 325);
        btnCreateGame.setName(ConstanteTestName.CONFIRM_NAME);
        panelMenu.add(btnCreateGame);

        btnCreateGame.addActionListener(e -> {
            setPlayer();
            initializeGame();
        });

    }

    private void addFileChooser() {
        fileImage = new JFileChooser();
    }

    private void setPlayer() {

        players = new ArrayList<>();
        listName = readXMLFiles();
        player = new HumanPlayer(txtName.getText());
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
        lblName.setText(ConstanteComponentMessage.MESS_PLAYER_NAME);
        lblName.setLocation(25, 25);
        lblName.setSize(lblName.getPreferredSize());
        lblName.setVisible(true);

        lblNumberOfAi = new JLabel();
        lblNumberOfAi.setText(ConstanteComponentMessage.MESS_NUMBER_OF_AI);
        lblNumberOfAi.setLocation(25, 125);
        lblNumberOfAi.setSize(lblNumberOfAi.getPreferredSize());
        lblNumberOfAi.setVisible(true);

        lblBackground = new JLabel();
        lblBackground.setText(ConstanteComponentMessage.MESS_BACKGROUND);
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
        txtName.setName(ConstanteTestName.PLAYER_NAME);
        txtName.setBounds(150, 20, 180, 30);

        txtName.addActionListener(e -> {
            String input = txtName.getText();
            txtName.setText(input);
        });
        panelMenu.add(txtName);
    }

    private void addImageFile() {
        File theFiles = null;

        URL url = Launcher.class.getResource(ConstanteComponentMessage.PATH_BACKGROUND_RES);
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
     *
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
            NodeList nodeOfName = documentOfName.getElementsByTagName(ConstanteComponentMessage.TAG_ITEM);

            for (int i = 0; i < nodeOfName.getLength(); i++) {
                Node itemName = nodeOfName.item(i);
                if (itemName.getNodeType() == Node.ELEMENT_NODE) {
                    Element aiName = (Element) itemName;
                    listOfName.add(aiName.getElementsByTagName(ConstanteComponentMessage.TAG_NAME).item(0).getTextContent());
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
            if (!fichier.getName().endsWith(ConstanteComponentMessage.EXT_JPG) || !fichier.getName().endsWith(ConstanteComponentMessage.EXT_PNG)
                    || !fichier.getName().endsWith(ConstanteComponentMessage.EXT_JPG)) {
                fichier = new File(fichier.getAbsolutePath());
                Path path = Paths.get(fichier.getAbsolutePath());
                cmbBackgroundScrabble.addItem(path.getFileName().toString());
            } else {
                JOptionPane.showMessageDialog(panelMenu, ConstanteComponentMessage.MESS_ERROR_LOADING_FILE, ConstanteComponentMessage.MESS_ERROR,
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}