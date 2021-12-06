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

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
public class EventServiceImplTest {

    private EventServiceImpl eventService;
    private EventRepository eventRepository;
    private User loggedUser;

    @Before
    public void setUp() throws Exception {
        eventRepository = new StubEventRepository();
        eventService = new EventServiceImpl(eventRepository, new EventMapper());
        loggedUser = new RegisteredUser("Ivan", "Stastny", "pass", "is@gmail.com", RoleEnum.USER);
    }

    @Test
    public void createNewEventCreatesNewEvent(){
        Place place = new Place("Profinit","Tychonova 2");
        EventDto eventDto = new EventDto(LocalDateTime.now(),place,6,loggedUser,false);
        Event event = eventService.createNewEvent(eventDto);
        Assert.assertEquals(eventDto.getDate(),event.getDate());
    }

    @Test
    public void updateEventUpdatesEvent() throws EntityNotFoundException {
        Place place = new Place("Profinit","Tychonova 2");

        EventDto eventDtoUpdated = new EventDto(LocalDateTime.now(),place,6,loggedUser,false);
        Assert.assertNotEquals(eventRepository.findEventById(0L).getDate(),eventDtoUpdated.getDate());
        Event event = eventService.updateEvent(eventDtoUpdated, eventService.findEventById(0L));
        Assert.assertEquals(eventDtoUpdated.getDate(),event.getDate());
    }

    @Test
    public void changeStatusChangesStatus() throws EntityNotFoundException {
        Place place = new Place("Profinit","Tychonova 2");
        EventDto eventDto = new EventDto(LocalDateTime.now(),place,6,loggedUser,false);
        Event event = eventService.createNewEvent(eventDto);
        event.setEntityId(0L);

        Event canceledEvent = eventService.changeEventStatus(event);

        Assert.assertTrue(canceledEvent.getIsCanceled());
    }

    @Test
    public void addMessagesAddsMessageToEvent() throws EntityNotFoundException {
        Place place = new Place("Profinit","Tychonova 2");
        EventDto eventDto = new EventDto(LocalDateTime.now(),place,6,loggedUser,false);
        Event event = eventService.createNewEvent(eventDto);
        event.setEntityId(0L);

        eventService.addNewMessage(loggedUser,"Ahoj",event);

        List<Message> messages = eventService.getAllMessages(event);

       Assert.assertEquals(messages.get(0).getMessage(),"Ahoj");
       Assert.assertEquals(messages.get(0).getUser(),loggedUser);

    }

    @Test (expected = EntityNotFoundException.class)
    public void updateNonExistingEventThrowsEntityNotFound() throws EntityNotFoundException {
        Place place = new Place("Profinit","Tychonova 2");
        EventDto eventDto = new EventDto(LocalDateTime.now(),place,6,loggedUser,false);
        Event event = eventService.createNewEvent(eventDto);
        event.setEntityId(0L);
        Assert.assertEquals(eventDto.getDate(),event.getDate());

        EventDto eventDtoUpdated = new EventDto(LocalDateTime.now(),place,6,loggedUser,false);

        event = eventService.updateEvent(eventDtoUpdated, eventService.findEventById(1L));
    }


}
