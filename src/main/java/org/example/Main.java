package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.example.Huffman.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String text = "This is an example of text, with, commas, and spaces This is an example with commas ";
        //System.out.println(text);

        String json = "";
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





        /*for (int i = 0; i < 256; i++) {
            if (histogram[i] != 0)
                freq.put(String.valueOf(i), histogram[i]);
        }*/

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