import ca.qc.bdeb.p56.scrabble.model.GameManager;
import ca.qc.bdeb.p56.scrabble.vue.MenuCreation;
import ca.qc.bdeb.p56.scrabble.vue.ScrabbleGUI;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
       // MenuCreation menu = new MenuCreation();
        ScrabbleGUI gameGUI = new ScrabbleGUI(new GameManager(), new Rectangle(screenSize));
        gameGUI.setVisible(true);
        gameGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

