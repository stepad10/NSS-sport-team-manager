/*
 * EventServiceImplTest
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.sportTeamManager.service;

import cz.profinit.sportTeamManager.configuration.ApplicationConfiguration;
import cz.profinit.sportTeamManager.dto.EventDto;
import cz.profinit.sportTeamManager.dto.MessageDto;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.mappers.EventMapper;
import cz.profinit.sportTeamManager.model.event.Event;
import cz.profinit.sportTeamManager.model.event.Message;
import cz.profinit.sportTeamManager.model.event.Place;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.model.user.User;
import cz.profinit.sportTeamManager.repositories.EventRepository;
import cz.profinit.sportTeamManager.stubRepositories.StubEventRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Tests testing Event business logic
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
public class EventServiceImplTest {

    private EventServiceImpl eventService;
    private EventRepository eventRepository;
    private User loggedUser;
    private Place place;

    /**
     * Initialization of services and repositories used in tests
     */
    @Before
    public void setUp() {
        eventRepository = new StubEventRepository();
        eventService = new EventServiceImpl(eventRepository, new EventMapper());
        loggedUser = new RegisteredUser("Ivan", "Stastny", "pass", "is@gmail.com", RoleEnum.USER);
        place = new Place("Profinit","Tychonova 2");
    }

    /**
     * Testing creation of a new Event. Positive ending.
     */
    @Test
    public void createNewEventCreatesNewEvent(){
        EventDto eventDto = new EventDto(LocalDateTime.now(),place,6,loggedUser,false);
        Event event = eventService.createNewEvent(eventDto);
        Assert.assertEquals(eventDto.getDate(),event.getDate());
    }

    /**
     * Testing event update. Expected positive ending.
     * @throws EntityNotFoundException Thrown when Entity was not found.
     * @throws InterruptedException Thrown when thread is sleeping before or during activity.
     */
    @Test
    public void updateEventUpdatesEvent() throws EntityNotFoundException, InterruptedException {

        TimeUnit.MILLISECONDS.sleep(2); //Had to put it here, because event and eventDto can be created in exact same time.
        EventDto eventDtoUpdated = new EventDto(LocalDateTime.now(),place,6,loggedUser,false);
        Assert.assertNotEquals(eventRepository.findEventById(0L).getDate(),eventDtoUpdated.getDate());
        Event event = eventService.updateEvent(eventDtoUpdated, 0L);
        Assert.assertEquals(eventDtoUpdated.getDate(),event.getDate());
    }

    /**
     * Testing changeEventStatus will change event status.
     * @throws EntityNotFoundException thrown when Entity is not found.
     */
    @Test
    public void changeStatusChangesStatusOfEvent() throws EntityNotFoundException {
        Event canceledEvent = eventService.changeEventStatus(0L);

        Assert.assertTrue(canceledEvent.getIsCanceled());
    }

    /**
     * Testing adding of new messages to an event.
     * @throws EntityNotFoundException thrown when Entity is not found.
     */
    @Test
    public void addMessagesAddsMessageToEvent() throws EntityNotFoundException {
        eventService.addNewMessage(loggedUser,"Ahoj",0L);

        List<MessageDto> messages = eventService.getAllMessages(0L);

       Assert.assertEquals(messages.get(0).getMessage(),"Ahoj");
       Assert.assertEquals(messages.get(0).getUser(),loggedUser);

    }

    /**
     * Testing if update of non existing entity will throw appropriate exception.
     * @throws EntityNotFoundException thrown when Entity is not found.
     */
    @Test (expected = EntityNotFoundException.class)
    public void updateNonExistingEventThrowsEntityNotFound() throws EntityNotFoundException {
        EventDto eventDtoUpdated = new EventDto(LocalDateTime.now(),place,6,loggedUser,false);

        eventService.updateEvent(eventDtoUpdated, 1L);
    }

    /**
     * Testing changeEventStatus will throw exception when event is not found.
     * @throws EntityNotFoundException thrown when Entity is not found.
     */
    @Test (expected = EntityNotFoundException.class)
    public void changeStatusOfNonExistingEventThrowsEntityNotFoundException() throws EntityNotFoundException {
        eventService.changeEventStatus(1L);
    }


    /**
     * Testing if addMessage throws EntityNotFoundException when Event is not existing
     * @throws EntityNotFoundException thrown when Entity is not found
     */
    @Test (expected = EntityNotFoundException.class)
    public void AddsMessageToNonExistingEventThrowsEntityNotFoundException() throws EntityNotFoundException {
        eventService.addNewMessage(loggedUser,"Ahoj",1L);
    }

}
