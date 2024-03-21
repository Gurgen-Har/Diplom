package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;

class Node
{
    String str;
    int freq;
    Node left = null, right = null;

    Node(String str, int freq)
    {
        this.str = str;
        this.freq = freq;
    }

    public Node(String str, int freq, Node left, Node right) {
        this.str = str;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }
};
public class Huffman {
    public void coding(Node root, String str,
                       Map<String, String> huffmanCode) {
        if (root == null)
            return;

        // found a leaf node
        if (root.left == null && root.right == null) {
            huffmanCode.put(root.str, str);
        }


        coding(root.left, str + "0", huffmanCode);
        coding(root.right, str + "1", huffmanCode);
    }

    // traverse the Huffman Tree and decode the encoded string
    public  int decode(Node root, int index, String sb, StringBuilder dec) {
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

    public String huffmanTreeCoding(String text){
        String[] parts = text.split("[,\\s]+");// Все слова
        Set<String> combination = new LinkedHashSet<>(Arrays.asList(parts));// уникальные слова
        Map<String, Integer> freqCombination = new LinkedHashMap<>();
        Map<Character, Integer> freq = new LinkedHashMap<>();
        List<Character> ch = new ArrayList<>();


        for (String str : combination) {
            freqCombination.put(str,0);
        }

        for (int i = 0 ; i < text.length(); i++) {
            if (!freq.containsKey(text.charAt(i))) {
                freq.put(text.charAt(i), 0);
            }
            freq.put(text.charAt(i), freq.get(text.charAt(i)) + 1);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != ' ' && text.charAt(i) != ',') {
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
        for (Map.Entry<String, Integer> entry : freqCombination.entrySet()) {
            if (entry.getValue() > 1) {
                String str = entry.getKey();
                for (int i = 0; i < str.length(); i++) {
                    freq.put(str.charAt(i), freq.get(str.charAt(i)) - entry.getValue());
                }
            }
        }

        freqCombination.entrySet().removeIf(entry -> entry.getValue() <= 1);

        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            freqCombination.put(entry.getKey().toString(), entry.getValue());
        }

        freqCombination.entrySet().removeIf(entry -> entry.getValue() < 1);
        //System.out.println(freqCombination);



        Node root = buildHuffmanTree(freqCombination);

        Map<String, String> huffmanCode = new HashMap<>();
        coding(root, "", huffmanCode);





        // создание сжатого кода
        StringBuilder string = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != ' ' && text.charAt(i) != ','
                    && text.charAt(i) != '.' && text.charAt(i) != '!'
                    && text.charAt(i) != '?') {
                string.append(text.charAt(i));
            } else {
                if (freqCombination.containsKey(string.toString())) {
                    sb.append(huffmanCode.get(string.toString()));
                    string.delete(0, string.length());
                } else {
                    for (int j = 0; j < string.length(); j++) {
                        sb.append(huffmanCode.get(String.valueOf(string.charAt(j))));
                    }
                    string.delete(0, string.length());
                }
                sb.append(huffmanCode.get(String.valueOf(text.charAt(i))));

            }


        }

        int index = -1;
        StringBuilder dec = new StringBuilder();

        while (index < sb.length() - 1) {
            index = decode(root, index, sb.toString(),dec);
        }
        System.out.println(dec);

        System.out.println(freqCombination);
        StringBuilder table = new StringBuilder();
        String regex = "11111111"; // Ваш символ
        sb.append(regex);
        for (Map.Entry<String,Integer> entry : freqCombination.entrySet()) {
            byte[] keys = entry.getKey().getBytes(StandardCharsets.UTF_8);
            for (byte b : keys) {
                table.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
            }


            table.append(String.format("%8s", Integer.toBinaryString(entry.getValue() & 0xFF)).replace(' ', '0'));
            table.append("01011100");
        }
        table.delete(table.length() - 8, table.length());
        sb.append(table);

        System.out.println(sb.length());
        ///////////////////////////////////////////////////////////////////////



        return sb.toString();
    }


    public String huffmanTreeDecode(String text) {
        StringBuilder huffmanCodeSb = new StringBuilder();
        StringBuilder map = new StringBuilder();
        HashMap<String, Integer> freq = new LinkedHashMap<>();


        int index = text.indexOf("11111111");


        huffmanCodeSb.append(text, 0, index);
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

        }

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
        System.out.println(dec);



        return dec.toString();
    }

    public Node buildHuffmanTree(Map<String, Integer> freq) {
        PriorityQueue<Node> pq = new PriorityQueue<>(
                (l, r) -> l.freq - r.freq);


        for (Map.Entry<String, Integer> entry : freq.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (pq.size() != 1)
        {

            Node left = pq.poll();
            Node right = pq.poll();


            int sum = left.freq + right.freq;
            pq.add(new Node("\u0000", sum, left, right));
        }

        Node root = pq.peek();
        return root;
    }
}
