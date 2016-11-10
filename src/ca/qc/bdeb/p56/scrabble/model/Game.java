package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.ai.AiGoal;
import ca.qc.bdeb.p56.scrabble.ai.AiPlayer;
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

   /* public boolean playWord(List<Square> tilesPlaced) {

        Direction direction = findColumnOrRow(tilesPlaced);
        boolean isAWord = false;
        int rowOrColumn = findColumnOrRowValue(tilesPlaced);
        List<Square> letters = orderByDirection(tilesPlaced, direction, rowOrColumn);

        if (!letters.isEmpty()) {
            if (letters.size() == 1) {   // Louis : Je ne suis pas certain de comprendre a quoi ca sert? car en partant, il faut jouer un mot avec au moins 2 caracteres
                direction = findCompleteWordRow(letters);
            }
            String word = null;
            letters = createWordList(letters, direction);
            if (letters != null) {
                word = createWord(letters);
                isAWord = verifyAllLetters(letters, direction);
                if (dictionary.checkWordExist(word) && isAWord) {
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
    }*/

    public boolean playWord(List<Square> tilesPlaced) {

        Direction direction = findColumnOrRow(tilesPlaced);
        boolean isAWord = false;
        List<Square> letters = formWordWithTilesPlayed(tilesPlaced, direction);

        if (!letters.isEmpty() && letters.size() > 1) {

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

    // TODO Antoine : refactoring a faire ici
    private boolean verifyAllLetters(List<Square> letters, Direction direction) {
        boolean allOk = true;
        int points = 0;
        for (int v = 0; v < letters.size() && allOk; v++) {

            StringBuilder word = new StringBuilder();
            Square square = letters.get(v);
            int rowOrColumn;
            int reverse;
            if (direction.equals(Direction.COLUMN)) {
                rowOrColumn = square.getPosRow();
                reverse = square.getPosColumn();
            } else {
                rowOrColumn = square.getPosColumn();
                reverse = square.getPosRow();
            }
            boolean isAWord = true;
            ArrayList<String> wordWrongSide = new ArrayList<>();

            for (int i = reverse - 1; i >= 0 && isAWord; i--) {
                Square squareAdjacent;
                if (direction.equals(Direction.COLUMN)) {
                    squareAdjacent = boardManager.getSquare(rowOrColumn, i);
                } else {
                    squareAdjacent = boardManager.getSquare(i, rowOrColumn);
                }
                if (squareAdjacent.getTileOn() != null) {
                    wordWrongSide.add(squareAdjacent.getLetterOn());
                } else {
                    isAWord = false;
                    for (int j = wordWrongSide.size() - 1; j >= 0; j--) {
                        word.append(wordWrongSide.get(j));
                    }
                }
            }
            word.append(square.getLetterOn());
            isAWord = true;
            for (int i = reverse + 1; i < boardManager.BOARD_SIZE && isAWord; i++) {
                Square squareAdjacent;
                if (direction.equals(Direction.COLUMN)) {
                    squareAdjacent = boardManager.getSquare(rowOrColumn, i);
                } else {
                    squareAdjacent = boardManager.getSquare(i, rowOrColumn);
                }
                if (squareAdjacent.getTileOn() != null) {
                    word.append(squareAdjacent.getTileOn().getLetter());
                } else {
                    isAWord = false;
                }
            }
            if (!word.toString().equals(letters.get(v).getLetterOn())) {
                if (dictionary.checkWordExist(word.toString().toLowerCase())) {
                    System.out.println("point donné Verifier");
                    points += calculateWordPoints(letters);
                } else {
                    allOk = false;
                }
            }
        }
        if (allOk) {
            getActivePlayer().addPoints(points);
        }
        return allOk;
    }

    private Direction findCompleteWordRow(List<Square> letters) {
        Square square = letters.get(0);
        Square squareLeft = null;
        Square squareRight = null;
        if (square.getPosColumn() + 1 < 15) {
            squareLeft = boardManager.getSquare(square.getPosRow(), square.getPosColumn() + 1);
        }
        if (square.getPosColumn() - 1 >= 0) {
            squareRight = boardManager.getSquare(square.getPosRow(), square.getPosColumn() - 1);
        }
        Direction direction = null;
        if ((squareLeft != null && squareLeft.getTileOn() != null) || (squareRight != null && squareRight.getTileOn() != null)) {
            direction = Direction.ROWN;
        } else {
            direction = Direction.COLUMN;
        }
        return direction;
    }

    // TODO Antoine : refactoring a faire ici
    private List<Square> createWordList(List<Square> letters, Direction direction) {
        boolean isConnected = false;
        List<Square> allLetters = new ArrayList<>();
        Square square = letters.get(0);
        int rowOrColumn;
        int reverseRowOrColumn;


        if (direction.equals(Direction.COLUMN)) {
            rowOrColumn = square.getPosColumn();
            reverseRowOrColumn = square.getPosRow();
        } else {
            rowOrColumn = square.getPosRow();
            reverseRowOrColumn = square.getPosColumn();
        }
        boolean sameWord = true;
        ArrayList<Square> wordWrongSide = new ArrayList<>();


        for (int i = reverseRowOrColumn - 1; i >= 0 && sameWord; i--) {
            Square squareAdjacent;
            if (direction.equals(Direction.COLUMN)) {
                squareAdjacent = boardManager.getSquare(i, rowOrColumn);
            } else {
                squareAdjacent = boardManager.getSquare(rowOrColumn, i);
            }
            if (squareAdjacent.getTileOn() != null) {
                isConnected = verifyIsAConnected(letters, squareAdjacent);
                wordWrongSide.add(squareAdjacent);
            } else {
                sameWord = false;
            }
        }
        for (int j = wordWrongSide.size() - 1; j >= 0; j--) {
            allLetters.add(wordWrongSide.get(j));
        }
        sameWord = true;
        for (int i = reverseRowOrColumn; i < boardManager.BOARD_SIZE && sameWord; i++) {
            Square squareAdjacent;
            if (direction.equals(Direction.COLUMN)) {
                squareAdjacent = boardManager.getSquare(i, rowOrColumn);
            } else {
                squareAdjacent = boardManager.getSquare(rowOrColumn, i);
            }
            if (squareAdjacent.getTileOn() != null) {
                if (!isConnected) {
                    isConnected = verifyIsAConnected(letters, squareAdjacent);
                }
                allLetters.add(squareAdjacent);
            } else {
                sameWord = false;
            }
        }
        if (!isConnected) {
            allLetters = null;
        }
        return allLetters;
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

    private boolean verifyIsAConnected(List<Square> letters, Square adjacent) {
        boolean isConnected = false;
        int culumnCurrentSquare = adjacent.getPosColumn();
        int rowCurrentSquare = adjacent.getPosRow();
        if ((culumnCurrentSquare + 1 < 15 && boardManager.getSquare(rowCurrentSquare, culumnCurrentSquare + 1).getTileOn() != null)
                || (culumnCurrentSquare - 1 >= 0 && boardManager.getSquare(rowCurrentSquare, culumnCurrentSquare - 1).getTileOn() != null)) {
            isConnected = true;
        }
        if (rowCurrentSquare + 1 < 15 && boardManager.getSquare(rowCurrentSquare + 1, culumnCurrentSquare).getTileOn() != null
                || rowCurrentSquare - 1 >= 0 && boardManager.getSquare(rowCurrentSquare - 1, culumnCurrentSquare).getTileOn() != null) {
            isConnected = true;
        }
        if (movesHistory.size() == 0) {
            isConnected = true;
        }

        return isConnected;
    }

    /**
     * Méthode qui refait la liste des lettres placé en jeu en ordre pour la vérification
     *
     * @param lettersPlayed
     * @param direction
     * @param rowOrColumn
     * @return
     */
    private List<Square> orderByDirection(List<Square> lettersPlayed, Direction direction, int rowOrColumn) {

        List<Square> newLetters = new ArrayList<>();
        Square square;
        int indexBoard = 0;

        while (indexBoard < boardManager.BOARD_SIZE) {

            if (direction.equals(Direction.COLUMN)) {
                square = boardManager.getSquare(indexBoard, rowOrColumn);
            } else {
                square = boardManager.getSquare(rowOrColumn, indexBoard);
            }
            if (lettersPlayed.contains(square)) {
                newLetters.add(square);
            }
            indexBoard++;
        }
        return newLetters;
    }

    private List<Square> formWordWithTilesPlayed(List<Square> lettersPlayed, Direction direction) {

        List<Square> sequenceFound = new ArrayList<>();
        boolean foundSequenceTilesWithAllLetters = false;
        Square start;

        if (direction.equals(Direction.COLUMN))
            start = boardManager.getSquare(0, lettersPlayed.get(0).getPosColumn());
        else
            start = boardManager.getSquare(lettersPlayed.get(0).getPosRow(), 0);


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
        String mot = "";
        for (Square square : letterChain) {
            mot += square.getLetterOn();
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

        System.out.println("Pts:" + points + " Mot:" + mot);

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

    private void playFirstWord(Player player) {
        AiGoal ai = new AiGoal(this);
        String letters = getPlayerLettersInStringFormat(player);
        List<String> wordsPlayable = ai.getPossibleWord(letters);
        String wordToPlay = wordsPlayable.get(0);   // TODO Louis : jouer le mot le plus long?

        Square start = boardManager.getSquare(boardManager.BOARD_CENTER, boardManager.BOARD_CENTER - wordToPlay.length());

        char[] wordLetters = wordToPlay.toCharArray();

        for (int i = 0; i < wordLetters.length; i++) {
            Tile playerTile = player.getTile(String.valueOf(wordLetters[i]));
            start.setLetter(playerTile);
            player.remove(playerTile);
        }
    }




  /*  public void PlaceAWord() {
        List<Square> squaresPlayable = boardManager.getSquarePositionAvailableToPlay();
        boolean wordFound = false;
        AiGoal ai = new AiGoal(this);
        String letters = getPlayerLettersInStringFormat(getActivePlayer());
        List<String> wordsPlayable = ai.getPossibleWord(letters);
        int indexCandidatsSquare = 0;
        int indexWord = 0;
        while (indexCandidatsSquare < squaresPlayable.size() && !wordFound) {
            boolean wordPlayable = false;
            int index = 0;
            List<Square> cases = new ArrayList<>();
            Square start = squaresPlayable.get(indexCandidatsSquare);
            Square currentSquare = start;

            while (indexWord < wordsPlayable.size() && !wordPlayable) {


                if (start.getPosColumn() + wordsPlayable.get(indexWord).length() < boardManager.BOARD_SIZE) {
                    boolean mayBePlayable = true;
                    while (index < letters.length() && mayBePlayable) {

                        if (currentSquare != null) {
                            if (currentSquare.getTileOn() == null) {
                                cases.add(currentSquare);
                                currentSquare = currentSquare.getAdjacentRight();
                                index++;
                            } else {
                                start = start.getAdjacentLeft();
                                cases.clear();
                                currentSquare = start;
                                index = 0;
                                mayBePlayable = false;
                            }
                        } else {
                            break;
                        }
                    }
                } else {
                    indexWord++;
                }
            }

            indexCandidatsSquare++;

        }

    }*/


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

            if(!suppLetters.isEmpty())
            {
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
                            start.setLetter(getActivePlayer().getTile(String.valueOf(validWord.charAt(index))));
                        }
                    }
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
            letters.append(tile.getLetter());
        }

        return letters.toString();
    }
}
