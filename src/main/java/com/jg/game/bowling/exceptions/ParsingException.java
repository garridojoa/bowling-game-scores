package com.jg.game.bowling.exceptions;

/**
 * Parsing Exception
 * @author jgarrido
 * @date 2021/03/13
 */
public class ParsingException extends BaseException {
    private static final long serialVersionUID = 1L;
    
    public ParsingException(String errorMessage) {
        super(errorMessage);
    }
}
