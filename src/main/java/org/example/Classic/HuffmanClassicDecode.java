package org.example.Classic;

import org.example.Node;

public class HuffmanClassicDecode extends HuffmanClassic{

    public HuffmanClassicDecode(String text) {
        super(text);
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

    public String decompress() {
        int index = -1;
        //System.out.println("\nDecoded string is: \n");
        StringBuilder dec = new StringBuilder();
        long end = System.currentTimeMillis();
        while (index < text.length() - 1) {
            index = decode(root, index, text,dec);
        }
        return dec.toString();
    }

}
