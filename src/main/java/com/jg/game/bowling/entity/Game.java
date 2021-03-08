package com.jg.game.bowling.entity;

import java.util.List;

/**
 * Common parent Game class
 * @author jgarrido
 * @date 2021/03/07
 */
public abstract class Game<T> {
    protected String name;
    protected List<T> players;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<T> getPlayers() {
        return players;
    }
    
    public void setPlayers(List<T> players) {
        this.players = players;
    }
}
