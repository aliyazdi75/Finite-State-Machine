package com.aliyazdi75;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Google Created by AliYazdi75 on Dec_2016
 */
public class UI extends JFrame {

    private String folderAddress = null;
    public static JTextField txtAdrs, txtCmnd;
    private JRadioButton jrdBST, jrdTST, jrdTrie, jrdHash;
    private JButton btnExct, btnBld, btnRst, btnSave, btnBrowse;
    private static ArrayList<File> fileExist = null;
    private ArrayList<File> folderExist;
    private static BST bst = null;
    private static TST tst = null;
    private static Trie trie = null;
    private static Hash hash = null;
    public static JTextArea txtPrint;
    private static StopWord stopWord = null;
    private static int charAt = 0, folderAdrsCnt = -1;
    private static typeList historyCnt = null;
    private boolean cmdTxt = false;

    public UI() {

        super("Google");
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        JPanel pnlCmpt = new JPanel(null);
        File folder = new File(".");
        folderExist = new ArrayList(Arrays.asList(folder.listFiles()));

        Font font1 = new Font("", Font.PLAIN, 30);
        JLabel label1 = new JLabel("Please enter folder address here or use brows button:");
        label1.setFont(font1);
        label1.setBounds(0, 0, 900, 80);
        pnlCmpt.add(label1);

        txtAdrs = new JTextField(100);
        font1 = new Font("", Font.LAYOUT_LEFT_TO_RIGHT, 35);
        txtAdrs.setFont(font1);
        txtAdrs.setBounds(0, 100, 750, 50);
        pnlCmpt.add(txtAdrs);

        btnBrowse = new JButton();
        btnBrowse.setSize(85, 50);
        btnBrowse.setText("Browse");
        font1 = new Font("", Font.PLAIN, 30);
        btnBrowse.setFont(font1);
        btnBrowse.setBounds(800, 100, 180, 50);
        pnlCmpt.add(btnBrowse);

        Font f = new Font("Consolas", Font.PLAIN, 33);
        txtPrint = new JTextArea();
        txtPrint.setBackground(Color.BLACK);
        txtPrint.setForeground(Color.WHITE);
        txtPrint.setFont(f);
        txtPrint.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtPrint);
        scrollPane.setBounds(0, 180, 1000, 700);
        pnlCmpt.add(scrollPane);

        font1 = new Font("", Font.PLAIN, 30);
        JLabel label2 = new JLabel("Please enter your command:");
        label2.setFont(font1);
        label2.setBounds(0, 870, 460, 80);
        pnlCmpt.add(label2);

        JPanel pnlJrd = new JPanel(new FlowLayout());
        jrdBST = new JRadioButton("BST(AVL)", true);
        jrdTST = new JRadioButton("TST(balance)");
        jrdTrie = new JRadioButton("Trie");
        jrdHash = new JRadioButton("Hash");
        jrdBST.setSize(100, 100);
        jrdTST.setSize(100, 100);
        jrdTrie.setSize(100, 100);
        jrdHash.setSize(100, 100);
        jrdBST.setFont(font1);
        jrdTST.setFont(font1);
        jrdTrie.setFont(font1);
        jrdHash.setFont(font1);
        ButtonGroup jrdG = new ButtonGroup();
        jrdG.add(jrdBST);
        jrdG.add(jrdTST);
        jrdG.add(jrdTrie);
        jrdG.add(jrdHash);
        pnlJrd.add(jrdBST);
        pnlJrd.add(jrdTST);
        pnlJrd.add(jrdTrie);
        pnlJrd.add(jrdHash);
        pnlJrd.setBounds(340, 885, 700, 50);
        pnlCmpt.add(pnlJrd);

        txtCmnd = new JTextField(100);
        font1 = new Font("Consolas", Font.PLAIN, 35);
        txtCmnd.setFont(font1);
        txtCmnd.setBounds(0, 950, 1000, 50);
        pnlCmpt.add(txtCmnd);

        JPanel btnPanel2 = new JPanel(new GridLayout(1, 4, 60, 0));
        btnExct = new JButton("EXCT");
        btnBld = new JButton("Build");
        btnRst = new JButton("Reset");
        btnSave = new JButton("Save");
        JButton btnExt = new JButton("Exit");
        font1 = new Font("", Font.PLAIN, 35);

        btnExct.setFont(font1);
        btnBld.setFont(font1);
        btnRst.setFont(font1);
        btnSave.setFont(font1);
        btnExt.setFont(font1);
        btnPanel2.add(btnExct);
        btnPanel2.add(btnBld);
        btnPanel2.add(btnRst);
        btnPanel2.add(btnSave);
        btnPanel2.add(btnExt);

        btnExct.setEnabled(false);
        btnRst.setEnabled(false);
        txtCmnd.setEnabled(false);

        btnPanel2.setBounds(0, 1020, 1000, 80);
        pnlCmpt.add(btnPanel2);

        pnlCmpt.setBounds(30, 30, 1440, 1235);
        add(pnlCmpt);
        setBackground(Color.LIGHT_GRAY);
        setSize(1100, 1240);

        btnBrowse.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setSize(1200, 1000);
                fileChooser.setDialogTitle("Set folder to start");
                fileChooser.setApproveButtonText("Open and Build");
                fileChooser.setCurrentDirectory(new java.io.File("."));
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (fileChooser.showOpenDialog(btnBrowse) == JFileChooser.APPROVE_OPTION) {
                    folderAddress = fileChooser.getSelectedFile().getAbsolutePath();
                    txtAdrs.setText(folderAddress);
                    bld();
                }
            }

        });
        btnSave.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                String folderToSave = "";
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setSize(1200, 1000);
                fileChooser.setDialogTitle("Set folder to save");
                if (fileChooser.showSaveDialog(btnSave) == JFileChooser.APPROVE_OPTION) {
                    folderToSave = fileChooser.getSelectedFile().getAbsolutePath();
                    try {
                        PrintWriter writer = new PrintWriter(folderToSave + ".txt", "UTF-8");
                        Scanner scn = new Scanner(txtPrint.getText());
                        while (scn.hasNextLine())
                            writer.println(scn.nextLine());
                        writer.close();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(new JFrame(), "Can't save file!"
                                , "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        btnExt.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {

                System.exit(0);
            }
        });
        btnBld.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                bld();
            }
        });
        btnExct.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                exct();
            }
        });
        btnRst.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                txtAdrs.setEnabled(true);
                btnBrowse.setEnabled(true);
                txtCmnd.setEnabled(false);
                btnExct.setEnabled(false);
                btnRst.setEnabled(false);
                btnBld.setEnabled(true);
                jrdBST.setEnabled(true);
                jrdTrie.setEnabled(true);
                jrdTST.setEnabled(true);
                jrdHash.setEnabled(true);
                txtPrint.setText("");
                txtCmnd.setText("");
                bst = null;
                tst = null;
                trie = null;
                stopWord = null;
                folderAddress = "";
                historyCnt = null;
                charAt = 0;
            }
        });

        txtAdrs.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    bld();
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (jrdBST.isSelected())
                        jrdTST.setSelected(true);
                    else if (jrdTST.isSelected())
                        jrdTrie.setSelected(true);
                    else if (jrdTrie.isSelected())
                        jrdHash.setSelected(true);
                    else if (jrdHash.isSelected())
                        jrdBST.setSelected(true);
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (jrdBST.isSelected())
                        jrdHash.setSelected(true);
                    else if (jrdTST.isSelected())
                        jrdBST.setSelected(true);
                    else if (jrdTrie.isSelected())
                        jrdTST.setSelected(true);
                    else if (jrdHash.isSelected())
                        jrdTrie.setSelected(true);
                }
                if (folderExist.size() > 0) {
                    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        if (folderAdrsCnt - 1 > -1) {
                            folderAdrsCnt--;
                            txtAdrs.setText(folderExist.get(folderAdrsCnt).getAbsolutePath());
                        }
                    } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                        if (folderAdrsCnt == -1) {
                            folderAdrsCnt++;
                            txtAdrs.setText(folderExist.get(folderAdrsCnt).getAbsolutePath());
                        } else if (folderAdrsCnt + 1 < folderExist.size()) {
                            folderAdrsCnt++;
                            txtAdrs.setText(folderExist.get(folderAdrsCnt).getAbsolutePath());
                        }
                    }
                }
            }
        });
        txtCmnd.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    exct();
                }
                if (HistoryStack.cmnds.size() > 0) {
                    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        if (historyCnt != null)
                            if (historyCnt.previous != null) {
                                historyCnt = historyCnt.previous;
                                txtCmnd.setText(historyCnt.key);
                            }
                    } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                        if (historyCnt == null) {
                            historyCnt = HistoryStack.root;
                            txtCmnd.setText(historyCnt.key);
                        } else if (historyCnt.next != null) {
                            historyCnt = historyCnt.next;
                            txtCmnd.setText(historyCnt.key);
                        }
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (txtCmnd.getText().equals("") || cmdTxt) {
                        cmdTxt = true;
                        if (historyCnt == null && charAt < HistoryStack.root.key.length()) {
                            txtCmnd.setText(txtCmnd.getText() + HistoryStack.root.key.charAt(charAt++));
                        }
                    }
                }
            }

        });

    }

    private void bld() {

        try {
            folderAddress = txtAdrs.getText();
            long timeStart = System.currentTimeMillis();
            ReadFile readFile = new ReadFile(folderAddress);
            fileExist = readFile.fileExist;
            stopWord = new StopWord();
            long timeEnd = 0;

            txtAdrs.setEnabled(false);
            btnBrowse.setEnabled(false);
            txtCmnd.setEnabled(true);
            btnExct.setEnabled(true);
            btnRst.setEnabled(true);
            btnBld.setEnabled(false);
            jrdBST.setEnabled(false);
            jrdTrie.setEnabled(false);
            jrdTST.setEnabled(false);
            jrdHash.setEnabled(false);

            txtCmnd.requestFocus();

            HistoryStack.cmnds = new ArrayList<typeList>();
            if (jrdBST.isSelected()) {
                stopWord.BSTStopWord();
                bst = new BST(fileExist, new ArrayList<>(), stopWord.stopBst, 1);
            } else if (jrdTST.isSelected()) {
                stopWord.TSTStopWord();
                tst = new TST(fileExist, new ArrayList<>(), stopWord.stopTst, 1);
            } else if (jrdTrie.isSelected()) {
                stopWord.TrieStopWord();
                trie = new Trie(fileExist, new ArrayList<>(), stopWord.stopTrie, 1);
            } else if (jrdHash.isSelected()) {
                stopWord.HashStopWord();
                hash = new Hash(fileExist, new ArrayList<>(), stopWord.stopHash, 1);
            }
            timeEnd = System.currentTimeMillis();
            txtPrint.append("Time Building: " + (timeEnd - timeStart) + " millis\n");
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "First enter your correct folder address!"
                    , "Error!", JOptionPane.ERROR_MESSAGE);
            UI.txtAdrs.setText("");
        }

    }

    private void exct() {
        Scanner cmnd = new Scanner(txtCmnd.getText());
        txtPrint.append(">> " + txtCmnd.getText() + "\n");
        String cmd = cmnd.next();
        long timeStart = System.currentTimeMillis();
        long timeEnd = 0;
        cmdTxt = false;
        if (cmd.equals("add") || cmd.equals("del") || cmd.equals("update")
                || cmd.equals("list") || cmd.equals("search")) {
            new HistoryStack(txtCmnd.getText());
            txtCmnd.setText("");
            historyCnt = null;
            charAt = 0;
            if (jrdBST.isSelected()) {
                if (cmd.equals("add")) {
                    new Add(1, folderAddress, cmnd.next(), fileExist, bst.bst, stopWord.stopBst, true);
                } else if (cmd.equals("del")) {
                    new Delete(1, folderAddress, cmnd.next(), fileExist, bst.bst, stopWord.stopBst, true);
                } else if (cmd.equals("update")) {
                    new Update(1, folderAddress, cmnd.next(), fileExist, bst.bst, stopWord.stopBst);
                } else if (cmd.equals("list")) {
                    GetList getList = new GetList(folderAddress, fileExist, bst.bst);
                    String s = cmnd.next();
                    if (s.equals("-l"))
                        getList.LFileOfProgram();
                    else if (s.equals("-f"))
                        getList.LFileOfFolder();
                    else if (s.equals("-w"))
                        getList.LBstWords(bst.bst);
                    else {
                        JOptionPane.showMessageDialog(new JFrame(), "Enter correct command!"
                                , "Error!", JOptionPane.ERROR_MESSAGE);
                        txtPrint.append("err: Enter correct command!\n");
                        txtCmnd.setText("");
                    }
                } else if (cmd.equals("search")) {
                    String s = cmnd.next();
                    if (s.equals("-w")) {
                        String name = cmnd.next();
                        Search search = new Search(name, bst.bst);
                        search.searchWBst(BST.bstRoot, bst.bst);
                        if (search.doc.isEmpty())
                            UI.txtPrint.append("The word not found in BST!\n");
                        else {
                            for (String i : search.doc)
                                UI.txtPrint.append(i + ", ");
                            UI.txtPrint.append("\n" + "The word found in " + search.count + " docs.\n");
                        }
                        timeEnd = System.currentTimeMillis();
                        txtPrint.append("Word Searching Time: " + (timeEnd - timeStart) + " millis\n");

                    } else if (s.equals("-s")) {
                        String sen = cmnd.nextLine();
                        Search search = new Search(sen, bst.bst);
                        search.searchSentence(1);
                        timeEnd = System.currentTimeMillis();
                        txtPrint.append("Sentence Searching Time: " + (timeEnd - timeStart) + " millis\n");
                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), "Enter correct command!"
                                , "Error!", JOptionPane.ERROR_MESSAGE);
                        txtPrint.append("err: Enter correct command!\n");
                        txtCmnd.setText("");
                    }
                }
            } else if (jrdTST.isSelected()) {
                if (cmd.equals("add")) {
                    new Add(2, folderAddress, cmnd.next(), fileExist, tst.tst, stopWord.stopTst, true);
                } else if (cmd.equals("del")) {
                    new Delete(2, folderAddress, cmnd.next(), fileExist, tst.tst, stopWord.stopTst, true);
                } else if (cmd.equals("update")) {
                    new Update(2, folderAddress, cmnd.next(), fileExist, tst.tst, stopWord.stopTst);
                } else if (cmd.equals("list")) {
                    GetList getList = new GetList(folderAddress, fileExist, tst.tst);
                    String s = cmnd.next();
                    if (s.equals("-l"))
                        getList.LFileOfProgram();
                    else if (s.equals("-f"))
                        getList.LFileOfFolder();
                    else if (s.equals("-w")) {
                        getList.LTstWords(TST.tstRoot, tst.tst);
                        UI.txtPrint.append("Number of words = " + getList.count + "\n");
                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), "Enter correct command!"
                                , "Error!", JOptionPane.ERROR_MESSAGE);
                        txtPrint.append("err: Enter correct command!\n");
                        txtCmnd.setText("");
                    }
                } else if (cmd.equals("search")) {
                    String s = cmnd.next();
                    if (s.equals("-w")) {
                        String name = cmnd.next();
                        Search search = new Search(name, tst.tst);
                        search.searchWTst(TST.tstRoot, tst.tst);
                        if (search.doc.isEmpty())
                            UI.txtPrint.append("The word not found in TST!\n");
                        else {
                            for (String i : search.doc)
                                UI.txtPrint.append(i + ", ");
                            UI.txtPrint.append("\n" + "The word found in " + search.count + " docs.\n");
                        }
                        timeEnd = System.currentTimeMillis();
                        txtPrint.append("Word Searching Time: " + (timeEnd - timeStart) + " millis\n");
                    } else if (s.equals("-s")) {
                        String sen = cmnd.nextLine();
                        Search search = new Search(sen, tst.tst);
                        search.searchSentence(2);
                        timeEnd = System.currentTimeMillis();
                        txtPrint.append("Sentence Searching Time: " + (timeEnd - timeStart) + " millis\n");
                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), "Enter correct command!"
                                , "Error!", JOptionPane.ERROR_MESSAGE);
                        txtPrint.append("err: Enter correct command!\n");
                        txtCmnd.setText("");
                    }
                }

            } else if (jrdTrie.isSelected()) {
                if (cmd.equals("add")) {
                    new Add(3, folderAddress, cmnd.next(), fileExist, trie.trie, stopWord.stopTrie, true);
                } else if (cmd.equals("del")) {
                    new Delete(3, folderAddress, cmnd.next(), fileExist, trie.trie, stopWord.stopTrie, true);
                } else if (cmd.equals("update")) {
                    new Update(3, folderAddress, cmnd.next(), fileExist, trie.trie, stopWord.stopTrie);
                } else if (cmd.equals("list")) {
                    GetList getList = new GetList(folderAddress, fileExist, trie.trie);
                    String s = cmnd.next();
                    if (s.equals("-l"))
                        getList.LFileOfProgram();
                    else if (s.equals("-f"))
                        getList.LFileOfFolder();
                    else if (s.equals("-w")) {
                        getList.LTrieWords(Trie.emptyNode);
                        UI.txtPrint.append("Number of words = " + getList.count + "\n");
                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), "Enter correct command!"
                                , "Error!", JOptionPane.ERROR_MESSAGE);
                        txtPrint.append("err: Enter correct command!\n");
                        txtCmnd.setText("");
                    }
                } else if (cmd.equals("search")) {
                    String s = cmnd.next();
                    if (s.equals("-w")) {
                        String name = cmnd.next();
                        Search search = new Search(name, trie.trie);
                        search.searchWTrie(Trie.emptyNode);
                        if (search.doc.isEmpty())
                            UI.txtPrint.append("The word not found in Trie!\n");
                        else {
                            for (String i : search.doc)
                                UI.txtPrint.append(i + ", ");
                            UI.txtPrint.append("\n" + "The word found in " + search.count + " docs.\n");
                        }
                        timeEnd = System.currentTimeMillis();
                        txtPrint.append("Word Searching Time: " + (timeEnd - timeStart) + " millis\n");
                    } else if (s.equals("-s")) {
                        String sen = cmnd.nextLine();
                        Search search = new Search(sen, trie.trie);
                        search.searchSentence(3);
                        timeEnd = System.currentTimeMillis();
                        txtPrint.append("Sentence Searching Time: " + (timeEnd - timeStart) + " millis\n");
                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), "Enter correct command!"
                                , "Error!", JOptionPane.ERROR_MESSAGE);
                        txtPrint.append("err: Enter correct command!\n");
                        txtCmnd.setText("");
                    }
                }
            }
            if (jrdHash.isSelected()) {
                if (cmd.equals("add")) {
                    new Add(4, folderAddress, cmnd.next(), fileExist, hash.hash, stopWord.stopHash, true);
                } else if (cmd.equals("del")) {
                    new Delete(4, folderAddress, cmnd.next(), fileExist, hash.hash, stopWord.stopHash, true);
                } else if (cmd.equals("update")) {
                    new Update(4, folderAddress, cmnd.next(), fileExist, hash.hash, stopWord.stopHash);
                } else if (cmd.equals("list")) {
                    GetList getList = new GetList(folderAddress, fileExist, hash.hash);
                    String s = cmnd.next();
                    if (s.equals("-l"))
                        getList.LFileOfProgram();
                    else if (s.equals("-f"))
                        getList.LFileOfFolder();
                    else if (s.equals("-w"))
                        getList.LHashWords();
                    else {
                        JOptionPane.showMessageDialog(new JFrame(), "Enter correct command!"
                                , "Error!", JOptionPane.ERROR_MESSAGE);
                        txtPrint.append("err: Enter correct command!\n");
                        txtCmnd.setText("");
                    }
                } else if (cmd.equals("search")) {
                    String s = cmnd.next();
                    if (s.equals("-w")) {
                        String name = cmnd.next();
                        Search search = new Search(name, hash.hash);
                        search.searchWHash(hash.hash);
                        if (search.doc.isEmpty())
                            UI.txtPrint.append("The word not found in Hash!\n");
                        else {
                            for (String i : search.doc)
                                UI.txtPrint.append(i + ", ");
                            UI.txtPrint.append("\n" + "The word found in " + search.count + " docs.\n");
                        }
                        timeEnd = System.currentTimeMillis();
                        txtPrint.append("Word Searching Time: " + (timeEnd - timeStart) + " millis\n");

                    } else if (s.equals("-s")) {
                        String sen = cmnd.nextLine();
                        Search search = new Search(sen, hash.hash);
                        search.searchSentence(4);
                        timeEnd = System.currentTimeMillis();
                        txtPrint.append("Sentence Searching Time: " + (timeEnd - timeStart) + " millis\n");
                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), "Enter correct command!"
                                , "Error!", JOptionPane.ERROR_MESSAGE);
                        txtPrint.append("err: Enter correct command!\n");
                        txtCmnd.setText("");
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "Enter correct command!"
                    , "Error!", JOptionPane.ERROR_MESSAGE);
            txtPrint.append("err: Enter correct command!\n");
            txtCmnd.setText("");
        }
    }

}