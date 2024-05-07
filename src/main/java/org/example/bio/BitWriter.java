package org.example.bio;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Класс, отвечающий за запись отдельных битов в поток вывода.
 * Изменяемый, но не потокобезопасный.
 */
public class BitWriter {
    private final OutputStream outputStream;
    private final WriteBuffer buffer = new WriteBuffer();

    // Конструктор для инициализации BitWriter с указанным потоком вывода.
    public BitWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    // Записывает один бит в поток вывода.
    public void writeBit(int bit) throws IOException {
        if (buffer.isFull()) {
            outputStream.write(buffer.getByteAndClear());
        }
        buffer.putBit(bit);
    }

    // Записывает определенное количество битов (до 64) из указанного значения в поток вывода.
    public void writeNBitValue(long value, int n) throws IOException {
        for (int i = 0; i < n; i++) {
            writeBit((int) ((value >> i) & 1));
        }
    }

    // Принудительно записывает все оставшиеся биты из буфера в поток вывода.
    public void flush() throws IOException {
        outputStream.write(buffer.getByteAndClear());
        outputStream.flush();
    }
}
