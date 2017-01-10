package com.aliyazdi75;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Google Created by AliYazdi75 on Dec_2016
 */
public class Trie {

    private ArrayList<File> fileExist = new ArrayList<>();
    public ArrayList<TrieNode> stopTrie = new ArrayList<>();
    public ArrayList<TrieNode> trie = new ArrayList<>();
    private int wordCount;
    private int build;
    static TrieNode emptyNode;
    static TrieNode stopEmptyNode;

    public Trie(ArrayList<File> fileExist, ArrayList<TrieNode> trie, ArrayList<TrieNode> stopTrie, int build) {
        this.fileExist = fileExist;
        this.trie = trie;
        this.stopTrie = stopTrie;
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
                UI.txtPrint.append("Unable to open file '" + i.getPath() + "' to build Trie!\n");
                System.exit(0);
            }

            while (scn.hasNext()) {

                String CurWord = scn.next();

                Pattern pattern = Pattern.compile("[^a-z A-Z]");
                Matcher matcher = pattern.matcher(CurWord);
                CurWord = matcher.replaceAll("");
                CurWord = CurWord.toLowerCase();

                if (CurWord.equals(""))
                    continue;

                if (build == 0)
                    addWordTrie(CurWord, i.getName());
                else if (!stopWorld(CurWord))
                    addWordTrie(CurWord, i.getName());

            }
        }

        if (build == 1) {
            UI.txtPrint.append("Number of list files: " + fileExist.size() + "\n");
            UI.txtPrint.append("Number of words of Trie: " + wordCount + "\n");
            UI.txtPrint.append("Height of Trie: " + TrieHeight(emptyNode) + "\n");
            build = 2;
        } else if (build == 0) {
            stopEmptyNode = emptyNode;
        }

    }

    private Boolean stopWorld(String CurWord) {

        Search search = new Search(CurWord, stopTrie);
        search.searchWTrie(stopEmptyNode);

        return !search.doc.isEmpty();

    }

    private void addWordTrie(String CurWord, String fileName) {


        fileName = fileName.replace(".txt", "");

        if (trie.isEmpty()) {
            emptyNode = new TrieNode();
            emptyNode.father = null;
            emptyNode.key = Character.MIN_VALUE;
            trie.add(emptyNode);
        }

        char[] charWord = CurWord.toCharArray();

        TrieNode CurNode = emptyNode;
        a:
        for (int j = 0; j < charWord.length; j++) {
            TrieNode newNode = new TrieNode();
            b:
            while (true) {

                assert CurNode != null;
                if (CurNode.child.isEmpty()) {
                    if (CurNode.key == charWord[j]) {
                        if (j > 0) {
                            if (charWord[j] != charWord[j - 1]) {
                                if (j == charWord.length - 1) {
                                    CurNode.endWord = true;
                                    if (CurNode.list == null) {
                                        wordCount++;
                                        CurNode.list = new List(fileName);
                                    } else
                                        CurNode.list.addFName(fileName);
                                    break a;
                                }
                                break;
                            }
                        } else {
                            if (j == charWord.length - 1) {
                                CurNode.endWord = true;
                                if (CurNode.list == null) {
                                    CurNode.list = new List(fileName);
                                    wordCount++;
                                } else {
                                    CurNode.list.addFName(fileName);
                                }
                                break a;
                            }
                            break;
                        }
                    }
                    newNode.key = charWord[j];
                    CurNode.child.add(newNode);
                    if (j == charWord.length - 1) {
                        newNode.endWord = true;
                        newNode.list = new List(fileName);
                        wordCount++;
                    }
                    newNode.father = CurNode;
                    trie.add(newNode);
                    CurNode = newNode;
                    break;
                } else {
                    for (int k = 0; k < CurNode.child.size(); k++) {
                        if (CurNode.child.get(k).key > charWord[j]) {
                            CurNode.child.add(k, newNode);
                            newNode.key = charWord[j];
                            if (j == charWord.length - 1) {
                                newNode.endWord = true;
                                wordCount++;
                                newNode.list = new List(fileName);
                                newNode.father = CurNode;
                                trie.add(newNode);
                                break a;
                            }
                            newNode.father = CurNode;
                            trie.add(newNode);
                            CurNode = newNode;
                            break b;
                        } else if (CurNode.child.get(k).key == charWord[j]) {
                            if (j == charWord.length - 1) {
                                CurNode.child.get(k).endWord = true;
                                if (CurNode.child.get(k).list == null) {
                                    wordCount++;
                                    CurNode.child.get(k).list = new List(fileName);
                                } else {
                                    CurNode.child.get(k).list.addFName(fileName);
                                }
                                break a;
                            }
                            CurNode = CurNode.child.get(k);
                            break b;
                        } else if (k == CurNode.child.size() - 1) {
                            CurNode.child.add(k + 1, newNode);
                            newNode.key = charWord[j];
                            if (j == charWord.length - 1) {
                                wordCount++;
                                newNode.endWord = true;
                                newNode.list = new List(fileName);
                                newNode.father = CurNode;
                                trie.add(newNode);
                                break a;
                            }
                            newNode.father = CurNode;
                            trie.add(newNode);
                            CurNode = newNode;
                            break b;
                        }
                    }
                }
            }

        }
    }

    private int TrieHeight(TrieNode CurNode) {
        if (CurNode.child.isEmpty()) return 1;
        else {
            if (CurNode.child.size() > 1)
                for (int j = 0; j < CurNode.child.size() - 1; j++)
                    return 1 + Math.max(TrieHeight(CurNode.child.get(j)),
                            TrieHeight(CurNode.child.get(j + 1)));
            else
                return 1 + TrieHeight(CurNode.child.get(0));
        }
        return 0;
    }

}