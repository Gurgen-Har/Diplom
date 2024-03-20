package org.example.GUI;
import org.example.Huffman;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Window extends JFrame {
    private JTextArea inputArea;
    private JTextArea outputArea;


    public Window() {
        setTitle("Пример");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        // Создание текстового поля для ввода текста для перевода
        inputArea = new JTextArea();
        JScrollPane inputScrollPane = new JScrollPane(inputArea);
        add(inputScrollPane);

        // Создание текстового поля для отображения перевода
        outputArea = new JTextArea();
        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        add(outputScrollPane);
        Huffman huffman = new Huffman();
        // Создание кнопки для запуска перевода
        JButton compressButton = new JButton("Сжать");

        compressButton.addActionListener(e -> {
            String inputText = inputArea.getText();
            outputArea.setText(huffman.huffmanTreeCoding(inputText));
        });
        add(compressButton);

        setVisible(true);
    }
    private class CompressButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Получение текста из левого текстового поля (inputArea)
            String inputText = inputArea.getText();
            // Ваш код для перевода текста
            String translatedText = compressText(inputText);
            // Установка переведенного текста в правое текстовое поле (outputArea)
            outputArea.setText(translatedText);
        }

        private String compressText(String inputText) {
            String outputText = "";
            Huffman huffman = new Huffman();
            huffman.huffmanTreeCoding(inputText);

            return outputText;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Window::new);
    }
}
