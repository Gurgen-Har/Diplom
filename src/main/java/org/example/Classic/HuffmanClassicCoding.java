package org.example.Classic;

import org.example.Dynamic.HuffmanDynamic;
import org.example.Dynamic.HuffmanDynamicCoding;
import org.example.Node;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HuffmanClassicCoding extends HuffmanClassic {

    public HuffmanClassicCoding(String text) {
        super(text);
    }
    public void coding(Node root, String str,
                              Map<Character, String> huffmanCode) {
        if (root == null)
            return;

        // found a leaf node
        if (root.left == null && root.right == null) {
            huffmanCode.put(root.ch, str);
        }


        coding(root.left, str + "0", huffmanCode);
        coding(root.right, str + "1", huffmanCode);
    }
    public String compress() {
        Map<Character, String> huffmanCode = new HashMap<>();
        coding(root, "", huffmanCode);
        StringBuilder txt = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            txt.append(String.format("%8s",Integer.toBinaryString((int)text.charAt(i))).replace(' ', '0'));
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < text.length(); i++) {
            sb.append(huffmanCode.get(text.charAt(i)));
        }
        StringBuilder table = new StringBuilder();
        for (Map.Entry<Character,Integer> entry : freq.entrySet()) {
            byte[] keys = entry.getKey().toString().getBytes(StandardCharsets.UTF_8);
            for (byte b : keys) {
                table.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
            }


            if (entry.getValue() < 256) {
                table.append(String.format("%8s", Integer.toBinaryString(entry.getValue())).replace(' ', '0'));
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
        String out = table + sb.toString();
        return out;
    }
}
