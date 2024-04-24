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
        /*HuffmanDynamicCoding huf = new HuffmanDynamicCoding(text);
        //System.out.println(text);
        huf.createFreq();
        String gg = huf.compress();
        HuffmanDynamicDecode hufm = new HuffmanDynamicDecode(gg);
        //System.out.println(huf.compress());
        String str = hufm.dataPreparation(gg);
        System.out.println(str);*/
        HuffmanClassicCoding huffmanClassic = new HuffmanClassicCoding(text);
        huffmanClassic.freqAndTree();
        String out = huffmanClassic.compress();
        HuffmanClassicDecode huffmanClassicDecode = new HuffmanClassicDecode(out);

        System.out.println(huffmanClassicDecode.decompress());

        /*Path inputPath = Paths.get("E:\\Dat\\dec.txt");
        String tex = Files.readString(inputPath, StandardCharsets.UTF_8);
        HuffmanClassicCoding huffmanClassicCoding = new HuffmanClassicCoding(tex);
        huffmanClassicCoding.freqAndTree();
        String out = huffmanClassicCoding.compress();
        Path outputPath = Paths.get("E:\\Dat\\dec1.txt");
        Files.writeString(outputPath, out);*/
    }
}