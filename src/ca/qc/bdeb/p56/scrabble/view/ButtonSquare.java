package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Premium;
import ca.qc.bdeb.p56.scrabble.model.Square;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import ca.qc.bdeb.p56.scrabble.utility.ImagesManager;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Classe qui permet la création d'une case du plateau de jeu de Scrabble.
 *
 * Created by Louis Luu Lim on 9/11/2016.
 */
class ButtonSquare extends JButton implements Observateur {

    private final static Color COLOR_DL = new Color(166, 213, 234);
    private final static Color COLOR_TL = new Color(230, 60, 52);
    private final static Color COLOR_TW = new Color(20, 145, 234);
    private final static Color COLOR_DW = new Color(234, 169, 166);
    private final static Color COLOR_CENTER = new Color(255, 192, 203);

    private static final String PATH_IMG_CENTER_STAR = "./images/star.png";
    private static final String TRIPLE_WORD = "TW";
    private static final String DOUBLE_WORD = "DW";
    private static final String DOUBLE_LETTER = "DL";
    private static final String TRIPLE_LETTER = "TL";
    private static final String CENTER = "CENTER";

    private Square square;
    private int size;
    private String theme;

    ButtonSquare(Square square, int size, String themePath) {

        super();
        this.theme = themePath;
        this.square = square;
        this.size = size;
        square.ajouterObservateur(ButtonSquare.this);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEtchedBorder());
        setFocusable(false);
        changementEtat();
    }

    protected Square getSelectedSquare()
    {
        return square;
    }

    @Override
    public void changementEtat() {

        String content = square.getLetterOn();

        if (content.isEmpty()) {
            Premium premium = square.getPremium();

            if (premium != null) {
                content = premium.getName();
            }
        }

        if (square != null && square.getTileOn() == null)
            setIcon(null);

        switch (content) {
            case TRIPLE_LETTER:
                setText(content);
                setBackground(COLOR_TW);
                break;
            case DOUBLE_WORD:
                setText(content);
                setBackground(COLOR_DW);
                break;
            case DOUBLE_LETTER:
                setText(content);
                setBackground(COLOR_DL);
                break;
            case TRIPLE_WORD:
                setText(content);
                setBackground(COLOR_TL);
                break;
            case CENTER:
                setText("");
                setBackground(COLOR_CENTER);
                setIcon(ImagesManager.getIcon(getClass().getClassLoader().getResource(PATH_IMG_CENTER_STAR), size, size));
                break;
            default:
                setImage();
                break;
        }
        repaint();
    }

    private void setImage() {

        if (square.getTileOn() != null) {

            setText("");
            String pathTheme;
            String valueOnTile = square.getLetterOn();

            if (square.getTileOn().isBlankTile()) {
                pathTheme = theme + getBlanksPath(theme);
            }
            else{
                pathTheme = theme;

            }
            String res = pathTheme + "/" + valueOnTile + ConstanteComponentMessage.EXT_PNG;
            URL path = getClass().getClassLoader().getResource(res);
            setIcon(ImagesManager.getIcon(path, size, size));
        }
        else if (theme.contains(ConstanteComponentMessage.RES_IMAGES_NOBLE)) {
            setBackground(Color.lightGray);
        } else {
            setBackground(new Color(188, 183, 122));
        }
    }
    private String getBlanksPath(String themePath){
        if(themePath.contains("NOBLE")){
            return ConstanteComponentMessage.RES_BLANKS_NOBLE;
        }else {
            return ConstanteComponentMessage.RES_BLANKS_BASIC;
        }
    }

    @Override
    public void changementEtat(Enum<?> property, Object o) {
    }
}
