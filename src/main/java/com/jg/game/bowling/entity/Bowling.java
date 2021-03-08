package com.jg.game.bowling.entity;

import java.util.ArrayList;

public class Bowling extends Game<BowlingPlayer> {
    /**
     * Default Constructor
     */
    public Bowling() {
        super();
        players = new ArrayList<BowlingPlayer>();
    }
    
}
