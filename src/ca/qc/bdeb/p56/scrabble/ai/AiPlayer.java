package ca.qc.bdeb.p56.scrabble.ai;

import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.model.State;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import sun.misc.Launcher;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Antoine on 9/17/2016.
 */
public class AiPlayer extends Player {

    private AiGoal aiGoal;

    public AiPlayer(ArrayList<String> listName) {
        super(chooseName(listName));
        aiGoal = new AiGoal(getGame());
        setState(new AiStatePending(this));
    }

    private static String chooseName(ArrayList<String> listName) {
        Random rand = new Random();
        int nom = rand.nextInt(listName.size());
        String Ainame = listName.get(nom);
        listName.remove(nom);
        return Ainame;
    }

    @Override
    public void changePlayer() {
        nextState();
        getGame().goToNextState();
    }


    @Override
    public boolean isHumanPlayer() {
        return false;
    }
}
