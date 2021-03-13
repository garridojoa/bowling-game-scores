package com.jg.game.bowling.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.jg.game.bowling.exceptions.BaseException;
import com.jg.game.bowling.exceptions.ParsingException;

/**
 * String utils to define common constants and functions.
 * @author jgarrido
 * @date 2021/03/13
 */
public class FileProcessor {
    private String filePath;
    
    public FileProcessor(String fileName) {
        this.filePath = fileName;
    }
    
    /**
     * Process the file to get the data into a map.
     * Assume file contains N lines, each one has format: PLAYER_NAME<TAB>PINFALLS
     * @return Map containing file data.
     */
    public Map<String, List<String>> process() {
        File resultsFile = new File(filePath);
        Map<String, List<String>> resultsMap = new HashMap<String, List<String>>();
        try {
            @SuppressWarnings("resource")
            Scanner fileEntryScanner = new Scanner(resultsFile);
            while (fileEntryScanner.hasNext()) {
                String resultLine = fileEntryScanner.nextLine();
                String[] resultArray = resultLine.split(TextUtils.STRING_SEPARATOR);
                if (resultArray.length == 2) {
                    String name = resultArray[0].trim();
                    String amount = resultArray[1].trim();
                    if (!resultsMap.containsKey(name)) {
                        resultsMap.put(name, new ArrayList<String>());
                    }
                    resultsMap.get(name).add(amount);
                } else {
                    throw new ParsingException(String.format("Error parsing result line, should have only Name and amount: " + resultLine));
                }
            }
        } catch (FileNotFoundException fnfExc) {
            throw new BaseException(String.format("Error reading the result file at path: %s. Please check: %s", filePath, fnfExc.getMessage()));
        }
        return resultsMap;
    }

}
