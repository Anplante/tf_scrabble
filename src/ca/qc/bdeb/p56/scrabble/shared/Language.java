package ca.qc.bdeb.p56.scrabble.shared;


/**
 * Created by 0993083 on 2016-12-06.
 */
public enum Language {

    FRENCH("frenchAlphabet"),
    ENGLISH("englishAlphabet");

    private String language;

    Language(String language) {
        this.language = language;
    }

    public String getLanguage()
    {
        return language;
    }
}
