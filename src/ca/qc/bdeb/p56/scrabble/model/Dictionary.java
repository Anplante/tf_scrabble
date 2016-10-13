package ca.qc.bdeb.p56.scrabble.model;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by 0993083 on 2016-10-11.
 */
public  class Dictionary {

    private static Dictionary INSTANCE;

    private HashMap<Integer, String> dict = new HashMap<>();

    private Dictionary(){
    }


    public static Dictionary getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new Dictionary();
        }
        return INSTANCE;
    }


    public void loadDictinnary(File file)
    {

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String word;
            int wordCode = 0;
            while((word = br.readLine()) != null)
            {
                dict.put(wordCode, word);
                wordCode++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public boolean checkWordExist(String word)
    {
        return dict.containsValue(word);
    }
}
