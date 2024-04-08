package org.example.GUI;
import org.apache.commons.io.FilenameUtils;
import org.example.Static.HuffmanStaticCoding;
import org.example.Static.HuffmanStaticDecode;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileSystemView;

public class MainFrame extends JFrame {
    private JTextArea inputArea;
    private JTextArea outputArea;
    private File inputFile;
    private MyMenuBar menuBar = new MyMenuBar();
    private StatusBar statusBar = new StatusBar();
    private Toolbar toolbar = new Toolbar();
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);
    private HuffmanStaticPanel huffmanPanel = new HuffmanStaticPanel();

    public MainFrame() {
        setTitle("CCSD Laboratory");
        setMinimumSize(new Dimension(600, 400));
        setLocationRelativeTo(null); //screen center

        setJMenuBar(menuBar);
        //endregion

        mainPanel.add(huffmanPanel, "1");


        cardLayout.show(mainPanel, "3");
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
        JComboBox comboBox = new JComboBox(new String[]{"Huffman", "LZ77", "LZW"});
        JFileChooser fileChooser;

        public Toolbar() {
            setLayout(new FlowLayout(FlowLayout.LEFT));
            setBackground(Color.lightGray);

            openFileButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                    if (fileChooser.showDialog(MainFrame.this, "OK")
                            == JFileChooser.APPROVE_OPTION) {
                        inputFile = fileChooser.getSelectedFile();
                        statusBar.rightStatus.setText("File: " + inputFile.getName());
                        huffmanPanel.southPanel.huffmanEncodeButton.setEnabled(true);
                        if (FilenameUtils.getExtension(inputFile.getName()).equals("hs")) {
                            huffmanPanel.southPanel.huffmanDecodeButton.setEnabled(true);
                        }

                    }
                }
            });

            comboBox.setSelectedIndex(2);
            comboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (comboBox.getSelectedIndex()) {
                        case 0:
                            cardLayout.show(mainPanel, "1");
                            break;
                        case 1:
                            cardLayout.show(mainPanel, "2");
                            break;
                        case 2:
                            cardLayout.show(mainPanel, "3");
                            break;
                    }
                }
            });

            setBorder(new MatteBorder(0, 0, 1, 0, Color.black));

            add(openFileButton);
            add(new JLabel("Algorithm: "));
            add(comboBox);
        }
    }



    private class HuffmanStaticPanel extends JPanel {
        private HuffmanStaticCoding encoder;
        private HuffmanStaticDecode decoder;
        private SouthPanel southPanel = new SouthPanel();

        public HuffmanStaticPanel() {
            setLayout(new BorderLayout());
            SouthPanel southPanel = new SouthPanel();
            add(southPanel, BorderLayout.SOUTH);
        }

        private class SouthPanel extends JPanel {
            private JButton huffmanEncodeButton = new JButton("Encode");
            private JButton huffmanDecodeButton = new JButton("Decode");

            public SouthPanel() {
                huffmanEncodeButton.setEnabled(false);
                huffmanEncodeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        statusBar.leftStatus.setText("Encoding. Please wait...");
                        EventQueue.invokeLater(new Thread(() -> {


                        }));
                    }
                });
                huffmanDecodeButton.setEnabled(false);
                huffmanDecodeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        statusBar.leftStatus.setText("Decoding. Please wait...");
                        EventQueue.invokeLater(new Thread(() -> {


                        }));
                    }
                });

                setLayout(new FlowLayout());
                add(huffmanEncodeButton);
                add(huffmanDecodeButton);
            }
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
