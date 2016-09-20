package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by TheFrenchOne on 9/10/2016.
 */
public class Game {

    private BoardManager boardManager;
    private List<Player> players;
    private int activePlayerIndex;
    private static List<Tile> alphabetBag;

    private static final Random randomGenerator = new Random();


    public Game(String filePath, List<Player> players) {


        loadParameters(filePath);

        this.players = players;

        for(Player player : players)
        {
            player.setGame(this);
        }
    }

    private void loadParameters(String filePath)
    {
        Element rootElement = getRootElement(filePath);
        initAlphabetBag(rootElement);
        boardManager = createBoard(rootElement);
    }

    private Element getRootElement(String path) {

        Element rootElement = null;

        try{
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            rootElement = doc.getDocumentElement();
            rootElement.normalize();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return rootElement;
      }

    private void initAlphabetBag(Element rootElement)
    {
        alphabetBag = new ArrayList<Tile>();

        Element alphabetsElement = (Element) rootElement.getElementsByTagName("frenchAlphabet").item(0);

        NodeList alphabetsNodes = alphabetsElement.getElementsByTagName("letter");

        for(int i = 0; i < alphabetsNodes.getLength(); i++)
        {
            Element activeElement = (Element) alphabetsNodes.item(i);

            char caracter = activeElement.getAttribute("text").charAt(0);
            int value = Integer.parseInt(activeElement.getAttribute("value"));
            Tile tile = new Tile(caracter, value);

            int amount = Integer.parseInt(activeElement.getAttribute("amount"));

            for(int j = 0; j < amount; j++)
            {
                alphabetBag.add(tile);
            }
        }
    }


    private BoardManager createBoard(Element rootElement) {
        BoardManager newBoardManager = new BoardManager();
        newBoardManager.createBoard(rootElement);

        return newBoardManager;
    }


    public void startGame() {

        activePlayerIndex = randomGenerator.nextInt(players.size());
        initPlayerRack();

        for (Player player : players) {
            player.getState().initialize(); // rien pour le moment
        }
        goToNextState();
    }


    public void goToNextState() {

        getActivePlayer().nextState();

        if (!getActivePlayer().isActivated()) {

            activateNextPlayer();
        }
    }

    public void passTurn() {
        getActivePlayer().selectNextState(IDState.PENDING);
        goToNextState();
    }

    private void activateNextPlayer() {
        activePlayerIndex = (activePlayerIndex + 1) % players.size();
        getActivePlayer().nextState();
    }

    private void initPlayerRack() {

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < players.size(); j++) {
                Tile tile = alphabetBag.get(randomGenerator.nextInt(alphabetBag.size()));
                players.get(j).addLetter(tile);
                alphabetBag.remove(tile);
            }
        }
    }

    public int lettersLeft() {
        return alphabetBag.size();
    }

    public int getPlayersLeft() {
        return players.size();
    }

    public Player getActivePlayer() {
        return players.get(activePlayerIndex);
    }


    public Square getSquare(int row, int column) {
        return boardManager.getSquare(row, column);
    }

    public char getContentSquare(int row, int column) {
        return boardManager.getContentSquare(row, column);
    }

    public String getPremiumSquare(int row, int column) {
        return boardManager.getPremiumSquare(row, column);
    }

    public void playTile(Square square) {
        getActivePlayer().selectSquare(square);
    }

    public void selectLetter(Tile tile) {
        getActivePlayer().selectTile(tile);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean isReadyForNextPhase() {
        return getActivePlayer().getState().readyForNextState();
    }

    public String getState() {
        return getActivePlayer().getState().getName();
    }

    public void recallTiles() {
    }
}
