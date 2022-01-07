/*
 * EntityNotFoundException
 *
 * 0.1
 *
 * Author: M. Halamka
 */
package cz.profinit.sportTeamManager.exceptions;

/**
 * EntityAlreadyExistsException is thrown when entity already exists in some object.
 */
public class EntityAlreadyExistsException extends Exception {

    public EntityAlreadyExistsException(String errorMessage) {
        super(errorMessage + " already exists!");
    }
}
