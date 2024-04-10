package org.example;

public class Node {
    public String str;
    public Character ch;
    public int freq;
    public Node left = null;
    public Node right = null;

    public Node(String str, int freq) {
        this.str = str;
        this.freq = freq;
    }

    public Node(String str, int freq, Node left, Node right) {
        this.str = str;
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
