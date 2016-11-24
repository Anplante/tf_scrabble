package ca.qc.bdeb.p56.scrabble.temporaire;

/**
 * Created by TheFrenchOne on 10/29/2016.
 */
public class Node {

    char caracter;
    Node firstChild;
    Node rightSibling;

    public Node()
    {

    }

    public Node(char character)
    {
        this.caracter = character;
    }


}
