/*
 * EventServiceImpl
 *
 * 0.1
 *
 * Author: M. Halamka
 */
package cz.profinit.sportTeamManager.service.event;

import cz.profinit.sportTeamManager.dto.event.EventDto;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.mappers.EventMapper;
import cz.profinit.sportTeamManager.mappers.PlaceMapper;
import cz.profinit.sportTeamManager.model.event.Event;
import cz.profinit.sportTeamManager.model.event.Message;
import cz.profinit.sportTeamManager.model.invitation.Invitation;
import cz.profinit.sportTeamManager.model.user.User;
import cz.profinit.sportTeamManager.repositories.event.EventRepository;
import cz.profinit.sportTeamManager.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
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
@Profile("Main")
public class EventServiceImpl implements EventService{

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private UserService userService;

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
     * Updates event to correspond with given DTO
     *
     * @param eventDto EvenDto class, from which is Event updated.
     * @param eventId Id of event that should be updated
     * @return Updated event
     * @throws EntityNotFoundException if event was not found
     */
    public Event updateEvent (EventDto eventDto, Long eventId) throws EntityNotFoundException {
        Event event = findEventById(eventId);
        event.setDate(eventDto.getDate());
        event.setPlace(PlaceMapper.toPlace(eventDto.getPlace()));
        event.setCapacity(eventDto.getMaxPersonAttendance());
        event.setIsCanceled(eventDto.isCanceled());

        return eventRepository.updateEvent(event);
    }

    /**
     * Finds Entity in database by ID.
     *
     * @param id which is used to find Event entity in database
     * @return found Event
     * @throws EntityNotFoundException if entity is not found.
     */
    public Event findEventById(Long id) throws EntityNotFoundException {
        return eventRepository.findEventById(id);
    }

    /**
     * Changes event status isCanceled. If event is going to be Canceled method changes isCanceled boolean to true;
     *
     * @param eventId ID if event that is going to be changed
     * @return changed Event
     * @throws EntityNotFoundException if entity is not found.
     */
    public Event changeEventStatus (Long eventId) throws EntityNotFoundException {
        Event event = findEventById(eventId);
        event.setIsCanceled(!event.getIsCanceled());
        return eventRepository.updateEvent(event);
    }

    /**
     * Adds new message to the database and to the event
     *
     * @param email of user who is a sender of the message
     * @param messageStr sent message
     * @param eventId ID of event where message will be store
     * @return updated event
     * @throws EntityNotFoundException if entity is not found.
     */
    public Message addNewMessage (String email, String messageStr, Long eventId) throws EntityNotFoundException {
        User user = userService.findUserByEmail(email);
        Event event =  findEventById(eventId);
        Message message = new Message(user,messageStr,LocalDateTime.now());
        event.addNewMessage(message);
        eventRepository.updateEvent(event);
        return message;
    }

    /**
     * Returns all messages related to given event.
     *
     * @param eventId ID of event which from we want to get messages.
     * @return List of DTO of message.
     * @throws EntityNotFoundException if entity is not found.
     */
    public List<Message> getAllMessages (Long eventId) throws EntityNotFoundException {
        return findEventById(eventId).getMessageList();
    }

    /**
     * Adds new Invitation to the Event
     *
     * @param eventId ID of event to which we want to add Invitation
     * @param invitation Invitation which we want to add to the event
     * @return updated event
     * @throws EntityNotFoundException if entity is not found.
     */
    public Event addNewInvitation(Long eventId, Invitation invitation) throws EntityNotFoundException {
        Event event = findEventById(eventId);
        event.addNewInvitation(invitation);
        eventRepository.updateEvent(event);
        return event;
    }

    /**
     * Returns all invitation related to given Event
     * @param eventId ID of event for which we are getting Invitations
     * @return List of Invitations
     * @throws EntityNotFoundException if entity is not found.
     */
    public List<Invitation> getAllInvitations (Long eventId) throws EntityNotFoundException {
        return findEventById(eventId).getInvitationList();
    }
}
