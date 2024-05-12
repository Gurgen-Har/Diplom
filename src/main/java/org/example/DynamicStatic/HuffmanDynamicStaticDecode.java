package org.example.DynamicStatic;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.example.Huffman;
import org.example.Node;
import org.example.bio.BitReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

public class HuffmanDynamicStaticDecode extends Huffman {
    private BitReader bitReader;
    public HuffmanDynamicStaticDecode(BitReader bitReader) {
        super();
        this.bitReader = bitReader;
    }
    public void getTree() {
        String filePath = "NodeDynamic.json";

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

    public void decodeAndWrite(OutputStream outputStream) throws IOException {

        Node root = this.root;
        int bit = 0;
        while (true){
            do {
                bit = bitReader.readBit(0);

                if (bit == 0)
                    root = root.left;
                else
                    root = root.right;
            }while (((root.left != null) && (root.right != null)) && bit != -1);
            if (bit == -1) break;
            for (int j = 0 ; j < root.list.size(); j++) {
                int integer = root.list.get(j);
                outputStream.write(integer);
            }
            root = this.root;
        }
    }
}
