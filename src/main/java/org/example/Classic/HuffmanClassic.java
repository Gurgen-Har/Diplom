package org.example.Classic;

import org.example.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanClassic {
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


        PriorityQueue<Node> pq = new PriorityQueue<>(
                (l, r) -> l.freq - r.freq);


        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (pq.size() != 1)
        {

            Node left = pq.poll();
            Node right = pq.poll();


            int sum = left.freq + right.freq;
            pq.add(new Node('\0', sum, left, right));
        }

        this.root = pq.peek();
    }
}
