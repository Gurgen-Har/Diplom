package org.example.Static;

import java.util.Map;

public class HuffmanStaticCoding extends HuffmanStatic{

    public HuffmanStaticCoding(String text) {
        super(text);
    }

    public String compress() {

        String sb = prepare(text, huffmanCode);;

        return sb;
    }


}
