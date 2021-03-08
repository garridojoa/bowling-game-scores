package com.jg.game.bowling.entity;

import java.util.List;

/**
 * Bowling player
 * @author jgarrido
 * @date 2021/03/07
 */
public class BowlingPlayer extends Player {
    // Set a list to allow in the future to change numbers of frames for each player.
    private List<Frame> frames;

    /**
     * BowlingPlayer Constructor
     * @param playerName - Player name
     * @param frames - List of frames
     */
    public BowlingPlayer(String playerName, List<Frame> frames) {
        this.name = playerName;
        this.frames = frames;
    }

    public List<Frame> getFrames() {
        return frames;
    }
    
    public void setFrames(List<Frame> frames) {
        this.frames = frames;
    }
}
