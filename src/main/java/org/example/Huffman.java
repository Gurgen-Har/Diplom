package org.example;

import java.util.Map;
import java.util.PriorityQueue;

public abstract class Huffman {
    public void coding(Node root, String str,
                              Map<String, String> huffmanMap) {
        if (root == null)
            return;

        // found a leaf node
        if (root.left == null && root.right == null) {
            huffmanMap.put(root.str, str);
        }


        coding(root.left, str + "0", huffmanMap);
        coding(root.right, str + "1", huffmanMap);
    }
    public int decode(Node root, int index, String sb, StringBuilder dec) {
        if (root == null)
            return index;

        // found a leaf node
        if (root.left == null && root.right == null)
        {
            dec.append(root.str);
            //System.out.print(root.ch);
            return index;
        }

        index++;

        if (sb.charAt(index) == '0')
            index = decode(root.left, index, sb,dec);
        else
            index = decode(root.right, index, sb,dec);

        return index;
    }
    public String huffmanCode(String text, Map<String, String> huffmanMap) {
        StringBuilder string = new StringBuilder();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i <= text.length(); i++) {
            if (i < text.length() && text.charAt(i) != ' ' && text.charAt(i) != ','
                    && text.charAt(i) != '.' && text.charAt(i) != '!'
                    && text.charAt(i) != '?') {
                string.append(text.charAt(i));
            } else {
                if (huffmanMap.containsKey(string.toString())) {
                    sb.append(huffmanMap.get(string.toString()));
                    string.delete(0, string.length());
                } else {
                    for (int j = 0; j < string.length(); j++) {
                        sb.append(huffmanMap.get(String.valueOf(string.charAt(j))));
                    }
                    string.delete(0, string.length());
                }
                if (i < text.length()) {
                    sb.append(huffmanMap.get(String.valueOf(text.charAt(i))));
                }

            }


        }
        return sb.toString();
    }

    public Node buildHuffmanTree(Map<String, Integer> freq) {
        PriorityQueue<Node> pq = new PriorityQueue<>(
                (l, r) -> l.freq - r.freq);


        for (Map.Entry<String, Integer> entry : freq.entrySet()) {
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
        return root;
    }
}
