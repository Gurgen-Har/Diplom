package org.example.ClassicStatic;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.example.Huffman;
import org.example.Node;
import org.example.bio.BitWriter;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;

public class HuffmanClassicStaticEncode extends Huffman {


    BitWriter bitWriter;

    public HuffmanClassicStaticEncode(BitWriter bitWriter) {
        super();
        this.bitWriter = bitWriter;

    }

    public void getDictionary() {
        String json = "";
        try {
            json = new String(Files.readAllBytes(Paths.get("TreeStaticClassic.json")));
        } catch (IOException e) {
            e.printStackTrace();

        }

        // Создание объекта Gson
        Gson gson = new Gson();

        // Получение типа для десериализации

        java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>() {
        }.getType();

        // Преобразование JSON в HashMap
        this.dictionaryClassic = gson.fromJson(json, type);

    }


    public void encodeAndWrite(InputStream inputStream) throws IOException {
        int readByte = inputStream.read();
        while (readByte != -1) {
            String val = dictionaryClassic.get(String.valueOf(readByte));

            bitWriter.writeNBitValue(Integer.parseInt(mirror(val), 2), val.length());
            readByte = inputStream.read();
        }

    }
    public void flush() throws IOException {
        bitWriter.flush();
    }

    public static String mirror(String original) {
        StringBuilder mirrored = new StringBuilder();

        for (int i = original.length() - 1; i >= 0; i--) {
            mirrored.append(original.charAt(i));
        }

        return mirrored.toString();
    }
}
