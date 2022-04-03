package fr.emse.ai.adversarial.ttt;

import fr.emse.ai.adversarial.Game;

import java.util.ArrayList;
import java.util.List;

public class TttGame implements Game<List<List<Integer>>, List<Integer>, Integer> {

    public final static Integer[] players = {0, 1};  // O player is 0 and X player is 1
    public final static List<List<Integer>> initialState = new ArrayList<List<Integer>>();

    public TttGame(){
        ArrayList<Integer> line1 = new ArrayList<Integer>();
        line1.add(-1);line1.add(-1);line1.add(-1);
        ArrayList<Integer> line2 = new ArrayList<Integer>();
        line2.add(-1);line2.add(-1);line2.add(-1);
        ArrayList<Integer> line3 = new ArrayList<Integer>();
        line3.add(-1);line3.add(-1);line3.add(-1);
        initialState.add(line1);initialState.add(line2);initialState.add(line3);
    }


    @Override
    public List<List<Integer>> getInitialState() {
        return initialState;
    }

    @Override
    public Integer[] getPlayers() {
        return players;
    }

    @Override
    public Integer getPlayer(List<List<Integer>> state) {
        // Returns player who has the next turn on a board.
        int Xcount = 0;
        int Ocount = 0;
        for (int i =0; i<3;i++){
            for (int j =0; j<3;j++){
                if(state.get(i).get(j)==1) Xcount++;
                else Ocount++;
            }
        }
        if (Xcount>Ocount) return 0; // O player turn
        return 1; // X player turn
    }

    @Override
    public List<List<Integer>> getActions(List<List<Integer>> state) {
        // returns set of all possible actions (i, j) available on the board.
        ArrayList<List<Integer>> actions = new ArrayList<List<Integer>>();
        for (int i =0; i<3;i++){
            for (int j =0; j<3;j++){
                if(state.get(i).get(j)==-1){ // = if the box is empty
                    ArrayList<Integer> action = new ArrayList<Integer>();
                    action.add(i);action.add(j);
                    actions.add(action);
                }
            }
        }
        return actions;
    }


    public List<List<Integer>> getResult(List<List<Integer>> state, List<Integer> action) {
        // Returns the board that results from making move (i, j) on the board.
        int player = getPlayer(state);
        ArrayList<Integer> line = (ArrayList)state.get(action.get(0));
        line.set(action.get(1), player);
        state.set(action.get(0), line);
        return state;
    }

    private Integer winner(List<List<Integer>> state){
        return null;
    }

    @Override
    public boolean isTerminal(List<List<Integer>> state) {
        // Returns True if game is over, False otherwise.
        if (winner(state)!=null) return true;
        if (getActions(state).size() == 0) return true;
        return false;
    }

    @Override
    public double getUtility(List<List<Integer>> lists, Integer integer) {
        return 0;
    }
}
