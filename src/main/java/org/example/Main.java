package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.example.Huffman.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String text = "This is an example of text, with, commas, and spaces This is an example with commas ";
        //System.out.println(text);


        /*String filePath = "NodeClassic.json";
        Node root = null;
        try {
            // Создаем экземпляр Gson
            Gson gsoon = new Gson();

            // Читаем данные из JSON-файла и десериализуем их в объект Node
            root = gsoon.fromJson(new FileReader(filePath), Node.class);

            // Выводим данные узла

        } catch (JsonSyntaxException | IOException e) {
            e.printStackTrace();
        }
        Map<String,String> map = new HashMap<>();
        codingClassic(root,"", map);
        System.out.println();
        Gson gsson = new Gson();

        // Преобразование HashMap в JSON строку
        String jsson = gsson.toJson(map);

        // Запись JSON строки в файл
        try (FileWriter writer = new FileWriter("TreeStaticClassic.json")) {
            writer.write(jsson);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /*String json = "";
        try {
            json = new String(Files.readAllBytes(Paths.get("TreeStatic3.json")));
        } catch (IOException e) {
            e.printStackTrace();

        }

        // Создание объекта Gson
        Gson gsonn = new Gson();

        // Получение типа для десериализации
        Type type = new TypeToken<HashMap<String, String>>(){}.getType();

        // Преобразование JSON в HashMap
        Map<String, String> map = new HashMap<>();
        map = gsonn.fromJson(json, type);

        Map<List<Integer>, Integer> freq = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            if (!map.containsKey(String.valueOf(i))) {
                freq.put(List.of(i), 1);
            }
        }*/




        String filePath = "NodeDynamic.json";
        Node root = null;
        try {
// Создаем экземпляр Gson
            Gson gson = new Gson();

// Читаем данные из JSON-файла и десериализуем их в объект Node
            root = gson.fromJson(new FileReader(filePath), Node.class);


// Выводим данные узла

        } catch (JsonSyntaxException | IOException e) {
            e.printStackTrace();
        }
        Map<List<Integer>, String> dictionaryClassic = new HashMap<>();
        codingDynamic(root, "", dictionaryClassic);
        int maxSize = 0;
        StringBuilder st = new StringBuilder();
        for (Map.Entry<List<Integer>, String> entry: dictionaryClassic.entrySet()) {

            if (maxSize < entry.getValue().length()) {
                st.delete(0, st.length());
                st.append(entry.getValue());
                maxSize = entry.getValue().length();
            }
        }
        System.out.println("Max " + maxSize);
        System.out.println(st);






        /*PriorityQueue<Node> pq = new PriorityQueue<>(
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

        String val = st.toString();

        for (int i = 0; i < val.length(); i++) {
            char bit = val.charAt(i);
            if (bit == '0')
                root = root.left;
            else if (bit == '1')
                root = root.right;
        }
        root.left = pq.peek();
        pq.poll();
        pq.add(new Node(root.list,root.freq, null, null));
        root.right = pq.peek();
        root.ch = null;
        root.list = null;
        root.str = "";
        root.freq = 1;

        PriorityQueue<Node> ps = new PriorityQueue<>(
                Comparator.comparingInt(l -> l.freq));

        ps.add(new Node(list, root.left.freq + root.right.freq, root.left, root.right));
        Node to = ps.peek();
        System.out.println();
        Gson gssson = new Gson();

// Сериализуем объект Node в JSON-строку
        String jssson = gssson.toJson(to);

// Выводим JSON-строку
        System.out.println(jssson);

// Сохраняем JSON-строку в файл
        try (FileWriter writer = new FileWriter("Node6.json")) {
            writer.write(jssson);
        } catch (IOException e) {
            e.printStackTrace();
        }*/





        /*String json = "";
        try {
            json = new String(Files.readAllBytes(Paths.get("Data.json")));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Создание объекта Gson
        Gson gson = new Gson();

        // Получение типа для десериализации
        java.lang.reflect.Type type = new TypeToken<HashMap<String, Integer>>(){}.getType();

        // Преобразование JSON в HashMap
        Map<List<Integer>, Integer> freq = new LinkedHashMap<>();
        HashMap<String, Integer> hashMap = gson.fromJson(json, type);
        //int[] histogram = new int[256];
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < entry.getKey().length(); i++) {
                int num = (int)entry.getKey().charAt(i);
                list.add(num);
            }
            freq.put(list, entry.getValue());

            //histogram[num] = entry.getValue();
        }





        *//*for (int i = 0; i < 256; i++) {
            if (histogram[i] != 0)
                freq.put(String.valueOf(i), histogram[i]);
        }*//*

        Node root = buildDynamicTree(freq);
        Map<List<Integer>, String> dictionaryDynamic = new HashMap<>();
        codingDynamic(root, "", dictionaryDynamic);




        Gson gsson = new Gson();

        // Преобразование HashMap в JSON строку
        String jsson = gsson.toJson(dictionaryDynamic);

        // Запись JSON строки в файл
        try (FileWriter writer = new FileWriter("TreeStatic4.json")) {
            writer.write(jsson);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Gson gssson = new Gson();

        // Сериализуем объект Node в JSON-строку
        String jssson = gssson.toJson(root);

        // Выводим JSON-строку
        System.out.println(jssson);

        // Сохраняем JSON-строку в файл
        try (FileWriter writer = new FileWriter("Node4.json")) {
            writer.write(jssson);
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

        /*try {
            // Создаем поток вывода в файл
            OutputStream outputStream = new FileOutputStream("output.txt");

            // Записываем байт, который представляет вашу последовательность 01010101
            outputStream.write(0b010101001); // Префикс 0b указывает на двоичное число в Java

            // Закрываем поток
            outputStream.close();

            System.out.println("Данные успешно записаны в файл.");
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }*/


    }
}