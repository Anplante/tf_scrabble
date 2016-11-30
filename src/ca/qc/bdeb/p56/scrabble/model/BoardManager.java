package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.Direction;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Classe qui s'occupe de gérer le plateau de jeu.
 *
 * Created by Louis Luu Lim on 9/10/2016.
 */
public class BoardManager {

    public static final int BOARD_SIZE = 15;
    public static final int BOARD_CENTER = 7;

    private static final String TAG_PREMIUMS = "premiums";
    private static final String TAG_BOARD = "board";
    private static final String TAG_PREMIUM = "premium";
    private static final String TAG_NAME = "name";
    private static final String TAG_ROW = "row";
    private static final String TAG_COLUMN = "col";
    private static final String TAG_MULTIPLIER = "multiplier";
    private static final String TAG_TYPE = "type";
    private static final String TAG_LETTER_SCORE = "letterscore";
    private static final String TAG_WORD_SCORE = "wordscore";

    private static Map<String, Premium.Type> premiumTypeMap;

    static {
        premiumTypeMap = new TreeMap<>();
        premiumTypeMap.put(TAG_LETTER_SCORE, Premium.Type.LETTER_SCORE);
        premiumTypeMap.put(TAG_WORD_SCORE, Premium.Type.WORD_SCORE);
    }

    private Map<String, Premium> premiums;
    private Board board;

    public BoardManager() {
        board = null;
    }

    public Board getBoard() {
        return board;
    }

    public void createBoard(Element rootElement) {

        board = new Board(BOARD_SIZE);

        Element premiumsElement = (Element) rootElement.getElementsByTagName(TAG_PREMIUMS).item(0);
        initPremiums(premiumsElement);

        Element boardElement = (Element) rootElement.getElementsByTagName(TAG_BOARD).item(0);

        NodeList premiumsNodes = boardElement.getElementsByTagName(TAG_PREMIUM);

        for (int i = 0; i < premiumsNodes.getLength(); i++) {
            Element activeElement = (Element) premiumsNodes.item(i);

            String identifier = activeElement.getAttribute(TAG_NAME);
            int row = Integer.parseInt(activeElement.getAttribute(TAG_ROW));
            int column = Integer.parseInt(activeElement.getAttribute(TAG_COLUMN));
            Premium premium = premiums.get(identifier);
            board.getSquare(row, column).setPremium(premium);
        }
    }

    private void initPremiums(Element premiumElement) {

        premiums = new TreeMap<>();

        NodeList premiumNodes = premiumElement.getElementsByTagName(TAG_PREMIUM);

        for (int i = 0; i < premiumNodes.getLength(); i++) {

            Element activeElement = (Element) premiumNodes.item(i);
            String type = activeElement.getAttribute(TAG_TYPE);

            Premium.Type premiumType = premiumTypeMap.get(type);
            int multiplier = Integer.parseInt(activeElement.getAttribute(TAG_MULTIPLIER));

            String identifier = activeElement.getAttribute(TAG_NAME);
            Premium premium = new Premium(identifier, premiumType, multiplier);

            premiums.put(identifier, premium);
        }
    }

    public Direction checkIfWordIsVerticalOrHorizontal(List<Square> letters) {

        Square square = letters.get(0);
        Direction direction = Direction.COLUMN;

        if (letters.size() == 1) {
            if(square.getAdjacentLeft() != null && !square.getAdjacentLeft().isEmpty()
            || square.getAdjacentRight() != null && !square.getAdjacentRight().isEmpty())
            {
                direction = Direction.ROWN;
            }
        }
        else
        {
            if (letters.size() > 1) {
                if (letters.get(1).getPosColumn() != letters.get(0).getPosColumn()) {
                    direction = Direction.ROWN;
                }
            }
        }
        return direction;
    }

    public List<Square> formWordWithTilesPlayed(List<Square> lettersPlayed, Direction direction) {

        List<Square> sequenceFound = new ArrayList<>();
        boolean foundSequenceTilesWithAllLetters = false;
        Square start;

        if (direction.equals(Direction.COLUMN)) {
            start = getSquare(0, lettersPlayed.get(0).getPosColumn());
        } else {
            start = getSquare(lettersPlayed.get(0).getPosRow(), 0);
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


    public Square getSquare(int row, int column) {
        return board.getSquare(row, column);
    }

}
