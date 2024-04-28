package org.example.Dynamic;



import org.example.Huffman;
import org.example.Node;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class HuffmanDynamic extends Huffman {

    Map<String, Integer> freq;
    Node root;

    String text;
    public HuffmanDynamic(String text) {
        this.freq = new HashMap<>();
        this.text = text;
    }

    public void createFreq() {
        String[] parts = text.split("[,\\s]+");// Все слова
        Set<String> combination = new LinkedHashSet<>(Arrays.asList(parts));// уникальные слова
        Map<String, Integer> freqCombination = new HashMap<>();
        Map<Character, Integer> freqSymbol = new HashMap<>();
        List<Character> ch = new ArrayList<>();


        for (String str : combination) {
            freqCombination.put(str,0);
        }

        for (int i = 0 ; i < text.length(); i++) {
            if (!freqSymbol.containsKey(text.charAt(i))) {
                freqSymbol.put(text.charAt(i), 0);
            }
            freqSymbol.put(text.charAt(i), freqSymbol.get(text.charAt(i)) + 1);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i <= text.length(); i++) {
            if (i < text.length() && text.charAt(i) != ' ' && text.charAt(i) != ','
                    && text.charAt(i) != '.' && text.charAt(i) != '!'
                    && text.charAt(i) != '?') {
                ch.add(text.charAt(i));
            } else if(ch.size() > 0) {
                for (Character character : ch) {
                    stringBuilder.append(character);
                }
                if (freqCombination.containsKey(stringBuilder.toString()) ) {
                    freqCombination.put(stringBuilder.toString(), freqCombination.get(stringBuilder.toString()) + 1);
                }

                stringBuilder.delete(0, stringBuilder.length());
                ch.clear();
            }
        }
        freqCombination.entrySet().removeIf(entry -> entry.getValue() <= 15);
        for (Map.Entry<String, Integer> entry : freqCombination.entrySet()) {
            if (entry.getValue() > 1) {
                String str = entry.getKey();
                for (int i = 0; i < str.length(); i++) {
                    freqSymbol.put(str.charAt(i), freqSymbol.get(str.charAt(i)) - entry.getValue());
                }
            }
        }



        for (Map.Entry<Character, Integer> entry : freqSymbol.entrySet()) {
            freq.put(entry.getKey().toString(), entry.getValue());
        }
        freq.putAll(freqCombination);

        freq.entrySet().removeIf(entry -> entry.getValue() < 1);
        //System.out.println(freqCombination);



        this.root = buildHuffmanTree(freq);
    }

    public String compress() {
        String output;
        Map<String, String> huffmanCode = new HashMap<>();
        coding(root, "", huffmanCode);


        String sb = huffmanCode(text, huffmanCode);


        StringBuilder table = new StringBuilder();
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
                table.append("000000000000000000000000");
            }
            table.append("11111111");
        }
        table.delete(table.length() - 8, table.length());
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
    public String decompress(String text){
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
            if (map.substring(iteration - 8, iteration).equals
                    ("00000000")) {
                if (map.substring(iteration - 24, iteration).equals("000000000000000000000000")) {
                    num = map.substring(iteration - 48, iteration - 24);
                    iteration -= 48;
                } else {
                    num = map.substring(iteration - 24, iteration - 8);
                    iteration -= 24;
                }
            } else {
                num = map.substring(iteration - 8, iteration);
                iteration -= 8;
            }
            int i = 0;
            while (!map.substring(iteration - 8 - i * 8, iteration - i * 8).equals("11111111")
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

        List<Map.Entry<String, Integer>> entries = new ArrayList<>(freq.entrySet());
        Collections.reverse(entries);

        LinkedHashMap<String, Integer> reversedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entries) {
            reversedMap.put(entry.getKey(), entry.getValue());
        }
        Node root = buildHuffmanTree(reversedMap);

        //System.out.println(freq);
        index = -1;
        StringBuilder dec = new StringBuilder();

        while (index < huffmanCodeSb.length() - 1) {
            index = decode(root, index, String.valueOf(huffmanCodeSb),dec);
        }


        return dec.toString();
    }
}