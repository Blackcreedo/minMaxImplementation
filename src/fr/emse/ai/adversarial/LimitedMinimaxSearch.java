package fr.emse.ai.adversarial;

import fr.emse.ai.adversarial.core.Metrics;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): page 169.<br>
 *
 * <pre>
 * <code>
 * function MINIMAX-DECISION(state) returns an action
 *   return argmax_[a in ACTIONS(s)] MIN-VALUE(RESULT(state, a))
 *
 * function MAX-VALUE(state) returns a utility value
 *   if TERMINAL-TEST(state) then return UTILITY(state)
 *   v = -infinity
 *   for each a in ACTIONS(state) do
 *     v = MAX(v, MIN-VALUE(RESULT(s, a)))
 *   return v
 *
 * function MIN-VALUE(state) returns a utility value
 *   if TERMINAL-TEST(state) then return UTILITY(state)
 *     v = infinity
 *     for each a in ACTIONS(state) do
 *       v  = MIN(v, MAX-VALUE(RESULT(s, a)))
 *   return v
 * </code>
 * </pre>
 *
 * Figure 5.3 An algorithm for calculating minimax decisions. It returns the
 * action corresponding to the best possible move, that is, the move that leads
 * to the outcome with the best utility, under the assumption that the opponent
 * plays to minimize utility. The functions MAX-VALUE and MIN-VALUE go through
 * the whole game tree, all the way to the leaves, to determine the backed-up
 * value of a state. The notation argmax_[a in S] f(a) computes the element a of
 * set S that has the maximum value of f(a).
 *
 *
 * @author Ruediger Lunde
 *
 * @param <STATE>
 *            Type which is used for states in the game.
 * @param <ACTION>
 *            Type which is used for actions in the game.
 * @param <PLAYER>
 *            Type which is used for players in the game.
 */
public class LimitedMinimaxSearch<STATE, ACTION, PLAYER> implements
        AdversarialSearch<STATE, ACTION> {

    private Game<STATE, ACTION, PLAYER> game;
    private int expandedNodes;
    private int maxDepth;

    /** Creates a new search object for a given game. */
    public static <STATE, ACTION, PLAYER> LimitedMinimaxSearch<STATE, ACTION, PLAYER> createFor(
            Game<STATE, ACTION, PLAYER> game, int maxDepth) {
        return new LimitedMinimaxSearch<STATE, ACTION, PLAYER>(game, maxDepth);
    }

    public LimitedMinimaxSearch(Game<STATE, ACTION, PLAYER> game, int maxDepth) {
        this.game = game;
        this.maxDepth = maxDepth;
    }

    @Override
    public ACTION makeDecision(STATE state, boolean ismax) {
        expandedNodes = 0;
        ACTION result = null;
        PLAYER player = game.getPlayer(state);
        if(ismax){
            double resultValue = Double.NEGATIVE_INFINITY;
            for (ACTION action : game.getActions(state)) {
                double value = minValue(game.getResult(state, action), player,1);
                if (value > resultValue) {
                    result = action;
                    resultValue = value;
                }
            }
        } else {
            double resultValue = Double.POSITIVE_INFINITY;
            for (ACTION action : game.getActions(state)) {
                double value = maxValue(game.getResult(state, action), player,1);
                if (value < resultValue) {
                    result = action;
                    resultValue = value;
                }
            }
        }
        return result;
    }

    public double maxValue(STATE state, PLAYER player, int depth) { // returns an utility
        // value
        expandedNodes++;
        if (game.isTerminal(state) || depth>=maxDepth)
            return game.getUtility(state, player);
        double value = Double.NEGATIVE_INFINITY;
        for (ACTION action : game.getActions(state))
            value = Math.max(value,
                    minValue(game.getResult(state, action), player, depth++));
        return value;
    }

    public double minValue(STATE state, PLAYER player, int depth) { // returns an utility
        // value
        expandedNodes++;
        if (game.isTerminal(state) || depth>=maxDepth)
            return game.getUtility(state, player);
        double value = Double.POSITIVE_INFINITY;
        for (ACTION action : game.getActions(state))
            value = Math.min(value,
                    maxValue(game.getResult(state, action), player, depth++));
        return value;
    }

    @Override
    public Metrics getMetrics() {
        Metrics result = new Metrics();
        result.set("expandedNodes", expandedNodes);
        return result;
    }
}
