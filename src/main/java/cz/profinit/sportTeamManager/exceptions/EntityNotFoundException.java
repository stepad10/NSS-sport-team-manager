/*
 * EntityNotFoundException
 *
 * 0.1
 *
 * Author: M. Halamka
 */
package cz.profinit.sportTeamManager.exceptions;

/**
 * EntityNotFoundException is thrown when entity is not found in database.
 */
public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(String errorMessage) {
        super(errorMessage + " entity not found!");
    }
}
