/*
 * UserOrPasswordNotMatchException
 *
 * 0.1
 *
 * Author: J. Janský
 */

package cz.profinit.sportTeamManager.exceptions;

/**
 * Exception is thrown when logging credentials do not match any user.
 */
public class UserOrPasswordNotMatchException extends RuntimeException {
    public UserOrPasswordNotMatchException() {
        super("User and password do not match");
    }
}
