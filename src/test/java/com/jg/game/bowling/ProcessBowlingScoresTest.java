package com.jg.game.bowling;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.jg.game.bowling.entity.Bowling;

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

    @Test(expected = BowlingException.class)
    public void processResultsInvalidEntry () {
        ProcessBowlingScores processBowlingScores = new ProcessBowlingScores();
        Map<String, List<String>> resultsMap = new HashMap<>();
        resultsMap.put("John", Arrays.asList("-1"));
        processBowlingScores.processResults(resultsMap, new Bowling());
    }

    @Test(expected = BowlingException.class)
    public void processResultsMoreThanTenFrames() {
        ProcessBowlingScores processBowlingScores = new ProcessBowlingScores();
        Map<String, List<String>> resultsMap = new HashMap<>();
        resultsMap.put("John", Arrays.asList("10", "10", "10", "10", "10", "10", "10", "10", "10", "10", "10", "10", "10"));
        processBowlingScores.processResults(resultsMap, new Bowling());
    }
}
