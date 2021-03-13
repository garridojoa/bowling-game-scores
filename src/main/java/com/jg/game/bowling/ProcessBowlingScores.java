package com.jg.game.bowling;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.jg.game.bowling.entity.Bowling;
import com.jg.game.bowling.entity.BowlingPlayer;
import com.jg.game.bowling.entity.Frame;
import com.jg.game.bowling.exceptions.BaseException;
import com.jg.game.bowling.exceptions.ParsingException;
import com.jg.game.bowling.utils.TextUtils;

/**
 * Class to process and print Bowling scores.
 * @author jgarrido
 * @date 2021/03/07
 */
public class ProcessBowlingScores {
    private static final int LAST_FRAME = 10;
    private static final String FRAMES_TITLE = "Frame";
    private static final String PINFALLS_TITLE = "Pinfalls";
    private static final String SCORES_TITLE = "Score";
    private static final int MAX_POINTS = 10;
    
    /**
     * Process Bowling results to create players.
     * @param resultsMap - Source result to process.
     * @param game - Bowling game to process.
     * @return Processed game.
     */
    public Bowling processResults(Map<String, List<String>> resultsMap, Bowling game) throws BaseException {
        for (Entry<String, List<String>> playerResult : resultsMap.entrySet()) {
            List<Frame> frameList = new ArrayList<Frame>();
            Frame frame = null;
            // Iterate each player pinfall
            for (String result : playerResult.getValue()) {
                // Start a new frame
                if (frame == null) {
                    frame = new Frame();
                }
                // Add pinfall to the frame
                frame.getPinfalls().add(getPinfallsNockedCharacter(playerResult.getKey(), result));
                // Once the frame is complete must add it to the frame list and reset frame.
                if (isFrameComplete(playerResult.getKey(), frameList.size() + 1, frame.getPinfalls())) {
                    frameList.add(frame);
                    frame = null;
                }
            }
            // Once all the frames are complete must create the player.
            BowlingPlayer bowlingPlayer = new BowlingPlayer(playerResult.getKey(), frameList);
            checkPlayerComplete(bowlingPlayer);
            game.getPlayers().add(bowlingPlayer);
        }
        return game;
    }
    
    /**
     * Check if the player is complete: has the max size of frames and the last one is complete.
     * @param bowlingPlayer - Player to check
     */
    private void checkPlayerComplete(BowlingPlayer bowlingPlayer) throws BaseException {
        if (bowlingPlayer.getFrames().size() != LAST_FRAME) {
            throw new BaseException(String.format("Error processing results. Player %s has missing frames or pinfalls.", bowlingPlayer.getName()));
        }
    }

    /**
     * Print results header.
     * @return Header to show.
     */
    public String printResultsHeaders() {
        String print = FRAMES_TITLE + TextUtils.STRING_SEPARATOR + TextUtils.STRING_SEPARATOR;
        for (int ind = 1; ind <= LAST_FRAME; ind++) {
            print = print.concat(String.valueOf(ind) + TextUtils.STRING_SEPARATOR + TextUtils.STRING_SEPARATOR);
        }
        return print;
    }

    /**
     * Print a player result for each frame.
     * @param player - Player to print results.
     * @return String including full results.
     */
    public String printPlayerResults(BowlingPlayer player) {
        String print = PINFALLS_TITLE + TextUtils.STRING_SEPARATOR;
        // Iterate list of frames.
        for (Frame frame : player.getFrames()) {
            if (frame.getPinfalls().size() == 1) {
                // If only has a pinfall print on second order.
                print = print.concat(TextUtils.STRING_SEPARATOR + frame.getPinfalls().get(0) + TextUtils.STRING_SEPARATOR);
            } else if (frame.getPinfalls().size() == 2) {
                print = print.concat(frame.getPinfalls().get(0) + TextUtils.STRING_SEPARATOR);
                // If has two check if get to max points to print the spare character.
                if (parsePinfallNumber(frame.getPinfalls().get(0)) + parsePinfallNumber(frame.getPinfalls().get(1)) == MAX_POINTS) {
                    print = print.concat(TextUtils.SPARE_CHARACTER + TextUtils.STRING_SEPARATOR);
                } else {
                    print = print.concat(frame.getPinfalls().get(1) + TextUtils.STRING_SEPARATOR);
                }
            } else {
                // Print all the characters in order.
                print = print.concat(frame.getPinfalls().stream().map(p -> p.toString()).collect(Collectors.joining(TextUtils.STRING_SEPARATOR)));
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
    public String printPlayerScores(BowlingPlayer player) throws BaseException {
        String print = SCORES_TITLE + TextUtils.STRING_SEPARATOR + TextUtils.STRING_SEPARATOR;
        Integer previousScore = 0;
        // Iterate frames as every player has a score per each frame.
        for (int ind = 0; ind < player.getFrames().size(); ind++) {
            Frame frame = player.getFrames().get(ind);
            Integer pinfallsSum = sumPinfalls(frame.getPinfalls(), null);
            Integer currentScore = previousScore + pinfallsSum;

            if (frame.getPinfalls().size() == 1) {
                // If has only one pinfall means a max score and should add next to two pinfalls as extra.
                Frame nextFrame = player.getFrames().get(ind + 1);
                currentScore += sumPinfalls(nextFrame.getPinfalls(), 2);
                if (nextFrame.getPinfalls().size() == 1) {
                    // If next frame also has one pinfall means one extra is missing and should get from the next.
                    currentScore += getNextFrameFirstPinfall(player.getFrames(), ind + 2);
                }
            } else if (frame.getPinfalls().size() == 2) {
                // If has two pinfalls means a max of one extra, in case pinfalls sums the max score.
                if (pinfallsSum  == MAX_POINTS) {
                    currentScore += getNextFrameFirstPinfall(player.getFrames(), ind + 1);
                }
            }
            print = print.concat(currentScore + TextUtils.STRING_SEPARATOR + TextUtils.STRING_SEPARATOR);
            // Current score must be set as previous because each frame score includes all the previous scores.
            previousScore = currentScore;
        }
        return print;
    }

    /*
     * Get the first pinfall of the next frame. 
     * @param frames - Frame list to get next frame.
     * @param nextFrameInd - Next frame index
     * @return Integer - Amount of pinfalls
     */
    private Integer getNextFrameFirstPinfall(List<Frame> frames, int nextFrameInd) {
        return parsePinfallNumber(frames.get(nextFrameInd).getPinfalls().get(0));
    }

    /*
     * Stated the corresponding Character according to result received.
     * @param result - Number/Letter to check.
     * @param playerName - Player name to report errors.
     * @return Pinfall Character
     * @throws Exception 
     */
    private Character getPinfallsNockedCharacter(String playerName, String result) throws ParsingException {
        switch (result) {
            case "0": case "1": case "2": case "3": case "4": case "5": case "6": case "7": case "8": case "9": case "F":
                return result.charAt(0);
            case "10":
                return TextUtils.CHARACTER_TEN_POINTS;
            default:
                throw new ParsingException(String.format("Error parsing result: %s for player: %s", result, playerName));
        }
    }

    /*
     * Check if a frame is completed or not. TRUE if:
     *  - One shot and 10 pins fall.
     *  - Two shots no matter the number of pins.
     *  - Exception for last frame as could have an extra shot.
     *  
     * @param playerName - Player name to report errors.
     * @param frameNumber - Frame number at the game.
     * @param pinfalls - List of pinfalls.
     * @return true if frame is completed
     */
    private boolean isFrameComplete(String playerName, int frameNumber, List<Character> pinfalls) throws BaseException {
        if (frameNumber < LAST_FRAME) {
            if (pinfalls.size() == 1 && pinfalls.get(0).equals(TextUtils.CHARACTER_TEN_POINTS)) {
                return true;
            } else if (pinfalls.size() == 2) {
                return true;
            }
        } else if (frameNumber == LAST_FRAME) {
            if (pinfalls.size() == 2 && parsePinfallNumber(pinfalls.get(0)) + parsePinfallNumber(pinfalls.get(1)) < MAX_POINTS) {
                return true;
            } else if (pinfalls.size() == 3 && parsePinfallNumber(pinfalls.get(0)) + parsePinfallNumber(pinfalls.get(1)) >= MAX_POINTS) {
                return true;
            }
        } else {
            throw new BaseException(String.format("Error processing results. Player %s has more than %s frames.", playerName, LAST_FRAME));
        }
        return false;
    }

    /*
     * Parse pinfall number from character
     * @param pinfalls - Character to parse
     * @return Number of pinfall
     * @throws Exception - Number incorrect.
     */
    private int parsePinfallNumber(Character pinfalls) throws ParsingException {
        switch (pinfalls) {
            case '0': case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9':
                return Integer.valueOf(pinfalls.toString());
            case 'F':
                return 0;
            case TextUtils.CHARACTER_TEN_POINTS:
                return 10;
            default:
                throw new ParsingException(String.format("Error parsing pinfalls number: %s.", pinfalls));
        }
    }

    /**
     * Sum all the pinfalls of the frame.
     * @param pinfalls - Pinfalls to sum
     * @param limit - Limit the sum to an specific range
     * @return Integer - the sum
     */
    private Integer sumPinfalls(List<Character> pinfalls, Integer limit) {
        Integer pinfallsSum = 0;
        int lastIndex = (limit != null && limit < pinfalls.size()? --limit: pinfalls.size() - 1);
        for (int ind = 0; ind <= lastIndex ; ind++) {
            Character pinfall = pinfalls.get(ind);
            pinfallsSum += parsePinfallNumber(pinfall);
        }
        return pinfallsSum;
    }
}
