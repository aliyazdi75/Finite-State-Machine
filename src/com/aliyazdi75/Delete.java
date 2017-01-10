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
public class Delete {

    private ArrayList tree = new ArrayList();
    private String fDel;
    private String name;
    private String fileName;
    private String CurWord;
    private char[] charName;
    private int curChar = 0;
    private int type;

    public Delete(int type, String fAddress, String name, ArrayList<File> fileExist,
                  ArrayList tree, ArrayList stopTree, boolean textShow) {
        this.fDel = fAddress + "\\" + name + ".txt";
        this.name = name;
        this.type = type;
        this.tree = tree;
        read(fileExist, stopTree, textShow);
    }

    private void read(ArrayList<File> fileExist, ArrayList stopTree, boolean textShow) {

        File f;
        f = new File(fDel);
        Scanner scn = null;

        if (fileExist.contains(f)) {
            try {
                scn = new Scanner(f);
            } catch (FileNotFoundException e) {
            }

            fileName = f.getName().replace(".txt", "");
            assert scn != null;
            while (scn.hasNext()) {

                CurWord = scn.next();

                Pattern pattern = Pattern.compile("[^a-z A-Z]");
                Matcher matcher = pattern.matcher(CurWord);
                CurWord = matcher.replaceAll("");
                CurWord = CurWord.toLowerCase();

                if (CurWord.equals(""))
                    continue;

                charName = CurWord.toCharArray();
                curChar = 0;
                if (type == 1) {
                    ArrayList<BSTNode> bst = tree;
                    if (!stopWorld(CurWord, stopTree))
                        delBST(BST.bstRoot, bst);
                } else if (type == 2) {
                    ArrayList<TstNode> tst = tree;
                    if (!stopWorld(CurWord, stopTree))
                        delTST(TST.tstRoot, tst);
                } else if (type == 3) {
                    ArrayList<TrieNode> trie = tree;
                    if (!stopWorld(CurWord, stopTree))
                        delTrie(trie);
                } else if (type == 4) {
                    ArrayList<HahTable> hahTable = tree;
                    if (!stopWorld(CurWord, stopTree))
                        delHash(hahTable);
                }
            }
            fileExist.remove(f);
            if (textShow)
                UI.txtPrint.append(name + " successfully removed from lists.\n");

        } else
            UI.txtPrint.append("err: document not found.\n");

    }

    private Boolean stopWorld(String CurWord, ArrayList stopTree) {

        Search search = new Search(CurWord, stopTree);
        if (type == 1) {
            search.searchWBst(BST.stopRoot, stopTree);
        } else if (type == 2) {
            search.searchWTst(TST.stopRoot, stopTree);
        } else if (type == 3) {
            search.searchWTrie(Trie.stopEmptyNode);
        } else if (type == 4) {
            search.searchWHash(stopTree);
        }

        return !search.doc.isEmpty();

    }

    private void delBST(BSTNode curNode, ArrayList<BSTNode> bst) {

        if (!bst.isEmpty())
            if (curNode.key.compareTo(CurWord) > 0) {
                if (curNode.LC != null)
                    delBST(curNode.LC, bst);
            } else if (curNode.key.compareTo(CurWord) < 0) {
                if (curNode.RC != null)
                    delBST(curNode.RC, bst);
            } else if (curNode.key.compareTo(CurWord) == 0) {
                if (curNode.list != null) {
                    for (typeList curLNode : curNode.list.fileName)
                        if (curLNode.key.equals(fileName)) {
                            if (curLNode.previous != null)
                                curLNode.previous.next = curLNode.next;
                            if (curLNode.next != null)
                                curLNode.next.previous = curLNode.previous;
                            curNode.list.fileName.remove(curLNode);
                            break;
                        }
                    if (curNode.list.fileName.isEmpty()) {
                        curNode.list = null;
                    }
                }
                if (curNode.list == null) {
                    if (curNode.RC != null) {
                        if (curNode.LC != null) {
                            if (curNode.father != null) {
                                if (curNode.father.key.compareTo(curNode.key) > 0) {
                                    BSTNode minChildNode = curNode.LC;
                                    while (minChildNode.RC != null)
                                        minChildNode = minChildNode.RC;
                                    curNode.father.LC = minChildNode;
                                    if (minChildNode != curNode.LC) {
                                        minChildNode.father.RC = minChildNode.LC;
                                        curNode.LC.father = minChildNode;
                                        minChildNode.LC = curNode.LC;
                                    }
                                    curNode.RC.father = minChildNode;
                                    minChildNode.RC = curNode.RC;
                                    BSTNode stackChckBlce = minChildNode.father;
                                    minChildNode.father = curNode.father;
                                    while (stackChckBlce != curNode.father) {
                                        if (Math.abs(BST.BSTHeight(stackChckBlce.LC) -
                                                BST.BSTHeight(stackChckBlce.RC)) > 1) {
                                            if (BST.BSTHeight(stackChckBlce.LC.LC) -
                                                    BST.BSTHeight(stackChckBlce.LC.RC) > 0) {
                                                BST.LL(stackChckBlce);
                                            } else {
                                                BST.LR(stackChckBlce);
                                            }
                                        }
                                        stackChckBlce = stackChckBlce.father;
                                    }
                                } else {
                                    BSTNode minChildNode = curNode.RC;
                                    while (minChildNode.LC != null)
                                        minChildNode = minChildNode.LC;
                                    curNode.father.RC = minChildNode;
                                    if (minChildNode != curNode.RC) {
                                        minChildNode.father.LC = minChildNode.RC;
                                        curNode.RC.father = minChildNode;
                                        minChildNode.RC = curNode.RC;
                                    }
                                    curNode.LC.father = minChildNode;
                                    minChildNode.LC = curNode.LC;
                                    BSTNode stackChckBlce = minChildNode.father;
                                    minChildNode.father = curNode.father;
                                    while (stackChckBlce != curNode.father) {
                                        if (Math.abs(BST.BSTHeight(stackChckBlce.LC) -
                                                BST.BSTHeight(stackChckBlce.RC)) > 1) {
                                            if (BST.BSTHeight(stackChckBlce.RC.LC) -
                                                    BST.BSTHeight(stackChckBlce.RC.RC) > 0)
                                                BST.RL(stackChckBlce);
                                            else
                                                BST.RR(stackChckBlce);
                                        }
                                        stackChckBlce = stackChckBlce.father;
                                    }
                                }
                            } else {
                                BSTNode minChildNode = curNode.LC;
                                while (minChildNode.RC != null)
                                    minChildNode = minChildNode.RC;
                                if (minChildNode != curNode.LC) {
                                    minChildNode.father.RC = minChildNode.LC;
                                    curNode.LC.father = minChildNode;
                                    minChildNode.LC = curNode.LC;
                                }
                                curNode.RC.father = minChildNode;
                                minChildNode.RC = curNode.RC;
                                BSTNode stackChckBlce = minChildNode.father;
                                minChildNode.father = null;
                                BST.bstRoot = minChildNode;
                                while (stackChckBlce != curNode.father) {
                                    if (Math.abs(BST.BSTHeight(stackChckBlce.LC) -
                                            BST.BSTHeight(stackChckBlce.RC)) > 1) {
                                        if (BST.BSTHeight(stackChckBlce.LC.LC) -
                                                BST.BSTHeight(stackChckBlce.LC.RC) > 0)
                                            BST.LL(stackChckBlce);
                                        else
                                            BST.LR(stackChckBlce);
                                    }
                                    stackChckBlce = stackChckBlce.father;
                                }
                            }
                        } else {
                            if (curNode.father != null) {
                                if (curNode.father.key.compareTo(curNode.key) > 0) {
                                    curNode.father.LC = curNode.RC;
                                    curNode.RC.father = curNode.father;
                                } else {
                                    curNode.father.RC = curNode.RC;
                                    curNode.RC.father = curNode.father;
                                }
                            } else {
                                curNode.RC.father = null;
                                BST.bstRoot = curNode.RC;
                            }
                        }
                    } else if (curNode.LC != null) {
                        if (curNode.father != null) {
                            if (curNode.father.key.compareTo(curNode.key) > 0) {
                                curNode.father.LC = curNode.LC;
                                curNode.LC.father = curNode.father;
                            } else {
                                curNode.father.RC = curNode.LC;
                                curNode.LC.father = curNode.father;
                            }
                        } else {
                            curNode.LC.father = null;
                            BST.bstRoot = curNode.LC;
                        }
                    } else {
                        if (curNode.father != null) {
                            if (curNode.father.key.compareTo(curNode.key) > 0)
                                curNode.father.LC = null;
                            else
                                curNode.father.RC = null;
                        }
                    }
                    bst.remove(curNode);
                }
            }
        if (Math.abs(BST.BSTHeight(curNode.LC) -
                BST.BSTHeight(curNode.RC)) > 1) {
            if (curNode.key.compareTo(CurWord) > 0) {
                BST.RR(curNode);
            } else {
                BST.LL(curNode);
            }
        }

    }

    private void delTST(TstNode curNode, ArrayList<TstNode> tst) {

        if (!tst.isEmpty()) {
            if (curChar < charName.length) {
                if (curNode.key == charName[curChar]) {
                    curChar++;
                    if (curNode.endWord && curChar == charName.length) {
                        changeTSTNode(curNode, tst);
                    } else if ((curNode.MC != null)) {
                        delTST(curNode.MC, tst);
                        changeTSTNode(curNode, tst);
                    }
                } else if (curNode.key > charName[curChar]) {
                    if ((curNode.LC != null)) {
                        delTST(curNode.LC, tst);
                    }
                } else if (curNode.key < charName[curChar]) {
                    if (curNode.RC != null) {
                        delTST(curNode.RC, tst);
                    }
                }
            }
        }
    }

    private void changeTSTNode(TstNode curNode, ArrayList<TstNode> tst) {
        if (curNode.list != null) {
            for (typeList curLNode : curNode.list.fileName)
                if (curLNode.key.equals(fileName)) {
                    if (curLNode.previous != null)
                        curLNode.previous.next = curLNode.next;
                    if (curLNode.next != null) {
                        curLNode.next.previous = curLNode.previous;
                    }
                    curNode.list.fileName.remove(curLNode);
                    break;
                }
            if (curNode.list.fileName.isEmpty()) {
                curNode.list = null;
            }
        }
        if (curNode.list == null) {
            if (curNode.MC == null) {
                if (curNode.RC != null) {
                    if (curNode.LC != null) {
                        if (curNode.father != null) {
                            if (curNode.father.LC == curNode) {
                                TstNode minChildNode = curNode.LC;
                                while (minChildNode.RC != null)
                                    minChildNode = minChildNode.RC;
                                curNode.father.LC = minChildNode;
                                if (minChildNode != curNode.LC) {
                                    minChildNode.father.RC = minChildNode.LC;
                                    curNode.LC.father = minChildNode;
                                    minChildNode.LC = curNode.LC;
                                }
                                curNode.RC.father = minChildNode;
                                minChildNode.RC = curNode.RC;
                                TstNode stackChckBlce = minChildNode.father;
                                minChildNode.father = curNode.father;
                                while (stackChckBlce != curNode.father) {
                                    if (Math.abs(TST.TSTHeight(stackChckBlce.LC) -
                                            TST.TSTHeight(stackChckBlce.RC)) > 1) {
                                        if (TST.TSTHeight(stackChckBlce.LC.LC) -
                                                TST.TSTHeight(stackChckBlce.LC.RC) > 0) {
                                            TST.LL(stackChckBlce);
                                        } else {
                                            TST.LR(stackChckBlce);
                                        }
                                    }
                                    stackChckBlce = stackChckBlce.father;
                                }
                            } else if (curNode.father.RC == curNode) {
                                TstNode minChildNode = curNode.RC;
                                while (minChildNode.LC != null)
                                    minChildNode = minChildNode.LC;
                                curNode.father.RC = minChildNode;
                                if (minChildNode != curNode.RC) {
                                    minChildNode.father.LC = minChildNode.RC;
                                    curNode.RC.father = minChildNode;
                                    minChildNode.RC = curNode.RC;
                                }
                                curNode.LC.father = minChildNode;
                                minChildNode.LC = curNode.LC;
                                TstNode stackChckBlce = minChildNode.father;
                                minChildNode.father = curNode.father;
                                while (stackChckBlce != curNode.father) {
                                    if (Math.abs(TST.TSTHeight(stackChckBlce.LC) -
                                            TST.TSTHeight(stackChckBlce.RC)) > 1) {
                                        if (TST.TSTHeight(stackChckBlce.RC.LC) -
                                                TST.TSTHeight(stackChckBlce.RC.RC) > 0)
                                            TST.RL(stackChckBlce);
                                        else
                                            TST.RR(stackChckBlce);
                                    }
                                    stackChckBlce = stackChckBlce.father;
                                }
                            } else {
                                TstNode minChildNode = curNode.RC;
                                while (minChildNode.LC != null)
                                    minChildNode = minChildNode.LC;
                                curNode.father.MC = minChildNode;
                                if (minChildNode != curNode.RC) {
                                    minChildNode.father.LC = minChildNode.RC;
                                    curNode.RC.father = minChildNode;
                                    minChildNode.RC = curNode.RC;
                                }
                                curNode.LC.father = minChildNode;
                                minChildNode.LC = curNode.LC;
                                TstNode stackChckBlce = minChildNode.father;
                                minChildNode.father = curNode.father;
                                while (stackChckBlce != curNode.father) {
                                    if (Math.abs(TST.TSTHeight(stackChckBlce.LC) -
                                            TST.TSTHeight(stackChckBlce.RC)) > 1) {
                                        if (TST.TSTHeight(stackChckBlce.RC.LC) -
                                                TST.TSTHeight(stackChckBlce.RC.RC) > 0) {
                                            TST.RL(stackChckBlce);
                                        } else {
                                            TST.RR(stackChckBlce);
                                        }
                                    }
                                    stackChckBlce = stackChckBlce.father;
                                }
                            }
                        } else {
                            TstNode minChildNode = curNode.LC;
                            while (minChildNode.RC != null)
                                minChildNode = minChildNode.RC;
                            if (minChildNode != curNode.LC) {
                                minChildNode.father.RC = minChildNode.LC;
                                curNode.LC.father = minChildNode;
                                minChildNode.LC = curNode.LC;
                            }
                            curNode.RC.father = minChildNode;
                            minChildNode.RC = curNode.RC;
                            TstNode stackChckBlce = minChildNode.father;
                            minChildNode.father = null;
                            TST.tstRoot = minChildNode;
                            while (stackChckBlce != curNode.father) {
                                if (Math.abs(TST.TSTHeight(stackChckBlce.LC) -
                                        TST.TSTHeight(stackChckBlce.RC)) > 1) {
                                    if (TST.TSTHeight(stackChckBlce.LC.LC) -
                                            TST.TSTHeight(stackChckBlce.LC.RC) > 0)
                                        TST.LL(stackChckBlce);
                                    else
                                        TST.LR(stackChckBlce);
                                }
                                stackChckBlce = stackChckBlce.father;
                            }
                        }
                    } else {
                        if (curNode.father != null) {
                            if (curNode.father.LC == curNode) {
                                curNode.father.LC = curNode.RC;
                                curNode.RC.father = curNode.father;
                            } else if (curNode.father.RC == curNode) {
                                curNode.father.RC = curNode.RC;
                                curNode.RC.father = curNode.father;
                            } else {
                                curNode.father.MC = curNode.RC;
                                curNode.RC.father = curNode.father;
                            }
                        } else {
                            curNode.RC.father = null;
                            TST.tstRoot = curNode.RC;
                        }
                    }
                } else if (curNode.LC != null) {
                    if (curNode.father != null) {
                        if (curNode.father.LC == curNode) {
                            curNode.father.LC = curNode.LC;
                            curNode.LC.father = curNode.father;
                        } else if (curNode.father.RC == curNode) {
                            curNode.father.RC = curNode.LC;
                            curNode.LC.father = curNode.father;
                        } else {
                            curNode.father.MC = curNode.LC;
                            curNode.LC.father = curNode.father;
                        }
                    } else {
                        curNode.LC.father = null;
                        TST.tstRoot = curNode.LC;
                    }
                } else {
                    if (curNode.father != null) {
                        if (curNode.father.LC == curNode)
                            curNode.father.LC = null;
                        else if (curNode.father.RC == curNode)
                            curNode.father.RC = null;
                        else
                            curNode.father.MC = null;
                    }
                }
                tst.remove(curNode);
                delTST(curNode.father, tst);
            }
        }
    }

    private void delTrie(ArrayList<TrieNode> trie) {

        ArrayList<Character> charW;

        if (trie.size() > 1) {

            int i;
            TrieNode nNode = Trie.emptyNode;
            for (i = 0; i < charName.length; i++) {
                if (!nNode.child.isEmpty()) {
                    charW = new ArrayList<>();
                    for (TrieNode k : nNode.child)
                        charW.add(k.key);
                    if (charW.contains(charName[i]))
                        nNode = nNode.child.get(charW.indexOf(charName[i]));
                    else
                        break;
                } else
                    break;
            }

            if (nNode.endWord && i == charName.length) {
                if (nNode.list != null) {
                    for (typeList curNode : nNode.list.fileName)
                        if (curNode.key.equals(fileName)) {
                            if (curNode.previous != null) {
                                curNode.previous.next = curNode.next;
                            }
                            if (curNode.next != null) {
                                curNode.next.previous = curNode.previous;
                            }
                            nNode.list.fileName.remove(curNode);
                            break;
                        }
                    if (nNode.list.fileName.isEmpty())
                        nNode.list = null;
                }
                if (nNode.child.isEmpty()) {
                    if (nNode.list == null) {
                        TrieNode father = nNode.father;
                        father.child.remove((father).child.indexOf(nNode));
                        trie.remove(nNode);
                        while (father.child.isEmpty()) {
                            nNode = father;
                            father = nNode.father;
                            father.child.remove(father.child.indexOf(nNode));
                            trie.remove(nNode);
                            if (father.father == null)
                                break;
                        }

                    }
                } else if (nNode.list == null) {
                    nNode.endWord = false;

                }
            }
        }

    }

    private void delHash(ArrayList<HahTable> hash) {

        if (!hash.isEmpty()) {
            int adrs = Hash.hashFunc(CurWord);
            if (hash.size() > adrs)
                if (hash.get(adrs) != null)
                    delBST(hash.get(adrs).bstRoot, hash.get(adrs).bst);
        }

    }

}
