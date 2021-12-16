/*
 * UserIsAlreadyInEventException
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.sportTeamManager.exceptions;

public class UserIsAlreadyInEventException extends Exception {

    public UserIsAlreadyInEventException() {
        super("User is already invited!");
    }

}
