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
        Map<String, Integer> freqCombination = new HashMap<>();
        Map<Character, Integer> freq = new HashMap<>();
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


        StringBuilder table = new StringBuilder();
        char symbol = '{'; // Ваш символ
        sb.append(String.format("%8s",Integer.toBinaryString((int) symbol & 0xFF)).replace(' ', '0'));
        for (Map.Entry<String,Integer> entry : freqCombination.entrySet()) {
            byte[] keys = entry.getKey().getBytes(StandardCharsets.UTF_8);
            for (byte b : keys) {
                table.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
            }

            table.append(String.format("%8s", Integer.toBinaryString(entry.getValue() & 0xFF)).replace(' ', '0'));
        }
        sb.append(table);
        symbol = '}';
        sb.append(String.format("%8s",Integer.toBinaryString((int) symbol & 0xFF)).replace(' ', '0'));
        ///////////////////////////////////////////////////////////////////////



        return sb.toString();
    }


    public String huffmanTreeDecode(String text) {
        StringBuilder huffmanCodeSb = new StringBuilder();
        StringBuilder map = new StringBuilder();
        HashMap<String, Integer> freq = new HashMap<>();
        int iteration = text.length() - 1;

        StringBuilder utf8Symbol = new StringBuilder(new String(new int[]{
                Integer.parseInt(text.substring(iteration - 7, iteration + 1), 2)
        }, 0, 1));

        if (utf8Symbol.toString().equals("}")) { //заменить { и } на // между кодом и таблицей

            while (!utf8Symbol.toString().equals("{")) {
                utf8Symbol.delete(0, utf8Symbol.length());
                iteration -= 8;
                utf8Symbol = new StringBuilder(new String(new int[]{
                        Integer.parseInt(text.substring(iteration - 7, iteration + 1), 2)
                }, 0, 1));

            }
            map.append(text, iteration + 1, text.length() - 8);
        }
        huffmanCodeSb.append(text, 0, iteration - 7);
        // разделителем внутри таблицы являет \ с кодом 01011100
        // также если число превышает 8 бит вставляем указатель

        iteration = map.length();
        while (iteration > 0) {
            String num;
            String string;
            StringBuilder resultString = new StringBuilder();

            if (!map.substring(iteration - 16, iteration - 8).equals("00000000")) { //поменять место нахождение разделителя на конец числа, а не между двух байт
                num = map.substring(iteration - 8, iteration);
                iteration -= 8;
            } else {
                num = map.substring(iteration - 16, iteration);
                iteration -= 16;
            }
            int i = 0;
            while (!map.substring(iteration - 8 - i * 8, iteration - i * 8).equals("01011100")) {
                i++;
            }
            string = map.substring(iteration - i * 8, iteration);
            iteration -= 8 - i * 8;
            for (int j = 0; j < string.length() / 8; j += 8) {
                resultString.append(new String(new int[]{
                        Integer.parseInt(string.substring(j, j + 8), 2)
                }, 0, 1));
            }
            freq.put(resultString.toString(), Integer.parseInt(num, 2));

        }
        /*

        Node root = buildHuffmanTree(freq);


        int index = -1;
        StringBuilder dec = new StringBuilder();

        while (index < huffmanCode.length() - 1) {
            index = decode(root, index, huffmanCode,dec);
        }
        System.out.println(dec);
*/


        return "";
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
