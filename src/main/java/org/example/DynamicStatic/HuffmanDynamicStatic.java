package org.example.DynamicStatic;

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

public class HuffmanDynamicStatic extends Huffman {
    protected final String text;
    protected HashMap<String, String> huffmanMap;
    protected Node root;
    public HuffmanDynamicStatic(String text) {
        this.text = text;

    }

    public void getHuffmanMap() {
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
        this.huffmanMap = gson.fromJson(json, type);

    }
    public void getTree() {
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
    public String decompress() {


        int index = -1;
        StringBuilder dec = new StringBuilder();

        while (index < text.length() - 1) {
            index = decode(root, index, text,dec);
        }


        return dec.toString();
    }

    public String compress() {

        String sb = huffmanCode(text, huffmanMap);;

        return sb;
    }
}
