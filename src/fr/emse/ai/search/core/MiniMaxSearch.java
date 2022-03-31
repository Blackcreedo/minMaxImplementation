package fr.emse.ai.search.core;

import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class MiniMaxSearch {

    public static int maxValue(SimpleTwoPlyGameTree<Integer> state){
        if(state.isLeaf()){
            return state.getValue();
        }
        int value = Integer.MIN_VALUE;
        ArrayList<SimpleTwoPlyGameTree<Integer>> children = state.getChildren();
        for (SimpleTwoPlyGameTree<Integer> child : children){
            value = max(value, minValue(child));
        }
        return value;
    }

    public static int minValue(SimpleTwoPlyGameTree<Integer> state){
        if(state.isLeaf()){
            return state.getValue();
        }
        int value = Integer.MAX_VALUE;
        ArrayList<SimpleTwoPlyGameTree<Integer>> children = state.getChildren();
        for (SimpleTwoPlyGameTree<Integer> child : children){
            value = min(value, maxValue(child));
        }
        return value;
    }

}
