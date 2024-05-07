package org.example.Classic;

import org.example.Huffman;
import org.example.Node;
import org.example.bio.BitReader;

import java.io.IOException;
import java.io.OutputStream;

public class HuffmanClassicDecode extends Huffman {


    private final BitReader bitReader;


    public HuffmanClassicDecode(BitReader bitReader) {
        super();
        this.bitReader = bitReader;
    }

    public void readCounterSizes(BitReader bitReader) throws IOException {
        counterCodes = new CounterLength[256];
        for (int i = 0; i < 256; i++) {
            counterCodes[i] = CounterLength.values()[(int) bitReader.readNBitValue(2)];
        }
    }

    public void readHistogram(BitReader bitReader) throws IOException {
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

    public int getTotalByteCount() {
        if (histogram == null) {
            throw new RuntimeException("Histogram is null. Cannot compute total byte count.");
        }
        int sum = 0;
        for (int i = 0; i < 256; i++) {
            sum += histogram[i];
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
            outputStream.write(Integer.parseInt(root.str));
            root = this.root;
        }
    }
}
