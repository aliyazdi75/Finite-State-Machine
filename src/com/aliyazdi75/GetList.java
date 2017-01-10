package com.aliyazdi75;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Google Created by AliYazdi75 on Dec_2016
 */
public class GetList {

    private ArrayList<File> fileExist = new ArrayList<>();
    private ArrayList tree = new ArrayList();
    private String fAddress;
    private static String word = "|", wordBefore, doc = "";
    public int count = 0;

    public GetList(String fAddress, ArrayList<File> fileExist, ArrayList tree) {
        this.fileExist = fileExist;
        this.tree = tree;
        this.fAddress = fAddress;
    }

    public void LBstWords(ArrayList<BSTNode> bst) {

        for (BSTNode i : bst) {
            UI.txtPrint.append("|" + i.key + " -> ");
            for (typeList s : i.list.fileName)
                UI.txtPrint.append(s.key + ", ");
            UI.txtPrint.append("\n");
            count++;
        }
        UI.txtPrint.append("Number of words = " + count + "\n");
    }

    public void LTstWords(TstNode cuNode, ArrayList<TstNode> tst) {

        if (cuNode.endWord) {
            word += cuNode.key;
            doc += " ";
            doc += "-> ";
            for (typeList s : cuNode.list.fileName)
                doc += s.key + ", ";
            wordBefore = word;
            if (cuNode.MC == null)
                wordBefore = wordBefore.substring(0, wordBefore.length() - 1);
            UI.txtPrint.append(word + doc + "\n");
            count++;
            doc = "";
        }

        if (cuNode.MC != null) {

            if (!cuNode.endWord)
                word += cuNode.key;

            TstNode j = cuNode.MC;
            LTstWords(j, tst);
            wordBefore = wordBefore.substring(0, wordBefore.length() - 1);
            word = wordBefore;
        }
        if ((cuNode.RC != null)) {
            TstNode j = cuNode.RC;
            word = wordBefore;
            LTstWords(j, tst);
        }
        if ((cuNode.LC != null)) {
            TstNode j = cuNode.LC;
            word = wordBefore;
            LTstWords(j, tst);
        }
    }

    public void LTrieWords(TrieNode curNode) {

        if (curNode.endWord) {
            word += curNode.key;
            doc += " -> ";
            for (typeList s : curNode.list.fileName)
                doc += s.key + ", ";
            UI.txtPrint.append(word + doc + "\n");
            wordBefore = word;
            wordBefore = wordBefore.substring(0, wordBefore.length() - 1);
            word = wordBefore;
            count++;
            doc = "";
        }

        for (TrieNode j : curNode.child) {
            if (curNode.key != Character.MIN_VALUE)
                word += curNode.key;

            LTrieWords(j);
            if (curNode.key != Character.MIN_VALUE) {
                wordBefore = wordBefore.substring(0, wordBefore.length() - 1);
                word = wordBefore;
            }
        }
    }

    public void LHashWords() {

        ArrayList<HahTable> hash = tree;

        for (HahTable hahTable : hash)
            if (hahTable != null)
                for (BSTNode i : hahTable.bst) {
                    UI.txtPrint.append("|" + i.key + " -> ");
                    for (typeList s : i.list.fileName)
                        UI.txtPrint.append(s.key + ", ");
                    UI.txtPrint.append("\n");
                    count++;
                }
        UI.txtPrint.append("Number of words = " + count + "\n");

    }

    public void LFileOfProgram() {
        for (File i : fileExist) {
            String fName = i.getName().replace(".txt", "");
            UI.txtPrint.append(fName + ", ");
        }
        UI.txtPrint.append("\n");
        UI.txtPrint.append("Number of listed docs = " + fileExist.size() + "\n");
    }

    public void LFileOfFolder() {
        try {
            ReadFile readFile = new ReadFile(fAddress);
            for (File i : readFile.fileExist) {
                String fName = i.getName().replace(".txt", "");
                UI.txtPrint.append(fName + ", ");
            }
            UI.txtPrint.append("\n");
            UI.txtPrint.append("Number of listed docs = " + readFile.fileExist.size() + "\n");
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "First enter your correct folder address!"
                    , "Error!", JOptionPane.ERROR_MESSAGE);
            UI.txtAdrs.setText("");
            ex.printStackTrace();
        }
    }

}
