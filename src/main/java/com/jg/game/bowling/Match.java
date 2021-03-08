package com.jg.game.bowling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.jg.game.bowling.entity.Bowling;
import com.jg.game.bowling.entity.BowlingPlayer;
import com.jg.game.bowling.ui.Console;
import com.jg.game.bowling.ui.UserInterface;

/**
 * Match class to read game results and print scores.
 * @author jgarrido
 * @date 2021/03/07
 */
public class Match {
    private static final String DEFAULT_FILE_PATH = "results.txt";
    private static final String RESULT_LINE_SEPARATOR = "\\t";

    public static void main(String[] args) {
        UserInterface userInterface = new Console();

        userInterface.showMessage("Welcome to Bowling Game.");
        userInterface.showMessage(String.format("Please enter path for results file or press enter to use defaualt: %s (at current directory).", DEFAULT_FILE_PATH));
        String resultsFilePath = userInterface.getUserData();
        
        if (resultsFilePath == null || resultsFilePath.trim().isEmpty()) {
            // Use default file path.
            resultsFilePath = DEFAULT_FILE_PATH;
        }
        File resultsFile = new File(resultsFilePath);
        Map<String, List<String>> resultsMap = new HashMap<String, List<String>>();
        try {
            Scanner fileEntryScanner = new Scanner(resultsFile);
            while (fileEntryScanner.hasNext()) {
                String resultLine = fileEntryScanner.nextLine();
                String[] resultArray = resultLine.split(RESULT_LINE_SEPARATOR);
                if (resultArray.length == 2) {
                    String name = resultArray[0].trim();
                    String amount = resultArray[1].trim();
                    if (!resultsMap.containsKey(name)) {
                        resultsMap.put(name, new ArrayList<String>());
                    }
                    resultsMap.get(name).add(amount);
                } else {
                    userInterface.showMessage(String.format("Error parsing result line, should have onlye Name and amount: " + resultLine));
                    return;
                }
            }
            ProcessBowlingScores processScores = new ProcessBowlingScores();
            Bowling game = processScores.processResults(resultsMap, new Bowling());
            userInterface.showMessage(processScores.printResultsHeaders());
            for (BowlingPlayer player : game.getPlayers()) {
                userInterface.showMessage(player.getName());
                userInterface.showMessage(processScores.printPlayerResults(player));
                userInterface.showMessage(processScores.printPlayerScores(player));
            }
        } catch (FileNotFoundException fnfExc) {
            userInterface.showMessage(String.format("Error reading the result file at path: %s. Please check: %s", resultsFilePath, fnfExc.getMessage()));
        } catch (BowlingException bExc) {
            userInterface.showMessage(String.format("Exception occurred processing results: %s", bExc.getErrorMessage()));
        }        
    }

}
