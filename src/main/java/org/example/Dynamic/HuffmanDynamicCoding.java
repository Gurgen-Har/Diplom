package org.example.Dynamic;

import org.example.Node;
import org.example.Static.HuffmanStaticCoding;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HuffmanDynamicCoding extends HuffmanDynamic {

    String output;
    public HuffmanDynamicCoding(String text) {
        super(text);
    }


    public String compress() {
        Map<String, String> huffmanCode = new HashMap<>();
        coding(root, "", huffmanCode);


        String sb = prepare(text, huffmanCode);


        StringBuilder table = new StringBuilder();
        /*String regex = "11111111"; // Ваш символ
        sb.append(regex);*/
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
        System.out.println(table.length());
        System.out.println(sb.length());
        if (table.charAt(table.length() - 1) == '0') {
            table.append("1000000110000001");
            table.insert(0, "0");
        } else {
            table.append("0111111001111110");
            table.insert(0, "1");
        }
        //sb.append(table);


        output = table + sb;
        ///////////////////////////////////////////////////////////////////////
        //output = sb.toString();
        return output;
    }

}
