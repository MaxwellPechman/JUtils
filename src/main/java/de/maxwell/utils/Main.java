package de.maxwell.utils;

import de.maxwell.utils.lists.FlexList;

public class Main {

    public static void main(String[] args) {
        FlexList<String> list = new FlexList<>();
        list.addValue("Dog");
        list.addValue("Me");
        list.addValue("Heya");
        list.addValue("Today is nice!");
        list.addValue("testing a list.");
        list.addValue("123");
        list.addValue("abc");
        list.addValue("my Brain hurts");
        list.addValue("oof");
        list.addValue("Adding");
        list.addValue("More");
        list.addValue("lines");
        list.addValue("of");
        list.addValue("code");
        System.out.println(list);

        list.shiftValues(2,4, 5);
        System.out.println(list);
    }
}