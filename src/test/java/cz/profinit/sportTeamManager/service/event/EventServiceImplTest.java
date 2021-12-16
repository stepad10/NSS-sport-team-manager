/*
 * EventServiceImplTest
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.sportTeamManager.service.event;

import cz.profinit.sportTeamManager.configuration.StubRepositoryConfiguration;
import cz.profinit.sportTeamManager.dto.EventDto;
import cz.profinit.sportTeamManager.dto.InvitationDto;
import cz.profinit.sportTeamManager.dto.MessageDto;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.mappers.EventMapper;
import cz.profinit.sportTeamManager.model.event.Event;
import cz.profinit.sportTeamManager.model.event.Place;
import cz.profinit.sportTeamManager.model.invitation.Invitation;
import cz.profinit.sportTeamManager.model.invitation.StatusEnum;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.model.user.User;
import cz.profinit.sportTeamManager.repositories.EventRepository;
import cz.profinit.sportTeamManager.service.EventServiceImpl;
import cz.profinit.sportTeamManager.service.user.UserService;
import cz.profinit.sportTeamManager.service.user.UserServiceImpl;
import cz.profinit.sportTeamManager.stubs.stubRepositories.StubEventRepository;
import cz.profinit.sportTeamManager.stubs.stubRepositories.StubUserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Tests testing Event business logic
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = StubRepositoryConfiguration.class)
@ActiveProfiles({"stub_repository"})
public class EventServiceImplTest {

    private EventServiceImpl eventService;
    private EventRepository eventRepository;
    private UserService userService;
    @Autowired
    private ApplicationContext context;

    private User loggedUser;
    private Place place;


    /**
     * Initialization of services and repositories used in tests
     */
    @Before
    public void setUp() {
        eventRepository = new StubEventRepository();
        userService = new UserServiceImpl(context.getBean(PasswordEncoder.class), new StubUserRepository());
        eventService = new EventServiceImpl(eventRepository, new EventMapper(),userService);
        loggedUser = new RegisteredUser("Ivan", "Stastny", "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2", "is@gmail.com", RoleEnum.USER);
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
     * Testing if update of non-existing entity will throw appropriate exception.
     * @throws EntityNotFoundException thrown when Entity is not found.
     */
    @Test (expected = EntityNotFoundException.class)
    public void updateNonExistingEventThrowsEntityNotFound() throws EntityNotFoundException {
        EventDto eventDtoUpdated = new EventDto(LocalDateTime.now(),place,6,loggedUser,false);
        eventService.updateEvent(eventDtoUpdated, 1L);
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
     * Testing changeEventStatus will throw exception when event is not found.
     * @throws EntityNotFoundException thrown when Entity is not found.
     */
    @Test (expected = EntityNotFoundException.class)
    public void changeStatusOfNonExistingEventThrowsEntityNotFoundException() throws EntityNotFoundException {
        eventService.changeEventStatus(1L);
    }

    /**
     * Testing adding of new messages to an event.
     * @throws EntityNotFoundException thrown when Entity is not found.
     */
    @Test
    public void addMessagesAddsMessageToEvent() throws EntityNotFoundException {
        eventService.addNewMessage("is@gmail.com","Ahoj",0L);

        Event event = eventRepository.findEventById(0L);

       Assert.assertEquals(event.getListOfMessages().get(1).getMessage(),"Ahoj");
       Assert.assertEquals(event.getListOfMessages().get(1).getUser(),loggedUser);

    }

    /**
     * Testing if addMessage throws EntityNotFoundException when Event is not existing
     * @throws EntityNotFoundException thrown when Entity is not found
     */
    @Test
    public void AddsMessageToNonExistingEventThrowsEntityNotFoundException() {
        try {
            eventService.addNewMessage("is@gmail.com","Ahoj",1L);
        } catch (EntityNotFoundException e){
            Assert.assertEquals("Event entity not found!",e.getMessage());
        }
    }

    /**
     * Testing if addMessage throws EntityNotFoundException when User is not existing
     * @throws EntityNotFoundException thrown when Entity is not found
     */
    @Test
    public void AddsMessageByNonExistingUserThrowsEntityNotFoundException() {
        try {
            eventService.addNewMessage("is@gmai.com","Ahoj",0L);
        } catch (EntityNotFoundException e){
            Assert.assertEquals("User entity not found!",e.getMessage());
        }
    }

    /**
     * Testing getMessage from event
     * @throws EntityNotFoundException thrown when Entity is not found.
     */
    @Test
    public void getMessagesGetsAllMessagesFromEvent() throws EntityNotFoundException {
        List<MessageDto> messages = eventService.getAllMessages(0L);

        Assert.assertEquals(messages.get(0).getMessage(),"Testuji");
        Assert.assertEquals(messages.get(0).getUser(),loggedUser);
    }

    /**
     * Testing getMessage from event from non-existing event
     * @throws EntityNotFoundException thrown when Entity is not existing.
     */
    @Test (expected = EntityNotFoundException.class)
    public void getMessagesFromNonExistingEventThrowsEntityNotFoundException() throws EntityNotFoundException {
        List<MessageDto> messages = eventService.getAllMessages(1L);
    }

    /**
     * Testing AddNewInvitation to event
     * @throws EntityNotFoundException thrown when Entity is not found.
     */
    @Test
    public void AddNewInvitationAddsNewInvitation() throws EntityNotFoundException {
        Invitation invitation = new Invitation(LocalDateTime.now(),LocalDateTime.now(), StatusEnum.PENDING,loggedUser);
        eventService.addNewInvitation(0L,invitation);
        Assert.assertEquals(invitation,eventRepository.findEventById(0L).getListOfInvitation().get(0));
    }

    /**
     * Testing AddNewInvitation to a non-existing event throws EntityNotFoundException
     * @throws EntityNotFoundException thrown when Entity is not found.
     */
    @Test (expected = EntityNotFoundException.class)
    public void AddNewInvitationToANonExistentEventThrowsEntityNotFoundException() throws EntityNotFoundException {
        Invitation invitation = new Invitation(LocalDateTime.now(),LocalDateTime.now(), StatusEnum.PENDING,loggedUser);
        eventService.addNewInvitation(1L,invitation);
    }

    /**
     * Testing getInvitation from event
     * @throws EntityNotFoundException thrown when Entity is not found.
     */
    @Test
    public void getInvitationsGetsInvitationFromGivenEvent() throws EntityNotFoundException {
        List<InvitationDto> invitations = eventService.getAllInvitations(0L);

        Assert.assertEquals(loggedUser,invitations.get(0).getIsFor());
        Assert.assertEquals(StatusEnum.PENDING,invitations.get(0).getStatus());
    }

    /**
     * Testing getInvitation from non-existent event throws EntityNotFoundException
     * @throws EntityNotFoundException thrown when Entity is not found.
     */
    @Test (expected = EntityNotFoundException.class)
    public void getInvitationsFromNonExistentEventThrowsEntityNotFoundException() throws EntityNotFoundException {
        List<InvitationDto> invitations = eventService.getAllInvitations(1L);
    }
}
