package org.example;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String text = "Это, пример, текста, с, запятыми, и пробелами Это Это пример с запятыми ";


        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        int sizeInBytes = bytes.length;
        System.out.println("Размер текста в байтах: " + sizeInBytes);

        Huffman huffman = new Huffman();
        huffman.huffmanTree(text);
    }
}