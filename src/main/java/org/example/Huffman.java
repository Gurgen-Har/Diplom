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

    public void sortMap () {
        List<Map.Entry<String, String>> list = new ArrayList<>(dictionaryClassic.entrySet());

        // Сортировка списка по размеру значений (value)
        list.sort((o1, o2) -> Integer.compare(o2.getValue().length(), o1.getValue().length()));

        // Создание новой отсортированной HashMap из списка
        HashMap<String, String> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        dictionaryClassic.clear();
        dictionaryClassic.putAll(sortedMap);
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
