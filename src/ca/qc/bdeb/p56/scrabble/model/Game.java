package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.model.Log.*;
import ca.qc.bdeb.p56.scrabble.shared.Event;
import ca.qc.bdeb.p56.scrabble.shared.IDState;
import ca.qc.bdeb.p56.scrabble.shared.Direction;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import ca.qc.bdeb.p56.scrabble.utility.Observable;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe du jeu de scrabbleé
 * <p>
 * Created by Louis Luu Lim on 9/10/2016.
 */
public class Game implements Observable {

    public static final int MAX_TILES_IN_HAND = 7;
    private static final String TAG_FRENCH_ALPHABET = "frenchAlphabet";

    private static final String TAG_LETTER = "letter";
    private static final String TAG_TEXT = "text";
    private static final String TAG_VALUE = "value";
    private static final String TAG_AMOUNT = "amount";
    private static final int DOUBLE_VALUE = 2;
    private static final int MAX_CONSECUTIVE_SCORELESS_TURN = 6;
    private transient LinkedList<Observateur> observateurs;
    private static final Random randomGenerator = new Random();
    private boolean waitingNextTurn;

    private BoardManager boardManager;
    private List<Player> players;
    private int activePlayerIndex;
    private static List<Tile> alphabetBag;
    private Dictionary dictionary;
    private int turn;
    private List<Player> eliminatedPlayers;
    private boolean isEndGame;
    private LogManager logManager;

    public Game(String filePath, List<Player> players) {
        waitingNextTurn = false;
        isEndGame = false;
        observateurs = new LinkedList<>();
        eliminatedPlayers = new ArrayList<>();
        logManager = new LogManager();
        this.players = players;

        for (Player player : players) {
            player.setGame(this);
        }

        loadParameters(filePath);
    }

    public boolean isWaitingNextTurn() {
        return waitingNextTurn;
    }

    public void setWaitingNextTurn(boolean waitingNextTurn) {
        this.waitingNextTurn = waitingNextTurn;
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

    public boolean isEndGame(){
        return isEndGame;
    }
    public Square getSquare(int row, int column) {
        return boardManager.getSquare(row, column);
    }

    public String getContentSquare(int row, int column) {
        return boardManager.getContentSquare(row, column);
    }

    public BoardManager getBoardManager() {
        return boardManager;
    }

    public LogManager getLogManager()
    {
        return logManager;
    }


    private void loadParameters(String filePath) {
        Element rootElement = getRootElement(filePath);
        initAlphabetBag(rootElement);
        boardManager = createBoard(rootElement);
        initDictionnary();
    }

    private void initDictionnary() {

        File dictFile = new File(ConstanteComponentMessage.DEFAULT_DICT_PATH);

        dictionary = Dictionary.getINSTANCE();
        dictionary.loadDictinnary(dictFile);
    }

    private Element getRootElement(String path) {

        Element rootElement = null;
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            File xmlFile = new File(path);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            rootElement = doc.getDocumentElement();
            rootElement.normalize();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(dbFactory.toString()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(path.toString()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(path.toString()).log(Level.SEVERE, null, ex);
        }

        return rootElement;
    }

    private void initAlphabetBag(Element rootElement) {

        alphabetBag = new ArrayList<>();
        Element alphabetsElement = (Element) rootElement.getElementsByTagName(TAG_FRENCH_ALPHABET).item(0);
        NodeList alphabetsNodes = alphabetsElement.getElementsByTagName(TAG_LETTER);
        initAllLetters(alphabetsNodes);
    }


    private void initAllLetters(NodeList alphabetsNodes) {

        for (int i = 0; i < alphabetsNodes.getLength(); i++) {
            Element activeElement = (Element) alphabetsNodes.item(i);

            String character = activeElement.getAttribute(TAG_TEXT);
            int value = Integer.parseInt(activeElement.getAttribute(TAG_VALUE));

            int amount = Integer.parseInt(activeElement.getAttribute(TAG_AMOUNT));

            for (int j = 0; j < amount; j++) {
                alphabetBag.add(new Tile(character, value));
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
        turn = 1;
        initPlayerRack();

        for (Player player : players) {
            player.getState().initialize();
        }

        goToNextState();
    }


    public void goToNextState() {

        do {
            getActivePlayer().nextState();


            if (!getActivePlayer().isActivated()) {

                if (checkForEndOfTheGame()) {

                    setEndOfGame();
                    aviserObservateurs();

                }
                else {

                    if(isLastTurnPlayer(getActivePlayer()))
                    {
                        turn++;
                    }
                    drawTile();
                    activateNextPlayer();


                }
            }

        }while(!getActivePlayer().isActivated() && !isEndGame());
    }

    private boolean isLastTurnPlayer(Player activePlayer) {
        return activePlayer.equals(players.get(players.size()-1));
    }

    public void passTurn() {

        waitingNextTurn = true;
        logManager.addPassedLog(getActivePlayer(), turn);
        getActivePlayer().selectNextState(IDState.PENDING);
        goToNextState();
        // TODO Louis : bloquer quand le joueur place un mot ou annuler les autres actions
    }

    private void activateNextPlayer() {

        activePlayerIndex = (activePlayerIndex + 1) % players.size();
        getActivePlayer().changePlayer();
    }

    private void initPlayerRack() {

        for (int i = 0; i < MAX_TILES_IN_HAND; i++) {

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

    public void selectPlayWordAction() {

        getActivePlayer().selectNextState(IDState.PENDING);
        if (isReadyForNextPhase()) {
            goToNextState();
        }
    }

    public boolean playWord(List<Square> tilesPlaced) {

        Direction direction;
        boolean isAWord = false;


        direction = boardManager.checkIfWordIsVerticalOrHorizontal(tilesPlaced);

        List<Square> letters = boardManager.formWordWithTilesPlayed(tilesPlaced, direction);

        if (!letters.isEmpty() && letters.size() > 0) {

            String word = createWord(letters);
            if (dictionary.checkWordExist(word)) {
                if (checkForComboWord(tilesPlaced, direction)) {
                    int wordValue = calculateWordPoints(letters);
                    getActivePlayer().addPoints(wordValue);
                    logManager.addWordLog(getActivePlayer(), turn, word, wordValue);
                    isAWord = true;
                } else {
                    isAWord = false;
                }
            }
        }
        return isAWord;
    }

    private String createWord(List<Square> letters) {
        StringBuilder word = new StringBuilder();
        for (Square letter : letters) {
            word.append(letter.getLetterOn());
        }
        return word.toString().toLowerCase();
    }

    // TODO Antoine : refactoring a faire ici -
    private boolean checkForComboWord(List<Square> tilesPlaced, Direction direction) {

        boolean allCombinaisonAreValid = true;
        int indexTilesPlaced = 0;
        List<List<Square>> combos = new ArrayList<>();

        while (indexTilesPlaced < tilesPlaced.size() && allCombinaisonAreValid) {

            Square start = null;

            if (direction.equals(Direction.COLUMN)) {
                Square adjacentLeft = tilesPlaced.get(indexTilesPlaced).getAdjacentLeft();
                while (adjacentLeft != null && adjacentLeft.getTileOn() != null) {
                    start = adjacentLeft;
                    adjacentLeft = adjacentLeft.getAdjacentLeft();
                }

            } else {
                Square adjacentUp = tilesPlaced.get(indexTilesPlaced).getAdjacentUp();
                while (adjacentUp != null && adjacentUp.getTileOn() != null) {
                    start = adjacentUp;
                    adjacentUp = adjacentUp.getAdjacentUp();
                }
            }

            if (start == null) {
                start = tilesPlaced.get(indexTilesPlaced);
            }

            List<Square> sequenceFound = new ArrayList<>();
            while (start.getTileOn() != null) {
                sequenceFound.add(start);
                if (direction.equals(Direction.COLUMN))
                    start = start.getAdjacentRight();
                else
                    start = start.getAdjacentDown();
            }
            if (sequenceFound.size() > 1) {
                String word = createWord(sequenceFound);
                if (dictionary.checkWordExist(word)) {
                    combos.add(sequenceFound);
                } else {
                    allCombinaisonAreValid = false;
                }
            }
            indexTilesPlaced++;
        }

        if (allCombinaisonAreValid) {
            for (List<Square> combo : combos) {
                int wordValue = calculateWordPoints(combo);
                getActivePlayer().addPoints(wordValue);
            }
        }

        return allCombinaisonAreValid;
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
        throw new UnsupportedOperationException("Not supported yet.");
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
        logManager.addExchangedLog(getActivePlayer(), turn, tilesSelected);

        turn++;
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

    public boolean checkForEndOfTheGame() {

        return checkForPlayerPlayingOut() || checkForSixConsecutiveScorelessTurn() || checkOnlyOnePlayerLeft();
    }

    public void setEndOfGame()
    {
        if(checkForPlayerPlayingOut())
        {
            calculatePlayOutPointsInStandartFormat();
        }

        for(Player p : players)
        {
            p.setState(new StateEnding(p));
        }

        isEndGame = true;
    }

    private boolean checkForPlayerPlayingOut() {

        return  (getActivePlayer().getTiles().isEmpty() && alphabetBag.isEmpty());
    }

    private void calculatePlayOutPointsInStandartFormat() {
        Player currentPlayer = getActivePlayer();

        for (Player player : players) {
            List<Tile> tiles = player.getTiles();

            for (Tile tile : tiles) {
                int value = tile.getValue();
                currentPlayer.addPoints(value);
                player.reducePoints(value);
            }
        }
    }

    private boolean checkForSixConsecutiveScorelessTurn() {

        List<MoveLog> movesHistory = logManager.getMovesHistory();

        ListIterator<MoveLog> litr = movesHistory.listIterator(movesHistory.size());

        int countScorelessTurn = 0;
        int index = 0;

        while (litr.hasPrevious() && index < MAX_CONSECUTIVE_SCORELESS_TURN) {
            if (litr.previous().getMovePoints() == 0) {
                countScorelessTurn++;
            }
            index++;
        }

        return countScorelessTurn == MAX_CONSECUTIVE_SCORELESS_TURN;
    }


    private boolean checkOnlyOnePlayerLeft() {
        return players.size() - eliminatedPlayers.size() == 1;
    }

    public void forfeit() {

        Player currentPlayer = getActivePlayer();

        eliminatedPlayers.add(currentPlayer);
        currentPlayer.forfeit();
        goToNextState();

    }

    public int getTurn() {
        return turn;
    }
}
