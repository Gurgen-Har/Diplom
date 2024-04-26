package org.example.Classic;

import org.example.Huffman;
import org.example.Node;

import java.util.*;

public class HuffmanClassic extends Huffman {
    Map<String, Integer> freq;
    Node root;

    String text;
    public HuffmanClassic(String text) {
        this.freq = new HashMap<>();
        this.text = text;
    }

    public void createFreq() {
        for (int i = 0 ; i < text.length(); i++) {
            if (!freq.containsKey(String.valueOf(text.charAt(i)))) {
                freq.put(String.valueOf(text.charAt(i)), 0);
            }
            freq.put(String.valueOf(text.charAt(i)), freq.get(String.valueOf(text.charAt(i))) + 1);
        }



        List<Map.Entry<String, Integer>> list = new ArrayList<>(freq.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                // Сравниваем значения в обратном порядке
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        LinkedHashMap<String, Integer> freqSorted = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            freqSorted.put(entry.getKey(), entry.getValue());
        }
        int size = freqSorted.size();
        for (Map.Entry<String, Integer> entry : freqSorted.entrySet()) {
            String key = entry.getKey();
            freqSorted.put(key, size);
            size--;
        }

        freq.clear();
        freq = freqSorted;
        this.root = buildHuffmanTree(freqSorted);
    }


}
