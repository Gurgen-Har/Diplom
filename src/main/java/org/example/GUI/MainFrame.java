
package org.example.GUI;
import org.apache.commons.io.FilenameUtils;
import org.example.Classic.HuffmanClassic;

import org.example.HuffmanClassicStatic;
import org.example.Static.HuffmanDynamicStatic;
import org.example.Dynamic.HuffmanDynamic;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileSystemView;

public class MainFrame extends JFrame {
    private JTextArea inputArea;
    private JTextArea outputArea;
    private Files inputFile;
    private Path inputPath;
    private MyMenuBar menuBar = new MyMenuBar();
    private StatusBar statusBar = new StatusBar();
    private Toolbar toolbar = new Toolbar();
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);
    private HuffmanDynamicPanel huffmanDynamicPanel = new HuffmanDynamicPanel();
    private HuffmanStaticPanel huffmanStaticPanel = new HuffmanStaticPanel();
    private HuffmanClassicPanel huffmanClassicPanel = new HuffmanClassicPanel();
    private HuffmanClassicStaticPanel huffmanClassicStaticPanel = new HuffmanClassicStaticPanel();

    public MainFrame() {
        setTitle("Diplom");
        setMinimumSize(new Dimension(600, 400));
        setLocationRelativeTo(null); //screen center

        setJMenuBar(menuBar);
        //endregion

        mainPanel.add(huffmanDynamicPanel, "1");
        mainPanel.add(huffmanStaticPanel, "2");
        mainPanel.add(huffmanClassicPanel, "3");
        mainPanel.add(huffmanClassicStaticPanel, "4");



        cardLayout.show(mainPanel, "1");
        setLayout(new BorderLayout());
        add(statusBar, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
        add(toolbar, BorderLayout.NORTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    private class MyMenuBar extends JMenuBar {
        private JMenu fileMenu = new JMenu("File");
        private JMenuItem exit = new JMenuItem("Exit");

        public MyMenuBar() {
            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            fileMenu.add(exit);
            add(fileMenu);
        }
    }

    private class StatusBar extends JPanel {
        private JLabel leftStatus = new JLabel("Ready");
        private JLabel rightStatus = new JLabel("No open file");

        public StatusBar() {
            setLayout(new GridBagLayout());
            setBorder(new EtchedBorder(EtchedBorder.RAISED));
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(2, 2, 2, 2);
            c.anchor = GridBagConstraints.WEST;
            c.weightx = 0.5;
            c.weighty = 1;
            c.gridx = 0;
            c.gridy = 0;
            add(leftStatus, c);
            c.gridx = 1;
            c.anchor = GridBagConstraints.EAST;
            c.weightx = 0.5;
            add(rightStatus, c);
        }
    }

    private class Toolbar extends JPanel {
        JButton openFileButton = new JButton("Open File");
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"HuffmanDynamic", "HuffmanStatic", "HuffmanClassic", "HuffmanClassicStatic"});
        JFileChooser fileChooser;

        public Toolbar() {
            setLayout(new FlowLayout(FlowLayout.LEFT));
            setBackground(Color.lightGray);

            openFileButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                    if (fileChooser.showDialog(MainFrame.this, "OK") == JFileChooser.APPROVE_OPTION) {
                        inputPath = fileChooser.getSelectedFile().toPath();
                        statusBar.rightStatus.setText("File: " + inputPath.getFileName());
                        huffmanDynamicPanel.southPanel.huffmanEncodeButton.setEnabled(true);
                        huffmanStaticPanel.southPanel.StaticEncodeButton.setEnabled(true);
                        huffmanClassicPanel.southPanel.huffmanEncodeButton.setEnabled(true);
                        huffmanClassicStaticPanel.southPanel.StaticEncodeButton.setEnabled(true);
                        if (FilenameUtils.getExtension(inputPath.getFileName().toString()).equals("hs")) {
                            huffmanDynamicPanel.southPanel.huffmanDecodeButton.setEnabled(true);
                        }
                        if (FilenameUtils.getExtension(inputPath.getFileName().toString()).equals("hs2")) {
                            huffmanStaticPanel.southPanel.StaticDecodeButton.setEnabled(true);
                        }
                        if (FilenameUtils.getExtension(inputPath.getFileName().toString()).equals("hs3")) {
                            huffmanClassicPanel.southPanel.huffmanDecodeButton.setEnabled(true);
                        }
                        if (FilenameUtils.getExtension(inputPath.getFileName().toString()).equals("hs4")) {
                            huffmanClassicStaticPanel.southPanel.StaticDecodeButton.setEnabled(true);
                        }

                    }
                }
            });

            comboBox.setSelectedIndex(2);
            comboBox.addActionListener(e -> {
                switch (comboBox.getSelectedIndex()) {
                    case 0 -> cardLayout.show(mainPanel, "1");
                    case 1 -> cardLayout.show(mainPanel, "2");
                    case 2 -> cardLayout.show(mainPanel, "3");
                    case 3 -> cardLayout.show(mainPanel, "4");
                }
            });

            setBorder(new MatteBorder(0, 0, 1, 0, Color.black));

            add(openFileButton);
            add(new JLabel("Algorithm: "));
            add(comboBox);
        }
    }



    private class HuffmanDynamicPanel extends JPanel {
        private HuffmanDynamic encoder;
        private HuffmanDynamic decoder;
        private final SouthPanel southPanel = new SouthPanel();

        public HuffmanDynamicPanel() {
            setLayout(new BorderLayout());
            add(southPanel, BorderLayout.SOUTH);
        }

        private class SouthPanel extends JPanel {
            private final JButton huffmanEncodeButton = new JButton("Encode");
            private final JButton huffmanDecodeButton = new JButton("Decode");

            public SouthPanel() {
                huffmanEncodeButton.setEnabled(false);
                huffmanEncodeButton.addActionListener(e -> {
                    statusBar.leftStatus.setText("Encoding. Please wait...");
                    EventQueue.invokeLater(new Thread(() -> {
                        String text = "";
                        try {
                            text = Files.readString(inputPath, StandardCharsets.UTF_8);
                            // Теперь у вас есть строка fileContent, содержащая содержимое выбранного файла
                            // Вы можете сделать что-то с этой строкой здесь
                        } catch (IOException ex) {
                            // Обработка ошибок чтения файла
                            ex.printStackTrace();
                        }
                        long time0 = System.currentTimeMillis();
                        encoder = new HuffmanDynamic(text);
                        encoder.createFreq();
                        String output = encoder.compress();
                        long timePassed = System.currentTimeMillis() - time0;
                        statusBar.leftStatus.setText("Encoded in " + (timePassed / 1000.0) + " seconds.");
                        Path outputPath = Paths.get("E:\\Dat\\"+inputPath.getFileName().getName(0).toString().replaceFirst("[.][^.]+$", "") + ".hs");
                        try {
                            Files.writeString(outputPath, output); // Запись содержимого в файл
                            System.out.println("Содержимое успешно записано в файл -Dynamic " + outputPath);
                        } catch (IOException ex) {
                            // Обработка ошибок записи файла
                            ex.printStackTrace();
                        }

                    }));
                });
                huffmanDecodeButton.setEnabled(false);
                huffmanDecodeButton.addActionListener(e -> {
                    statusBar.leftStatus.setText("Decoding. Please wait...");
                    EventQueue.invokeLater(new Thread(() -> {
                        String text = "";
                        try {
                            text = Files.readString(inputPath, StandardCharsets.UTF_8);

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        long time0 = System.currentTimeMillis();
                        decoder = new HuffmanDynamic(text);
                        String output = decoder.decompress(text);
                        long timePassed = System.currentTimeMillis() - time0;
                        statusBar.leftStatus.setText("Decoded in " + (timePassed / 1000.0) + " seconds.");
                        Path outputPath = Paths.get("E:\\Dat\\"+inputPath.getFileName().getName(0).toString().replaceFirst("[.][^.]+$", "") + "hsDyn.txt");
                        try {
                            Files.writeString(outputPath, output); // Запись содержимого в файл
                            System.out.println("Содержимое успешно записано в файл " + outputPath);
                        } catch (IOException ex) {
                            // Обработка ошибок записи файла
                            ex.printStackTrace();
                        }
                    }));
                });

                setLayout(new FlowLayout());
                add(huffmanEncodeButton);
                add(huffmanDecodeButton);
            }
        }
    }


    private class HuffmanStaticPanel extends JPanel {
        private HuffmanDynamicStatic encoder;
        private HuffmanDynamicStatic decoder;
        private final SouthPanel southPanel = new SouthPanel();

        public HuffmanStaticPanel() {
            setLayout(new BorderLayout());
            add(southPanel, BorderLayout.SOUTH);
        }

        private class SouthPanel extends JPanel {
            private final JButton StaticEncodeButton = new JButton("Encode");
            private final JButton StaticDecodeButton = new JButton("Decode");


            public SouthPanel() {
                StaticEncodeButton.setEnabled(false);
                StaticEncodeButton.addActionListener(e -> {
                    statusBar.leftStatus.setText("Encoding. Please wait...");
                    EventQueue.invokeLater(new Thread(() -> {
                        String text = "";
                        try {
                            text = Files.readString(inputPath, StandardCharsets.UTF_8);
                            // Теперь у вас есть строка fileContent, содержащая содержимое выбранного файла
                            // Вы можете сделать что-то с этой строкой здесь
                        } catch (IOException ex) {
                            // Обработка ошибок чтения файла
                            ex.printStackTrace();
                        }
                        long time0 = System.currentTimeMillis();
                        encoder = new HuffmanDynamicStatic(text);
                        encoder.getHuffmanMap();
                        String output = encoder.compress();
                        long timePassed = System.currentTimeMillis() - time0;
                        statusBar.leftStatus.setText("Encoded in " + (timePassed / 1000.0) + " seconds.");
                        Path outputPath = Paths.get("E:\\Dat\\"+ inputPath.getFileName().getName(0).toString().replaceFirst("[.][^.]+$", "") + ".hs2");
                        try {
                            Files.writeString(outputPath, output); // Запись содержимого в файл
                            System.out.println("Содержимое успешно записано в файл - Static" + outputPath);
                        } catch (IOException ex) {
                            // Обработка ошибок записи файла
                            ex.printStackTrace();
                        }

                    }));
                });
                StaticDecodeButton.setEnabled(false);
                StaticDecodeButton.addActionListener(e -> {
                    statusBar.leftStatus.setText("Decoding. Please wait...");
                    EventQueue.invokeLater(new Thread(() -> {
                        String text = "";
                        try {
                            text = Files.readString(inputPath, StandardCharsets.UTF_8);

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        long time0 = System.currentTimeMillis();
                        decoder = new HuffmanDynamicStatic(text);
                        decoder.getTree();
                        String output = decoder.decompress();
                        long timePassed = System.currentTimeMillis() - time0;
                        statusBar.leftStatus.setText("Decoded in " + (timePassed / 1000.0) + " seconds.");
                        Path outputPath = Paths.get("E:\\Dat\\"+inputPath.getFileName().getName(0).toString().replaceFirst("[.][^.]+$", "") + "hsStat.txt");
                        try {
                            Files.writeString(outputPath, output); // Запись содержимого в файл
                            System.out.println("Содержимое успешно записано в файл " + outputPath);
                        } catch (IOException ex) {
                            // Обработка ошибок записи файла
                            ex.printStackTrace();
                        }
                    }));
                });

                setLayout(new FlowLayout());
                add(StaticEncodeButton);
                add(StaticDecodeButton);
            }
        }
    }


    private class HuffmanClassicPanel extends JPanel {
        private HuffmanClassic encoder;
        private HuffmanClassic decoder;
        private final SouthPanel southPanel = new SouthPanel();

        public HuffmanClassicPanel() {
            setLayout(new BorderLayout());
            add(southPanel, BorderLayout.SOUTH);
        }

        private class SouthPanel extends JPanel {
            private final JButton huffmanEncodeButton = new JButton("Encode");
            private final JButton huffmanDecodeButton = new JButton("Decode");


            public SouthPanel() {
                huffmanEncodeButton.setEnabled(false);
                huffmanEncodeButton.addActionListener(e -> {
                    statusBar.leftStatus.setText("Encoding. Please wait...");
                    EventQueue.invokeLater(new Thread(() -> {
                        String text = "";
                        try {
                            text = Files.readString(inputPath, StandardCharsets.UTF_8);
                            // Теперь у вас есть строка fileContent, содержащая содержимое выбранного файла
                            // Вы можете сделать что-то с этой строкой здесь
                        } catch (IOException ex) {
                            // Обработка ошибок чтения файла
                            ex.printStackTrace();
                        }
                        long time0 = System.currentTimeMillis();
                        encoder = new HuffmanClassic(text);
                        encoder.createFreq();
                        String output = encoder.compress();
                        long timePassed = System.currentTimeMillis() - time0;
                        statusBar.leftStatus.setText("Encoded in " + (timePassed / 1000.0) + " seconds.");
                        Path outputPath = Paths.get("E:\\Dat\\" + inputPath.getFileName().getName(0).toString().replaceFirst("[.][^.]+$", "") + ".hs3");
                        try {
                            Files.writeString(outputPath, output); // Запись содержимого в файл
                            System.out.println("Содержимое успешно записано в файл - Classic" + outputPath);
                        } catch (IOException ex) {
                            // Обработка ошибок записи файла
                            ex.printStackTrace();
                        }

                    }));
                });
                huffmanDecodeButton.setEnabled(false);
                huffmanDecodeButton.addActionListener(e -> {
                    statusBar.leftStatus.setText("Decoding. Please wait...");
                    EventQueue.invokeLater(new Thread(() -> {
                        String text = "";
                        try {
                            text = Files.readString(inputPath, StandardCharsets.UTF_8);

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        long time0 = System.currentTimeMillis();
                        decoder = new HuffmanClassic(text);
                        String output = decoder.decompress();
                        long timePassed = System.currentTimeMillis() - time0;
                        statusBar.leftStatus.setText("Decoded in " + (timePassed / 1000.0) + " seconds.");
                        Path outputPath = Paths.get("E:\\Dat\\"+inputPath.getFileName().getName(0).toString().replaceFirst("[.][^.]+$", "") + "hsClassic.txt");
                        try {
                            Files.writeString(outputPath, output); // Запись содержимого в файл
                            System.out.println("Содержимое успешно записано в файл " + outputPath);
                        } catch (IOException ex) {
                            // Обработка ошибок записи файла
                            ex.printStackTrace();
                        }
                    }));
                });

                setLayout(new FlowLayout());
                add(huffmanEncodeButton);
                add(huffmanDecodeButton);
            }
        }
    }

    private class HuffmanClassicStaticPanel extends JPanel {
        private HuffmanClassicStatic encoder;
        private HuffmanClassicStatic decoder;
        private final SouthPanel southPanel = new SouthPanel();

        public HuffmanClassicStaticPanel() {
            setLayout(new BorderLayout());
            add(southPanel, BorderLayout.SOUTH);
        }

        private class SouthPanel extends JPanel {
            private final JButton StaticEncodeButton = new JButton("Encode");
            private final JButton StaticDecodeButton = new JButton("Decode");


            public SouthPanel() {
                StaticEncodeButton.setEnabled(false);
                StaticEncodeButton.addActionListener(e -> {
                    statusBar.leftStatus.setText("Encoding. Please wait...");
                    EventQueue.invokeLater(new Thread(() -> {
                        String text = "";
                        try {
                            text = Files.readString(inputPath, StandardCharsets.UTF_8);
                            // Теперь у вас есть строка fileContent, содержащая содержимое выбранного файла
                            // Вы можете сделать что-то с этой строкой здесь
                        } catch (IOException ex) {
                            // Обработка ошибок чтения файла
                            ex.printStackTrace();
                        }
                        long time0 = System.currentTimeMillis();
                        encoder = new HuffmanClassicStatic(text);
                        encoder.getHuffmanMap();
                        String output = encoder.compress();
                        long timePassed = System.currentTimeMillis() - time0;
                        statusBar.leftStatus.setText("Encoded in " + (timePassed / 1000.0) + " seconds.");
                        Path outputPath = Paths.get("E:\\Dat\\"+ inputPath.getFileName().getName(0).toString().replaceFirst("[.][^.]+$", "") + ".hs4");
                        try {
                            Files.writeString(outputPath, output); // Запись содержимого в файл
                            System.out.println("Содержимое успешно записано в файл - Static" + outputPath);
                        } catch (IOException ex) {
                            // Обработка ошибок записи файла
                            ex.printStackTrace();
                        }

                    }));
                });
                StaticDecodeButton.setEnabled(false);
                StaticDecodeButton.addActionListener(e -> {
                    statusBar.leftStatus.setText("Decoding. Please wait...");
                    EventQueue.invokeLater(new Thread(() -> {
                        String text = "";
                        try {
                            text = Files.readString(inputPath, StandardCharsets.UTF_8);

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        long time0 = System.currentTimeMillis();
                        decoder = new HuffmanClassicStatic(text);
                        decoder.getTree();
                        String output = decoder.decompress();
                        long timePassed = System.currentTimeMillis() - time0;
                        statusBar.leftStatus.setText("Decoded in " + (timePassed / 1000.0) + " seconds.");
                        Path outputPath = Paths.get("E:\\Dat\\"+inputPath.getFileName().getName(0).toString().replaceFirst("[.][^.]+$", "") + "hsClasStat.txt");
                        try {
                            Files.writeString(outputPath, output); // Запись содержимого в файл
                            System.out.println("Содержимое успешно записано в файл " + outputPath);
                        } catch (IOException ex) {
                            // Обработка ошибок записи файла
                            ex.printStackTrace();
                        }
                    }));
                });

                setLayout(new FlowLayout());
                add(StaticEncodeButton);
                add(StaticDecodeButton);
            }
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}