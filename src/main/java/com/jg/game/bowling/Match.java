package com.jg.game.bowling;

import java.util.List;
import java.util.Map;

import com.jg.game.bowling.entity.Bowling;
import com.jg.game.bowling.entity.BowlingPlayer;
import com.jg.game.bowling.exceptions.BaseException;
import com.jg.game.bowling.exceptions.ParsingException;
import com.jg.game.bowling.ui.Console;
import com.jg.game.bowling.ui.UserInterface;
import com.jg.game.bowling.utils.FileProcessor;

/**
 * Match class to read game results and print scores.
 * @author jgarrido
 * @date 2021/03/07
 */
public class Match {
    private static final String DEFAULT_FILE_PATH = "results.txt";

    public static void main(String[] args) {
        UserInterface userInterface = new Console();

        userInterface.showMessage("Welcome to Bowling Game.");
        userInterface.showMessage(String.format("Please enter path for results file or press enter to use defaualt: %s (at current directory).", DEFAULT_FILE_PATH));
        String resultsFilePath = userInterface.getUserData();
        
        if (resultsFilePath == null || resultsFilePath.trim().isEmpty()) {
            // Use default file path.
            resultsFilePath = DEFAULT_FILE_PATH;
        }
        FileProcessor fileProcessor = new FileProcessor(resultsFilePath);
        ProcessBowlingScores processScores = new ProcessBowlingScores();
        try {
            Map<String, List<String>> resultsMap = fileProcessor.process();
            Bowling game = processScores.processResults(resultsMap, new Bowling());
            userInterface.showMessage(processScores.printResultsHeaders());
            for (BowlingPlayer player : game.getPlayers()) {
                userInterface.showMessage(player.getName());
                userInterface.showMessage(processScores.printPlayerResults(player));
                userInterface.showMessage(processScores.printPlayerScores(player));
            }
        } catch (ParsingException pExc) {
            userInterface.showMessage(String.format("Exception occurred parsing data: %s", pExc.getErrorMessage()));
        } catch (BaseException bExc) {
            userInterface.showMessage(String.format("Exception occurred processing results: %s", bExc.getErrorMessage()));
        }        
    }

}
