package org.example.Static;

public class HuffmanStaticDecode extends HuffmanStatic{
    public HuffmanStaticDecode(String text) {
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
