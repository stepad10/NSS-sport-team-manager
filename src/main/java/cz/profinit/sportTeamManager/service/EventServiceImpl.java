/*
 * EventServiceImpl
 *
 * 0.1
 *
 * Author: M. Halamka
 */
package cz.profinit.sportTeamManager.service;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.dto.EventDto;
import cz.profinit.sportTeamManager.mappers.EventMapper;
import cz.profinit.sportTeamManager.model.event.Event;
import cz.profinit.sportTeamManager.model.event.Message;
import cz.profinit.sportTeamManager.model.user.User;
import cz.profinit.sportTeamManager.repositories.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service handling Event and Message entities.
 * Contains methods that creates and modifies Event and Message entities or
 * finds Event entity.
 */
@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService{

    private EventRepository eventRepository;
    private EventMapper eventMapper;

    /**
     * Adds a new Event into database created from EventDto object.
     *
     * @param eventDto EventDto class, from which is new Event created
     * @return Event that was saved into database
     */
    public Event createNewEvent(EventDto eventDto){
        return eventRepository.createNewEvent(eventMapper.toEvent(eventDto));
    }

    /**
     * Updates existing event, with data stored in EventDto
     *
     * @param eventDto EvenDto class, from which is Event updated.
     * @param event Event that has been updated
     * @return Updated event
     */
    public Event updateEvent (EventDto eventDto, Event event) {
        event.setDate(eventDto.getDate());
        event.setPlace(eventDto.getPlace());
        event.setMaxPersonAttendance(eventDto.getMaxPersonAttendance());

        return eventRepository.updateEvent(event);
    }

    /**
     * Finds Entity in database by id.
     *
     * @param id which is used to find Event entity in database
     * @return found Event
     * @throws EntityNotFoundException if entity is not found.
     */
    public Event findEventById(Long id) throws EntityNotFoundException {
        Event event = eventRepository.findEventById(id);
        return event;
    }

    /**
     * Changes event status isCanceled. If event is going to be Canceled method changes isCanceled boolean to true;
     *
     * @param event that is going to be changed
     * @return changed Event
     */
    public Event changeEventStatus (Event event) {
        event.setIsCanceled(!event.getIsCanceled());
        return eventRepository.updateEvent(event);
    }

    /**
     * Adds new message to the database and to the event
     *
     * @param user who is sender of the message
     * @param messageStr sended message
     * @param event where message will be stored
     * @return updated event
     */
    public Event addNewMessage (User user, String messageStr, Event event){
        Message message = new Message(user,messageStr,LocalDateTime.now());
        event.addNewMessage(message);
        return eventRepository.updateEvent(event);
    }

    /**
     * Returns all messages related to selected event.
     *
     * @param event which from we want to get messages.
     * @return List of messages.
     */
    public List<Message> getAllMessages (Event event)  {
        return event.getListOfMessages();
    }
}
