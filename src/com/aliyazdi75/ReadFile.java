package com.aliyazdi75;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Google Created by AliYazdi75 on Dec_2016
 */
public class ReadFile {

    public ArrayList<File> fileExist;

    /**
     * Building tree function
     *
     * @String fAddress
     */
    public ReadFile(String fAddress) throws FileNotFoundException {

        //Get file of folder to array
        File f = new File(fAddress);
        fileExist = new ArrayList(Arrays.asList(f.listFiles()));
        f = new File(fileExist.get(0).getPath());
        Scanner scn = new Scanner(f);

    }

}
