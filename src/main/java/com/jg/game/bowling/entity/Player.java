package com.jg.game.bowling.entity;

/**
 * Common parent Player class
 * @author jgarrido
 * @date 2021/03/07
 */
public class Player {
    protected String name;
    protected Integer score;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getScore() {
        return score;
    }
    
    public void setScore(Integer score) {
        this.score = score;
    }
}
