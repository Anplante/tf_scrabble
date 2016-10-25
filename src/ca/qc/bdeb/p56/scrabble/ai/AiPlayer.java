package ca.qc.bdeb.p56.scrabble.ai;

import ca.qc.bdeb.p56.scrabble.model.Game;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Antoine on 9/17/2016.
 */
public class AiPlayer extends Player implements ListOfName{

    public static List<String> tmpList = AIName;

    public AiPlayer() {
        super(chooseName());
    }

    private static String chooseName() {
        Random rand = new Random();
        int  nom = rand.nextInt(AIName.size());
        String Ainame = tmpList.get(nom);
        tmpList.remove(nom);
        return Ainame;
    }

    private void readXMLFiles() {
        try {

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            URL url = Launcher.class.getResource("/fichiers/ListOfName.xml");
            Document documentOfName = docBuilder.parse(new File(url.toURI()));
            // normalize text representation
            documentOfName.getDocumentElement().normalize();
            String test  = "Root element of the doc is " + documentOfName.getDocumentElement().getNodeName();


            NodeList listOfName = documentOfName.getElementsByTagName("item");
            int totalName = listOfName.getLength();
            System.out.println("Total no of people : " + listOfName);

            for (int i = 0; i < listOfName.getLength(); i++) {

                Node firstItem = listOfName.item(i);
                if (firstItem.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) firstItem;
                    String yrdy = eElement.getElementsByTagName("name").item(0).getTextContent();
                    System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
                    System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
                    System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
                    System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());

                }


            }
        } catch (SAXParseException err) {
        System.out.println("Parsing error" + ", line " + err.getLineNumber() + ", uri " + err.getSystemId());
        System.out.println(" " + err.getMessage());

    } catch (SAXException e) {
        Exception x = e.getException();
        ((x == null) ? e : x).printStackTrace();
    } catch (IOException e) {

    } catch (ParserConfigurationException e) {

    } catch (URISyntaxException e) {
        e.printStackTrace();
    }
}
}
