package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.model.Log.ExchangedLog;
import ca.qc.bdeb.p56.scrabble.model.Log.MoveLog;
import ca.qc.bdeb.p56.scrabble.model.Log.PassedLog;
import ca.qc.bdeb.p56.scrabble.model.Log.WordLog;
import ca.qc.bdeb.p56.scrabble.shared.Event;
import ca.qc.bdeb.p56.scrabble.utility.Observable;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Louis Luu Lim on 2016-11-24.
 */
public class LogManager implements Observable {



    private List<MoveLog> movesHistory;
    private transient LinkedList<Observateur> observateurs;
    private int currentRound;

    public LogManager(){

        movesHistory = new ArrayList<>();
        observateurs = new LinkedList<>();
        currentRound = 0;
    }


    public List<MoveLog> getMovesHistory() {

        List<MoveLog> moveLogs = new ArrayList<>();
        for (MoveLog moveLog : movesHistory) {
            moveLogs.add(moveLog);
        }
        return moveLogs;
    }

    
    @Override
    public void ajouterObservateur(Observateur o) {
        observateurs.add(o);
    }

    @Override
    public void retirerObservateur(Observateur o) {
            observateurs.remove(o);
    }

    @Override
    public void aviserObservateurs() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void aviserObservateurs(Enum<?> e, Object o) {
        for (Observateur ob : observateurs) {
            ob.changementEtat(e, o);
        }
    }

    public void addPassedLog(Player player, int turnPlayed) {
        checkIfTurnPlayedIsDifferentRound(turnPlayed);
        movesHistory.add(new PassedLog(player, turnPlayed));
        aviserObservateurs(Event.MOVE_PLAYED, movesHistory.get(movesHistory.size()-1));
    }

    public void addWordLog(Player player, int turnPlayed, String word, int wordValue) {
        checkIfTurnPlayedIsDifferentRound(turnPlayed);
        movesHistory.add(new WordLog(player, turnPlayed, word, wordValue));
        aviserObservateurs(Event.MOVE_PLAYED, movesHistory.get(movesHistory.size()-1));
    }

    public void addExchangedLog(Player player, int turnPlayed, List<Tile> tilesSelected) {
        checkIfTurnPlayedIsDifferentRound(turnPlayed);
        movesHistory.add(new ExchangedLog(player, turnPlayed, tilesSelected.size()));
        aviserObservateurs(Event.MOVE_PLAYED, movesHistory.get(movesHistory.size()-1));
    }

   private void checkIfTurnPlayedIsDifferentRound(int turnPlayed){

       if(currentRound != turnPlayed){
           aviserObservateurs(Event.ROTATION_PLAYERS, null);
       }
   }
}
