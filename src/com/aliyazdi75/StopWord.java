package com.aliyazdi75;

import java.io.File;
import java.util.ArrayList;

/**
 * Google Created by AliYazdi75 on Dec_2016
 */
public class StopWord {

    public ArrayList<BSTNode> stopBst = new ArrayList<>();
    public ArrayList<TstNode> stopTst = new ArrayList<>();
    public ArrayList<TrieNode> stopTrie = new ArrayList<>();
    public ArrayList<HahTable> stopHash = new ArrayList<>();
    private ArrayList<File> fileExist = new ArrayList<>();

    public StopWord() {
        this.fileExist.add(new File("src\\com\\aliyazdi75\\StopWords.txt"));
    }

    public void BSTStopWord() {
        BST bst = new BST(fileExist, new ArrayList<>(), new ArrayList<>(), 0);
        stopBst = bst.bst;
    }

    public void TSTStopWord() {
        TST tst = new TST(fileExist, new ArrayList<>(), new ArrayList<>(), 0);
        stopTst = tst.tst;
    }

    public void TrieStopWord() {
        Trie trie = new Trie(fileExist, new ArrayList<>(), new ArrayList<>(), 0);
        stopTrie = trie.trie;
    }

    public void HashStopWord() {
        Hash hash = new Hash(fileExist, new ArrayList<>(), new ArrayList<>(), 0);
        stopHash = hash.hash;
    }

}
