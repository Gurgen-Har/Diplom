package org.example.Classic;

import org.example.Node;

import java.util.*;

public class HuffmanClassicDecode extends HuffmanClassic{

    public HuffmanClassicDecode(String text) {
        super(text);
    }



    public String decompress() {
        StringBuilder map = new StringBuilder();
        HashMap<Character, Integer> freq = new LinkedHashMap<>();
        StringBuilder huffmanCodeSb = new StringBuilder();



        int index;
        if (text.charAt(0) == '0') {
            index = text.indexOf("1000000110000001");//ищем разделитель
        } else {
            index = text.indexOf("0111111001111110");
        }
        map.append(text,1,index);
        huffmanCodeSb.append(text, index + 16, text.length());

        int iteration = map.length();
        while (iteration > 0) {
            String num;
            String string;
            StringBuilder resultString = new StringBuilder();
            if (map.substring(iteration - 8, iteration).equals
                    ("00000000")) {
                if (map.substring(iteration - 16, iteration).equals("0000000000000000")) {
                    num = map.substring(iteration - 40, iteration - 16);
                    iteration -= 40;
                } else {
                    num = map.substring(iteration - 24, iteration - 8);
                    iteration -= 24;
                }
            } else {
                num = map.substring(iteration - 8, iteration);
                iteration -= 8;
            }
            int i = 0;
            while (!map.substring(iteration - 8 - i * 8, iteration - i * 8).equals("01011100")
                    && iteration - 8 - i * 8 != 0) {
                i++;
            }
            if (iteration - 8 - i * 8 != 0) {
                string = map.substring(iteration - i * 8, iteration);
            } else {
                string = map.substring(0, iteration);
            }
            iteration = iteration - 8 - i * 8;
            for (int j = 0; j < string.length() / 8; j ++) {
                resultString.append(new String(new int[]{
                        Integer.parseInt(string.substring(j * 8, j * 8 + 8), 2)
                }, 0, 1));
            }
            freq.put(resultString.charAt(0), Integer.parseInt(num, 2));
        }
        List<Map.Entry<Character, Integer>> entries = new ArrayList<>(freq.entrySet());
        Collections.reverse(entries);

        LinkedHashMap<Character, Integer> reversedMap = new LinkedHashMap<>();
        for (Map.Entry<Character, Integer> entry : entries) {
            reversedMap.put(entry.getKey(), entry.getValue());
        }
        Node root = buildHuffmanTree(reversedMap);

        index = -1;
        StringBuilder dec = new StringBuilder();

        while (index < huffmanCodeSb.length() - 1) {
            index = decode(root, index, String.valueOf(huffmanCodeSb),dec);
        }
        return dec.toString();
    }

}
