package ca.qc.bdeb.p56.scrabble.model;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by TheFrenchOne on 9/10/2016.
 */
public class BoardManager {

    public static final int BOARD_SIZE = 15;

    private Map<String, Premium> premiums;
    private static Map<String, Premium.Type> premiumTypeMap;
    private Board board;

    static {
        premiumTypeMap = new TreeMap<String, Premium.Type>();
        premiumTypeMap.put("letterscore", Premium.Type.LETTER_SCORE);
        premiumTypeMap.put("wordscore", Premium.Type.WORD_SCORE);
    }



    public BoardManager()
    {
        board = null;
    }

    public Board getBoard(){
        return board;
    }

    public void createBoard(Element rootElement) {

        board = new Board(BOARD_SIZE);


        Element premiumsElement = (Element) rootElement.getElementsByTagName("premiums").item(0);
        initPremiums(premiumsElement);

        Element boardElement = (Element) rootElement.getElementsByTagName("board").item(0);

        NodeList premiumsNodes = boardElement.getElementsByTagName("premium");

        for(int i = 0; i < premiumsNodes.getLength(); i++)
        {
            Element activeElement = (Element) premiumsNodes.item(i);

            String identifier = activeElement.getAttribute("name");
            int row = Integer.parseInt(activeElement.getAttribute("row"));
            int column = Integer.parseInt(activeElement.getAttribute("col"));

            Premium premium = premiums.get(identifier);
            board.getSquare(row, column).setPremium(premium);
        }
    }


    public char getContentSquare(int row, int column) {

        char value = '\0';

        Square square = board.getSquare(row, column);

        char content = square.getLetterOn();

        if(content != Character.MIN_VALUE)
        {
            value = content;
        }
        return value;
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

    private void initPremiums(Element premiumElement)
    {
        premiums = new TreeMap<String, Premium>();

        NodeList premiumNodes = premiumElement.getElementsByTagName("premium");

        for(int i = 0; i < premiumNodes.getLength(); i++)
        {
            Element activeElement = (Element) premiumNodes.item(i);

            String identifier = activeElement.getAttribute("name");
            String type = activeElement.getAttribute("type");
            int multiplier = Integer.parseInt(activeElement.getAttribute("multiplier"));

            Premium.Type premiumType = premiumTypeMap.get(type);
            Premium premium = new Premium(premiumType, multiplier);
            premiums.put(identifier, premium);
        }
    }

    public Square getSquare(int row, int column) {
        return board.getSquare(row, column);
    }


}
