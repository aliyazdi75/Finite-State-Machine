package com.aliyazdi75;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by AliYazdi75 on Jan_2017
 */
public class Hash {

    private ArrayList<File> fileExist = new ArrayList<>();
    public ArrayList<HahTable> hash = new ArrayList<>();
    private ArrayList<HahTable> stopHash = new ArrayList<>();
    private String CurWord;
    private String fileName;
    private int wordCount, build;

    public Hash(ArrayList<File> fileExist, ArrayList<HahTable> hash, ArrayList<HahTable> stopHash, int build) {
        this.fileExist = fileExist;
        this.hash = hash;
        this.stopHash = stopHash;
        this.build = build;
        readFile();
    }

    private void readFile() {
        for (File i : fileExist) {

            File f;
            Scanner scn = null;
            try {
                f = new File(i.getPath());
                scn = new Scanner(f);
            } catch (FileNotFoundException ex) {
                UI.txtPrint.append("Unable to open file '" + i.getPath() + "' to build Hash!\n");
                System.exit(0);
            }

            while (scn.hasNext()) {

                CurWord = scn.next();

                Pattern pattern = Pattern.compile("[^a-z A-Z]");
                Matcher matcher = pattern.matcher(CurWord);
                CurWord = matcher.replaceAll("");
                CurWord = CurWord.toLowerCase();

                if (CurWord.equals(""))
                    continue;

                fileName = i.getName().replace(".txt", "");
                BSTNode newNode = new BSTNode();
                newNode.key = CurWord;

                if (build == 0)
                    addNode();
                else if (!stopWorld(CurWord))
                    addNode();

            }
        }

        if (build == 1) {
            UI.txtPrint.append("Number of list files: " + fileExist.size() + "\n");
            UI.txtPrint.append("Number of words of Hash: " + wordCount + "\n");
            build = 2;
        }

    }

    private Boolean stopWorld(String CurWord) {

        Search search = new Search(CurWord, stopHash);
        search.searchWHash(stopHash);

        return !search.doc.isEmpty();
    }

    private void addNode() {

        int adrs = hashFunc(CurWord);
        int size = hash.size();

        for (int i = size; i <= adrs; i++)
            hash.add(null);
        if (hash.get(adrs) == null) {
            HahTable newNode = new HahTable();
            BST bst = new BST(null, new ArrayList<>(), null, 0);
            bst.readText(CurWord, fileName);
            wordCount+=bst.wordCount;
            newNode.bstRoot = BST.bstRoot;
            newNode.bst=bst.bst;
            hash.set(adrs, newNode);
        } else {
            BST bst = new BST(null, hash.get(adrs).bst, null, 0);
            BST.bstRoot = hash.get(adrs).bstRoot;
            bst.readText(CurWord, fileName);
            wordCount+=bst.wordCount;
            hash.get(adrs).bstRoot = BST.bstRoot;
        }

    }

    public static int hashFunc(String curWord) {

        int hash = 7;
        for (Character i : curWord.toCharArray()) {
            hash = (hash * 31 + i) % i;
        }

        return hash;
    }

}
