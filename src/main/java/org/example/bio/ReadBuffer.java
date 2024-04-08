package org.example.bio;

/**
 * Буфер для чтения битов из входного байта.
 * Изменяемый, но не потокобезопасный.
 */
public class ReadBuffer {
    private byte bits;
    private int pos = 8;

    // Возвращает количество оставшихся для чтения битов в текущем байте.
    public int length() {
        return 8 - pos;
    }

    // Перезаполняет буфер указанным байтом.
    public void refill(byte value) {
        bits = value;
        pos = 0;
    }

    // Проверяет, пуст ли буфер (все биты прочитаны).
    public boolean isEmpty() {
        return pos == 8;
    }

    // Читает следующий бит из буфера.
    public int nextBit() {
        return bits >> pos++ & 1;
    }
}
