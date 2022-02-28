/*
 * EventRepository
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.stm.repository.event;

import cz.profinit.stm.exception.EntityAlreadyExistsException;
import cz.profinit.stm.exception.EntityNotFoundException;
import cz.profinit.stm.model.event.Event;

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
