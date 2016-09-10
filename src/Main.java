import ca.qc.bdeb.p56.scrabble.model.GameManager;
import ca.qc.bdeb.p56.scrabble.vue.ScrabbleGUI;

import java.awt.*;

public class Main {

    public static void main(String[] args)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        ScrabbleGUI gameGUI = new ScrabbleGUI(new GameManager(), screenSize);
        gameGUI.setVisible(true);
    }
}

