package ca.qc.bdeb.p56.scrabble.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by TheFrenchOne on 9/10/2016.
 */
public class GameManager {

    private Game game;

    private final String GAME_FILE = "src/resources/scrabbleParameters.xml";


    private List<Letter> alphabetBag;

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
            initAlphabetBag(rootElement);
            game = new Game(rootElement, alphabetBag);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return game;
    }

    private void initAlphabetBag(Element rootElement)
    {
        alphabetBag = new ArrayList<Letter>();

        Element alphabetsElement = (Element) rootElement.getElementsByTagName("square").item(0);

        NodeList alphabetsNodes = alphabetsElement.getElementsByTagName("letter");

        for(int i = 0; i < alphabetsNodes.getLength(); i++)
        {
            Element activeElement = (Element) alphabetsNodes.item(i);


            char caracter = activeElement.getAttribute("text").charAt(0);
            int value = Integer.parseInt(activeElement.getAttribute("value"));
            Letter letter = new Letter(caracter, value);

            int amount = Integer.parseInt(activeElement.getAttribute("amount"));

            for(int j = 0; j < amount; j++)
            {
                alphabetBag.add(letter);   // Potentiellement un probleme, car c'est la meme reference pour chaque lettre
            }
        }
    }
}
