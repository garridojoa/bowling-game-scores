package com.jg.game.bowling.exceptions;

/**
 * Base Exception
 * @author jgarrido
 * @date 2021/03/07
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    private String errorMessage;

    public BaseException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
