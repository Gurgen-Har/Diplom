package org.example.Dynamic;

import org.example.Node;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HuffmanDynamicCoding extends HuffmanDynamic {

    String output;
    public HuffmanDynamicCoding(String text) {
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



        StringBuilder table = new StringBuilder();
        //String regex = "11111111"; // Ваш символ
        //sb.append(regex);
        for (Map.Entry<String,Integer> entry : freq.entrySet()) {
            byte[] keys = entry.getKey().getBytes(StandardCharsets.UTF_8);
            for (byte b : keys) {
                table.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
            }

            if (entry.getValue() < 256) {
                table.append(String.format("%8s", Integer.toBinaryString(entry.getValue())).replace(' ', '0'));
            } else if (entry.getValue() < 65536) {
                table.append(String.format("%16s", Integer.toBinaryString(entry.getValue())).replace(' ', '0'));
                table.append("00000000");
            } else if (entry.getValue() < 16777216) {
                table.append(String.format("%24s", Integer.toBinaryString(entry.getValue())).replace(' ', '0'));
                table.append("0000000000000000");
            }
            table.append("01011100");
        }
        table.delete(table.length() - 8, table.length());
        if (table.charAt(table.length() - 1) == '0') {
            table.append("1000000110000001");
            table.insert(0, "0");
        } else {
            table.append("0111111001111110");
            table.insert(0, "1");
        }
        //sb.append(table);

        System.out.println(table.length());
        System.out.println(sb.length());
        System.out.println(huffmanCode);
        ///////////////////////////////////////////////////////////////////////
        output = table + sb.toString();
        return output;
    }

}
