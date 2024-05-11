package org.example.DynamicStatic;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.example.Huffman;
import org.example.Node;
import org.example.bio.BitWriter;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class HuffmanDynamicStaticEncode extends Huffman {

    protected HashMap<String, String> huffmanMap;
   // protected Node root;
    private BitWriter bitWriter;
    public HuffmanDynamicStaticEncode(BitWriter bitWriter) {
        super();
        this.bitWriter = bitWriter;

    }

    public void getTree() {
        String filePath = "Node4.json";

        try {
            // Создаем экземпляр Gson
            Gson gson = new Gson();

            // Читаем данные из JSON-файла и десериализуем их в объект Node
            this.root = gson.fromJson(new FileReader(filePath), Node.class);
            System.out.println("dss");

            // Выводим данные узла

        } catch (JsonSyntaxException | IOException e) {
            e.printStackTrace();
        }
        codingDynamic(root, "", this.dictionaryDynamic);
    }
    public void getDictionary() {
        String json = "";
        try {
            json = new String(Files.readAllBytes(Paths.get("TreeStatic4.json")));
        } catch (IOException e) {
            e.printStackTrace();

        }

        // Создание объекта Gson
        Gson gson = new Gson();

        // Получение типа для десериализации
        Type type = new TypeToken<HashMap<List<Integer>, String>>(){}.getType();

        // Преобразование JSON в HashMap
        this.dictionaryDynamic = gson.fromJson(json, type);

    }
    public void encodeAndWrite(InputStream inputStream) throws IOException {
        int readByte = inputStream.read();

        while (readByte != -1) {
            List<Integer> list = new LinkedList<>();
            while ( (97 <= readByte && readByte <= 122) || (65 <= readByte && readByte <= 90) ) {
                list.add(readByte);
                readByte = inputStream.read();
            }
            if (dictionaryDynamic.containsKey(list)) {
                bitWriter.writeNBitValue(Integer.parseInt(mirror(dictionaryDynamic.get(list)),2), dictionaryDynamic.get(list).length());
            } else {
                for (Integer integer : list) {
                    List<Integer> list1 = new ArrayList<>();
                    list1.add(integer);
                    bitWriter.writeNBitValue(Integer.parseInt( mirror(dictionaryDynamic.get(list1)),2), dictionaryDynamic.get(list1).length());
                }
            }

            List<Integer> list1 = new ArrayList<>();
            list1.add(readByte);
            if (readByte != -1) {
                bitWriter.writeNBitValue(Integer.parseInt(mirror(dictionaryDynamic.get(list1)), 2), dictionaryDynamic.get(list1).length());
                readByte = inputStream.read();
            } else {
                break;
            }
        }
    }

    public String mirror(String original) {
        StringBuilder mirrored = new StringBuilder();

        for (int i = original.length() - 1; i >= 0; i--) {
            mirrored.append(original.charAt(i));
        }

        return mirrored.toString();
    }

    public void flush() throws IOException {
        bitWriter.flush();
    }

}
