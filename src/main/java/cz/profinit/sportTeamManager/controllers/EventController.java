/*
 * EventController
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.sportTeamManager.controllers;


import cz.profinit.sportTeamManager.dto.EventDto;
import cz.profinit.sportTeamManager.dto.InvitationDto;
import cz.profinit.sportTeamManager.dto.MessageDto;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.exceptions.UserIsAlreadyInEventException;
import cz.profinit.sportTeamManager.mappers.EventMapper;
import cz.profinit.sportTeamManager.mappers.InvitationMapper;
import cz.profinit.sportTeamManager.mappers.MessageMapper;
import cz.profinit.sportTeamManager.model.event.Event;
import cz.profinit.sportTeamManager.model.invitation.StatusEnum;
import cz.profinit.sportTeamManager.service.EventService;
import cz.profinit.sportTeamManager.service.InvitationService;
import cz.profinit.sportTeamManager.service.user.AuthenticationFacade;
import cz.profinit.sportTeamManager.service.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Controller for event business logic
 */
@RestController
public class EventController {

    @Autowired
    EventService eventService;
    @Autowired
    InvitationService invitationService;
    @Autowired
    private AuthenticationFacade authenticationFacade;

    /**
     * Returns Event found by id or HttpStatus.NOT_FOUND (404)
     *
     * @param eventId ID of wanted event
     * @return EventDto of desired event or HttpStatus.NOT_FOUND if event was not found
     */
    @GetMapping (value = "/event/{eventId}")
    public @ResponseBody EventDto findEventById(@PathVariable Long eventId) {
        try {
            Event event = eventService.findEventById(eventId);
            return EventMapper.toDto(event);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
             throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
    }

    /**
     * Post that creates new event in database
     *
     * @param eventDto from which is new Event created
     * @return EventDto of created Event,
     */
    @PostMapping (value = "/event")
    @ResponseStatus (HttpStatus.CREATED)
    public EventDto createNewEvent (@RequestBody EventDto eventDto){
        return EventMapper.toDto(eventService.createNewEvent(eventDto));
    }

    /**
     * Post for updating event which is already in database. Returns NotFound (404) if event is not found
     *
     * @param eventId ID of wanted event
     * @param eventDto from which event will be updated
     * @return EvenDto of updated Event or HttpStatus.NOT_FOUND if Event was not found
     */
    @PostMapping(value = "/event/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventDto updateEvent (@PathVariable Long eventId, @RequestBody EventDto eventDto){
        try {
            return EventMapper.toDto(eventService.updateEvent(eventDto,eventId));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
    }

    /**
     * Changes event status.
     *
     * @param eventId ID of event which status is going to be changed
     * @return EventDto of Event with changed status or HttpStatus.NOT_FOUND if Event was not found
     */
    @PostMapping(value = "/event/{eventId}/status")
    @ResponseStatus(HttpStatus.OK)
    public EventDto changeEventStatus (@PathVariable Long eventId){
        try {
            return EventMapper.toDto(eventService.changeEventStatus(eventId));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
    }

    /**
     * Post new message to the event
     *
     * @param eventId ID of event where message should be sent
     * @param message which is going to be stored
     * @return MessageDto of created Message or HttpStatus.NOT_FOUND if Event was not found
     */
    @PostMapping(value = "/event/{eventId}/message/{message}")
    @ResponseStatus(HttpStatus.OK)
    public MessageDto addNewMessage (@PathVariable Long eventId, @PathVariable String message){
        try {
            Authentication authentication = authenticationFacade.getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            return MessageMapper.toDto(eventService.addNewMessage(userDetails.getUsername(), message,eventId));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
    }

    /**
     * Gets list of all messages from given Event
     *
     * @param eventId ID of event from which messages should be extracted
     * @return List of MessageDto from Event or HttpStatus.NOT_FOUND if Event was not found
     */
    @GetMapping(value = "/event/{eventId}/messages")
    public @ResponseBody List<MessageDto> getAllMessages(@PathVariable Long eventId){
        try {
            return MessageMapper.toListOfDto(eventService.getAllMessages(eventId));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
    }

    /**
     * Post new invitation to an Event
     *
     * @param eventId ID of event for which is invitation created
     * @param email Email of person who is invited
     * @return InvitationDto of Invitation that has been created or
     * HttpStatus.NOT_FOUND if Event or User was not found or
     * HttpStatus.CONFLICT if User has been already invited for given Event or
     * HttpStatus.BAD_REQUEST if something unexpected happens
     */
    @PostMapping(value = "/event/{eventId}/invitation/{email}")
    @ResponseStatus (HttpStatus.OK)
    public InvitationDto addNewInvitation(@PathVariable Long eventId, @PathVariable String email){
        try {
           return InvitationMapper.toDto(invitationService.createNewInvitation(email,eventId));
        } catch (EntityNotFoundException | UserIsAlreadyInEventException e) {
            e.printStackTrace();
            if (e.getMessage().contains("entity")) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
            } else if (e.getMessage().contains("User is already invited!")){
                throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    /**
     * Returns List of invitations for given Event.
     *
     * @param eventId ID of selected Event
     * @return List of invitations or HttpStatus.NOT_FOUND if Event was not found
     */
    @GetMapping (value = "/event/{eventId}/invitations")
    public @ResponseBody List<InvitationDto> getAllInvitations (@PathVariable Long eventId){
        try {
            return InvitationMapper.toDtoList(eventService.getAllInvitations(eventId));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
    }

    /**
     * Changes Invitation StatusEnum for current user and given event
     *
     * @param eventId ID of selected Event
     * @param statusString Status to which should be invitation changed
     * @return DTO of changed Invitation or HttpStatus.NOT_FOUND if event was not found
     * or HttpStatus.BAD_REQUEST if statusString was in bad format or contained non-existing enum value
     */
    @PostMapping (value = "/event/{eventId}/invitation/statusChange/{statusString}")
    public InvitationDto changeInvitationStatus (@PathVariable Long eventId, @PathVariable String statusString){
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        try {
            StatusEnum status = StatusEnum.valueOf(statusString);
            return InvitationMapper.toDto(invitationService.changeInvitationStatus(eventId, userDetails.getUsername(),status));
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            e.printStackTrace();
            if (e.getMessage().contains("entity"))
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
            else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
            }
        }
    }

    /**
     * Returns sorted list of invitations for selected Event and Invitation status.
     *
     * @param eventId  ID of selected Event
     * @param statusString Status for which will invitations be sorted
     * @return Sorted list of invitations for given status or HttpStatus.NOT_FOUND if event was not found
     * or HttpStatus.BAD_REQUEST if statusString was in bad format or contained non-existing enum value
     */
    @GetMapping (value = "/event/{eventId}/invitations/{statusString}/sorted")
    public @ResponseBody List<InvitationDto> getSortedInvitations (@PathVariable Long eventId,@PathVariable String statusString){
        try {
            StatusEnum status = StatusEnum.valueOf(statusString);
            return invitationService.OrderListOfInvitationByDateForSpecificStatus(InvitationMapper.toDtoList(eventService.getAllInvitations(eventId)), status);
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            e.printStackTrace();
            if (e.getMessage().contains("entity"))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
            else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
            }
        }
    }
}
