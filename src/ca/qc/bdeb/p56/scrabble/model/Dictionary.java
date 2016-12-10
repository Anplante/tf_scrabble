package ca.qc.bdeb.p56.scrabble.model;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe qui continent et gère les mots valides pour le jeu de scrabble.
 *
 * Created by Louis Luu Lim on 2016-10-11.
 */
public class Dictionary {

    private static Dictionary INSTANCE;
    private HashMap<Integer, String> dict = new HashMap<>();

    private Dictionary() {
    }

    public static Dictionary getINSTANCE() {

        if (INSTANCE == null) {
            INSTANCE = new Dictionary();
        }
        return INSTANCE;
    }

    public void loadDictinnary(File file) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String word;
            int wordCode = 0;
            while ((word = reader.readLine()) != null) {
                dict.put(wordCode, word);
                wordCode++;
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(file.toString()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(file.toString()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkWordExist(String word) {
        return dict.containsValue(word);
    }
}
