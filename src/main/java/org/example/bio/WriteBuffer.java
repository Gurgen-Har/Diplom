package org.example.bio;

/**
 * Буфер для записи битов в байт.
 * Изменяемый, но не потокобезопасный.
 */
public class WriteBuffer {
    private byte bits;
    private int pos = 0;

    // Устанавливает указанный бит в буфер.
    public void putBit(int bit) {
        bits |= (bit << pos++);
    }

    // Проверяет, заполнен ли буфер (содержит 8 бит).
    public boolean isFull() {
        return pos == 8;
    }

    // Возвращает количество битов, находящихся в данный момент в буфере.
    public int length() {
        return pos;
    }

    // Возвращает байт, содержащий буферизованные биты, и очищает буфер.
    public byte getByteAndClear() {
        byte theByte = bits;
        bits = 0;
        pos = 0;
        return theByte;
    }
}
