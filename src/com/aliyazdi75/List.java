package com.aliyazdi75;

import java.util.ArrayList;

/**
 * Google Created by AliYazdi75 on Dec_2016
 */
public class List {

    public ArrayList<typeList> fileName = new ArrayList<>();
    private typeList prvFName = null;

    public List(String fName) {
        addFName(fName);
    }

    public void addFName(String fName) {

        typeList newFName = new typeList();
        newFName.key = fName;

        if (!fileName.isEmpty()) {
            if (!prvFName.key.equals(fName)) {
                prvFName.next = newFName;
                newFName.previous = prvFName;
                prvFName = newFName;
                fileName.add(newFName);
            }
        } else {
            prvFName  = newFName;
            fileName.add(newFName);
        }

    }

}