/*
 * EventRepository
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.sportTeamManager.repositories.event;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.event.Event;

/**
 * Interface for Event repository
 */
public interface EventRepository {

    Event insertEvent(Event event) throws EntityNotFoundException;
    Event findEventById(Long id) throws EntityNotFoundException;
    void updateEvent(Event event) throws EntityNotFoundException;
}
