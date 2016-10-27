package ca.qc.bdeb.p56.scrabble.model;

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

    private static Map<String, Premium.Type> premiumTypeMap;

    static {
        premiumTypeMap = new TreeMap<>();
        premiumTypeMap.put("letterscore", Premium.Type.LETTER_SCORE);
        premiumTypeMap.put("wordscore", Premium.Type.WORD_SCORE);
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


    public String getContentSquare(int row, int column) {

        Square square = board.getSquare(row, column);

        String content = square.getLetterOn();

        if (content.isEmpty()) {
            content += getPremiumSquare(row, column);
        }

        return content;
    }

    public String getPremiumSquare(int row, int column) {

        String value = "";
        Square square = board.getSquare(row, column);
        Premium premiumContent = square.getPremium();

        if (premiumContent != null) {
            for (Map.Entry<String, Premium> premium : premiums.entrySet()) {
                if (premiumContent.equals(premium.getValue())) {
                    value = premium.getKey();
                }
            }
        }
        return value;
    }

    private void initPremiums(Element premiumElement) {
        premiums = new TreeMap<>();

        NodeList premiumNodes = premiumElement.getElementsByTagName(TAG_PREMIUM);

        for (int i = 0; i < premiumNodes.getLength(); i++) {
            Element activeElement = (Element) premiumNodes.item(i);

            String identifier = activeElement.getAttribute(TAG_NAME);
            String type = activeElement.getAttribute(TAG_TYPE);
            int multiplier = Integer.parseInt(activeElement.getAttribute(TAG_MULTIPLIER));

            Premium.Type premiumType = premiumTypeMap.get(type);
            Premium premium = new Premium(premiumType, multiplier);
            premiums.put(identifier, premium);
        }
    }

    public Square getSquare(int row, int column) {
        return board.getSquare(row, column);
    }

    public List<Square> getSquarePositionAvailableToPlay() {

        List<Square> squaresAvailable = new ArrayList<>();
        Square centerSquare = board.getSquare(BOARD_CENTER, BOARD_CENTER);

        if (centerSquare.getLetterOn() != null) {

            List<Square> candidats = new ArrayList<>();
            candidats.add(centerSquare);

            while (!candidats.isEmpty()) {

                Square candidatAnalysed = candidats.get(0);
                List<Square> neighbours = candidatAnalysed.getNeighbours();

                boolean available = false;

                for (Square neighbour : neighbours) {
                    if (neighbour.getLetterOn() == null) {
                        available = true;
                    }
                    else{
                        candidats.add(neighbour);
                    }
                }

                if (available && !squaresAvailable.contains(candidatAnalysed)) {
                    squaresAvailable.add(candidatAnalysed);
                }
                candidats.remove(candidatAnalysed);
            }
        } else {
            squaresAvailable.add(centerSquare);
        }
        return squaresAvailable;
    }


/*
    public List<Square> getSquarePositionAvailableToPlay() {
        List<Square> squaresAvailable = new ArrayList<>();


        Square centerSquare = board.getSquare(BOARD_CENTER, BOARD_CENTER);

        List<Square> candidats = new ArrayList<>();
        candidats.add(centerSquare);




        while (!candidats.isEmpty()) {


            Square candidatAnalysed = candidats.get(0);

            if (candidatAnalysed.getLetterOn() == null) {

                if(!squaresAvailable.contains(candidatAnalysed))
                {
                    squaresAvailable.add(candidatAnalysed);
                }
            } else {

                List<Square> neighbours = candidatAnalysed.getNeighbours();

                candidats.addAll(neighbours);
            }
            candidats.remove(candidatAnalysed);
        }
        return squaresAvailable;
    }*/
}
