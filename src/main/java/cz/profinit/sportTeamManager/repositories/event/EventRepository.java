/*
 * EventRepository
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.sportTeamManager.repositories.event;

import cz.profinit.sportTeamManager.exceptions.EntityAlreadyExistsException;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.event.Event;

/**
 * Interface for Event repository
 */
public interface EventRepository {

    /**
     *
     * @param event to insert
     * @throws EntityAlreadyExistsException if Event already exists
     */
    void insertEvent(Event event) throws EntityAlreadyExistsException;

    /**
     *
     * @param id to find Event by
     * @return found Event
     * @throws EntityNotFoundException if Event could not be found
     */
    Event findEventById(Long id) throws EntityNotFoundException;

    /**
     *
     * @param event to update
     * @throws EntityNotFoundException if Event could not be found
     */
    void updateEvent(Event event) throws EntityNotFoundException;
}
