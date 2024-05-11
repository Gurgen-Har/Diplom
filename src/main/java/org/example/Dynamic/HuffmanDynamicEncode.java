package org.example.Dynamic;



import org.example.Classic.HuffmanClassicEncode;
import org.example.Huffman;
import org.example.bio.BitWriter;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class HuffmanDynamicEncode extends Huffman {

    private final BitWriter bitWriter;

    protected Map<List<Integer>, Integer> histogramWord;


    public HuffmanDynamicEncode(BitWriter bitWriter) {
        super();
        this.bitWriter = bitWriter;
        this.frequency = new LinkedHashMap<>();
        histogramWord = new LinkedHashMap<>();

    }

    public void computeSymbolHistogram(InputStream inputStream) throws IOException {
        histogram = new int[256];
        int byteIn = inputStream.read();
        while (byteIn != -1) {
            histogram[byteIn]++;
            byteIn = inputStream.read();
        }

    }

    public void computeWordHistogram(InputStream inputStream) throws IOException {



        int byteIn = inputStream.read();
        while (byteIn != -1) {
            List<Integer> histogram = new LinkedList<>();
            while ( (97 <= byteIn && byteIn <= 122) || (65 <= byteIn && byteIn <= 90) ) {
                histogram.add(byteIn);
                byteIn = inputStream.read();
            }
            if (histogram.size() > 1) {
                if (histogramWord.containsKey(histogram)) {
                    histogramWord.put(histogram,histogramWord.get(histogram) + 1);
                } else {
                    histogramWord.put(histogram,1);
                }

            } else if (histogram.size() == 0){
                byteIn = inputStream.read();
            }

        }

        histogramWord.entrySet().removeIf(entry -> entry.getValue() < 2);// установить значение
        for (Map.Entry<List<Integer>, Integer> entry : histogramWord.entrySet()) {
            List<Integer> list = entry.getKey();

            for (Integer integer : list) {
                this.histogram[integer] -= entry.getValue();

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

        // Написать выписыватель символов из histogramSymbol
    }
    public void computeCounterLengths() {
        if (histogramWord == null ) {
            throw new RuntimeException("Histogram not initialized");
        } else if (histogram == null) {
            throw new RuntimeException("Histogram not initialized");
        }
        int size = histogramWord.size();
        while (size % 8 != 0) {
            size++;
        }
        counterCodes = new CounterLength[256 + size];
        for (int i = 0; i < histogram.length; i++) {
            counterCodes[i] =
                    histogram[i] > 0 ?
                            histogram[i] < 256 ? CounterLength.SE_REPREZINTA_PE_1_OCTET
                                    : histogram[i] < 65536 ? CounterLength.SE_REPREZINTA_PE_2_OCTETI
                                    : CounterLength.SE_REPREZINTA_PE_4_OCTETI
                            : CounterLength.NU_SE_REPREZINTA;
        }
        int j = 256;
        for (Map.Entry<List<Integer>, Integer> entry : histogramWord.entrySet()) {
            counterCodes[j] =
                    entry.getValue() > 0 ?
                            entry.getValue() < 256 ? CounterLength.SE_REPREZINTA_PE_1_OCTET
                                    : entry.getValue() < 65536 ? CounterLength.SE_REPREZINTA_PE_2_OCTETI
                                    : CounterLength.SE_REPREZINTA_PE_4_OCTETI
                            : CounterLength.NU_SE_REPREZINTA;
            j++;
        }
        for (int i = 0;i < size - histogramWord.size(); j++, i++) {
            counterCodes[j] = CounterLength.NU_SE_REPREZINTA;
        }
        // Написать countercodes для слов так чтобы он делился на 8
    }

    public void writeHeader(BitWriter bitWriter) throws IOException {
        int size = histogramWord.size();
        if (counterCodes == null) {
            throw new RuntimeException("Counter sizes not computed");
        } else {

            while (size % 8 != 0) {
                size++;
            }
            bitWriter.writeNBitValue(size, 8);
        }

        for (int i = 0; i < 256; i++) {
            bitWriter.writeNBitValue(counterCodes[i].ordinal(), 2);
        }
        for (int i = 0; i < 256; i++) {
            if (counterCodes[i] != CounterLength.NU_SE_REPREZINTA) {
                bitWriter.writeNBitValue(histogram[i], counterCodes[i].getNrBytes() * 8);
            }
        }

        for (int i = 0, j = 256; i < size; i++, j++) {
            bitWriter.writeNBitValue(counterCodes[j].ordinal(), 2);
        }
        int j = 256;
        for (List<Integer> list : histogramWord.keySet()) {
            for (Integer integer : list) {
                bitWriter.writeNBitValue(integer, 8);
            }
            bitWriter.writeNBitValue(0, 8 );//разделитель
            bitWriter.writeNBitValue(histogramWord.get(list), counterCodes[j].getNrBytes() * 8 );
            j++;
        }



        // при записи в начало в 8 бит вписать количество слов закодированных
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

    /*Map<String, Integer> freq;
    Node root;

    String text;
    public HuffmanDynamicEncode(String text) {
        this.freq = new HashMap<>();
        this.text = text;
    }

    public void createFreq() {
        String[] parts = text.split("[,\\s]+");// Все слова
        Set<String> combination = new LinkedHashSet<>(Arrays.asList(parts));// уникальные слова
        Map<String, Integer> freqCombination = new HashMap<>();
        Map<Character, Integer> freqSymbol = new HashMap<>();
        List<Character> ch = new ArrayList<>();


        for (String str : combination) {
            freqCombination.put(str,0);
        }

        for (int i = 0 ; i < text.length(); i++) {
            if (!freqSymbol.containsKey(text.charAt(i))) {
                freqSymbol.put(text.charAt(i), 0);
            }
            freqSymbol.put(text.charAt(i), freqSymbol.get(text.charAt(i)) + 1);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i <= text.length(); i++) {
            if (i < text.length() && text.charAt(i) != ' ' && text.charAt(i) != ','
                    && text.charAt(i) != '.' && text.charAt(i) != '!'
                    && text.charAt(i) != '?') {
                ch.add(text.charAt(i));
            } else if(ch.size() > 0) {
                for (Character character : ch) {
                    stringBuilder.append(character);
                }
                if (freqCombination.containsKey(stringBuilder.toString()) ) {
                    freqCombination.put(stringBuilder.toString(), freqCombination.get(stringBuilder.toString()) + 1);
                }

                stringBuilder.delete(0, stringBuilder.length());
                ch.clear();
            }
        }
        freqCombination.entrySet().removeIf(entry -> entry.getValue() <= 15);
        for (Map.Entry<String, Integer> entry : freqCombination.entrySet()) {
            if (entry.getValue() > 1) {
                String str = entry.getKey();
                for (int i = 0; i < str.length(); i++) {
                    freqSymbol.put(str.charAt(i), freqSymbol.get(str.charAt(i)) - entry.getValue());
                }
            }
        }



        for (Map.Entry<Character, Integer> entry : freqSymbol.entrySet()) {
            freq.put(entry.getKey().toString(), entry.getValue());
        }
        freq.putAll(freqCombination);

        freq.entrySet().removeIf(entry -> entry.getValue() < 1);
        //System.out.println(freqCombination);



        this.root = buildHuffmanTree(freq);
    }

    public String compress() {
        String output;
        Map<String, String> huffmanCode = new HashMap<>();
        coding(root, "", huffmanCode);


        String sb = huffmanCode(text, huffmanCode);


        StringBuilder table = new StringBuilder();
        for (Map.Entry<String,Integer> entry : freq.entrySet()) {
            byte[] keys = entry.getKey().getBytes(StandardCharsets.UTF_8);
            for (byte b : keys) {
                table.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
            }


            if (entry.getValue() < 256) {
                table.append(String.format("%8s", Integer.toBinaryString(entry.getValue())).replace(' ', '0'));
            } else if (entry.getValue() < 65536) {
                table.append(String.format("%16s", Integer.toBinaryString(entry.getValue())).replace(' ', '0'));
                table.append("00000000");
            } else if (entry.getValue() < 16777216) {
                table.append(String.format("%24s", Integer.toBinaryString(entry.getValue())).replace(' ', '0'));
                table.append("000000000000000000000000");
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
        //sb.append(table);


        output = table + sb;
        ///////////////////////////////////////////////////////////////////////
        //output = sb.toString();
        return output;
    }
    public String decompress(String text){
        StringBuilder huffmanCodeSb = new StringBuilder();
        StringBuilder map = new StringBuilder();
        HashMap<String, Integer> freq = new LinkedHashMap<>();


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
                if (map.substring(iteration - 24, iteration).equals("000000000000000000000000")) {
                    num = map.substring(iteration - 48, iteration - 24);
                    iteration -= 48;
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
            for (int j = 0; j < string.length() / 8; j ++) {
                resultString.append(new String(new int[]{
                        Integer.parseInt(string.substring(j * 8, j * 8 + 8), 2)
                }, 0, 1));
            }
            freq.put(resultString.toString(), Integer.parseInt(num, 2));
        }

        List<Map.Entry<String, Integer>> entries = new ArrayList<>(freq.entrySet());
        Collections.reverse(entries);

        LinkedHashMap<String, Integer> reversedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entries) {
            reversedMap.put(entry.getKey(), entry.getValue());
        }
        Node root = buildHuffmanTree(reversedMap);

        //System.out.println(freq);
        index = -1;
        StringBuilder dec = new StringBuilder();

        while (index < huffmanCodeSb.length() - 1) {
            index = decode(root, index, String.valueOf(huffmanCodeSb),dec);
        }


        return dec.toString();
    }*/
}