
package org.example.GUI;
import org.apache.commons.io.FilenameUtils;
import org.example.Classic.HuffmanClassicDecode;
import org.example.Classic.HuffmanClassicEncode;

import org.example.ClassicStatic.HuffmanClassicStaticDecode;
import org.example.Dynamic.HuffmanDynamicDecode;
import org.example.ClassicStatic.HuffmanClassicStaticEncode;
import org.example.DynamicStatic.HuffmanDynamicStaticDecode;
import org.example.DynamicStatic.HuffmanDynamicStaticEncode;
import org.example.Dynamic.HuffmanDynamicEncode;
import org.example.bio.BitReader;
import org.example.bio.BitWriter;

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
    private File inputFile;
    private BitWriter bitWriter;
    private BitReader bitReader;
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
                        inputFile = fileChooser.getSelectedFile();
                        statusBar.rightStatus.setText("File: " + inputFile.getName());

                        huffmanDynamicPanel.southPanel.huffmanEncodeButton.setEnabled(true);
                        huffmanStaticPanel.southPanel.StaticEncodeButton.setEnabled(true);
                        huffmanClassicPanel.southPanel.huffmanEncodeButton.setEnabled(true);
                        huffmanClassicStaticPanel.southPanel.StaticEncodeButton.setEnabled(true);
                        if (FilenameUtils.getExtension(inputFile.getName()).equals("hs")) {
                            huffmanDynamicPanel.southPanel.huffmanDecodeButton.setEnabled(true);
                        }
                        if (FilenameUtils.getExtension(inputFile.getName()).equals("hs2")) {
                            huffmanStaticPanel.southPanel.StaticDecodeButton.setEnabled(true);
                        }
                        if (FilenameUtils.getExtension(inputFile.getName()).equals("hs3")) {
                            huffmanClassicPanel.southPanel.huffmanDecodeButton.setEnabled(true);
                        }
                        if (FilenameUtils.getExtension(inputFile.getName()).equals("hs4")) {
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
        private HuffmanDynamicEncode encoder;
        private HuffmanDynamicDecode decoder;
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
                        File outputFile = new File(FilenameUtils
                                .removeExtension(inputFile.getAbsolutePath()) + ".hs");
                        OutputStream outputStream = null;
                        InputStream inputStream = null;
                        try {
                            outputFile.createNewFile();
                            outputStream = new FileOutputStream(outputFile);
                            inputStream = new BufferedInputStream(new FileInputStream(inputFile));
                            bitWriter = new BitWriter(new BufferedOutputStream(outputStream));
                            encoder = new HuffmanDynamicEncode(bitWriter);
                            long time0 = System.currentTimeMillis();
                            encoder.computeSymbolHistogram(inputStream);
                            inputStream = new BufferedInputStream(new FileInputStream(inputFile));
                            encoder.computeWordHistogram(inputStream);
                            encoder.computeCounterLengths();
                            encoder.computeDynamicDictionary();
                            encoder.writeHeader(bitWriter);
                            inputStream = new BufferedInputStream(new FileInputStream(inputFile));
                            encoder.encodeAndWrite(inputStream);
                            encoder.flush();
                            long timePassed = System.currentTimeMillis() - time0;
                            statusBar.leftStatus.setText("Encoded in " + (timePassed / 1000.0) + " seconds.");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } finally {
                            try {
                                outputStream.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            try {
                                inputStream.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }


                    }));
                });
                huffmanDecodeButton.setEnabled(false);
                huffmanDecodeButton.addActionListener(e -> {
                    statusBar.leftStatus.setText("Decoding. Please wait...");
                    EventQueue.invokeLater(new Thread(() -> {
                        File outputFile = new File(FilenameUtils.removeExtension(inputFile.getAbsolutePath()) + ".hsd");
                        OutputStream outputStream = null;
                        InputStream inputStream = null;
                        try {
                            outputFile.createNewFile();
                            outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
                            inputStream = new BufferedInputStream(new FileInputStream(inputFile));
                            bitReader = new BitReader(inputStream);
                            decoder = new HuffmanDynamicDecode(bitReader);
                            long time0 = System.currentTimeMillis();
                            decoder.readSymbolCounterSizes(bitReader);
                            decoder.readSymbolHistogram(bitReader);
                            decoder.readWordCounterSizes(bitReader);
                            decoder.readWordHistogram(bitReader);
                            decoder.computeDynamicDictionary();
                            decoder.decodeAndWrite(outputStream);
                            long timePassed = System.currentTimeMillis() - time0;
                            statusBar.leftStatus.setText("Decoded in " + (timePassed / 1000.0) + " seconds.");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } finally {
                            try {
                                assert outputStream != null;
                                outputStream.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            try {
                                assert inputStream != null;
                                inputStream.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
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
        private HuffmanDynamicStaticEncode encoder;
        private HuffmanDynamicStaticDecode decoder;
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
                        File outputFile = new File(FilenameUtils
                                .removeExtension(inputFile.getAbsolutePath()) + ".hs2");
                        OutputStream outputStream = null;
                        InputStream inputStream = null;
                        try {
                            outputFile.createNewFile();
                            outputStream = new FileOutputStream(outputFile);
                            inputStream = new BufferedInputStream(new FileInputStream(inputFile));
                            bitWriter = new BitWriter(new BufferedOutputStream(outputStream));
                            encoder = new HuffmanDynamicStaticEncode(bitWriter);
                            long time0 = System.currentTimeMillis();
                            encoder.getTree();
                            encoder.encodeAndWrite(inputStream);
                            encoder.flush();
                            long timePassed = System.currentTimeMillis() - time0;
                            statusBar.leftStatus.setText("Encoded in " + (timePassed / 1000.0) + " seconds.");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } finally {
                            try {
                                outputStream.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            try {
                                inputStream.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }

                    }));
                });
                StaticDecodeButton.setEnabled(false);
                StaticDecodeButton.addActionListener(e -> {
                    statusBar.leftStatus.setText("Decoding. Please wait...");
                    EventQueue.invokeLater(new Thread(() -> {
                        File outputFile = new File(FilenameUtils.removeExtension(inputFile.getAbsolutePath()) + ".hsd2");
                        OutputStream outputStream = null;
                        InputStream inputStream = null;
                        try {
                            outputFile.createNewFile();
                            outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
                            inputStream = new BufferedInputStream(new FileInputStream(inputFile));
                            bitReader = new BitReader(inputStream);
                            decoder = new HuffmanDynamicStaticDecode(bitReader);
                            long time0 = System.currentTimeMillis();

                            decoder.getTree();
                            decoder.decodeAndWrite(outputStream);
                            long timePassed = System.currentTimeMillis() - time0;
                            statusBar.leftStatus.setText("Decoded in " + (timePassed / 1000.0) + " seconds.");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } finally {
                            try {
                                assert outputStream != null;
                                outputStream.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            try {
                                assert inputStream != null;
                                inputStream.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
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
        private HuffmanClassicEncode encoder;
        private HuffmanClassicDecode decoder;
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
                        File outputFile = new File(FilenameUtils
                                .removeExtension(inputFile.getAbsolutePath()) + ".hs3");
                        OutputStream outputStream = null;
                        InputStream inputStream = null;
                        try {
                            outputFile.createNewFile();
                            outputStream = new FileOutputStream(outputFile);
                            inputStream = new BufferedInputStream(new FileInputStream(inputFile));
                            bitWriter = new BitWriter(new BufferedOutputStream(outputStream));
                            encoder = new HuffmanClassicEncode(bitWriter);
                            long time0 = System.currentTimeMillis();
                            encoder.computeHistogram(inputStream);
                            encoder.computeCounterLengths();
                            encoder.computeClassicDictionary();
                            encoder.writeHeader(bitWriter);
                            inputStream = new BufferedInputStream(new FileInputStream(inputFile));
                            encoder.encodeAndWrite(inputStream);
                            encoder.flush();
                            long timePassed = System.currentTimeMillis() - time0;
                            statusBar.leftStatus.setText("Encoded in " + (timePassed / 1000.0) + " seconds.");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } finally {
                            try {
                                outputStream.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            try {
                                inputStream.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }

                    }));
                });
                huffmanDecodeButton.setEnabled(false);
                huffmanDecodeButton.addActionListener(e -> {
                    statusBar.leftStatus.setText("Decoding. Please wait...");
                    EventQueue.invokeLater(new Thread(() -> {
                        File outputFile = new File(FilenameUtils.removeExtension(inputFile.getAbsolutePath()) + ".hsd3");
                        OutputStream outputStream = null;
                        InputStream inputStream = null;
                        try {
                            outputFile.createNewFile();
                            outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
                            inputStream = new BufferedInputStream(new FileInputStream(inputFile));
                            bitReader = new BitReader(inputStream);
                            decoder = new HuffmanClassicDecode(bitReader);
                            long time0 = System.currentTimeMillis();
                            decoder.readCounterSizes(bitReader);
                            decoder.readHistogram(bitReader);
                            decoder.computeClassicDictionary();
                            decoder.decodeAndWrite(outputStream);
                            long timePassed = System.currentTimeMillis() - time0;
                            statusBar.leftStatus.setText("Decoded in " + (timePassed / 1000.0) + " seconds.");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } finally {
                            try {
                                assert outputStream != null;
                                outputStream.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            try {
                                assert inputStream != null;
                                inputStream.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
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
        private HuffmanClassicStaticEncode encoder;
        private HuffmanClassicStaticDecode decoder;
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
                        File outputFile = new File(FilenameUtils
                                .removeExtension(inputFile.getAbsolutePath()) + ".hs4");
                        OutputStream outputStream = null;
                        InputStream inputStream = null;
                        try {
                            outputFile.createNewFile();
                            outputStream = new FileOutputStream(outputFile);
                            inputStream = new BufferedInputStream(new FileInputStream(inputFile));
                            bitWriter = new BitWriter(new BufferedOutputStream(outputStream));
                            encoder = new HuffmanClassicStaticEncode(bitWriter);
                            long time0 = System.currentTimeMillis();
                            encoder.getDictionary();
                            encoder.encodeAndWrite(inputStream);
                            encoder.flush();
                            long timePassed = System.currentTimeMillis() - time0;
                            statusBar.leftStatus.setText("Encoded in " + (timePassed / 1000.0) + " seconds.");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } finally {
                            try {
                                outputStream.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            try {
                                inputStream.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }


                    }));
                });
                StaticDecodeButton.setEnabled(false);
                StaticDecodeButton.addActionListener(e -> {
                    statusBar.leftStatus.setText("Decoding. Please wait...");
                    EventQueue.invokeLater(new Thread(() -> {
                        File outputFile = new File(FilenameUtils.removeExtension(inputFile.getAbsolutePath()) + ".hsd4");
                        OutputStream outputStream = null;
                        InputStream inputStream = null;
                        try {
                            outputFile.createNewFile();
                            outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
                            inputStream = new BufferedInputStream(new FileInputStream(inputFile));
                            bitReader = new BitReader(inputStream);
                            decoder = new HuffmanClassicStaticDecode(bitReader);
                            long time0 = System.currentTimeMillis();

                            decoder.getTree();
                            decoder.decodeAndWrite(outputStream);
                            long timePassed = System.currentTimeMillis() - time0;
                            statusBar.leftStatus.setText("Decoded in " + (timePassed / 1000.0) + " seconds.");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } finally {
                            try {
                                assert outputStream != null;
                                outputStream.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            try {
                                assert inputStream != null;
                                inputStream.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
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