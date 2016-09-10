package ca.qc.bdeb.p56.scrabble.model;

import com.sun.javaws.jnl.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by TheFrenchOne on 9/10/2016.
 */
public class BoardManager {

    public static final int BOARD_SIZE = 15;

    private final String GAME_FILE = "src/resources/scrabbleParameters.xml";

    private Board board;

    private Map<String, Premium> premiums;

    private static Map<String, Premium.Type> premiumTypeMap;

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

    public void createBoard() {

        board = new Board(BOARD_SIZE);

        try{

            File fXmlFile = new File(GAME_FILE);
            System.out.println(fXmlFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            Element rootElement = doc.getDocumentElement();
            rootElement.normalize();

            Element premiumsElement = (Element) doc.getElementsByTagName("premiums").item(0);
            initPremiums(premiumsElement);

            Element boardElement = (Element) doc.getElementsByTagName("board").item(0);

            NodeList premiumsNodes = boardElement.getElementsByTagName("premium");

            for(int i = 0; i < premiumsNodes.getLength(); i++)
            {
                Element activeElement = (Element) premiumsNodes.item(i);

                String identifier = activeElement.getAttribute("name");
                int row = Integer.parseInt(activeElement.getAttribute("row"));
                int column = Integer.parseInt(activeElement.getAttribute("col"));

                Premium premium = premiums.get(identifier);
                System.out.println(row + " " + column);
                board.getSquare(row, column).setPremium(premium);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

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

    public String getContentSquare(int row, int column) {

        String value = "";

        Square square = board.getSquare(row, column);

        char content = square.getContent();

        if(content != Character.MIN_VALUE)
        {
            value = ""+content;
        }
        else {
            Premium premiumContent = square.getPremium();
            if (premiumContent != null) {

                for (Map.Entry<String, Premium> premium : premiums.entrySet()) {
                    if (premiumContent.equals(premium.getValue())) {
                        value = premium.getKey();
                    }

                }


            }
        }

        return value;
    }
}
