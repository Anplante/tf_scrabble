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
    private static final ResourceBundle messages = ResourceBundle.getBundle("strings", locale);

    public static final String OPT_QUITTER_TITLE = messages.getString("Quit");
    public static final String MESS_CONFIRM_EXIT = messages.getString("Confirmation_Quit");
    public static final String TITLE_MESS_EXIT = messages.getString("App_Closure");
    public static final String TITLE_SURRENDER = messages.getString("Abandon");
    public static final String MESS_CONFIRM_ACTION = messages.getString("Confirm_Action");
    public static final String MESS_SURRENDER_GAME = messages.getString("Surrender_Game");
    public static final String MESS_DEFEAT = messages.getString("Lost");
    public static final String MESS_END_GAME = messages.getString("End_Game");
    public static final String TITLE_RETURN_GAME = messages.getString("Return");
    public static final String ESCAPE_KEY = messages.getString("Escape_Key");
    public static final String TITLE_MENU = messages.getString("Title_Menu");
    public static final String MESS_CONFIRM = messages.getString("Confirm");
    public static final String MESS_CANCEL = messages.getString("Cancel");
    public static final String MESS_NUMBER_OF_AI = messages.getString("Number_Of_Ai");
    public static final String MESS_NUMBER_OF_HUMAN = messages.getString("Number_Of_Player");
    public static final String MESS_THEME = messages.getString("Theme");
    public static final String MESS_BACKGROUND = messages.getString("Background");
    public static final String MESS_THEME_CLASSIQUE = messages.getString("Classic");
    public static final String MESS_THEME_NOBLE = messages.getString("Noble");
    public static final String PATH_BACKGROUND_RES = "/background/";
    public static final String EXT_JPG = ".jpg";
    public static final String MESS_ERROR_LOADING_FILE = messages.getString("Error_Loading_File");
    public static final String MESS_ERROR = messages.getString("Error");
    public static final String FILE_SEPARATOR = "file.separator";
    public static final String EXT_PNG = ".png";
    public static final String TAG_NAME = "name";
    public static final String TAG_ITEM = "item";
    public static final String MESS_PASS_TURN = messages.getString("Pass_Turn");
    public static final String RES_WAITING_IMAGE = "./images/waiting.png";
    public static final String MESS_PLAY = messages.getString("Play");
    public static final String MESS_RECALL = messages.getString("Recall");;
    public static final String MESS_EXCHANGE = messages.getString("Exchange");
    public static final String MESS_RESTART_GAME  = messages.getString("Replay");
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

    public static final String TITLE_SELECT_LETTER = messages.getString("Select_Letter");
    public static final char START_ALPHABET = 'A';
    public static final char END_ALPHABET = 'Z';
    public static final String DEFAULT_DICT_PATH = "resources/dictionary/fr_dictionary.txt";
    public static final String ENTER_PLAYER_NAME = messages.getString("Enter_Name");
    public static final String TITLE_ORDER = "Ordonner";
    public static final String INVALID_WORD_POINTS = messages.getString("Non_Valid");
    public static final String NO_WORD_POINTS = messages.getString("Place_Letter");

    public static final String ENTER_WORD = messages.getString("Enter_Word");
    public static final String VALID_WORD = messages.getString("Valid_Word");
    public static final String INVALID_WORD = messages.getString("Invalid_Word");

}
