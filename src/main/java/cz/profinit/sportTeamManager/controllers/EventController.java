/*
 * EventController
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.sportTeamManager.controllers;


import cz.profinit.sportTeamManager.dto.EventDto;
import cz.profinit.sportTeamManager.dto.MessageDto;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.mappers.EventMapper;
import cz.profinit.sportTeamManager.model.event.Event;
import cz.profinit.sportTeamManager.model.invitation.Invitation;
import cz.profinit.sportTeamManager.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for event business logic.
 */
@Controller
public class EventController {

    @Autowired
    EventService eventService;

    /**
     * Returns Event found by id or HttpStatus.NOT_FOUND (404)
     * @param eventId ID of wanted event
     * @return EventDto of desired event
     */
    @GetMapping (value = "/event/{eventId}")
    public @ResponseBody EventDto findEventById(@PathVariable Long eventId) {
        Event event = null;
        try {
            event = eventService.findEventById(eventId);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
             notFound();
        }
        return EventMapper.toDto(event);
    }

    /**
     * Basic implementation of ExceptionHandler
     */
    @ResponseStatus(value= HttpStatus.NOT_FOUND,
            reason="Event entity not found!")
    @ExceptionHandler(EntityNotFoundException.class)
    public void notFound() {
    }

    /**
     * Post that creates new event in database
     * @param eventDto from which is new Event created
     */
    @PostMapping (value = "/event")
    @ResponseStatus (HttpStatus.CREATED)
    public void createNewEvent (@RequestBody EventDto eventDto){
        eventService.createNewEvent(eventDto);
    }

    /**
     * Post for updating event which is already in database. Returns NotFound (404) if event is not found
     * @param eventId ID of wanted event
     * @param eventDto from which event will be updated
     */
    @PostMapping(value = "/event/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateEvent (@PathVariable Long eventId, @RequestBody EventDto eventDto){
        try {
            eventService.updateEvent(eventDto,eventId);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            notFound();
        }
    }

    /**
     * Changes event status.
     * @param eventId ID of event which status is going to be changed
     */
    @PostMapping(value = "/event/{eventId}/status")
    @ResponseStatus(HttpStatus.OK)
    public void changeEventStatus (@PathVariable Long eventId){
        try {
            eventService.changeEventStatus(eventId);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            notFound();
        }
    }

    /**
     * Post new message to the event
     * @param eventId ID of event where message should be send
     * @param message which is going to be stored
     */
    @PostMapping(value = "/event/{eventId}/message/{message}")
    @ResponseStatus(HttpStatus.OK)
    public void addNewMessage (@PathVariable Long eventId, @PathVariable String message){
        try {
            //TODO dat tam current usera
            eventService.addNewMessage(null,message,eventId);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            notFound();
        }
    }

    /**
     *
     * @param eventId
     * @return
     */
    @GetMapping(value = "/event/{eventId}/messages")
    public @ResponseBody List<MessageDto> getAllMessages(@PathVariable Long eventId){
        try {
            return eventService.getAllMessages(eventId);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            notFound();
        }
        return null;
    }

    @PostMapping(value = "/event/{eventId}/invitation/{userId}")
    @ResponseStatus (HttpStatus.OK)
    public void addNewInvitation(@PathVariable Long eventId){
        try {
            //TODO vyresit usera!!
            eventService.addNewInvitation(eventId,null);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            notFound();
        }
    }

    /**
     * Returns List of invitations for selected Event.
     * @param eventId ID of selected Event
     * @return List of invitations.
     */
    @GetMapping (value = "/event/{eventId}/invitations")
    public @ResponseBody List<Invitation> getAllInvitations (@PathVariable Long eventId){
        try {
            eventService.getAllInvitations(eventId);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            notFound();
        }
        return null;
    }

}
