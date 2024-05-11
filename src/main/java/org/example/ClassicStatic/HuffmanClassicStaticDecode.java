package org.example.ClassicStatic;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.example.Huffman;
import org.example.Node;
import org.example.bio.BitReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class HuffmanClassicStaticDecode extends Huffman {
    int [] histogram;
    private BitReader bitReader;
    public HuffmanClassicStaticDecode(BitReader bitReader) {
        super();
        this.bitReader = bitReader;

    }
    public void getTree() {
        String filePath = "Node3.json";

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

    public void getDictionary() {
        String json = "";
        try {
            json = new String(Files.readAllBytes(Paths.get("TreeStatic3.json")));
        } catch (IOException e) {
            e.printStackTrace();

        }

        // Создание объекта Gson
        Gson gson = new Gson();

        // Получение типа для десериализации
        java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>() {
        }.getType();

        // Преобразование JSON в HashMap

    }

    public void decodeAndWrite(OutputStream outputStream) throws IOException {
        //int totalByteCount = getTotalByteCount();
        Node root = this.root;

        int bit = 0;
        while (true){

            do {
                bit = bitReader.readBit(1);

                if (bit == 0)
                    root = root.left;
                else if (bit == 1)
                    root = root.right;
            }while (((root.left != null) && (root.right != null)) && bit != -1);
            if (bit == -1) break;
            outputStream.write(Integer.parseInt(root.str));
            root = this.root;
        }
    }
}
