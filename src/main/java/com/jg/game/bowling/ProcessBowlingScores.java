package com.jg.game.bowling;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jg.game.bowling.entity.Bowling;
import com.jg.game.bowling.entity.BowlingPlayer;
import com.jg.game.bowling.entity.Frame;

/**
 * Class to process and print Bowling scores.
 * @author jgarrido
 * @date 2021/03/07
 */
public class ProcessBowlingScores {
    private static final char CHARACTER_TEN_POINTS = 'X';
    private static final int BOWLING_MAX_FRAME = 10;
    private static final String PRINT_SEPARATOR = "\t";
    private static final String FRAMES_TITLE = "Frame";
    private static final String PINFALLS_TITLE = "Pinfalls";
    private static final String SCORES_TITLE = "Score";
    private static final String SPARE_CHARACTER = "/";
    private static final int MAX_POINTS = 10;
    
    /**
     * Process Bowling results to create players.
     * @param resultsMap - Source result to process.
     * @param game - Bowling game to process.
     * @return Processed game.
     */
    public Bowling processResults(Map<String, List<String>> resultsMap, Bowling game)  {
        for (Entry<String, List<String>> playerResult : resultsMap.entrySet()) {
            List<Frame> frameList = new ArrayList<Frame>();
            Frame frame = null;
            for (String result : playerResult.getValue()) {
                if (frame == null) {
                    frame = new Frame();
                }
                frame.getPinfalls().add(getPinfallsNocked(playerResult.getKey(), result));
                if (isFrameComplete(playerResult.getKey(), frameList.size() + 1, frame.getPinfalls())) {
                    frameList.add(frame);
                    frame = null;
                }
            }
            game.getPlayers().add(new BowlingPlayer(playerResult.getKey(), frameList));
        }
        return game;
    }
    
    /**
     * Print results header.
     * @return Header to show.
     */
    public String printResultsHeaders() {
        String print = FRAMES_TITLE + PRINT_SEPARATOR;
        for (int ind = 1; ind <= BOWLING_MAX_FRAME; ind++) {
            print = print.concat(String.valueOf(ind) + PRINT_SEPARATOR + PRINT_SEPARATOR);
        }
        return print;
    }

    /**
     * Print a player result
     * @param player - Player to print results.
     * @return String including full results.
     */
    public String printPlayerResults(BowlingPlayer player) {
        String print = PINFALLS_TITLE + PRINT_SEPARATOR;
        for (Frame frame : player.getFrames()) {
            if (frame.getPinfalls().size() == 1) {
                print = print.concat(PRINT_SEPARATOR + frame.getPinfalls().get(0) + PRINT_SEPARATOR);
            } else if (frame.getPinfalls().size() == 2) {
                print = print.concat(frame.getPinfalls().get(0) + PRINT_SEPARATOR);
                if (getPinfallsNumber(frame.getPinfalls().get(0)) + getPinfallsNumber(frame.getPinfalls().get(1)) == MAX_POINTS) {
                    print = print.concat(SPARE_CHARACTER + PRINT_SEPARATOR);
                } else {
                    print = print.concat(frame.getPinfalls().get(1) + PRINT_SEPARATOR);
                }
            } else {
                for (Character pinfall : frame.getPinfalls()) {
                    print = print.concat(pinfall + PRINT_SEPARATOR);
                }
            }
        }
        return print;
    }

    /**
     * Print a player scores
     * @param player - Player to print scores.
     * @return String including full scores.
     * @throws Exception 
     */
    public String printPlayerScores(BowlingPlayer player) throws BowlingException {
        String print = SCORES_TITLE + PRINT_SEPARATOR;
        Integer previousScore = 0;
        for (int ind = 0; ind < player.getFrames().size(); ind++) {
            Integer currentScore = 0;
            Frame frame = player.getFrames().get(ind);
            if (frame.getPinfalls().size() == 1) {
                currentScore = previousScore + getPinfallsNumber(frame.getPinfalls().get(0));
                Frame nextFrame = player.getFrames().get(ind + 1);
                if (nextFrame.getPinfalls().size() == 1) {
                    currentScore += getPinfallsNumber(nextFrame.getPinfalls().get(0));
                    currentScore += getPinfallsNumber(player.getFrames().get(ind + 2).getPinfalls().get(0));
                } else {
                    currentScore += getPinfallsNumber(nextFrame.getPinfalls().get(0)) + getPinfallsNumber(nextFrame.getPinfalls().get(1));
                }
            } else if (frame.getPinfalls().size() == 2) {
                int pinfallsSum = getPinfallsNumber(frame.getPinfalls().get(0)) + getPinfallsNumber(frame.getPinfalls().get(1));
                currentScore += previousScore + pinfallsSum;
                if (pinfallsSum  == MAX_POINTS) {
                    currentScore += getPinfallsNumber(player.getFrames().get(ind + 1).getPinfalls().get(0));
                }
            } else {
                int pinfallsSum = getPinfallsNumber(frame.getPinfalls().get(0)) + getPinfallsNumber(frame.getPinfalls().get(1))
                    + getPinfallsNumber(frame.getPinfalls().get(2));
                currentScore += previousScore + pinfallsSum;
            }
            print = print.concat(currentScore + PRINT_SEPARATOR + PRINT_SEPARATOR);
            previousScore = currentScore;
        }
        return print;
    }


    /*
     * Stated the corresponding Character according to result received.
     * @param result - Number/Letter to check.
     * @param playerName - Player name to report errors.
     * @return Pinfall Character
     * @throws Exception 
     */
    private Character getPinfallsNocked(String playerName, String result) throws BowlingException {
        switch (result) {
            case "0": case "1": case "2": case "3": case "4": case "5": case "6": case "7": case "8": case "9": case "F":
                return result.charAt(0);
            case "10":
                return CHARACTER_TEN_POINTS;
            default:
                throw new BowlingException(String.format("Error parsing result: %s for player: %s", result, playerName));
        }
    }

    /*
     * Check if a frame is completed or not.
     * @param playerName - Player name to report errors.
     * @param frameNumber - Frame number at the game.
     * @param pinfalls - List of pinfalls.
     * @return true if frame is completed.
     */
    private boolean isFrameComplete(String playerName, int frameNumber, List<Character> pinfalls) throws BowlingException {
        if (frameNumber < BOWLING_MAX_FRAME) {
            if (pinfalls.size() == 1 && pinfalls.get(0).equals(CHARACTER_TEN_POINTS)) {
                return true;
            } else if (pinfalls.size() == 2) {
                return true;
            }
        } else if (frameNumber == BOWLING_MAX_FRAME) {
           if (pinfalls.size() == 2 && getPinfallsNumber(pinfalls.get(0)) + getPinfallsNumber(pinfalls.get(1)) < 10) {
                return true;
            } else if (pinfalls.size() == 3 && getPinfallsNumber(pinfalls.get(0)) + getPinfallsNumber(pinfalls.get(1)) >= 10) {
                return true;
            }
        } else {
            throw new BowlingException(String.format("Error processing results. Player %s has more than %s frames.", playerName, BOWLING_MAX_FRAME));
        }
        return false;
    }

    /*
     * Parse pinfalls number from character
     * @param pinfalls - Character to parse
     * @return Number of pinfalls
     * @throws Exception 
     */
    private int getPinfallsNumber(Character pinfalls) throws BowlingException {
        switch (pinfalls) {
            case '0': case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9':
                return Integer.valueOf(pinfalls.toString());
            case 'F':
                return 0;
            case CHARACTER_TEN_POINTS:
                return 10;
            default:
                throw new BowlingException(String.format("Error parsing pinfalls number: %s.", pinfalls));
        }
    }

}
