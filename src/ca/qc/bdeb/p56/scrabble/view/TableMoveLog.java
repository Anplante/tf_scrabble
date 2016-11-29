package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.Log.MoveLog;
import ca.qc.bdeb.p56.scrabble.shared.Event;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Created by Louis Luu Lim on 11/23/2016.
 */
public class TableMoveLog extends JTable implements Observateur {

    private static final String ROUND = "Tour ";
    private static final String[] HEADERS = {"Joueur", "Coup", "Pts", "Total"};
    private static final Color COLOR_HEADERS = new Color(188, 252, 250);
    private static final Color COLOR_ROW_ROUND= new Color(161, 212, 252);
    private static final Color COLOR_ROW_MOVE = new Color(165, 250, 252);
    private static final Font FONT = new Font("Serif", Font.BOLD, 20);

    private Game gameModel;
    DefaultTableModel dtm;

    public TableMoveLog(Game gameModel) {

        super();
        this.gameModel = gameModel;
        initTableModel();
        setPreferredScrollableViewportSize(getPreferredSize());
        setFocusable(false);
        setRowSelectionAllowed(false);
        setFont(FONT);
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {

        Component c = super.prepareRenderer(renderer, row, column);

        String text = (String) dtm.getValueAt(row, 0);

        if( text.indexOf(ROUND) != - 1)
        {
            c.setBackground(COLOR_ROW_ROUND);
        }
        else{
            c.setBackground(COLOR_ROW_MOVE);
        }

        return c;
    }

    private void initTableModel() {

        dtm = new DefaultTableModel(0, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        dtm.setColumnIdentifiers(HEADERS);
        setModel(dtm);

        JTableHeader header = getTableHeader();
        header.setBackground(COLOR_HEADERS);
        header.setFont(FONT);

    }

    @Override
    public void changementEtat() {

    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {

        if (e.equals(Event.MOVE_PLAYED)) {
            MoveLog move = (MoveLog) o;
            addMove(move);
        } else if (e.equals(Event.ROTATION_PLAYERS)) {
            addRoundSeparator();
        }


    }

    private void addRoundSeparator() {
        dtm.addRow(new Object[]{ROUND + gameModel.getTurn()});
    }

    private void addMove(MoveLog move) {
        dtm.addRow(new Object[]{move.getPlayer().getName(), move.getMove(), move.getMovePoints(), move.getPointsAccumulated()});
    }
}
