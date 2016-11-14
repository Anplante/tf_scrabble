package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.ai.AiGoal;
import ca.qc.bdeb.p56.scrabble.shared.IDState;
import ca.qc.bdeb.p56.scrabble.shared.Direction;
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
    private static final String DEFAULT_DICT_PATH = "resources/dictionary/fr_dictionary.txt";
    private static final String TAG_LETTER = "letter";
    private static final String TAG_TEXT = "text";
    private static final String TAG_VALUE = "value";
    private static final String TAG_AMOUNT = "amount";
    private static final int DOUBLE_VALUE = 2;
    private static final int MAX_CONSECUTIVE_SCORELESS_TURN = 6;
    private transient LinkedList<Observateur> observateurs;
    private static final Random randomGenerator = new Random();


    private BoardManager boardManager;

    private List<Player> players;
    private int activePlayerIndex;
    private static List<Tile> alphabetBag;
    private List<MoveLog> movesHistory;
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

    public BoardManager getBoardManager() {
        return boardManager;
    }

    public List<MoveLog> getMovesHistory() {
        List<MoveLog> moveLogs = new ArrayList<>();
        for (MoveLog moveLog : movesHistory) {
            moveLogs.add(moveLog);
        }
        return moveLogs;
    }

    private void loadParameters(String filePath) {
        Element rootElement = getRootElement(filePath);
        initAlphabetBag(rootElement);
        boardManager = createBoard(rootElement);
        initDictionnary();
    }

    private void initDictionnary() {

        File dictFile = new File(DEFAULT_DICT_PATH);

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
        initPlayerRack();

        for (Player player : players) {
            player.getState().initialize();
        }

        goToNextState();
    }


    public void goToNextState() {

        getActivePlayer().nextState();

        if (!getActivePlayer().isActivated()) {
            if(checkForEndOfTheGame())
            {
                    // TODO Louis : FIN DE LA PARTIE
            }
            else{
                drawTile();
                activateNextPlayer();
            }
        }
    }

    public void passTurn() {
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

    public void playWord() {

        getActivePlayer().selectNextState(IDState.PENDING);
        if (isReadyForNextPhase()) {
            goToNextState();
        }
    }

    public boolean playWord(List<Square> tilesPlaced) {

        Direction direction;
        boolean isAWord = false;

        if (tilesPlaced.size() == 1) {
            direction = findCompleteWordRow(tilesPlaced);
        } else {
            direction = findColumnOrRow(tilesPlaced);
        }
        List<Square> letters = formWordWithTilesPlayed(tilesPlaced, direction);

        if (!letters.isEmpty() && letters.size() > 0) {

            String word = createWord(letters);
            if (dictionary.checkWordExist(word)) {
                if (checkForComboWord(tilesPlaced, direction)) {
                    System.out.println("point donné MainWord");
                    int wordValue = calculateWordPoints(letters);
                    getActivePlayer().addPoints(wordValue);
                    movesHistory.add(new MoveLog(getActivePlayer(), word.toString(), wordValue));
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

    private Direction findCompleteWordRow(List<Square> letters) {
        Square square = letters.get(0);
        Square squareLeft = null;
        Square squareRight = null;
        Direction direction;

        if (square.getPosColumn() + 1 < 15) {
            squareLeft = boardManager.getSquare(square.getPosRow(), square.getPosColumn() + 1);
        }
        if (square.getPosColumn() - 1 >= 0) {
            squareRight = boardManager.getSquare(square.getPosRow(), square.getPosColumn() - 1);
        }

        if ((squareLeft != null && squareLeft.getTileOn() != null) || (squareRight != null && squareRight.getTileOn() != null)) {
            direction = Direction.ROWN;
        } else {
            direction = Direction.COLUMN;
        }
        return direction;
    }

    // TODO Antoine : refactoring a faire ici
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

    private List<Square> formWordWithTilesPlayed(List<Square> lettersPlayed, Direction direction) {

        List<Square> sequenceFound = new ArrayList<>();
        boolean foundSequenceTilesWithAllLetters = false;
        Square start;

        if (direction.equals(Direction.COLUMN)) {
            start = boardManager.getSquare(0, lettersPlayed.get(0).getPosColumn());
        } else {
            start = boardManager.getSquare(lettersPlayed.get(0).getPosRow(), 0);
        }

        while (start != null && !foundSequenceTilesWithAllLetters) {
            if (start.getTileOn() != null) {
                sequenceFound.add(start);
            } else {
                boolean allLettersUtilised = true;
                int indexLetterPlayed = 0;
                while (allLettersUtilised && indexLetterPlayed < lettersPlayed.size()) {
                    if (!sequenceFound.contains(lettersPlayed.get(indexLetterPlayed))) {
                        sequenceFound.clear();
                        allLettersUtilised = false;
                    }
                    indexLetterPlayed++;
                }
                if (allLettersUtilised)
                    foundSequenceTilesWithAllLetters = true;
            }

            if (direction.equals(Direction.COLUMN))
                start = start.getAdjacentDown();
            else
                start = start.getAdjacentRight();

        }

        return sequenceFound;
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

    public void placeAWord() {

        List<Square> squaresPlayable = boardManager.getSquarePositionAvailableToPlay();
        boolean wordFound = false;
        AiGoal ai = new AiGoal(this);
        String validWord;
        int indexCandidatsSquare = 0;

        while (indexCandidatsSquare < squaresPlayable.size() && !wordFound) {
            String letters = getPlayerLettersInStringFormat(getActivePlayer());
            Square start = squaresPlayable.get(indexCandidatsSquare);
            Square currentSquare = start;
            List<String> wordsPlayable = ai.getPossibleWord(letters);
            List<String> tempsWords;
            String suppLetters = "";
            while (!currentSquare.getAdjacentRight().isEmpty()) {
                currentSquare = currentSquare.getAdjacentRight();
                suppLetters += currentSquare.getLetterOn();
            }

            if (!suppLetters.isEmpty()) {
                tempsWords = ai.getPossibleWord(letters);
                wordsPlayable = removeDuplicateWord(wordsPlayable, tempsWords);
            }


            while (!wordsPlayable.isEmpty() && !wordFound) {
                String currentWord = wordsPlayable.get(0);
                int indexLetter = 0;
                boolean playable = true;
                while (indexLetter < currentWord.length() && playable) {
                    if (!currentSquare.isEmpty()) {
                        if (!currentSquare.getLetterOn().equals(String.valueOf(currentWord.charAt(indexLetter)))) {
                            playable = false;
                            indexLetter = 0;
                        }
                    }
                    indexLetter++;
                }
                if (playable) {
                    wordFound = true;
                    validWord = currentWord;
                    int index = 0;

                    while (index < validWord.length()) {
                        if (start.isEmpty()) {
                            Tile tile = getActivePlayer().getTile(String.valueOf(validWord.charAt(index)).toUpperCase());
                            start.setLetter(tile);
                            getActivePlayer().remove(tile);
                        }
                        start = start.getAdjacentRight();
                        index++;
                    }
                    aviserObservateurs();
                } else {
                    wordsPlayable.remove(currentWord);
                }
            }
        }
    }

    private List<String> removeDuplicateWord(List<String> original, List<String> newWords) {
        for (String word : original) {
            if (newWords.contains(word)) {
                newWords.remove(word);
            }
        }
        return newWords;
    }

    private String getPlayerLettersInStringFormat(Player player) {
        StringBuilder letters = new StringBuilder();

        List<Tile> playerTiles = player.getTiles();

        for (Tile tile : playerTiles) {
            letters.append(tile.getLetter().toLowerCase());
        }

        return letters.toString();
    }

    public boolean checkForEndOfTheGame() {

        return checkForPlayerPlayingOut() || checkForSixConsecutiveScorelessTurn();
    }

    private boolean checkForPlayerPlayingOut() {

        boolean endOfGame = false;

        if (getActivePlayer().getTiles().isEmpty() && alphabetBag.isEmpty()) {
            calculPlayOutPointsInStandartFormat();
            endOfGame = true;
        }

        return endOfGame;
    }

    private void calculPlayOutPointsInStandartFormat() {
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

    /**
     * TODO Louis : Si jamais on veut ajouter des modes de jeux différents
     */
    private void calculPlayOytPointsInTournamentFormat() {

        Player currentPlayer = getActivePlayer();

        for (Player player : players) {
            List<Tile> tiles = player.getTiles();

            for (Tile tile : tiles) {
                int value = tile.getValue() * DOUBLE_VALUE;
                currentPlayer.addPoints(value);
            }
        }
    }

    private boolean checkForSixConsecutiveScorelessTurn()
    {
        ListIterator<MoveLog> litr = movesHistory.listIterator(movesHistory.size());

        int countScorelessTurn = 0;
        int index = 0;

        while(litr.hasPrevious() &&  index < MAX_CONSECUTIVE_SCORELESS_TURN)
        {
            if(litr.previous().getWordPoints() == 0)
            {
                countScorelessTurn++;
            }
            index++;
        }

        return countScorelessTurn == MAX_CONSECUTIVE_SCORELESS_TURN;
    }

}
