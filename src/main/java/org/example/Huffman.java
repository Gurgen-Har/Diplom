package org.example;

import java.util.*;

public abstract class Huffman {
    protected CounterLength[] counterCodes;

    protected Map<String, String> dictionaryClassic;
    protected Map<List<Integer>, String> dictionaryDynamic;
    protected int[] histogram;

    protected Map<List<Integer>, Integer> frequency;
    protected Node root;

    public Huffman() {
        dictionaryClassic = new HashMap<>();
        dictionaryDynamic = new HashMap<>();
    }
    public static void codingClassic(Node root, String str,
                                     Map<String, String> huffmanMap) {
        if (root == null)
            return;

        // found a leaf node
        if (root.left == null && root.right == null) {
            huffmanMap.put(root.str, str);
        }


        codingClassic(root.left, str + "0", huffmanMap);
        codingClassic(root.right, str + "1", huffmanMap);
    }

    public static void codingDynamic(Node root, String str,
                              Map<List<Integer>, String> huffmanMap) {
        if (root == null)
            return;

        // found a leaf node
        if (root.left == null && root.right == null) {
            huffmanMap.put(root.list, str);
        }


        codingDynamic(root.left, str + "0", huffmanMap);
        codingDynamic(root.right, str + "1", huffmanMap);
    }
    public int decode(Node root, int index, String sb, StringBuilder dec) {
        if (root == null)
            return index;

        // found a leaf node
        if (root.left == null && root.right == null)
        {
            dec.append(root.str);
            //System.out.print(root.ch);
            return index;
        }

        index++;

        if (sb.charAt(index) == '0')
            index = decode(root.left, index, sb,dec);
        else
            index = decode(root.right, index, sb,dec);

        return index;
    }
    public String huffmanCode(String text, Map<String, String> huffmanMap) {
        StringBuilder string = new StringBuilder();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i <= text.length(); i++) {
            if (i < text.length() && text.charAt(i) != ' ' && text.charAt(i) != ','
                    && text.charAt(i) != '.' && text.charAt(i) != '!'
                    && text.charAt(i) != '?') {
                string.append(text.charAt(i));
            } else {
                if (huffmanMap.containsKey(string.toString())) {
                    sb.append(huffmanMap.get(string.toString()));
                    string.delete(0, string.length());
                } else {
                    for (int j = 0; j < string.length(); j++) {
                        sb.append(huffmanMap.get(String.valueOf(string.charAt(j))));
                    }
                    string.delete(0, string.length());
                }
                if (i < text.length()) {
                    sb.append(huffmanMap.get(String.valueOf(text.charAt(i))));
                }

            }


        }
        return sb.toString();
    }

    public static Node buildClassicTree(Map<String, Integer> freq) {
        PriorityQueue<Node> pq = new PriorityQueue<>(
                Comparator.comparingInt(l -> l.freq));


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

    public static Node buildDynamicTree(Map<List<Integer>, Integer> freq) {
        PriorityQueue<Node> pq = new PriorityQueue<>(
                Comparator.comparingInt(l -> l.freq));


        for (Map.Entry<List<Integer>, Integer> entry : freq.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }

        List<Integer> list = null;
        while (pq.size() != 1)
        {

            Node left = pq.poll();
            Node right = pq.poll();


            int sum = left.freq + right.freq;
            pq.add(new Node(list, sum, left, right));
        }

        Node root = pq.peek();
        return root;
    }

    public void computeClassicDictionary() {
        Map<String, Integer> freq = new LinkedHashMap<>();
        for (int i = 0; i < 256; i++) {
            if (histogram[i] != 0)
                freq.put(String.valueOf(i), histogram[i]);
        }

        root = buildClassicTree(freq);
        codingClassic(root, "", dictionaryClassic);
    }

    public void computeDynamicDictionary() {
        Map<List<Integer>, Integer> freq = new HashMap<>();
        freq.putAll(frequency);

        root = buildDynamicTree(freq);
        codingDynamic(root, "", dictionaryDynamic);
    }


    public enum CounterLength {
        NU_SE_REPREZINTA(0),
        SE_REPREZINTA_PE_1_OCTET(1),
        SE_REPREZINTA_PE_2_OCTETI(2),
        SE_REPREZINTA_PE_4_OCTETI(4);

        private int nrBytes;

        CounterLength(int nrBytes) {
            this.nrBytes = nrBytes;
        }

        public int getNrBytes() {
            return nrBytes;
        }
    }
}
