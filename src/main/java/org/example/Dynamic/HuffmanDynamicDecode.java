package org.example.Dynamic;

import org.example.Huffman;
import org.example.Node;
import org.example.bio.BitReader;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HuffmanDynamicDecode extends Huffman{
    private final BitReader bitReader;
    private int size;
    protected Map<List<Integer>, Integer> histogramWord;
    public HuffmanDynamicDecode(BitReader bitReader) {
        super();
        this.bitReader = bitReader;
        this.frequency = new LinkedHashMap<>();

    }

    public void readSymbolCounterSizes(BitReader bitReader) throws IOException {
        size = (int)bitReader.readNBitValue(8);
        counterCodes = new CounterLength[256 + size];
        for (int i = 0; i < 256; i++) {
            counterCodes[i] = CounterLength.values()[(int) bitReader.readNBitValue(2)];
        }
    }

    public void readWordCounterSizes(BitReader bitReader) throws IOException {
        for (int i = 256; i < 256 + size; i++) {
            counterCodes[i] = CounterLength.values()[(int) bitReader.readNBitValue(2)];
        }
    }

    public void readSymbolHistogram(BitReader bitReader) throws IOException {
        if (counterCodes == null) {
            throw new RuntimeException("Counter sizes not initialized");
        }
        histogram = new int[256];
        for (int i = 0; i < 256; i++) {
            if (counterCodes[i] != CounterLength.NU_SE_REPREZINTA) {
                histogram[i] = (int) bitReader.readNBitValue(counterCodes[i].getNrBytes() * 8);
            }
        }
    }

    public void readWordHistogram(BitReader bitReader) throws IOException {
        if (counterCodes == null) {
            throw new RuntimeException("Counter sizes not initialized");
        }
        histogramWord = new LinkedHashMap<>();
        for (int i = 256; i < 256 + size; i++) {
            List<Integer> list = new ArrayList<>();
            if (counterCodes[i] != CounterLength.NU_SE_REPREZINTA) {
                int readByte = (int)bitReader.readNBitValue(8);
                while (readByte != 0) {
                    list.add(readByte);
                    readByte = (int)bitReader.readNBitValue(8);
                }
                histogramWord.put(list, (int) bitReader.readNBitValue(counterCodes[i].getNrBytes() * 8));

            }
        }

        frequency.putAll(histogramWord);
        for (int i = 0; i < this.histogram.length; i++) {
            if (histogram[i] != 0) {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                frequency.put(list, this.histogram[i]);
            }
        }
    }

    public int getTotalByteCount() {
        if (histogram == null) {
            throw new RuntimeException("Histogram is null. Cannot compute total byte count.");
        }
        int sum = 0;
        for (int i = 0; i < 256; i++) {
            sum += histogram[i];
        }
        for (Map.Entry<List<Integer>,Integer> entry : histogramWord.entrySet()) {
            sum += entry.getValue();
        }
        return sum;
    }
    public void decodeAndWrite(OutputStream outputStream) throws IOException {
        int totalByteCount = getTotalByteCount();
        Node root = this.root;

        for (int i = 0; i < totalByteCount; i++) {
            do {
                int bit = bitReader.readBit(1);

                if (bit == 0)
                    root = root.left;
                else
                    root = root.right;
            }while ((root.left != null) && (root.right != null));
            for (int j = 0 ; j < root.list.size(); j++) {
                int integer = root.list.get(0);
                outputStream.write(integer);
            }
            root = this.root;
        }
    }
}
