package ru.avalon.javapp.devj110.onedirlist;

public class Main {
    public static void main(String[] args) {
        OneDirList<String> lst = new OneDirList<>();
        lst.addToHead("111");
        lst.addToHead("222");
        lst.addToHead("333");

        for (String el: lst) {
            System.out.println(el);
        }

//        Integer str = lst.aggregate(0, (a, s) -> a + (Integer)Integer.parseInt(s));
//        System.out.println(str);

//        for (String s: lst) {
//            System.out.println(s);
//        }
//        System.out.println("***");
//
//        lst.printAll();
    }
}
