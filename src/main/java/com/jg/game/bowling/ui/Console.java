package com.jg.game.bowling.ui;

import java.util.Scanner;

/**
 * Interface to interacte with the user.
 * @author jgarrido
 * @date 2021/03/07
 */
public class Console implements UserInterface {

    /**
     * Print messages to user using console.
     * @param message - Message to show
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Get data from the user.
     * @return - User data.
     */
    public String getUserData() {
        Scanner textEntryScanner = new Scanner(System.in);
        return textEntryScanner.nextLine();
    }

}
