package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.Square;
import ca.qc.bdeb.p56.scrabble.shared.IDState;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import ca.qc.bdeb.p56.scrabble.utility.ImagesManager;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by TheFrenchOne on 9/11/2016.
 */
public class BtnSquare extends JButton implements Observateur {

    private final static Color COLOR_DL = new Color(113, 205, 207);
    private final static Color COLOR_TW = new Color(252, 179, 87);
    private final static Color COLOR_TL = new Color(91, 187, 71);
    private final static Color COLOR_DW = new Color(238, 49, 50);
    private final static Color COLOR_CENTER = new Color(255,192,203);

    private static final String PATH_IMG_CENTER_STAR = "./images/star.png";
    private static final String TRIPLE_WORD = "TW";
    private static final String DOUBLE_WORD = "DW";
    private static final String DOUBLE_LETTER = "DL";
    private static final String TRIPLE_LETTER = "TL";
    private static final String CENTER = "CENTER";

    private Game gameModel;
    private int posRow;
    private int posColumn;
    private int size;
    private Square square;
    private HashMap<String, ImageIcon> icons;
    private String imgPath;

    public BtnSquare(Game gameModel, int posRow, int posColumn, int size, String pathImg) {

        super(gameModel.getPremiumSquare(posRow, posColumn));
        imgPath = pathImg;
        this.gameModel = gameModel;
        this.posRow = posRow;
        this.posColumn = posColumn;
        this.size = size;
        this.square = gameModel.getSquare(posRow, posColumn);
        square.ajouterObservateur(BtnSquare.this);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEtchedBorder());
        setFocusable(false);
        changementEtat();
        setPremiumColor();
        addActionListener(e -> {

            gameModel.playTile(square);

            if (square.getTileOn() != null
                    && gameModel.getActivePlayer().getState().getName().equals(IDState.PLAY_TILE.getName())) {
                setText("");
                String valueOnTile = square.getLetterOn();
                if(valueOnTile.trim().equals("")){
                    valueOnTile = "1";
                }
                URL path = getClass().getClassLoader().getResource(imgPath + valueOnTile + ConstanteComponentMessage.EXT_PNG);
                setIcon(ImagesManager.getIcon(path, size, size));
            }
        });
    }

    @Override
    public void changementEtat() {

        String content = gameModel.getContentSquare(posRow, posColumn);

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
                setIcon(ImagesManager.getIcon(getClass().getClassLoader().getResource(PATH_IMG_CENTER_STAR), size, size));
            default:
                if(imgPath.equals(ConstanteComponentMessage.RES_IMAGES_FR_NOBLE)) {
                    setBackground(Color.lightGray);
                }else {
                    setBackground(new Color(188,183,122));
                }
                break;
        }
        repaint();
    }

    private void setPremiumColor() {

        if (square.getPremium() != null) {
            String content = gameModel.getContentSquare(posRow, posColumn);

            switch (content) {
                case TRIPLE_LETTER:
                    setBackground(COLOR_TW);
                    break;
                case DOUBLE_WORD:
                    setBackground(COLOR_DW);
                    break;
                case DOUBLE_LETTER:
                    setBackground(COLOR_DL);
                    break;
                case TRIPLE_WORD:
                    setBackground(COLOR_TL);
                    break;
                case CENTER:
                    setBackground(COLOR_CENTER);
                default:
            }
        }
    }

    @Override
    public void changementEtat(Enum<?> property, Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
