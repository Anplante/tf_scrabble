package ca.qc.bdeb.p56.scrabble.utility;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Interface contenant les contenants pour les noms des components.
 * <p>
 * Created by Louis Luu Lim on 2016-10-27.
 */
public class ConstanteComponentMessage {

    private static final Locale locale = new Locale(System.getProperty("user.language"), System.getProperty("user.country"));
    private static final ResourceBundle messages = ResourceBundle.getBundle("strings", Locale.getDefault());

    public static final String PATH_BACKGROUND_RES = "/background/";
    public static final String EXT_JPG = ".jpg";
    public static final String FILE_SEPARATOR = "file.separator";
    public static final String EXT_PNG = ".png";
    public static final String TAG_NAME = "name";
    public static final String TAG_ITEM = "item";
    public static final String RES_WAITING_IMAGE = "./images/waiting.png";
    public static final String ELLIPSIS = "...";
    public static final String RES_IMAGES_ENG = "./letters/englishDictionaryValue/";
    public static final String RES_IMAGES_FR_BASIC = "./letters/frenchDictionaryValue/basic/";
    public static final String RES_IMAGES_FR_NOBLE = "./letters/frenchDictionaryValue/noble/";
    public static final String RES_BLANKS_FR_NOBLE = "./letters/frenchDictionaryValue/blank_noble";
    public static final String RES_BLANKS_FR_BASIC = "./letters/frenchDictionaryValue/blank_basic";

    public static final String RES_SWAP = "./images/swap.png";
    public static final String RES_RETURN = "./images/back.png";
    public static final String RES_NEXT = "./images/next.png";
    public static final String RES_ABANDON = "./images/abandon.png";
    public static final String RES_AZ = "./images/az.png";

    public static final char START_ALPHABET = 'A';
    public static final char END_ALPHABET = 'Z';
    public static final String DEFAULT_DICT_PATH = "resources/dictionary/fr_dictionary.txt";


}
