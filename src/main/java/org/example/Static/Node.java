package org.example.Static;

public class Node {
    String str;
    int freq;
    Node left = null, right = null;
    Character ch;
    //для символов и слов
    Node(String str, int freq)
    {
        this.str = str;
        this.freq = freq;
    }
    public Node(String str, int freq, Node left, Node right) {
        this.str = str;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }
    //для символов
    Node(Character ch, int freq)
    {
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
