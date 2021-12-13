/*
 * EntityNotFoundException
 *
 * 0.1
 *
 * Author: M. Halamka
 */
package cz.profinit.sportTeamManager.exceptions;

/**
 * Exception used when any entity do not match any record in the database.
 */
public class EntityNotFoundException extends Exception {

    /**
     * @param errorMessage message specifying an entity
     */
    public EntityNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}