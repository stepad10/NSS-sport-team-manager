/*
 * EventService
 *
 * 0.1
 *
 * Author: M. Halamka
 */
package cz.profinit.sportTeamManager.service;

import cz.profinit.sportTeamManager.dto.EventDto;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.event.Event;
import cz.profinit.sportTeamManager.model.event.Message;
import cz.profinit.sportTeamManager.model.user.User;

import java.util.List;

/**
 * Service interface for Event business logic.
 */
public interface EventService {

    Event createNewEvent(EventDto eventDto);
    Event updateEvent (EventDto eventDto, Event event);
    Event findEventById(Long id) throws EntityNotFoundException;
    Event addNewMessage (User user, String messageStr, Event event);
    List<Message> getAllMessages (Event event);


}
