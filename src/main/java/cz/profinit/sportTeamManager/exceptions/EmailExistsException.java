/*
 * EmailExistsException
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.exceptions;

/**
 * Exception is thrown when a new user use email address already registered in database.
 */
public class EmailExistsException extends RuntimeException {
    public EmailExistsException(String message) {
        super(message);
    }
}
