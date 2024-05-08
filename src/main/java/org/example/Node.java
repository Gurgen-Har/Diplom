package org.example;

import java.util.List;

public class Node {
    public String str;
    public Character ch;
    public int freq;
    public Node left = null;
    public Node right = null;
    public List<Integer> list;

    public Node(String str, int freq) {
        this.str = str;
        this.freq = freq;
    }

    public Node(List<Integer> list, int freq) {
        this.list = list;
        this.freq = freq;
    }

    public Node(String str, int freq, Node left, Node right) {
        this.str = str;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }
    public Node(List<Integer> list, int freq, Node left, Node right) {
        this.list = list;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }

    public Node(Character ch, int freq) {
        this.ch = ch;
        this.freq = freq;
    }

    public Node(Character ch, int freq, Node left, Node right) {
        this.ch = ch;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }

}
