package ca.qc.bdeb.p56.scrabble.ai;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Antoine on 9/17/2016.
 */
public class AiPlayer extends Player implements ListOfName{


    public AiPlayer(Game game) {
        super(chooseName());
    }

    private static String chooseName() {
        List<String> tmpList = AIName;
        Random rand = new Random();
        int  nom = rand.nextInt(tmpList.size());
        String Ainame = tmpList.get(nom);
        AIName.remove(nom);
        return Ainame;
    }
}
