package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;
import ca.qc.bdeb.p56.scrabble.utility.Observable;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

/**
 * Created by TheFrenchOne on 9/10/2016.
 */
public class Game implements Observable {


    private BoardManager boardManager;
    private List<Player> players;
    private int activePlayerIndex;
    private static List<Tile> alphabetBag;
    private List<Square> tilesPlaced;
    private transient LinkedList<Observateur> observateurs;

    private static final Random randomGenerator = new Random();


    public Game(String filePath, List<Player> players) {

        observateurs = new LinkedList<>();
        loadParameters(filePath);
        tilesPlaced = new ArrayList<>();
        this.players = players;

        for (Player player : players) {
            player.setGame(this);
        }
    }

    private void loadParameters(String filePath) {
        Element rootElement = getRootElement(filePath);
        initAlphabetBag(rootElement);
        boardManager = createBoard(rootElement);
    }

    private Element getRootElement(String path) {

        Element rootElement = null;

        try {
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            rootElement = doc.getDocumentElement();
            rootElement.normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootElement;
    }

    private void initAlphabetBag(Element rootElement) {
        alphabetBag = new ArrayList<Tile>();

        Element alphabetsElement = (Element) rootElement.getElementsByTagName("frenchAlphabet").item(0);

        NodeList alphabetsNodes = alphabetsElement.getElementsByTagName("letter");

        for (int i = 0; i < alphabetsNodes.getLength(); i++) {
            Element activeElement = (Element) alphabetsNodes.item(i);

            char caracter = activeElement.getAttribute("text").charAt(0);
            int value = Integer.parseInt(activeElement.getAttribute("value"));
            Tile tile = new Tile(caracter, value);

            int amount = Integer.parseInt(activeElement.getAttribute("amount"));

            for (int j = 0; j < amount; j++) {
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


    public int getlettersLeft() {
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
        tilesPlaced.add(square);
        goToNextState();
    }

    public void selectLetter(Tile tile) {
        getActivePlayer().selectTile(tile);
        if(isReadyForNextPhase())
        {
            goToNextState();
        }
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

    public void drawTile() {
        while (getActivePlayer().canDraw()) {
            Tile tile = alphabetBag.get(randomGenerator.nextInt(alphabetBag.size()));
            getActivePlayer().addLetter(tile);
            alphabetBag.remove(tile);
        }
    }


    public void playWord() {

        getActivePlayer().addPoints(calculateWordPoints(tilesPlaced)); // TODO Louis : Vérifier que le mot peut être placé
        tilesPlaced.clear();
        getActivePlayer().selectNextState(IDState.PENDING);
        if (isReadyForNextPhase()) {
            goToNextState();
        }

    }

    private int calculateWordPoints(List<Square> letterChain) {
        int points = 0;

        for (Square square : letterChain) {
            points += square.getTileOn().getValue();
        }

        return points;
    }

    @Override
    public void ajouterObservateur(Observateur o) {

        observateurs.add(o);
    }

    @Override
    public void retirerObservateur(Observateur o) {
        observateurs.remove(o);
    }

    @Override
    public void aviserObservateurs() {
        for (Observateur ob : observateurs) {
            ob.changementEtat();
        }
    }

    @Override
    public void aviserObservateurs(Enum<?> e, Object o) {

    }

    public void activateExchangeOption() {
        getActivePlayer().selectNextState(IDState.EXCHANGE);
        goToNextState();
    }


    public void exchangeLetters() {
        getActivePlayer().selectNextState(IDState.PENDING);  // TODO Louis : Bloquer end turn
        goToNextState();
    }

    public void exchangeLetters(ArrayList<Tile> selectedTiles) {

        if (selectedTiles.size() != 0) {

            for (Tile tile : selectedTiles) {
                alphabetBag.add(tile);
                getActivePlayer().remove(tile);
            }
            drawTile();
        } else {
            //TODO MESSSAGE ERROR CANT SWAP NOTHING
        }
    }
}
