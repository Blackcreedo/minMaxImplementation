package fr.emse.ai.adversarial.ttt;

import fr.emse.ai.adversarial.AlphaBetaSearch;
import fr.emse.ai.adversarial.MinimaxSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TttGamePlay {

    public static void main(String[] args) {
        TttGame game = new TttGame();
        MinimaxSearch<List<List<Integer>>, List<Integer>, Integer> minimaxSearch = MinimaxSearch.createFor(game);
        AlphaBetaSearch<List<List<Integer>>, List<Integer>, Integer> alphabetaSearch = AlphaBetaSearch.createFor(game);
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
                System.out.println("Machine player, what is your action?");
                action = minimaxSearch.makeDecision(state, playerChosen==0);
                System.out.println("Metrics for Minimax : " + minimaxSearch.getMetrics());
            }
            System.out.println("Chosen action is " + action);
            state = game.getResult(state, action);

        }
        System.out.print("GAME OVER: ");
        if (game.winner(state)==null)
            System.out.println("Draw :)");
        else if (game.winner(state) == 0)
            System.out.println("Human wins!");
        else
            System.out.println("Machine wins!");
        game.printState(state);
    }
}
