package org.example.Static;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.example.Huffman;
import org.example.Node;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class HuffmanStatic extends Huffman {
    protected final String text;
    protected HashMap<String, String> huffmanCode;
    protected Node root;
    public HuffmanStatic(String text) {
        this.text = text;

    }

    public void tree() {
        String json = "";
        try {
            json = new String(Files.readAllBytes(Paths.get("TreeStatic.json")));
        } catch (IOException e) {
            e.printStackTrace();

        }

        // Создание объекта Gson
        Gson gson = new Gson();

        // Получение типа для десериализации
        java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>(){}.getType();

        // Преобразование JSON в HashMap
        this.huffmanCode = gson.fromJson(json, type);

    }
    public void setRoot() {
        String filePath = "Node.json";

        try {
            // Создаем экземпляр Gson
            Gson gson = new Gson();

            // Читаем данные из JSON-файла и десериализуем их в объект Node
            this.root = gson.fromJson(new FileReader(filePath), Node.class);

            // Выводим данные узла

        } catch (JsonSyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}
