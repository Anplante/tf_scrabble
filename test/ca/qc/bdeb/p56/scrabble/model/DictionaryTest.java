package ca.qc.bdeb.p56.scrabble.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Louis Luu Lim on 2016-11-15.
 */
public class DictionaryTest {


    private static final String DEFAULT_DICT_PATH = "resources/dictionary/fr_dictionary.txt";
    private Dictionary dictionary;

    @Before
    public void setUp() throws Exception {

        File dictFile = new File(DEFAULT_DICT_PATH);

        dictionary = Dictionary.getINSTANCE();
        dictionary.loadDictinnary(dictFile);
    }

    @After
    public void tearDown() throws Exception {
       dictionary = null;
    }

    @Test
    public void testValidWordFr() {

        String validWord = "bonjour";
        assertTrue(dictionary.checkWordExist(validWord));
    }

    @Test
    public void testInvalidWord() {

        String notAWord = "ssdfaf";
        assertTrue(dictionary.checkWordExist(notAWord));
    }

    @Test
    public void testValidWordEn() {
        String wordtested = "wrong";
        assertTrue(dictionary.checkWordExist(wordtested));
    }
}
