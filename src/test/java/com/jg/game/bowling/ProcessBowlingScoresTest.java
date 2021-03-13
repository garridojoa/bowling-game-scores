package com.jg.game.bowling;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.jg.game.bowling.entity.Bowling;
import com.jg.game.bowling.entity.BowlingPlayer;
import com.jg.game.bowling.entity.Frame;
import com.jg.game.bowling.exceptions.BaseException;
import com.jg.game.bowling.exceptions.ParsingException;
import com.jg.game.bowling.utils.TextUtils;

/**
 * Unit Tests for class {@link ProcessBowlingScoresTest}.
 * @author jgarrido
 * @date 2021/03/07
 */
public class ProcessBowlingScoresTest {
    @Test()
    public void processResultsValidEntry() {
        ProcessBowlingScores processBowlingScores = new ProcessBowlingScores();
        Map<String, List<String>> resultsMap = new HashMap<>();
        resultsMap.put("John", Arrays.asList("10", "10", "10", "10", "10", "10", "10", "10", "10", "10", "10", "10"));
        Bowling bowling = processBowlingScores.processResults(resultsMap, new Bowling());
        assertTrue(bowling.getPlayers().size() == 1);
        assertTrue(bowling.getPlayers().get(0).getFrames().size() == 10);
    }

    @Test(expected = ParsingException.class)
    public void processResultsInvalidEntry () {
        ProcessBowlingScores processBowlingScores = new ProcessBowlingScores();
        Map<String, List<String>> resultsMap = new HashMap<>();
        resultsMap.put("John", Arrays.asList("-1"));
        processBowlingScores.processResults(resultsMap, new Bowling());
    }

    @Test(expected = BaseException.class)
    public void processResultsMoreThanTenFrames() {
        ProcessBowlingScores processBowlingScores = new ProcessBowlingScores();
        Map<String, List<String>> resultsMap = new HashMap<>();
        resultsMap.put("John", Arrays.asList("10", "10", "10", "10", "10", "10", "10", "10", "10", "10", "10", "10", "10"));
        processBowlingScores.processResults(resultsMap, new Bowling());
    }

    @Test(expected = BaseException.class)
    public void processResultsMissingFrames() {
        ProcessBowlingScores processBowlingScores = new ProcessBowlingScores();
        Map<String, List<String>> resultsMap = new HashMap<>();
        resultsMap.put("John", Arrays.asList("10", "10", "10", "10", "10", "10", "10", "10", "10"));
        processBowlingScores.processResults(resultsMap, new Bowling());
    }

    @Test(expected = BaseException.class)
    public void processResultsMissingPinfalls() {
        ProcessBowlingScores processBowlingScores = new ProcessBowlingScores();
        Map<String, List<String>> resultsMap = new HashMap<>();
        resultsMap.put("Jane", Arrays.asList("10", "10", "10", "10", "10", "10", "10", "10", "10", "9"));
        processBowlingScores.processResults(resultsMap, new Bowling());
    }

    @Test()
    public void printResultsValidEntry() {
        ProcessBowlingScores processBowlingScores = new ProcessBowlingScores();
        List<Frame> frames = new ArrayList<Frame>();
        Frame frame = null;
        for (int ind = 1; ind < 10; ind++) {
            frame = new Frame();
            frame.setPinfalls(Arrays.asList(TextUtils.CHARACTER_TEN_POINTS));
            frames.add(frame);
        }
        frame = new Frame();
        frame.setPinfalls(Arrays.asList(new Character[] {TextUtils.CHARACTER_TEN_POINTS, TextUtils.CHARACTER_TEN_POINTS, TextUtils.CHARACTER_TEN_POINTS}));
        frames.add(frame);
        String result = processBowlingScores.printPlayerResults(new BowlingPlayer("Peter", frames));
        String expectedResult = "Pinfalls".concat(TextUtils.STRING_SEPARATOR + TextUtils.STRING_SEPARATOR).
                concat(String.join(TextUtils.STRING_SEPARATOR + TextUtils.STRING_SEPARATOR, Collections.nCopies(9, String.valueOf(TextUtils.CHARACTER_TEN_POINTS)))).
                concat(TextUtils.STRING_SEPARATOR).
                concat(String.join(TextUtils.STRING_SEPARATOR, Collections.nCopies(3, String.valueOf(TextUtils.CHARACTER_TEN_POINTS))));
        System.out.println();
        assertTrue(result.equals(expectedResult));
    }
}
