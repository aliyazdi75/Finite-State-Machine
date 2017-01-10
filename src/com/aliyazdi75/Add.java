package com.aliyazdi75;

import java.io.File;
import java.util.ArrayList;

/**
 * Google Created by AliYazdi75 on Dec_2016
 */
public class Add {

    private String fAdd;
    private String name;
    private int type;
    public ArrayList<File> fExist = new ArrayList<>();

    public Add(int type, String fAddress, String name, ArrayList<File> fileExist,
               ArrayList tree, ArrayList stopTree, boolean textShow) {
        this.fAdd = fAddress + "\\" + name + ".txt";
        this.name = name;
        this.type = type;
        read(fileExist, tree, stopTree, textShow);
    }

    private void read(ArrayList<File> fileExist, ArrayList tree, ArrayList stopTree, boolean textShow) {

        File f;
        f = new File(fAdd);
        if (f.exists()) {
            if (fileExist.contains(f))
                UI.txtPrint.append("err: already exists, you may want to update.\n");
            else {
                fileExist.add(f);
                fExist.add(f);

                if (type == 1) {
                    new BST(fExist, tree, stopTree, 2);
                } else if (type == 2) {
                    new TST(fExist, tree, stopTree, 2);
                } else if (type == 3) {
                    new Trie(fExist, tree, stopTree, 2);
                } else if (type == 4) {
                    new Hash(fExist, tree, stopTree, 2);
                }
                if (textShow)
                    UI.txtPrint.append(name + " successfully added.\n");
            }
        } else
            UI.txtPrint.append("err: document not found.\n");

    }

}
