package fr.emse.ai.util;


import java.util.ArrayList;

public class Tree {
    public static void main(String[] args){
        ArrayList<SimpleTwoPlyGameTree<Integer>> sublist1 = new ArrayList<SimpleTwoPlyGameTree<Integer>>();
        sublist1.add(new SimpleTwoPlyGameTree<Integer>(3,true));
        sublist1.add(new SimpleTwoPlyGameTree<Integer>(12,true));
        sublist1.add(new SimpleTwoPlyGameTree<Integer>(8,true));

        SimpleTwoPlyGameTree<Integer> subtree1 = new SimpleTwoPlyGameTree<Integer>(Integer.MIN_VALUE,false,sublist1);
        ArrayList<SimpleTwoPlyGameTree<Integer>> sublist2 = new ArrayList<SimpleTwoPlyGameTree<Integer>>();
        sublist2.add(new SimpleTwoPlyGameTree<Integer>(2,true));
        sublist2.add(new SimpleTwoPlyGameTree<Integer>(4,true));
        sublist2.add(new SimpleTwoPlyGameTree<Integer>(6,true));

        SimpleTwoPlyGameTree<Integer> subtree2 = new SimpleTwoPlyGameTree<Integer>(Integer.MIN_VALUE,false,sublist2);
        ArrayList<SimpleTwoPlyGameTree<Integer>> sublist3 = new ArrayList<SimpleTwoPlyGameTree<Integer>>();
        sublist3.add(new SimpleTwoPlyGameTree<Integer>(14,true));
        sublist3.add(new SimpleTwoPlyGameTree<Integer>(5,true));
        sublist3.add(new SimpleTwoPlyGameTree<Integer>(2,true));

        SimpleTwoPlyGameTree<Integer> subtree3 = new SimpleTwoPlyGameTree<Integer>(Integer.MIN_VALUE,false,sublist3);

        SimpleTwoPlyGameTree<Integer> tree1 = new SimpleTwoPlyGameTree<Integer>(Integer.MAX_VALUE, true);
        tree1.addChild(subtree1);
        tree1.addChild(subtree2);
        tree1.addChild(subtree3);
        System.out.println(tree1.toString());
        System.out.println(MiniMaxSearch.maxValue(tree1));
        System.out.println(MiniMaxSearch.minValue(tree1));
        System.out.println(AlphaBetaSearch.maxValue(tree1, 0, 0));
        System.out.println(AlphaBetaSearch.minValue(tree1, 0, 0));
    }
}
