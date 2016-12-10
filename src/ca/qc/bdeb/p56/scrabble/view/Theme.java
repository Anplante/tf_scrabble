package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;

/**
 * Enum représant les thèmes que la partie peut avoir.
 * <p>
 * Created by Louis Luu Lim on 12/4/2016.
 */
public enum Theme {

    BASIC(ConstanteComponentMessage.RES_IMAGES_BASIC),
    NOBLE(ConstanteComponentMessage.RES_IMAGES_NOBLE);

    private String folderPath;

    Theme(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getThemeFolderPath()
    {
        return folderPath;
    }
}
