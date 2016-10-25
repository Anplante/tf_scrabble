package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;
import ca.qc.bdeb.p56.scrabble.utility.Direction;
import ca.qc.bdeb.p56.scrabble.utility.Observable;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;
import java.util.List;

/**
 * Created by TheFrenchOne on 9/10/2016.
 */
public class Game implements Observable {


    private transient LinkedList<Observateur> observateurs;
    private static final Random randomGenerator = new Random();

    private BoardManager boardManager;
    private List<Player> players;
    private int activePlayerIndex;
    private static List<Tile> alphabetBag;

    private List<Move> movesHistory;
    private Dictionary dictionary;


    public Game(String filePath, List<Player> players) {

        observateurs = new LinkedList<>();
        movesHistory = new ArrayList<>();
        loadParameters(filePath);
        this.players = players;

        for (Player player : players) {
            player.setGame(this);
        }
    }


    public Board getBoard() {
        return boardManager.getBoard();
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

    public String getContentSquare(int row, int column) {
        return boardManager.getContentSquare(row, column);
    }

    public String getPremiumSquare(int row, int column) {
        return boardManager.getPremiumSquare(row, column);
    }


    public List<Move> getMovesHistory() {
        List<Move> moves = new ArrayList<>();
        for (Move move : movesHistory) {
            moves.add(move);
        }
        return moves;
    }

    private void loadParameters(String filePath) {
        Element rootElement = getRootElement(filePath);
        initAlphabetBag(rootElement);
        boardManager = createBoard(rootElement);
        initDictionnary();
    }

    private void initDictionnary() {

        File dictFile = new File("resources/dictionary/fr_dictionary.txt");

        dictionary = Dictionary.getINSTANCE();
        dictionary.loadDictinnary(dictFile);
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

            String caracter = activeElement.getAttribute("text");
            int value = Integer.parseInt(activeElement.getAttribute("value"));

            int amount = Integer.parseInt(activeElement.getAttribute("amount"));

            for (int j = 0; j < amount; j++) {
                alphabetBag.add(new Tile(caracter, value));
            }
        }
        Collections.shuffle(alphabetBag);
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
            drawTile();
            activateNextPlayer();

        }
    }

    public void passTurn() {
        getActivePlayer().selectNextState(IDState.PENDING);
        goToNextState();
        // TODO Louis : bloquer quand le joueur place un mot ou annuler les autres actions
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


    public void playTile(Square square) {
        getActivePlayer().selectSquare(square);
    }

    public void selectLetter(Tile tile) {
        getActivePlayer().selectTile(tile);
        if (isReadyForNextPhase()) {
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


    /**
     * Pour l'utilisation des tests
     */
    public void emptyBag() {
        alphabetBag.clear();
    }


    public void drawTile() {

        while (getActivePlayer().canDraw() && !alphabetBag.isEmpty()) {

            Tile tile = alphabetBag.get(alphabetBag.size() - 1);
            getActivePlayer().addLetter(tile);
            alphabetBag.remove(tile);
        }
        aviserObservateurs();
    }


    public void playWord() {

        getActivePlayer().selectNextState(IDState.PENDING);
        if (isReadyForNextPhase()) {
            goToNextState();
        }
    }

    public boolean playWord(List<Square> tilesPlaced) {

        Direction direction = findColumnOrRow(tilesPlaced);
        boolean isAWord = false;
        int rowOrColumn = findColumnOrRowValue(tilesPlaced);
        List<Square> letters = orderByDirection(tilesPlaced, direction, rowOrColumn);

        if (!letters.isEmpty()) {
            String word = createWord(letters).toLowerCase();

            if (dictionary.checkWordExist(word)) {
                movesHistory.add(new Move(getActivePlayer(), word.toString()));
                getActivePlayer().addPoints(calculateWordPoints(letters));
                isAWord = true;
            }
        }
        return isAWord;
    }

    private List<Square> orderByDirection(List<Square> lettersPlayed, Direction direction, int rowOrColumn) {

        List<Square> newLetters = new ArrayList<>();
        Square square;
        int indexBoard = 0;
        boolean foundWordUtilisingAllLetters = false;

        while (!foundWordUtilisingAllLetters && indexBoard < boardManager.BOARD_SIZE) {
            if (direction.equals(Direction.COLUMN)) {
                square = boardManager.getSquare(indexBoard, rowOrColumn);
            } else {
                square = boardManager.getSquare(rowOrColumn, indexBoard);
            }
            if (square.getTileOn() != null) {
                newLetters.add(square);
            } else {
                boolean allLettersUtilised = true;
                int indexLetterPlayed = 0;
                while (allLettersUtilised && indexLetterPlayed < lettersPlayed.size()) {
                    if (!newLetters.contains(lettersPlayed.get(indexLetterPlayed))) {
                        newLetters.clear();
                        allLettersUtilised = false;
                    }
                    indexLetterPlayed++;
                }
                if (allLettersUtilised)
                    foundWordUtilisingAllLetters = true;
            }
            indexBoard++;
        }
        return newLetters;
    }


    private String createWord(List<Square> letters) {
        StringBuilder word = new StringBuilder();
        for (Square square : letters) {
            word.append(square.getTileOn().getLetter());
        }
        return word.toString();
    }

    private Direction findColumnOrRow(List<Square> letters) {

        Direction direction = Direction.COLUMN;

        int column = letters.get(0).getPosColumn();
        if (letters.size() > 1) {
            if (letters.get(1).getPosColumn() != column) {
                direction = Direction.ROWN;
            }
        }
        return direction;
    }

    private int findColumnOrRowValue(List<Square> letters) {
        int column = letters.get(0).getPosColumn();
        if (letters.size() > 1) {
            if (letters.get(1).getPosColumn() != column) {
                column = letters.get(1).getPosRow();
            }
        }
        return column;
    }


    public void recallTiles() {
        getActivePlayer().selectNextState(IDState.SELECT_ACTION);
        if (isReadyForNextPhase()) {
            goToNextState();
        }
    }

    public void recallTiles(List<Square> tilesPlaced) {

        if (tilesPlaced != null) {
            for (Square tileLocation : tilesPlaced) {
                getActivePlayer().addLetter(tileLocation.getTileOn());
                tileLocation.setLetter(null);
            }
            getActivePlayer().aviserObservateurs();
        }
    }

    public int calculateWordPoints(List<Square> letterChain) {

        int points = 0;
        int wordMultiplier = 1;

        for (Square square : letterChain) {

            int letterMultiplier = 1;

            Premium premium = square.getPremium();

            if (premium != null) {
                switch (premium.getType()) {
                    case LETTER_SCORE:
                        letterMultiplier = premium.getMultiplier();
                        break;
                    case WORD_SCORE:
                        wordMultiplier *= premium.getMultiplier();
                        break;
                }
                square.setPremium(null);
            }
            points += letterMultiplier * square.getTileOn().getValue();
        }
        points *= wordMultiplier;

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


    public void exchangeLetters(List<Tile> tilesSelected) {

        for (Tile tileToExchange : tilesSelected) {
            getActivePlayer().remove(tileToExchange);
        }

        drawTile();

        for (Tile tileToExchange : tilesSelected) {
            alphabetBag.add(tileToExchange);
        }

        Collections.shuffle(alphabetBag);
    }

    public void replacePlayerTilesInOrder(List<Tile> originalOrder) {

        if (originalOrder != null) {
            List<Tile> currentOrder = getActivePlayer().getTiles();

            if (currentOrder.containsAll(originalOrder)) {

                for (Tile tile : originalOrder) {
                    getActivePlayer().remove(tile);
                }
                for (Tile tile : originalOrder) {
                    getActivePlayer().addLetter(tile);
                }
            }
        }
    }

    public boolean isValidWord(String wordTested) {

        return dictionary.checkWordExist(wordTested);
    }

    public void exchangeLetter() {
        getActivePlayer().selectNextState(IDState.EXCHANGE);
        goToNextState();
    }

    public void cancelExchange() {

        getActivePlayer().selectNextState(IDState.SELECT_ACTION);
        goToNextState();
    }
}
