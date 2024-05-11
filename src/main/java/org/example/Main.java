package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.example.Huffman.buildClassicTree;
import static org.example.Huffman.codingClassic;

public class Main {
    public static void main(String[] args) throws IOException {
        String text = "This is an example of text, with, commas, and spaces This is an example with commas ";
        //System.out.println(text);

       /* String json = "";
        try {
            json = new String(Files.readAllBytes(Paths.get("Data2.json")));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Создание объекта Gson
        Gson gson = new Gson();

        // Получение типа для десериализации
        java.lang.reflect.Type type = new TypeToken<HashMap<String, Integer>>(){}.getType();

        // Преобразование JSON в HashMap
        HashMap<String, Integer> hashMap = gson.fromJson(json, type);
        int[] histogram = new int[256];

        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            int num = (int) entry.getKey().charAt(0);
            histogram[num] = entry.getValue();
        }




        Map<String, Integer> freq = new LinkedHashMap<>();
        for (int i = 0; i < 256; i++) {
            if (histogram[i] != 0)
                freq.put(String.valueOf(i), histogram[i]);
        }

        Node root = buildClassicTree(freq);
        Map<String, String> dictionaryClassic = new LinkedHashMap<>();
        codingClassic(root, "", dictionaryClassic);




        Gson gsson = new Gson();

        // Преобразование HashMap в JSON строку
        String jsson = gsson.toJson(dictionaryClassic);

        // Запись JSON строки в файл
        try (FileWriter writer = new FileWriter("TreeStatic3.json")) {
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
        try (FileWriter writer = new FileWriter("Node3.json")) {
            writer.write(jssson);
        } catch (IOException e) {
            e.printStackTrace();
        }*/


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