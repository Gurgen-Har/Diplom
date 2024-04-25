package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.Classic.HuffmanClassic;
import org.example.Classic.HuffmanClassicCoding;
import org.example.Classic.HuffmanClassicDecode;
import org.example.Dynamic.HuffmanDynamic;
import org.example.Dynamic.HuffmanDynamicCoding;
import org.example.Dynamic.HuffmanDynamicDecode;
import org.example.Node;
import org.example.Static.HuffmanStaticCoding;
import org.example.Static.HuffmanStaticDecoding;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        String text = "This is an example of text, with, commas, and spaces This is an example with commas";
        //System.out.println(text);
        HuffmanDynamicCoding huf = new HuffmanDynamicCoding(text);
        //System.out.println(text);
        huf.createFreq();
        String gg = huf.compress();
        HuffmanDynamicDecode hufm = new HuffmanDynamicDecode(gg);
        //System.out.println(huf.compress());
        String str = hufm.dataPreparation(gg);
        System.out.println(str);
        /*HuffmanClassicCoding huffmanClassic = new HuffmanClassicCoding(text);
        huffmanClassic.freqAndTree();
        String out = huffmanClassic.compress();
        System.out.println(out);
        HuffmanClassicDecode huffmanClassicDecode = new HuffmanClassicDecode(out);

        System.out.println(huffmanClassicDecode.decompress());*/

        /*Path inputPath = Paths.get("E:\\Dat\\dec.txt");
        String tex = Files.readString(inputPath, StandardCharsets.UTF_8);
        HuffmanClassicCoding huffmanClassicCoding = new HuffmanClassicCoding(tex);
        huffmanClassicCoding.freqAndTree();
        String out = huffmanClassicCoding.compress();
        Path outputPath = Paths.get("E:\\Dat\\dec1.txt");
        Files.writeString(outputPath, out);*/
        //System.out.println(text);
        /*HuffmanStaticCoding huffmanStaticCoding = new HuffmanStaticCoding(text);
        huffmanStaticCoding.tree();
        String out = huffmanStaticCoding.compress();
        HuffmanStaticDecoding huffmanStaticDecoding = new HuffmanStaticDecoding(out);
        huffmanStaticDecoding.setRoot();
        System.out.println(huffmanStaticDecoding.decompress());*/



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
        HashMap<String, Integer> hashMap = gson.fromJson(json, type);
        Node root = HuffmanDynamicCoding.buildHuffmanTree(hashMap);
        HashMap<String, String> str = new HashMap<>();
        HuffmanDynamicCoding.coding(root,"",str);
        Gson gsson = new Gson();

        // Преобразование HashMap в JSON строку
        String jsson = gsson.toJson(str);

        // Запись JSON строки в файл
        try (FileWriter writer = new FileWriter("TreeStatic.json")) {
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
        try (FileWriter writer = new FileWriter("Node.json")) {
            writer.write(jssson);
        } catch (IOException e) {
            e.printStackTrace();
        }*/


    }
}