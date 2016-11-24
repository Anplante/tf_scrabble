package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.Log.MoveLog;
import ca.qc.bdeb.p56.scrabble.shared.Event;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Created by TheFrenchOne on 11/23/2016.
 */
public class TableMoveLog extends JTable implements Observateur {

    private static final String ROUND = "Round ";
    private Game gameModel;
    DefaultTableModel dtm = new DefaultTableModel(0, 0);
    String[] columnNames = {"Joueur", "Coup", "Pts", "Total"};


    public TableMoveLog(Game gameModel) {

        super();
        this.gameModel = gameModel;
        dtm.setColumnIdentifiers(columnNames);
        setModel(dtm);

        addRoundSeparator();
    }

    @Override
    public void changementEtat() {

    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {

        if (e.equals(Event.MOVE_PLAYED)) {
            MoveLog move = (MoveLog) o;
            addMove(move);
        }
        else if(e.equals(Event.ROTATION_PLAYERS)) {
            addRoundSeparator();
        }
    }

    private void addRoundSeparator()
    {
        dtm.addRow(new Object[] { ROUND + gameModel.getTurn()});
    }

    private void addMove(MoveLog move){
        //dtm.addRow(new Object[]{move.getPlayer(), move.getString()});
    }
}
