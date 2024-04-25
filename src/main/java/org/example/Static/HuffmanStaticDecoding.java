package org.example.Static;

import org.example.Node;

public class HuffmanStaticDecoding extends HuffmanStatic{
    public HuffmanStaticDecoding(String text) {
        super(text);

    }
    public int decode(Node root, int index, String sb, StringBuilder dec) {
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
    public String decompress() {


        int index = -1;
        StringBuilder dec = new StringBuilder();

        while (index < text.length() - 1) {
            index = decode(root, index, text,dec);
        }
        System.out.println(dec);

        return dec.toString();
    }


}
