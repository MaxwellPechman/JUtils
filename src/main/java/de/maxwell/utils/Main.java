package de.maxwell.utils;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        FlexList<String> list = new FlexList<>();

        list.addValue("Dog");
        list.addValue("Me");
        list.addValue("Heya");
        list.addValue("Today is nice!");
        list.addValue("testing a list....");
        list.addValue("123");
        list.addValue("abc");
        list.addValue("my Brain hurts");
        list.addValue("oof");

        String[] values = new String[]{"Hi", "Im", "Max"};
        list.insertValues(2, values);
        System.out.println(list);
    }
}