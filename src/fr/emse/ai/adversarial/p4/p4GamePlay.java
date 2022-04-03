package fr.emse.ai.adversarial.p4;

import fr.emse.ai.adversarial.AlphaBetaSearch;
import fr.emse.ai.adversarial.IterativeDeepeningAlphaBetaSearch;
import fr.emse.ai.adversarial.MinimaxSearch;
import fr.emse.ai.adversarial.ttt.TttGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class p4GamePlay {

    public static void main(String[] args) {
        p4Game game = new p4Game();
        MinimaxSearch<List<List<Integer>>, List<Integer>, Integer> minimaxSearch = MinimaxSearch.createFor(game);
        AlphaBetaSearch<List<List<Integer>>, List<Integer>, Integer> alphabetaSearch = AlphaBetaSearch.createFor(game);
        IterativeDeepeningAlphaBetaSearch<List<List<Integer>>, List<Integer>, Integer> iterativeDeepeningAlphaBetaSearch = IterativeDeepeningAlphaBetaSearch.createFor(game, -1, 1, 10);
        List<List<Integer>> state = game.getInitialState();
        System.out.println("Who do you want to play X (first player) or O (second player)");
        int playerChosen = 0;
        Scanner in = new Scanner(System.in);
        String temp = in.next();
        if (temp.equals("X")) {
            playerChosen = 1;
        }
        System.out.println(playerChosen);
        while (!game.isTerminal(state)) {
            System.out.println("======================");
            game.printState(state);
            int line = -1;
            int column = -1;
            List<Integer> action = new ArrayList<Integer>();
            if(game.getPlayer(state)==playerChosen){
                // human player turn
                List<List<Integer>> actions = game.getActions(state);
                while (!actions.contains(action)) {
                    System.out.println("Human player, what is your action?");
                    action.clear();
                    line = in.nextInt();
                    column = in.nextInt();
                    action.add(line);action.add(column);
                }
            }
            else{
                // machine turn
                boolean ismax = playerChosen==0;  // replace playerChosen==0 by !playerChosen==0 if you want the AI to lose everytime
                System.out.println("Machine player, what is your action?");
                //action = minimaxSearch.makeDecision(state, ismax); // Is to long to process
                //System.out.println("Metrics for Minimax : " + minimaxSearch.getMetrics());
                //action = alphabetaSearch.makeDecision(state, ismax); //same
                //System.out.println("Metrics for AlphaBeta : " + alphabetaSearch.getMetrics());
                action = iterativeDeepeningAlphaBetaSearch.makeDecision(state, ismax);
                System.out.println("Metrics for IDAlphaBetaSearch : " + iterativeDeepeningAlphaBetaSearch.getMetrics());
            }
            System.out.println("Chosen action is " + action);
            state = game.getResult(state, action);

        }
        System.out.print("GAME OVER: ");
        if (game.winner(state)==null)
            System.out.println("Draw :)");
        else if (game.winner(state) == playerChosen)
            System.out.println("Human wins!");
        else
            System.out.println("Machine wins!");
        game.printState(state);
    }
}
