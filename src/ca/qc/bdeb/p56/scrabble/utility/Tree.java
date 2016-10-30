package ca.qc.bdeb.p56.scrabble.utility;

/**
 * Created by TheFrenchOne on 10/29/2016.
 */
public class Tree {

    public Node root;

    public Tree ()
    {
        root = null;
    }

    public Tree(Node root)
    {
        this.root = root;
    }

    public Tree(char value)
    {
        Node newNode = new Node(value);
        this.root = newNode;
    }






    public void addWord(String word) {

    }
}
