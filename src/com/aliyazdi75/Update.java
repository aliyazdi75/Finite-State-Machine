package com.aliyazdi75;

import java.io.File;
import java.util.ArrayList;

/**
 * Google Created by AliYazdi75 on Dec_2016
 */
public class Update {

    private ArrayList tree = new ArrayList();
    private ArrayList stopTree = new ArrayList();
    private String fUp;
    private String fileAdrs;
    private String name;
    private int type;
    public ArrayList<File> fExist = new ArrayList<>();

    public Update(int type, String fAddress, String name, ArrayList<File> fileExist,
                  ArrayList tree, ArrayList stopTree) {
        this.fUp = fAddress;
        this.fileAdrs = fAddress + "\\" + name + ".txt";
        this.name = name;
        this.tree = tree;
        this.stopTree = stopTree;
        this.type = type;
        read(fileExist, stopTree);
    }

    private void read(ArrayList<File> fileExist, ArrayList stopTree) {

        File f;
        f = new File(fileAdrs);
        if (fileExist.contains(f)) {

            if (type == 1) {
                new Delete(1, fUp, name, fileExist, tree, stopTree, false);
                new Add(1, fUp, name, fileExist, tree, stopTree, false);
            } else if (type == 2) {
                new Delete(2, fUp, name, fileExist, tree, stopTree, false);
                new Add(2, fUp, name, fileExist, tree, stopTree, false);
            } else if (type == 3) {
                new Delete(3, fUp, name, fileExist, tree, stopTree, false);
                new Add(3, fUp, name, fileExist, tree, stopTree, false);
            } else if (type == 4) {
                new Delete(4, fUp, name, fileExist, tree, stopTree, false);
                new Add(4, fUp, name, fileExist, tree, stopTree, false);
            }
            UI.txtPrint.append(name + " successfully updated." + "\n");

        } else
            UI.txtPrint.append("err: document not found." + "\n");

    }

}
