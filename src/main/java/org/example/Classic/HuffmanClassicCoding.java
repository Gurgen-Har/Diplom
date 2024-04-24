package org.example.Classic;

import org.example.Dynamic.HuffmanDynamic;
import org.example.Dynamic.HuffmanDynamicCoding;
import org.example.Node;

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

        return sb.toString();
    }
}
