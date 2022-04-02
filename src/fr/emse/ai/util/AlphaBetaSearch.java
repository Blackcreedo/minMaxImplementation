package fr.emse.ai.search.core;

import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class AlphaBetaSearch {

    public static int maxValue(SimpleTwoPlyGameTree<Integer> state, int alpha, int beta){
        if(state.isLeaf()){
            return state.getValue();
        }
        //System.out.println(alpha);
        int value = Integer.MIN_VALUE;
        ArrayList<SimpleTwoPlyGameTree<Integer>> children = state.getChildren();
        for (SimpleTwoPlyGameTree<Integer> child : children){
            value = max(value, minValue(child, alpha, beta));
            if(value>= beta){alpha = max(alpha, value);}
        }
        return value;
    }

    public static int minValue(SimpleTwoPlyGameTree<Integer> state, int alpha, int beta){
        if(state.isLeaf()){
            return state.getValue();
        }
        int value = Integer.MAX_VALUE;
        ArrayList<SimpleTwoPlyGameTree<Integer>> children = state.getChildren();
        for (SimpleTwoPlyGameTree<Integer> child : children){
            value = min(value, maxValue(child, alpha, beta));
            if(value<= alpha){beta = min(beta, value);}
        }
        return value;
    }

}
