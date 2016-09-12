package ca.qc.bdeb.p56.scrabble.model;

import java.util.ArrayList;

/**
 * Created by TheFrenchOne on 9/10/2016.
 */
public class GameManager {

    private Game game;


    public GameManager() {
        game = null;

    }


    public Game createGame() {
        game = new Game();

        return game;
    }


    public ArrayList<Tile> createTileList() {
        ArrayList<Tile> tileBag = new ArrayList<Tile>();

        //ini Blank
        for (int i = 0; i < 2; i++) {
            tileBag.add(new Tile(' ', 0));
        }
        //ini E
        for (int i = 0; i < 12; i++) {
            tileBag.add(new Tile('E', 1));
        }
        //ini A
        for (int i = 0; i < 9; i++) {
            tileBag.add(new Tile('A', 1));
        }
        //ini I
        for (int i = 0; i < 9; i++) {
            tileBag.add(new Tile('I', 1));
        }
        //ini O
        for (int i = 0; i < 8; i++) {
            tileBag.add(new Tile('O', 1));
        }
        //ini N
        for (int i = 0; i < 6; i++) {
            tileBag.add(new Tile('N', 1));
        }
        //ini R
        for (int i = 0; i < 6; i++) {
            tileBag.add(new Tile('R', 1));
        }
        //ini T
        for (int i = 0; i < 6; i++) {
            tileBag.add(new Tile('T', 1));
        }
        //ini L
        for (int i = 0; i < 4; i++) {
            tileBag.add(new Tile('L', 1));
        }
        //ini S
        for (int i = 0; i < 4; i++) {
            tileBag.add(new Tile('S', 1));
        }
        //ini U
        for (int i = 0; i < 4; i++) {
            tileBag.add(new Tile('U', 1));
        }
        //ini D
        for (int i = 0; i < 4; i++) {
            tileBag.add(new Tile('D', 2));
        }
        //ini G
        for (int i = 0; i < 3; i++) {
            tileBag.add(new Tile('G', 2));
        }
        //ini B
        for (int i = 0; i < 2; i++) {
            tileBag.add(new Tile('B', 3));
        }
        //ini C
        for (int i = 0; i < 2; i++) {
            tileBag.add(new Tile('C', 3));
        }
        //ini M
        for (int i = 0; i < 2; i++) {
            tileBag.add(new Tile('M', 3));
        }
        //ini P
        for (int i = 0; i < 2; i++) {
            tileBag.add(new Tile('P', 3));
        }
        //ini F
        for (int i = 0; i < 2; i++) {
            tileBag.add(new Tile('F', 4));
        }
        //ini H
        for (int i = 0; i < 2; i++) {
            tileBag.add(new Tile('H', 4));
        }
        //ini V
        for (int i = 0; i < 2; i++) {
            tileBag.add(new Tile('V', 4));
        }
        //ini W
        for (int i = 0; i < 2; i++) {
            tileBag.add(new Tile('W', 4));
        }

        //ini Y
        for (int i = 0; i < 2; i++) {
            tileBag.add(new Tile('Y', 4));
        }
        //ini K
        tileBag.add(new Tile('K', 5));
        //ini J
        tileBag.add(new Tile('J', 8));
        //ini X
        tileBag.add(new Tile('X', 8));
        //ini Q
        tileBag.add(new Tile('Q', 10));
        //ini Z
        tileBag.add(new Tile('Z', 10));


        return tileBag;
    }

}
