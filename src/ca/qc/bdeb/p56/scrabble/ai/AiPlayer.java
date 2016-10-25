package ca.qc.bdeb.p56.scrabble.ai;

import ca.qc.bdeb.p56.scrabble.model.Player;
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
public class AiPlayer extends Player implements ListOfName{

    public static List<String> tmpList = AIName;

    public static final URL PATH_TO_FILE = Launcher.class.getResource("/fichiers/ListOfName.xml");

    public AiPlayer() {
        super(chooseName());
        ArrayList<String> test = readXMLFiles();
    }

    private static String chooseName() {
        Random rand = new Random();
        int nom = rand.nextInt(AIName.size());
        String Ainame = tmpList.get(nom);
        tmpList.remove(nom);
        return Ainame;
    }

    private ArrayList<String> readXMLFiles() {
        ArrayList<String> listOfName = new ArrayList<>();
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document documentOfName = docBuilder.parse(new File(PATH_TO_FILE.toURI()));
            documentOfName.getDocumentElement().normalize();
            NodeList nodeOfName = documentOfName.getElementsByTagName("item");

            for (int i = 0; i < nodeOfName.getLength(); i++) {
                Node itemName = nodeOfName.item(i);
                System.out.println("\nCurrent Element :" + itemName.getNodeName());
                if (itemName.getNodeType() == Node.ELEMENT_NODE) {
                    Element aiName = (Element) itemName;
                    listOfName.add(aiName.getElementsByTagName("name").item(0).getTextContent());
                }
            }
            // TODO : donner du feedback à l'utilisateur
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(docBuilderFactory.toString()).log(Level.SEVERE, null, ex);
        } catch (SAXParseException ex) {
            Logger.getLogger(PATH_TO_FILE.toString()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(PATH_TO_FILE.toString()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(PATH_TO_FILE.toString()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(docBuilderFactory.toString()).log(Level.SEVERE, null, ex);
        }
        return listOfName;
    }
}
