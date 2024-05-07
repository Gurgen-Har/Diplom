
package org.example.Classic;

import org.example.Huffman;
import org.example.bio.BitWriter;

import java.io.IOException;
import java.io.InputStream;

public class HuffmanClassicEncode extends Huffman {
    private final BitWriter bitWriter;


    public HuffmanClassicEncode(BitWriter bitWriter) {
        super();
        this.bitWriter = bitWriter;

    }

    public void computeHistogram(InputStream inputStream) throws IOException {
        histogram = new int[256];
        int byteIn = inputStream.read();
        while (byteIn != -1) {
            histogram[byteIn]++;
            byteIn = inputStream.read();
        }
    }

    public void computeCounterLengths() {
        if (histogram == null) {
            throw new RuntimeException("Histogram not initialized");
        }
        counterCodes = new CounterLength[256];
        for (int i = 0; i < histogram.length; i++) {
            counterCodes[i] =
                    histogram[i] > 0 ?
                            histogram[i] < 256 ? CounterLength.SE_REPREZINTA_PE_1_OCTET
                                    : histogram[i] < 65536 ? CounterLength.SE_REPREZINTA_PE_2_OCTETI
                                    : CounterLength.SE_REPREZINTA_PE_4_OCTETI
                            : CounterLength.NU_SE_REPREZINTA;
        }
    }

    public void writeHeader(BitWriter bitWriter) throws IOException {
        if (counterCodes == null) {
            throw new RuntimeException("Counter sizes not computed");
        }
        for (int i = 0; i < 256; i++) {
            bitWriter.writeNBitValue(counterCodes[i].ordinal(), 2);
        }
        for (int i = 0; i < 256; i++) {
            if (counterCodes[i] != CounterLength.NU_SE_REPREZINTA) {
                bitWriter.writeNBitValue(histogram[i], counterCodes[i].getNrBytes() * 8);
            }
        }
    }

    public void encodeAndWrite(InputStream inputStream) throws IOException {
        int readByte = inputStream.read();
        while (readByte != -1) {
            String val = dictionary.get(String.valueOf(readByte));

            bitWriter.writeNBitValue(Integer.parseInt(mirror(val), 2), val.length());
            readByte = inputStream.read();
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


    /*Map<String, Integer> freq;
    Node root;

    String text;
    public HuffmanClassic(String text) {
        this.freq = new HashMap<>();
        this.text = text;
    }

    public void createFreq() {
        for (int i = 0 ; i < text.length(); i++) {
            if (!freq.containsKey(String.valueOf(text.charAt(i)))) {
                freq.put(String.valueOf(text.charAt(i)), 0);
            }
            freq.put(String.valueOf(text.charAt(i)), freq.get(String.valueOf(text.charAt(i))) + 1);
        }



        *//*List<Map.Entry<String, Integer>> list = new ArrayList<>(freq.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                // Сравниваем значения в обратном порядке
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        LinkedHashMap<String, Integer> freqSorted = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            freqSorted.put(entry.getKey(), entry.getValue());
        }
        int size = freqSorted.size();
        for (Map.Entry<String, Integer> entry : freqSorted.entrySet()) {
            String key = entry.getKey();
            freqSorted.put(key, size);
            size--;
        }

        freq.clear();
        freq = freqSorted;*//*
        this.root = buildHuffmanTree(freq);
    }

    public String compress() {
        Map<String, String> huffmanCode = new HashMap<>();
        coding(root, "", huffmanCode);
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < text.length(); i++) {
            sb.append(huffmanCode.get(String.valueOf(text.charAt(i))));
        }
        StringBuilder table = new StringBuilder();
        for (Map.Entry<String,Integer> entry : freq.entrySet()) {
            byte[] keys = entry.getKey().getBytes(StandardCharsets.UTF_8);
            for (byte b : keys) {
                table.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
            }


            if (entry.getValue() < 256) {
                table.append(String.format("%8s", Integer.toBinaryString(entry.getValue())).replace(' ', '0'));
            }else if (entry.getValue() < 65536) {
                table.append(String.format("%16s", Integer.toBinaryString(entry.getValue())).replace(' ', '0'));
                table.append("00000000");
            } else if (entry.getValue() < 16777216) {
                table.append(String.format("%24s", Integer.toBinaryString(entry.getValue())).replace(' ', '0'));
                table.append("0000000000000000");
            }
            table.append("11111111");
        }
        table.delete(table.length() - 8, table.length());
        if (table.charAt(table.length() - 1) == '0') {
            table.append("1000000110000001");
            table.insert(0, "0");
        } else {
            table.append("0111111001111110");
            table.insert(0, "1");
        }
        return table + sb.toString();
    }

    public String decompress() {
        StringBuilder map = new StringBuilder();
        HashMap<String, Integer> freq = new LinkedHashMap<>();
        StringBuilder huffmanCodeSb = new StringBuilder();



        int index;
        if (text.charAt(0) == '0') {
            index = text.indexOf("1000000110000001");//ищем разделитель
        } else {
            index = text.indexOf("0111111001111110");
        }
        map.append(text,1,index);
        huffmanCodeSb.append(text, index + 16, text.length());

        int iteration = map.length();
        while (iteration > 0) {
            String num;
            String string;
            StringBuilder resultString = new StringBuilder();
            if (map.substring(iteration - 8, iteration).equals
                    ("00000000")) {
                if (map.substring(iteration - 16, iteration).equals("0000000000000000")) {
                    num = map.substring(iteration - 40, iteration - 16);
                    iteration -= 40;
                } else {
                    num = map.substring(iteration - 24, iteration - 8);
                    iteration -= 24;
                }
            } else {
                num = map.substring(iteration - 8, iteration);
                iteration -= 8;
            }
            int i = 0;
            while (!map.substring(iteration - 8 - i * 8, iteration - i * 8).equals("11111111")
                    && iteration - 8 - i * 8 != 0) {
                i++;
            }
            if (iteration - 8 - i * 8 != 0) {
                string = map.substring(iteration - i * 8, iteration);
            } else {
                string = map.substring(0, iteration);
            }
            iteration = iteration - 8 - i * 8;
            if (i > 1) {
                resultString.append((char) Integer.parseInt(string.substring(0, i * 8), 2));
                *//*resultString.append(new String(new int[]{
                        Integer.parseInt(string.substring(0, i * 8), 2)
                }, 0, 1));*//*
            } else {

                for (int j = 0; j < string.length() / 8; j++) {
                    resultString.append(new String(new int[]{
                            Integer.parseInt(string.substring(j * 8, j * 8 + 8), 2)
                    }, 0, 1));
                }
            }
            freq.put(String.valueOf(resultString.charAt(0)), Integer.parseInt(num, 2));
        }
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(freq.entrySet());
        Collections.reverse(entries);

        LinkedHashMap<String, Integer> reversedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entries) {
            reversedMap.put(entry.getKey(), entry.getValue());
        }
        Node root = buildHuffmanTree(reversedMap);

        index = -1;
        StringBuilder dec = new StringBuilder();

        while (index < huffmanCodeSb.length() - 1) {
            index = decode(root, index, String.valueOf(huffmanCodeSb),dec);
        }
        return dec.toString();
    }*/



}
