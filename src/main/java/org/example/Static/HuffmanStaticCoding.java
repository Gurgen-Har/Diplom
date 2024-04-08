package org.example.Static;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HuffmanStaticCoding extends HuffmanStatic {

    String output;
    public HuffmanStaticCoding(String text) {
        super(text);
    }
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

    public String compress() {
        Map<String, String> huffmanCode = new HashMap<>();
        coding(root, "", huffmanCode);


        StringBuilder string = new StringBuilder();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != ' ' && text.charAt(i) != ','
                    && text.charAt(i) != '.' && text.charAt(i) != '!'
                    && text.charAt(i) != '?') {
                string.append(text.charAt(i));
            } else {
                if (freq.containsKey(string.toString())) {
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



        StringBuilder table = new StringBuilder();
        char symbol = '{'; // Ваш символ
        sb.append(String.format("%8s",Integer.toBinaryString((int) symbol & 0xFF)).replace(' ', '0'));
        for (Map.Entry<String,Integer> entry : freq.entrySet()) {
            byte[] keys = entry.getKey().getBytes(StandardCharsets.UTF_8);
            for (byte b : keys) {
                table.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
            }

            table.append(String.format("%8s", Integer.toBinaryString(entry.getValue() & 0xFF)).replace(' ', '0'));
        }
        sb.append(table);
        symbol = '}';
        sb.append(String.format("%8s",Integer.toBinaryString((int) symbol & 0xFF)).replace(' ', '0'));
        ///////////////////////////////////////////////////////////////////////
        output = sb.toString();
        return output;
    }

}
