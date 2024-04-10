package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.Dynamic.HuffmanDynamic;
import org.example.Node;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        String text = "This is an example of text, with, commas, and spaces This is an example with commas ";
        /*byte byteValue = Byte.parseByte("01111111", 2);
        //char symbol = '101';
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        String utf8Symbol = new String(new int[] { Integer.parseInt("01111101", 2) }, 0, 1);
        System.out.println(new StringBuilder(new String(new int[] { Integer.parseInt("01111101", 2) }, 0, 1)));
        int sizeInBytes = bytes.length;
        System.out.println("Размер текста в байтах: " + sizeInBytes);*/

        String txt = "101001100111111010100101";
        String targetSubstring = "01111110";

        int index = txt.indexOf(targetSubstring);
       // System.out.println("Подстрока '" + targetSubstring + "' найдена в позиции: " + index);



        //Huffman huffman = new Huffman();
        //huffman.huffmanTreeCoding("aabacdab");
        //huffman.huffmanTreeDecode(huffman.huffmanTreeCoding(text));

        /*HuffmanDynamicCoding huffmanStaticCoding = new HuffmanDynamicCoding(text);
        huffmanStaticCoding.createFreq();
        String tex = huffmanStaticCoding.compress();
        HuffmanDynamicDecode dec = new HuffmanDynamicDecode(tex);
        dec.dataPreparation(tex);*/
        String json = "";
        try {
            json = new String(Files.readAllBytes(Paths.get("B:\\Users\\Gurgen\\Downloads\\Diplom\\Data.json")));
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
        Node root = HuffmanDynamic.buildHuffmanTree(hashMap);
        String jjson = gson.toJson(root);

        // Запись JSON строки в файл
        try (FileWriter writer = new FileWriter("Node.json")) {
            writer.write(jjson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}