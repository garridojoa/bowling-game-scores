package com.jg.game.bowling.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Bowling game frame
 * @author jgarrido
 * @date 2021/03/07
 */
public class Frame  {
    // Set a list to allow in the future to change numbers of pinfalls for each frame.
    private List<Character> pinfalls;
    
    public Frame() {
        pinfalls = new ArrayList<Character>();
    }

    public List<Character> getPinfalls() {
        return pinfalls;
    }

    public void setPinfalls(List<Character> pinfalls) {
        this.pinfalls = pinfalls;
    }
}
