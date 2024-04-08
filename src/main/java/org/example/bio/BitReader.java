package org.example.bio;
import java.io.IOException;
import java.io.InputStream;

/**
 * Класс, отвечающий за чтение отдельных битов из потока ввода.
 * Изменяемый, но не потокобезопасный.
 */
public class BitReader {
    private InputStream inputStream;
    private ReadBuffer buffer = new ReadBuffer();

    // Конструктор для инициализации BitReader с указанным потоком ввода.
    public BitReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    // Читает один бит из потока ввода.
    public int readBit() throws IOException {
        if (buffer.isEmpty()) {
            int readByte = inputStream.read();
            if (readByte == -1) {
                throw new UnsupportedOperationException("Невозможно прочитать бит, поток закончен");
            }
            buffer.refill((byte) readByte);
        }
        return buffer.nextBit();
    }

    // Читает определенное количество битов (до 64) из потока ввода и возвращает значение.
    public long readNBitValue(int n) throws IOException {
        long nBitValue = 0;
        for (int i = 0; i < n; i++) {
            nBitValue |= (readBit() << i);
        }
        return nBitValue;
    }
}
