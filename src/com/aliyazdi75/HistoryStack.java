package com.aliyazdi75;

import java.util.ArrayList;

/**
 * Google Created by AliYazdi75 on Dec_2016
 */
public class HistoryStack {

    public static ArrayList<typeList> cmnds;
    public static typeList root = null;

    public HistoryStack(String cmnd) {
        addFName(cmnd);
    }

    private void addFName(String cmnd) {

        typeList newFName = new typeList();
        newFName.key = cmnd;

        if (cmnds.size() > 0) {
            if (!root.key.equals(cmnd)) {
                newFName.next = root;
                root.previous=newFName;
                root = newFName;
                cmnds.add(newFName);
            }
        } else {
            root = newFName;
            cmnds.add(newFName);
        }

    }

}
