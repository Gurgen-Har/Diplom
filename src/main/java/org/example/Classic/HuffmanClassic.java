package org.example.Classic;

import org.example.Huffman;
import org.example.Node;

import java.util.*;

public class HuffmanClassic extends Huffman {
    Map<Character, Integer> freq;
    Node root;

    String text;
    public HuffmanClassic(String text) {
        this.freq = new HashMap<>();
        this.text = text;
    }

    public void freqAndTree() {
        for (int i = 0 ; i < text.length(); i++) {
            if (!freq.containsKey(text.charAt(i))) {
                freq.put(text.charAt(i), 0);
            }
            freq.put(text.charAt(i), freq.get(text.charAt(i)) + 1);
        }



        List<Map.Entry<Character, Integer>> list = new ArrayList<>(freq.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<Character, Integer>>() {
            @Override
            public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                // Сравниваем значения в обратном порядке
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        LinkedHashMap<Character, Integer> freqSorted = new LinkedHashMap<>();
        for (Map.Entry<Character, Integer> entry : list) {
            freqSorted.put(entry.getKey(), entry.getValue());
        }
        int size = freqSorted.size();
        for (Map.Entry<Character, Integer> entry : freqSorted.entrySet()) {
            Character key = entry.getKey();
            freqSorted.put(key, size);
            size--;
        }

        freq.clear();
        freq = freqSorted;
        this.root = buildHuffmanTree(freqSorted);
    }

    public Node buildHuffmanTree(LinkedHashMap<Character, Integer> freqSorted) {
        Node root;
        PriorityQueue<Node> pq = new PriorityQueue<>(
                (l, r) -> l.freq - r.freq);


        for (Map.Entry<Character, Integer> entry : freqSorted.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (pq.size() != 1)
        {

            Node left = pq.poll();
            Node right = pq.poll();


            int sum = left.freq + right.freq;
            pq.add(new Node('\0', sum, left, right));
        }

        root = pq.peek();
        return root;
    }
}
