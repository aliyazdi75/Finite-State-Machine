package com.aliyazdi75;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Google Created by AliYazdi75 on Dec_2016
 */
public class Search {

    private ArrayList tree = new ArrayList();
    private String name;
    private char[] charName;
    private int curChar = 0;
    public int count = 0;
    public ArrayList<String> doc = new ArrayList();

    public Search(String name, ArrayList tree) {
        this.tree = tree;
        Pattern pattern = Pattern.compile("[^a-z A-Z]");
        Matcher matcher = pattern.matcher(name);
        name = matcher.replaceAll("");
        this.name = name.toLowerCase();
        this.charName = name.toCharArray();
    }

    public void searchWBst(BSTNode cuNode, ArrayList<BSTNode> bst) {
        if (!bst.isEmpty())
            if (cuNode.key.compareTo(name) > 0) {
                if (cuNode.LC != null)
                    searchWBst(cuNode.LC, bst);
            } else if (cuNode.key.compareTo(name) < 0) {
                if (cuNode.RC != null)
                    searchWBst(cuNode.RC, bst);
            } else if (cuNode.key.compareTo(name) == 0) {
                for (typeList j : cuNode.list.fileName) {
                    doc.add(j.key);
                    count++;
                }
            }
    }

    public void searchWTst(TstNode cuNode, ArrayList<TstNode> tst) {

        if (!tst.isEmpty()) {
            if (curChar < charName.length) {
                if (cuNode.key == charName[curChar]) {
                    curChar++;
                    if (cuNode.endWord && curChar == charName.length) {
                        for (typeList j : cuNode.list.fileName) {
                            doc.add(j.key);
                            count++;
                        }
                    } else if ((cuNode.MC != null))
                        searchWTst(cuNode.MC, tst);
                } else if (cuNode.key > charName[curChar]) {
                    if ((cuNode.LC != null)) {
                        searchWTst(cuNode.LC, tst);
                    }
                } else if (cuNode.key < charName[curChar]) {
                    if ((cuNode.RC != null)) {
                        searchWTst(cuNode.RC, tst);
                    }
                }
            }
        }

    }

    public void searchWTrie(TrieNode nNode) {

        ArrayList<TrieNode> trie = tree;
        ArrayList<Character> charW;

        if (trie.size() > 1) {
            int i;
            for (i = 0; i < charName.length; i++) {
                if (!nNode.child.isEmpty()) {
                    charW = new ArrayList<>();
                    for (TrieNode k : nNode.child) {
                        charW.add(k.key);
                    }
                    if (charW.contains(charName[i]))
                        nNode = nNode.child.get(charW.indexOf(charName[i]));
                    else
                        break;
                } else
                    break;
            }

            if (nNode.endWord && i == charName.length)
                for (typeList k : nNode.list.fileName) {
                    doc.add(k.key);
                    count++;
                }
        }

    }

    public void searchWHash(ArrayList<HahTable> hash) {

        if (!hash.isEmpty()) {
            int adrs = Hash.hashFunc(name);
            if (hash.size() > adrs)
                if (hash.get(adrs) != null)
                    searchWBst(hash.get(adrs).bstRoot, hash.get(adrs).bst);
        }

    }

    public void searchSentence(int type) {

        ArrayList<String> words = new ArrayList<>();
        ArrayList<String> commonDocs = new ArrayList<>();
        ArrayList<String> commonWords = new ArrayList<>();
        Scanner scn = new Scanner(name);

        while (scn.hasNext())
            words.add(scn.next());

        boolean found = false;
        for (String i : words) {
            curChar = 0;
            doc = new ArrayList<>();
            Pattern pattern = Pattern.compile("[^a-z A-Z]");
            Matcher matcher = pattern.matcher(i);
            i = matcher.replaceAll("");
            this.name = i.toLowerCase();
            if (type == 1)
                searchWBst(BST.bstRoot, tree);
            else if (type == 2) {
                this.charName = name.toCharArray();
                searchWTst(TST.tstRoot, tree);
            } else if (type == 3) {
                this.charName = name.toCharArray();
                searchWTrie(Trie.emptyNode);
            } else if (type == 4) {
                searchWHash(tree);
            }
            if (!doc.isEmpty()) {
                if (!found)
                    commonDocs.addAll(doc);
                found = true;
                commonDocs.retainAll(doc);
                commonWords.add(i);
            }
        }

        if (found) {
            if (!commonDocs.isEmpty()) {
                UI.txtPrint.append("Appears in:\n");
                for (String i : commonDocs) {
                    UI.txtPrint.append(i + "\n");
                    UI.txtPrint.append("|-> ");
                    for (String j : commonWords) {
                        UI.txtPrint.append(j + ", ");
                        count++;
                    }
                    UI.txtPrint.append("\n");
                }
                UI.txtPrint.append("The sentence found in " + commonDocs.size() + " docs.\n");
            } else
                UI.txtPrint.append("The sentence not found in Tree!\n");
        } else
            UI.txtPrint.append("The sentence not found in Tree!\n");

    }

}
