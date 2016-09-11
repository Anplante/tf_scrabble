package ca.qc.bdeb.p56.scrabble.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by TheFrenchOne on 9/10/2016.
 */
public class GameManager {

    private Game game;

    private final String GAME_FILE = "src/resources/scrabbleParameters.xml";


    public GameManager()
    {
        game = null;
    }


    public Game createNewGame()
    {

        try{

            File fXmlFile = new File(GAME_FILE);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            Element rootElement = doc.getDocumentElement();
            rootElement.normalize();
            game = new Game(rootElement);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return game;

    }
}
