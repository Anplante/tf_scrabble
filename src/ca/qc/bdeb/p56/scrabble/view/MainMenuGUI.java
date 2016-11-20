package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.ai.AiPlayer;
import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.GameManager;
import ca.qc.bdeb.p56.scrabble.model.HumanPlayer;
import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteTestName;
import ca.qc.bdeb.p56.scrabble.utility.NumberOfPlayer;
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

import static ca.qc.bdeb.p56.scrabble.utility.NumberOfPlayer.FOUR_PLAYER;
import static ca.qc.bdeb.p56.scrabble.utility.NumberOfPlayer.THREE_PLAYER;
import static ca.qc.bdeb.p56.scrabble.utility.NumberOfPlayer.TWO_PLAYER;

/**
 * Menu de démarrage de notre scrabble, on demande d'entrer un nom utilisateur , en y incluant le nombre d'adversaire.
 * Il peut aussi changer son arrière-plan de scrabble s'il veut.
 * <p>
 * Created by Antoine on 9/12/2016.
 */
public class MainMenuGUI extends JDialog {

    private static final int BASIC_THEME = 0;
    private static final int NOBLE_THEME = 1;
    private static final int LIMIT_OF_PLAYER = 4;

    private static final String[] numberOfAi = {"0", "1", "2", "3"};
    private static final NumberOfPlayer[] numberOfHuman = {TWO_PLAYER, THREE_PLAYER, FOUR_PLAYER};
    private JPanel panelMenu;
    private List<JTextField> allTextField;
    private List<JLabel> allLabelOfPlayers;
    private JButton btnCreateGame;
    private JLabel lblTheme;
    private JComboBox cmbTheme;
    private JLabel lblName;
    private JButton btnExit;
    private JLabel lblNumberOfAi;
    private JLabel lblNumberOfHuman;
    private JComboBox cmbNumberOfAi = new JComboBox();
    private JComboBox cmbNumberOfHuman = new JComboBox();
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
        setSize(new Dimension(insets.left + insets.right + 500,
                insets.top + insets.bottom + 550));

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
        for (int i = 0; i < numberOfAi.length; i++) {
            cmbNumberOfAi.addItem(numberOfAi[i]);
        }
        // Pour l'instant, on n'affiche pas cette combo box puisqu'on n'a pas de AI
        cmbNumberOfAi.setVisible(false);
        cmbNumberOfAi.setLocation(180, 215);
        cmbNumberOfAi.setSize(100, 25);

        cmbNumberOfHuman = new JComboBox<>();
        cmbNumberOfHuman.setName(ConstanteTestName.QTE_HUMAN_NAME);
        for (int i = 0; i < numberOfHuman.length; i++) {
            cmbNumberOfHuman.addItem(numberOfHuman[i].getNumberOfPlayer());
        }
        cmbNumberOfHuman.setVisible(true);
        cmbNumberOfHuman.setLocation(180, 215);
        cmbNumberOfHuman.setSize(100, 25);

        addEventOnComboBox();

        cmbBackgroundScrabble = new JComboBox<>();

        cmbBackgroundScrabble.setName(ConstanteTestName.BACKGROUND_NAME);
        addImageFile();
        cmbBackgroundScrabble.setVisible(true);
        cmbBackgroundScrabble.setLocation(180, 315);
        cmbBackgroundScrabble.setSize(180, 25);

        cmbTheme =  new JComboBox();
        cmbTheme.setName(ConstanteTestName.THEME_NAME);
        cmbTheme.addItem(ConstanteComponentMessage.MESS_THEME_CLASSIQUE);
        cmbTheme.addItem(ConstanteComponentMessage.MESS_THEME_NOBLE);
        cmbTheme.setVisible(true);
        cmbTheme.setLocation(180, 365);
        cmbTheme.setSize(100,25);


        panelMenu.add(cmbNumberOfHuman);
        panelMenu.add(cmbNumberOfAi);
        panelMenu.add(cmbBackgroundScrabble);
        panelMenu.add(cmbTheme);
    }

    private void addEventOnComboBox() {
        ActionListener numberOfHuman = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                NumberOfPlayer numberofPlayer = NumberOfPlayer.fromInteger((int)cmbNumberOfHuman.getSelectedItem());

                switch (numberofPlayer) {
                    case TWO_PLAYER:
                        allTextField.get(2).setVisible(false);
                        allTextField.get(3).setVisible(false);
                        allLabelOfPlayers.get(2).setVisible(false);
                        allLabelOfPlayers.get(3).setVisible(false);
                        break;
                    case THREE_PLAYER:
                        allTextField.get(2).setVisible(true);
                        allTextField.get(3).setVisible(false);
                        allLabelOfPlayers.get(2).setVisible(true);
                        allLabelOfPlayers.get(3).setVisible(false);
                        break;
                    case FOUR_PLAYER:
                        allTextField.get(2).setVisible(true);
                        allTextField.get(3).setVisible(true);
                        allLabelOfPlayers.get(2).setVisible(true);
                        allLabelOfPlayers.get(3).setVisible(true);
                        break;
                }
            }
        };

        cmbNumberOfHuman.addActionListener(numberOfHuman);
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
        btnChooseBackgroundImg.setLocation(365, 315);
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
        btnExit.setLocation(250, 420);
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
        btnCreateGame.setLocation(50, 420);
        btnCreateGame.setName(ConstanteTestName.CONFIRM_NAME);
        panelMenu.add(btnCreateGame);

        btnCreateGame.addActionListener(e -> {
            setPlayer();
            initializeGame();
        });

    }

    private String getLetttersDirectory(){
        String path;
        switch (cmbTheme.getSelectedIndex()){
            case BASIC_THEME:
                path = ConstanteComponentMessage.RES_IMAGES_FR_BASIC;
                break;
            case NOBLE_THEME:
                path = ConstanteComponentMessage.RES_IMAGES_FR_NOBLE;
                break;
            default:
                path = ConstanteComponentMessage.RES_IMAGES_FR_BASIC;
                break;
        }
        return path;
    }

    private void addFileChooser() {
        fileImage = new JFileChooser();
    }

    private void setPlayer() {

        players = new ArrayList<>();
        listName = readXMLFiles();

        int numberOfHumanPlayers = cmbNumberOfHuman.getSelectedIndex();
        numberOfHumanPlayers += 2;

        for (int i = 0; i < numberOfHumanPlayers; i++) {
            players.add(new HumanPlayer(allTextField.get(i).getText()));
        }

        int limit = cmbNumberOfAi.getSelectedIndex();
        for (int i = 0; i < limit; i++) {
            players.add(new AiPlayer(listName));
        }
    }

    private void initializeGame() {
        gameManager = new GameManager();
        game = gameManager.createNewGame(players);
        parent.setImgPath(getLetttersDirectory());
        parent.changeBackground(cmbBackgroundScrabble.getSelectedItem().toString());
        parent.createScrabbleGame(game);
        setVisible(false);
    }

    private void addLabels() {

        allLabelOfPlayers = new ArrayList<>();
        allLabelOfPlayers.add(new JLabel());
        allLabelOfPlayers.add(new JLabel());
        allLabelOfPlayers.add(new JLabel());
        allLabelOfPlayers.add(new JLabel());

        int y = -5;
        for (int i = 0; i < allLabelOfPlayers.size(); i++) {
            y += 35;
            initializeLabel(i, y);
        }

        lblNumberOfAi = new JLabel();
        lblNumberOfAi.setText(ConstanteComponentMessage.MESS_NUMBER_OF_AI);
        lblNumberOfAi.setLocation(25, 220);
        lblNumberOfAi.setSize(lblNumberOfAi.getPreferredSize());
        // Pour l'instant, on n'affiche pas ce label puisqu'on n'a pas de AI
        lblNumberOfAi.setVisible(false);

        lblNumberOfHuman = new JLabel();
        lblNumberOfHuman.setText(ConstanteComponentMessage.MESS_NUMBER_OF_HUMAN);
        lblNumberOfHuman.setLocation(25, 220);
        lblNumberOfHuman.setSize(lblNumberOfAi.getPreferredSize());
        lblNumberOfHuman.setVisible(true);

        lblBackground = new JLabel();
        lblBackground.setText(ConstanteComponentMessage.MESS_BACKGROUND);
        lblBackground.setLocation(25, 320);
        lblBackground.setSize(lblBackground.getPreferredSize());
        lblBackground.setVisible(true);

        lblTheme = new JLabel();
        lblTheme.setText(ConstanteComponentMessage.MESS_THEME);
        lblTheme.setLocation(25, 370);
        lblTheme.setSize(lblBackground.getPreferredSize());
        lblTheme.setVisible(true);

        panelMenu.add(lblNumberOfAi);
        panelMenu.add(lblNumberOfHuman);
        panelMenu.add(lblBackground);
        panelMenu.add(lblTheme);

        for (int i = 0; i < allLabelOfPlayers.size(); i++) {
            panelMenu.add(allLabelOfPlayers.get(i));
        }

        allLabelOfPlayers.get(2).setVisible(false);
        allLabelOfPlayers.get(3).setVisible(false);
    }

    private void initializeLabel(int index, int y) {
        JLabel lblOfPlayer = allLabelOfPlayers.get(index);
        lblOfPlayer.setName(ConstanteTestName.PLAYER_NAME );
        lblOfPlayer.setText(ConstanteComponentMessage.MESS_PLAYER_NAME + ++index + " :");
        lblOfPlayer.setLocation(25, y);
        lblOfPlayer.setSize(lblOfPlayer.getPreferredSize());
        lblOfPlayer.setVisible(true);
    }

    private void addTextBox() {
        panelMenu.setLocation(0, 0);
        panelMenu.setSize(new Dimension(500, 550));

        allTextField = new ArrayList<>();
        allTextField.add(new JTextField("", 30));
        allTextField.add(new JTextField("", 30));
        allTextField.add(new JTextField("", 30));
        allTextField.add(new JTextField("", 30));

        int y = -15;
        for (int i = 0; i < LIMIT_OF_PLAYER; i++) {
            y += 35;
            initializeTextField(i, y);
        }

        allTextField.get(2).setVisible(false);
        allTextField.get(3).setVisible(false);
    }

    private void initializeTextField(int index, int y) {
        JTextField txtOfPlayer = allTextField.get(index);
        txtOfPlayer.setName(ConstanteTestName.PLAYER_NAME + " " + index);
        txtOfPlayer.setBounds(150, y, 180, 30);
        txtOfPlayer.setVisible(true);

        allTextField.set(index, txtOfPlayer);

        txtOfPlayer.addActionListener(e -> {
            String input = allTextField.get(index).getText();
            allTextField.get(index).setText(input);
        });
        panelMenu.add(txtOfPlayer);
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