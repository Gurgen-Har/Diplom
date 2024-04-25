package org.example;

import java.util.Map;

public abstract class Huffman {
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
    public int decode(Node root, int index, String sb, StringBuilder dec) {
        if (root == null)
            return index;

        // found a leaf node
        if (root.left == null && root.right == null)
        {
            dec.append(root.ch);
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
    public String prepare(String text, Map<String, String> huffmanCode) {
        StringBuilder string = new StringBuilder();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i <= text.length(); i++) {
            if (i < text.length() && text.charAt(i) != ' ' && text.charAt(i) != ','
                    && text.charAt(i) != '.' && text.charAt(i) != '!'
                    && text.charAt(i) != '?') {
                string.append(text.charAt(i));
            } else {
                if (huffmanCode.containsKey(string.toString())) {
                    sb.append(huffmanCode.get(string.toString()));
                    string.delete(0, string.length());
                } else {
                    for (int j = 0; j < string.length(); j++) {
                        sb.append(huffmanCode.get(String.valueOf(string.charAt(j))));
                    }
                    string.delete(0, string.length());
                }
                if (i < text.length()) {
                    sb.append(huffmanCode.get(String.valueOf(text.charAt(i))));
                }

            }


        }
        return sb.toString();
    }
}
