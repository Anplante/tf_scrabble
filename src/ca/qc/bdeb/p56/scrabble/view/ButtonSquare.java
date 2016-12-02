package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Premium;
import ca.qc.bdeb.p56.scrabble.model.Square;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import ca.qc.bdeb.p56.scrabble.utility.ImagesManager;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import javax.swing.*;
import javax.xml.stream.Location;
import java.awt.*;
import java.net.URL;

/**
 * Created by TheFrenchOne on 9/11/2016.
 */
public class ButtonSquare extends JButton implements Observateur {

    private final static Color COLOR_DL = new Color(113, 205, 207);
    private final static Color COLOR_TW = new Color(252, 179, 87);
    private final static Color COLOR_TL = new Color(91, 187, 71);
    private final static Color COLOR_DW = new Color(238, 49, 50);
    private final static Color COLOR_CENTER = new Color(255, 192, 203);

    private static final String PATH_IMG_CENTER_STAR = "./images/star.png";
    private static final String TRIPLE_WORD = "TW";
    private static final String DOUBLE_WORD = "DW";
    private static final String DOUBLE_LETTER = "DL";
    private static final String TRIPLE_LETTER = "TL";
    private static final String CENTER = "CENTER";

    private Square square;
    private int size;
    private String imgPath;

    public ButtonSquare(Square square, int size, String pathImg) {

        super();
        imgPath = pathImg;
        this.square = square;
        this.size = size;
        square.ajouterObservateur(ButtonSquare.this);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEtchedBorder());
        setFocusable(false);
        changementEtat();
    }

    public int getPositionX(){
        return  getX();
    }

    public int getPositionY(){
        return  getY();
    }

    public Square getSelectedSquare()
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


    // temporaire - à changer TODO
    private void setImage() {
        if (square.getTileOn() != null) {
            setText("");
            String valueOnTile = square.getLetterOn();
            if (valueOnTile.isEmpty()) {
                valueOnTile = "1";
            }
            URL path = getClass().getClassLoader().getResource(imgPath + valueOnTile + ConstanteComponentMessage.EXT_PNG);
            setIcon(ImagesManager.getIcon(path, size, size));
        } else if (imgPath.equals(ConstanteComponentMessage.RES_IMAGES_FR_NOBLE)) {
            setBackground(Color.lightGray);
        } else {
            setBackground(new Color(188, 183, 122));
        }
    }


    @Override
    public void changementEtat(Enum<?> property, Object o) {
    }
}
