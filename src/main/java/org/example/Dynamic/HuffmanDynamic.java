package org.example.Dynamic;



import org.example.Node;

import java.util.*;

public class HuffmanDynamic {

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
        for (Map.Entry<String, Integer> entry : freqCombination.entrySet()) {
            if (entry.getValue() > 50) {
                String str = entry.getKey();
                for (int i = 0; i < str.length(); i++) {
                    freqSymbol.put(str.charAt(i), freqSymbol.get(str.charAt(i)) - entry.getValue());
                }
            }
        }

        freqCombination.entrySet().removeIf(entry -> entry.getValue() <= 1);

        for (Map.Entry<Character, Integer> entry : freqSymbol.entrySet()) {
            freq.put(entry.getKey().toString(), entry.getValue());
        }
        freq.putAll(freqCombination);

        freq.entrySet().removeIf(entry -> entry.getValue() < 1);
        //System.out.println(freqCombination);



        this.root = buildHuffmanTree(freq);
    }

    public static Node buildHuffmanTree(Map<String, Integer> freq) {
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
    /*public static void main(String[] args) {
        try {
            // Путь к файлу
            String filePath = "file.txt";

            // Считывание текста из файла в виде массива байт
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));

            // Преобразование массива байт в строку с помощью кодировки UTF-8 (или другой, если нужно)
            String text = new String(bytes, "UTF-8");

            // Вывод текста
            System.out.println(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/