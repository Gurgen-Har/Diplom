package org.example;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String text = "This is an example of text, with, commas, and spaces This is an example with commas ";
        /*byte byteValue = Byte.parseByte("01111111", 2);
        //char symbol = '101';
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        String utf8Symbol = new String(new int[] { Integer.parseInt("01111101", 2) }, 0, 1);
        System.out.println(new StringBuilder(new String(new int[] { Integer.parseInt("01111101", 2) }, 0, 1)));
        int sizeInBytes = bytes.length;
        System.out.println("Размер текста в байтах: " + sizeInBytes);*/

        Huffman huffman = new Huffman();
        huffman.huffmanTreeDecode(huffman.huffmanTreeCoding(text));
    }
}