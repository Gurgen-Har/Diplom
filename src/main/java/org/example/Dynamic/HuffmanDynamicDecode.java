package org.example.Dynamic;


import org.example.Node;

import java.util.*;

public class HuffmanDynamicDecode extends HuffmanDynamic {
    public HuffmanDynamicDecode(String text) {
        super(text);
    }

    public static int decode(Node root, int index, String sb, StringBuilder dec) {
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

    public String dataPreparation(String text){
        StringBuilder huffmanCodeSb = new StringBuilder();
        StringBuilder map = new StringBuilder();
        HashMap<String, Integer> freq = new LinkedHashMap<>();


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

            if (map.substring(iteration - 8, iteration).equals("00000000")) {
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
            freq.put(resultString.toString(), Integer.parseInt(num, 2));
        }
        /*huffmanCodeSb.append(text, 0, index);
        map.append(text, index + 8, text.length());

        // разделителем внутри таблицы являет \ с кодом 01011100
        // также если число превышает 8 бит вставляем указатель в конец цифры

        int iteration = map.length();
        while (iteration > 0) {
            String num;
            String string;
            StringBuilder resultString = new StringBuilder();

            if (!map.substring(iteration - 8, iteration).equals("00000000")) { //поменять место нахождение разделителя на конец числа, а не между двух байт
                num = map.substring(iteration - 8, iteration);
                iteration -= 8;
            } else {
                num = map.substring(iteration - 24, iteration - 8);
                iteration -= 24;
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
            freq.put(resultString.toString(), Integer.parseInt(num, 2));

        }*/

        List<Map.Entry<String, Integer>> entries = new ArrayList<>(freq.entrySet());
        Collections.reverse(entries);

        LinkedHashMap<String, Integer> reversedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entries) {
            reversedMap.put(entry.getKey(), entry.getValue());
        }
        Node root = buildHuffmanTree(reversedMap);

        System.out.println(freq);
        index = -1;
        StringBuilder dec = new StringBuilder();

        while (index < huffmanCodeSb.length() - 1) {
            index = decode(root, index, huffmanCodeSb.toString(),dec);
        }
        //System.out.println(dec);

        return dec.toString();
    }
}
