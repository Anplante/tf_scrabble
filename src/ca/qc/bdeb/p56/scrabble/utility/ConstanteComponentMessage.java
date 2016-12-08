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
    public static final String MESS_ERROR_LOADING_FILE = messages.getString("Error_Loading_File");
    public static final String MESS_ERROR = messages.getString("Error");
    public static final String EXT_PNG = ".png";
    public static final String TAG_NAME = "name";
    public static final String TAG_ITEM = "item";
    public static final String RES_WAITING_IMAGE = "./images/waiting.png";
    public static final String MESS_RECALL = messages.getString("Recall");;
    public static final String MESS_EXCHANGE = messages.getString("Exchange");
    public static final String MESS_RESTART_GAME  = messages.getString("Replay");
    public static final String ELLIPSIS = "...";
    public static final String RES_ROOT_ENGLISH = "./englishDictionaryValue";
    public static final String RES_ROOT_FRENCH = "./frenchDictionaryValue";
    public static final String RES_IMAGES_BASIC = "/basic";
    public static final String RES_IMAGES_NOBLE = "/noble";
    public static final String RES_BLANKS_NOBLE = "/blank_noble/";
    public static final String RES_BLANKS_BASIC = "/blank_regular/";

    public static final String RES_RETURN = "./images/back.png";
    public static final String RES_NEXT = "./images/next.png";
    public static final String RES_ABANDON = "./images/abandon.png";
    public static final String RES_AZ = "./images/az.png";

    public static final char START_ALPHABET = 'A';
    public static final char END_ALPHABET = 'Z';
    public static final String DEFAULT_DICT_PATH = "resources/dictionary/fr_dictionary.txt";


}
