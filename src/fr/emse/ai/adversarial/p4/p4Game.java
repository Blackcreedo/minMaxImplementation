package fr.emse.ai.adversarial.p4;

import fr.emse.ai.adversarial.Game;

import java.util.ArrayList;
import java.util.List;

public class p4Game implements Game<List<List<Integer>>, List<Integer>, Integer> {
    public final static Integer[] players = {0, 1};  // O player is 0 and X player is 1
    public final static List<List<Integer>> initialState = new ArrayList<List<Integer>>();

    public p4Game(){
        for(int i = 0; i<7; i++){
            ArrayList<Integer> line = new ArrayList<Integer>();
            for(int j = 0; j<7; j++){
                line.add(-1);
            }
            initialState.add(line);
        }
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
        for (int i =0; i<7;i++){
            for (int j =0; j<7;j++){
                if(state.get(i).get(j)==1) Xcount++;
                if(state.get(i).get(j)==0) Ocount++;
            }
        }
        if (Xcount>Ocount) return 0; // O player turn
        return 1; // X player turn
    }

    @Override
    public List<List<Integer>> getActions(List<List<Integer>> state) {
        // returns set of all possible actions (i, j) available on the board.
        ArrayList<List<Integer>> actions = new ArrayList<List<Integer>>();
        for (int i =0; i<7;i++) {
            for (int j = 6; j >= 0; j--) {
                if(state.get(j).get(i)==-1){ // = if the box is empty
                    ArrayList<Integer> action = new ArrayList<Integer>();
                    action.add(j);action.add(i);
                    actions.add(action);
                    break; // No more actions possible in this column
                }
            }
        }
        return actions;
    }

    @Override
    public List<List<Integer>> getResult(List<List<Integer>> state, List<Integer> action) {
        // Returns the board that results from making move (i, j) on the board.
        int player = getPlayer(state);

        // make a copy of the list
        ArrayList<List<Integer>> newState = new ArrayList<List<Integer>>();
        for (List<Integer> lineOld: state){
            ArrayList<Integer> lineNew = new ArrayList<Integer>();
            for(int e: lineOld){
                lineNew.add(e);
            }
            newState.add(lineNew);
        }

        ArrayList<Integer> line = (ArrayList)newState.get(action.get(0));
        line.set(action.get(1), player);
        newState.set(action.get(0), line);
        return newState;
    }

    public Integer winner(List<List<Integer>> state){
        for (int i =0; i<7; i++){
            for (int j =0; j<4; j++){
                int compareH = state.get(i).get(j);
                int compareV = state.get(j).get(i);
                if (compareH != -1){
                    if (compareH==state.get(i).get(j+1) && compareH==state.get(i).get(j+2) && compareH==state.get(i).get(j+3)) return compareH;
                }
                if (compareV!=-1){
                    if (compareV==state.get(j+1).get(i) && compareV==state.get(j+2).get(i) && compareV==state.get(j+3).get(i)) return compareV;
                }
                if (i<4){
                    int compareD1 = state.get(i).get(j);
                    int compareD2 = state.get(i).get(6-j);
                    if (compareD1 != -1){
                        if (compareD1==state.get(i+1).get(j+1) && compareD1==state.get(i+2).get(j+2) && compareD1==state.get(i+3).get(j+3)) return compareD1;
                    }
                    if (compareD2 != -1){
                        if (compareD2==state.get(i+1).get(5-j) && compareD2==state.get(i+2).get(4-j) && compareD2==state.get(i+3).get(3-j)) return compareD2;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean isTerminal(List<List<Integer>> state) {
        // Returns True if game is over, False otherwise.
        if (winner(state)!=null) return true;
        List<List<Integer>> actions = getActions(state);
        if (getActions(state).size() == 0) return true;
        return false;
    }

    @Override
    public double getUtility(List<List<Integer>> state, Integer player) {

        player = winner(state);
        if (player==null) {
            double value = 0;
            for (int i =0; i<7; i++){
                for (int j =0; j<6; j++){
                    int length = 0;
                    int compareH = state.get(i).get(j);
                    int compareV = state.get(j).get(i);
                    if (compareH != -1){
                        if (compareH==state.get(i).get(j+1)) {
                            length=2;
                            if(j!=5){
                                if (compareH==state.get(i).get(j+2)) length=3;
                            }
                        }
                        if (compareH == 1) value += length*length;
                        else value -= length*length;
                    }
                    length = 0;
                    if (compareV!=-1){
                        if (compareV==state.get(j+1).get(i)){
                            length=2;
                            if(j!=5){
                                if (compareV==state.get(j+2).get(i)) length=3;
                            }
                        }
                        if (compareV == 1) value += length*length;
                        else value -= length*length;
                    }
                    if (i<4){
                        int compareD1 = state.get(i).get(j);
                        int compareD2 = state.get(i).get(6-j);
                        length = 0;
                        if (compareD1 != -1){
                            if (compareD1==state.get(i+1).get(j+1)){
                                length=2;
                                if(j!=5){
                                    if (compareD1==state.get(i+2).get(j+2)) length=3;
                                }
                            }
                            if (compareD1 == 1) value += length*length;
                            else value -= length*length;
                        }
                        length = 0;
                        if (compareD2 != -1){
                            if (compareD2==state.get(i+1).get(5-j)){
                                length=2;
                                if(j!=5){
                                    if (compareD2==state.get(i+2).get(4-j)) length=3;
                                }
                            }
                            if (compareD2 == 1) value += length*length;
                            else value -= length*length;
                        }
                    }
                }
            }
            return value;
        }

        if (player == 1) return Double.POSITIVE_INFINITY;
        return Double.NEGATIVE_INFINITY;
    }


    public void printState(List<List<Integer>> state){
        for(List<Integer> lineI: state){
            System.out.print("|");
            for (Integer e: lineI){
                if(e==1) System.out.print("X|");
                else if(e==0) System.out.print("O|");
                else System.out.print(" |");
            }
            System.out.println("\n-----------------------------");
        }
    }
}
