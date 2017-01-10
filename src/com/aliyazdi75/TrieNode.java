package com.aliyazdi75;

import java.util.ArrayList;

public class TrieNode {

    char key = 0;
    TrieNode father = null;
    ArrayList<TrieNode> child = new ArrayList<>();
    boolean endWord = false;
    List list;

}
