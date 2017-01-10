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
public class BST {

    private ArrayList<File> fileExist = new ArrayList<>();
    public ArrayList<BSTNode> bst = new ArrayList<>();
    private ArrayList<BSTNode> stopBst = new ArrayList<>();
    private String CurWord;
    private String fileName;
    private int build;
    public int wordCount;
    static BSTNode stopRoot;
    static BSTNode bstRoot;

    public BST(ArrayList<File> fileExist, ArrayList<BSTNode> bst, ArrayList<BSTNode> stopBst, int build) {
        this.fileExist = fileExist;
        this.bst = bst;
        this.stopBst = stopBst;
        this.build = build;
        readFile();
    }

    private void readFile() {

        if (fileExist != null) {
            for (File i : fileExist) {

                File f;
                Scanner scn = null;
                try {
                    f = new File(i.getPath());
                    scn = new Scanner(f);
                } catch (FileNotFoundException ex) {
                    UI.txtPrint.append("Unable to open file '" + i.getPath() + "' to build BST!\n");
                    System.exit(0);
                }

                while (scn.hasNext()) {

                    CurWord = scn.next();
                    fileName = i.getName().replace(".txt", "");

                    Pattern pattern = Pattern.compile("[^a-z A-Z]");
                    Matcher matcher = pattern.matcher(CurWord);
                    CurWord = matcher.replaceAll("");
                    CurWord = CurWord.toLowerCase();

                    if (CurWord.equals(""))
                        continue;

                    readText(CurWord, fileName);
                }
            }

            if (build == 1) {
                UI.txtPrint.append("Number of list files: " + fileExist.size() + "\n");
                UI.txtPrint.append("Number of words of BST: " + wordCount + "\n");
                UI.txtPrint.append("Height of BST: " + BSTHeight(bstRoot) + "\n");
                build = 2;
            } else if (build == 0) {
                stopRoot = bstRoot;
            }
        }
    }

    public void readText(String CurWord, String fileName) {

        this.CurWord = CurWord;
        this.fileName = fileName;

        BSTNode newNode = new BSTNode();
        newNode.key = CurWord;

        if (bst.size() == 0) {
            bstRoot = newNode;
        }

        if (build == 0)
            addNode(newNode, bstRoot);
        else if (!stopWorld(CurWord))
            addNode(newNode, bstRoot);

    }

    private Boolean stopWorld(String CurWord) {

        Search search = new Search(CurWord, stopBst);
        search.searchWBst(stopRoot, stopBst);

        return !search.doc.isEmpty();

    }

    public void addNode(BSTNode newNode, BSTNode curNode) {

        if (bst.size() == 0) {
            newNode = curNode;
            bst.add(newNode);
        }
        if (curNode.key.compareTo(CurWord) > 0) {
            if (curNode.LC != null)
                addNode(newNode, curNode.LC);
            else {
                curNode.LC = newNode;
                newNode.list = new List(fileName);
                wordCount++;
                newNode.father = curNode;
                bst.add(newNode);
            }

            if (Math.abs(BSTHeight(curNode.LC) - BSTHeight(curNode.RC)) > 1) {
                if (curNode.LC.key.compareTo(CurWord) > 0)
                    LL(curNode);
                else
                    LR(curNode);
            }

        } else if (curNode.key.compareTo(CurWord) < 0) {
            if (curNode.RC != null)
                addNode(newNode, curNode.RC);
            else {
                curNode.RC = newNode;
                newNode.list = new List(fileName);
                wordCount++;
                newNode.father = curNode;
                bst.add(newNode);
            }

            if (Math.abs(BSTHeight(curNode.LC) - BSTHeight(curNode.RC)) > 1) {
                if (curNode.RC.key.compareTo(CurWord) < 0)
                    RR(curNode);
                else
                    RL(curNode);
            }

        } else if (curNode.key.compareTo(CurWord) == 0) {
            if (curNode.list == null) {
                curNode.list = new List(fileName);
                wordCount++;
            } else
                curNode.list.addFName(fileName);
            return;
        }

    }

    public static int BSTHeight(BSTNode curNode) {
        if (curNode == null) return 0;
        else return 1 + Math.max(BSTHeight(curNode.LC), BSTHeight(curNode.RC));
    }

    public static void LL(BSTNode curNode) {
        BSTNode a = curNode.LC.RC;
        if (a != null)
            a.father = curNode;
        if (bstRoot == curNode)
            bstRoot = curNode.LC;
        if (curNode.father != null) {
            if (curNode.father.key.compareTo(curNode.key) > 0)
                curNode.father.LC = curNode.LC;
            else
                curNode.father.RC = curNode.LC;
        }
        curNode.LC.RC = curNode;
        curNode.LC.father = curNode.father;
        curNode.father = curNode.LC;
        curNode.LC = a;
    }

    public static void RR(BSTNode curNode) {
        BSTNode a = curNode.RC.LC;
        if (a != null)
            a.father = curNode;
        if (bstRoot == curNode)
            bstRoot = curNode.RC;
        if (curNode.father != null) {
            if (curNode.father.key.compareTo(curNode.key) > 0)
                curNode.father.LC = curNode.RC;
            else
                curNode.father.RC = curNode.RC;
        }
        curNode.RC.LC = curNode;
        curNode.RC.father = curNode.father;
        curNode.father = curNode.RC;
        curNode.RC = a;
    }

    public static void LR(BSTNode curNode) {
        BSTNode a = curNode.LC.RC;
        BSTNode RCa = a.RC;
        BSTNode LCa = a.LC;
        if (bstRoot == curNode)
            bstRoot = a;
        if (curNode.father != null) {
            if (curNode.father.key.compareTo(curNode.key) > 0)
                curNode.father.LC = a;
            else
                curNode.father.RC = a;
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

    public static void RL(BSTNode curNode) {
        BSTNode a = curNode.RC.LC;
        BSTNode RCa = a.RC;
        BSTNode LCa = a.LC;
        if (bstRoot == curNode)
            bstRoot = a;
        if (curNode.father != null) {
            if (curNode.father.key.compareTo(curNode.key) > 0)
                curNode.father.LC = a;
            else
                curNode.father.RC = a;
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

