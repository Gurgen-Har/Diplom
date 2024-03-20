package org.example;

import java.nio.charset.StandardCharsets;
import java.util.*;

class Node
{
    String str;
    int freq;
    Node left = null, right = null;

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
};
public class Huffman {
    public void coding(Node root, String str,
                       Map<String, String> huffmanCode) {
        if (root == null)
            return;

        // found a leaf node
        if (root.left == null && root.right == null) {
            huffmanCode.put(root.str, str);
        }


        coding(root.left, str + "0", huffmanCode);
        coding(root.right, str + "1", huffmanCode);
    }

    // traverse the Huffman Tree and decode the encoded string
    public  int decode(Node root, int index, StringBuilder sb, StringBuilder dec) {
        if (root == null)
            return index;

        // found a leaf node
        if (root.left == null && root.right == null)
        {
            dec.append(root.str);
            //System.out.print(root.str);
            return index;
        }

        index++;

        if (sb.charAt(index) == '0')
            index = decode(root.left, index, sb,dec);
        else
            index = decode(root.right, index, sb,dec);

        return index;
    }

    public void huffmanTreeCoding(String text){
        String[] parts = text.split("[,\\s]+");// Все слова
        Set<String> combination = new LinkedHashSet<>(Arrays.asList(parts));// уникальные слова
        Map<String, Integer> freqCombination = new HashMap<>();
        Map<Character, Integer> freq = new HashMap<>();
        List<Character> ch = new ArrayList<>();


        for (String str : combination) {
            freqCombination.put(str,0);
        }

        for (int i = 0 ; i < text.length(); i++) {
            if (!freq.containsKey(text.charAt(i))) {
                freq.put(text.charAt(i), 0);
            }
            freq.put(text.charAt(i), freq.get(text.charAt(i)) + 1);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != ' ' && text.charAt(i) != ',') {
                ch.add(text.charAt(i));
            } else if(ch.size() > 0) {
                for (Character character : ch) {
                    stringBuilder.append(character);
                }
                if (freqCombination.containsKey(stringBuilder.toString()) ) {
                    freqCombination.put(stringBuilder.toString(), freqCombination.get(stringBuilder.toString()) + 1);
                }

                stringBuilder.delete(0, stringBuilder.length());
                ch.clear();
            }
        }
        for (Map.Entry<String, Integer> entry : freqCombination.entrySet()) {
            if (entry.getValue() > 1) {
                String str = entry.getKey();
                for (int i = 0; i < str.length(); i++) {
                    freq.put(str.charAt(i), freq.get(str.charAt(i)) - entry.getValue());
                }
            }
        }

        freqCombination.entrySet().removeIf(entry -> entry.getValue() <= 1);

        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            freqCombination.put(entry.getKey().toString(), entry.getValue());
        }

        freqCombination.entrySet().removeIf(entry -> entry.getValue() < 1);
        //System.out.println(freqCombination);


        PriorityQueue<Node> pq = new PriorityQueue<>(
                (l, r) -> l.freq - r.freq);


        for (Map.Entry<String, Integer> entry : freqCombination.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (pq.size() != 1)
        {

            Node left = pq.poll();
            Node right = pq.poll();


            int sum = left.freq + right.freq;
            pq.add(new Node("\u0000", sum, left, right));
        }

        Node root = pq.peek();

        Map<String, String> huffmanCode = new HashMap<>();
        coding(root, "", huffmanCode);





        // print encoded string
        StringBuilder string = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != ' ' && text.charAt(i) != ','
                    && text.charAt(i) != '.' && text.charAt(i) != '!'
                    && text.charAt(i) != '?') {
                string.append(text.charAt(i));
            } else {
                if (freqCombination.containsKey(string.toString())) {
                    sb.append(huffmanCode.get(string.toString()));
                    string.delete(0, string.length());
                } else {
                    for (int j = 0; j < string.length(); j++) {
                        sb.append(huffmanCode.get(String.valueOf(string.charAt(j))));
                    }
                    string.delete(0, string.length());
                }
                sb.append(huffmanCode.get(String.valueOf(text.charAt(i))));

            }


        }
        byte[] bytes = sb.toString().getBytes(StandardCharsets.UTF_8);
        int sizeInBytes = bytes.length;
        System.out.println("Размер текста в байтах: " + sizeInBytes);

        ///////////////////////////////////////////////////////////////////////


        int index = -1;
        StringBuilder dec = new StringBuilder();

        while (index < sb.length() - 1) {
            index = decode(root, index, sb,dec);
        }

        System.out.println(dec);
        sb.delete(0, sb.length());

    }

    public void huffmanTreeDecode(String text) {
        String[] str = text.split("/");
        String huffmanCode = str[0];
        String freq = str[1];

    }
}
