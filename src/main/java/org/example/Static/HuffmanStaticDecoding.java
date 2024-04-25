package org.example.Static;

import org.example.Node;

public class HuffmanStaticDecoding extends HuffmanStatic{
    public HuffmanStaticDecoding(String text) {
        super(text);

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
