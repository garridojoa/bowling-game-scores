package com.jg.game.bowling.entity;

import java.util.ArrayList;

/**
 * Bowling Game
 * @author jgarrido
 * @date 2021/03/07
 */
public class Bowling extends Game<BowlingPlayer> {
    /**
     * Default Constructor
     */
    public Bowling() {
        super();
        setName("Bowling");
        players = new ArrayList<BowlingPlayer>();
    }
    
}
