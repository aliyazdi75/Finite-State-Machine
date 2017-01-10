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
public class TST {

    private ArrayList<File> fileExist = new ArrayList<>();
    public ArrayList<TstNode> tst = new ArrayList<>();
    private ArrayList<TstNode> stopTst = new ArrayList<>();
    private String fileName;
    private char[] charWord;
    private int wordCount, build, curChar, startChar, endChar;
    static TstNode stopRoot;
    static TstNode tstRoot;

    public TST(ArrayList<File> fileExist, ArrayList<TstNode> tst, ArrayList<TstNode> stopTst, int build) {
        this.fileExist = fileExist;
        this.tst = tst;
        this.stopTst = stopTst;
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
                UI.txtPrint.append("Unable to open file '" + i.getPath() + "' to build TST!\n");
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

                charWord = CurWord.toCharArray();
                endChar = charWord.length - 1;
                curChar = 0;

                fileName = i.getName().replace(".txt", "");
                TstNode newNode = new TstNode();

                if (tst.isEmpty()) {
                    tstRoot = newNode;
                }
                if (build == 0) {
                    addWordTst(newNode, tstRoot);
                } else if (!stopWorld(CurWord))
                    addWordTst(newNode, tstRoot);

            }
        }

        if (build == 1) {
            UI.txtPrint.append("Number of list files: " + fileExist.size() + "\n");
            UI.txtPrint.append("Number of words of TST: " + wordCount + "\n");
            UI.txtPrint.append("Height of TST: " + TSTHeight(tstRoot) + "\n");
            build = 2;
        } else if (build == 0) {
            stopRoot = tstRoot;
        }

    }

    private Boolean stopWorld(String CurWord) {

        Search search = new Search(CurWord, stopTst);
        search.searchWTst(stopRoot, stopTst);

        return !search.doc.isEmpty();

    }

    private void addWordTst(TstNode newNode, TstNode curNode) {


        if (tst.isEmpty()) {
            newNode = curNode;
            newNode.key = charWord[curChar];
            addMC(curNode);
        } else {
            if (curNode.key < charWord[curChar]) {
                if (curNode.RC != null) {
                    addWordTst(newNode, curNode.RC);
                    if (Math.abs(TSTHeight(curNode.LC) - TSTHeight(curNode.RC)) > 1) {
                        if (curNode.RC.key < charWord[startChar])
                            RR(curNode);
                        else
                            RL(curNode);
                    }
                } else {
                    curNode.RC = newNode;
                    newNode.father = curNode;
                    newNode.key = charWord[curChar];
                    startChar = curChar;
                    addMC(newNode);
                }

            } else if (curNode.key > charWord[curChar]) {
                if (curNode.LC != null) {
                    addWordTst(newNode, curNode.LC);
                    if (Math.abs(TSTHeight(curNode.LC) - TSTHeight(curNode.RC)) > 1) {
                        if (curNode.LC.key > charWord[startChar])
                            LL(curNode);
                        else
                            LR(curNode);
                    }
                } else {
                    curNode.LC = newNode;
                    newNode.father = curNode;
                    newNode.key = charWord[curChar];
                    startChar = curChar;
                    addMC(newNode);
                }
            } else if (curNode.key == charWord[curChar]) {
                if (curChar == endChar) {
                    curNode.endWord = true;
                    if (curNode.list == null) {
                        curNode.list = new List(fileName);
                        curChar++;
                        wordCount++;
                    } else
                        curNode.list.addFName(fileName);
                } else if (curNode.MC != null) {
                    curChar++;
                    addWordTst(newNode, curNode.MC);
                } else {
                    curChar++;
                    curNode.MC = newNode;
                    newNode.father = curNode;
                    newNode.key = charWord[curChar];
                    addMC(newNode);
                }
            }
        }
    }

    private void addMC(TstNode curNode) {
        if (curChar == endChar) {
            curNode.endWord = true;
            curNode.list = new List(fileName);
            curChar++;
            wordCount++;
            tst.add(curNode);
        } else {
            tst.add(curNode);
            curChar++;
            while (curChar <= endChar) {
                TstNode newNode = new TstNode();
                curNode.MC = newNode;
                newNode.father = curNode;
                newNode.key = charWord[curChar];
                curNode = newNode;
                if (curChar == endChar) {
                    newNode.endWord = true;
                    newNode.list = new List(fileName);
                    wordCount++;
                }
                tst.add(newNode);
                curChar++;
            }
        }
    }

    public static int TSTHeight(TstNode curNode) {
        if (curNode == null) return 0;
        else return 1 + Math.max(TSTHeight(curNode.LC), TSTHeight(curNode.RC));
    }

    public static void LL(TstNode curNode) {
        TstNode a = curNode.LC.RC;
        if (a != null)
            a.father = curNode;
        if (tstRoot == curNode)
            tstRoot = curNode.LC;
        if (curNode.father != null) {
            if (curNode.father.MC == curNode)
                curNode.father.MC = curNode.LC;
            else {
                if (curNode.father.key > curNode.key)
                    curNode.father.LC = curNode.LC;
                else
                    curNode.father.RC = curNode.LC;
            }
        }
        curNode.LC.RC = curNode;
        curNode.LC.father = curNode.father;
        curNode.father = curNode.LC;
        curNode.LC = a;
    }

    public static void RR(TstNode curNode) {
        TstNode a = curNode.RC.LC;
        if (a != null)
            a.father = curNode;
        if (tstRoot == curNode)
            tstRoot = curNode.RC;
        if (curNode.father != null) {
            if (curNode.father.MC == curNode)
                curNode.father.MC = curNode.RC;
            else {
                if (curNode.father.key > curNode.key)
                    curNode.father.LC = curNode.RC;
                else
                    curNode.father.RC = curNode.RC;
            }
        }
        curNode.RC.LC = curNode;
        curNode.RC.father = curNode.father;
        curNode.father = curNode.RC;
        curNode.RC = a;
    }

    public static void LR(TstNode curNode) {
        TstNode a = curNode.LC.RC;
        TstNode RCa = a.RC;
        TstNode LCa = a.LC;
        if (tstRoot == curNode)
            tstRoot = a;
        if (curNode.father != null) {
            if (curNode.father.MC == curNode)
                curNode.father.MC = a;
            else {
                if (curNode.father.key > curNode.key)
                    curNode.father.LC = a;
                else
                    curNode.father.RC = a;
            }
        }
        a.father = curNode.father;
        a.RC = curNode;
        a.LC = curNode.LC;
        a.LC.father = a;
        a.RC.father = a;
        a.LC.RC = LCa;
        a.RC.LC = RCa;
        if (LCa != null)
            LCa.father = a.LC;
        if (RCa != null)
            RCa.father = a.RC;
    }

    public static void RL(TstNode curNode) {
        TstNode a = curNode.RC.LC;
        TstNode RCa = a.RC;
        TstNode LCa = a.LC;
        if (tstRoot == curNode)
            tstRoot = a;
        if (curNode.father != null) {
            if (curNode.father.MC == curNode)
                curNode.father.MC = a;
            else {
                if (curNode.father.key > curNode.key)
                    curNode.father.LC = a;
                else
                    curNode.father.RC = a;
            }
        }
        a.father = curNode.father;
        a.LC = curNode;
        a.RC = curNode.RC;
        a.RC.father = a;
        a.LC.father = a;
        a.RC.LC = RCa;
        a.LC.RC = LCa;
        if (RCa != null)
            RCa.father = a.RC;
        if (LCa != null)
            LCa.father = a.LC;
    }

}

