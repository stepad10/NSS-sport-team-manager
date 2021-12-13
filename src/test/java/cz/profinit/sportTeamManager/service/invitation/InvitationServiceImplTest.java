/*
 * InvitationServiceImplTest
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.sportTeamManager.service.invitation;

import cz.profinit.sportTeamManager.configuration.ApplicationConfigurationTest;
import cz.profinit.sportTeamManager.dto.InvitationDto;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.exceptions.UserIsAlreadyInEventException;
import cz.profinit.sportTeamManager.mappers.EventMapper;
import cz.profinit.sportTeamManager.mappers.InvitationMapper;
import cz.profinit.sportTeamManager.model.event.Event;
import cz.profinit.sportTeamManager.model.invitation.Invitation;
import cz.profinit.sportTeamManager.model.invitation.StatusEnum;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.repositories.InvitationRepository;
import cz.profinit.sportTeamManager.service.EventServiceImpl;
import cz.profinit.sportTeamManager.service.InvitationService;
import cz.profinit.sportTeamManager.service.InvitationServiceImpl;
import cz.profinit.sportTeamManager.service.user.UserService;
import cz.profinit.sportTeamManager.service.user.UserServiceImpl;
import cz.profinit.sportTeamManager.stubs.stubRepositories.TODOMERGEStubUserRepository;
import cz.profinit.sportTeamManager.stubs.stubRepositories.StubEventRepository;
import cz.profinit.sportTeamManager.stubs.stubRepositories.StubInvitationRepository;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Tests testing Invitation business logic
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationConfigurationTest.class)
@ActiveProfiles({"stub","stub_event_testing"})
public class InvitationServiceImplTest {

    private InvitationService invitationService;
    private InvitationRepository invitationRepository;
    private EventServiceImpl eventService;
    private UserService userService;
    private RegisteredUser loggedUser;
    @Autowired
    private ApplicationContext context;

    /**
     * Initialization of services and repositories used in tests
     */
    @Before
    public void setUp() {
        userService = new UserServiceImpl(context.getBean(PasswordEncoder.class), new TODOMERGEStubUserRepository());
        eventService = new EventServiceImpl(new StubEventRepository(), new EventMapper(),userService);
        invitationRepository = new StubInvitationRepository();
        invitationService = new InvitationServiceImpl(invitationRepository,eventService,userService);
        loggedUser = new RegisteredUser("Ivan", "Stastny", "pass", "is@gmail.com", RoleEnum.USER);
    }

    /**
     * Testing creation of a new invitation. Positive ending
     */
    @Test
    public void createNewInvitationCreatesNewInvitation() throws EntityNotFoundException, UserIsAlreadyInEventException {
        Invitation invitation = invitationService.createNewInvitation(loggedUser.getEmail(),0L);

        Assert.assertEquals(invitation.getStatus(),StatusEnum.PENDING);
        Event event = eventService.findEventById(0L);
        Assert.assertEquals(event.getListOfInvitation().get(0).getStatus(),StatusEnum.PENDING);
        Assert.assertEquals(event.getListOfInvitation().get(0).getIsFor(),loggedUser);
    }

    /**
     * Testing delete of invitation. Positive ending
     */
    @Test
    public void deleteInvitationDeletesInvitation() throws EntityNotFoundException {
        Assert.assertEquals(true, invitationService.deleteInvitation(loggedUser.getEmail(),0L));
    }

    /**
     * Testing status changing Positive ending.
     * @throws EntityNotFoundException throws if entity is not found
     */
    @Test
    public void changeInvitationStatusChangesInvitationStatus() throws EntityNotFoundException {
        Invitation invitation = invitationRepository.getInvitationById(0L);
        invitationService.changeInvitationStatus(invitation,StatusEnum.ACCEPTED);

        Assert.assertEquals(invitationRepository.getInvitationById(0L).getStatus(),StatusEnum.ACCEPTED);
    }


    /**
     * Testing createNewInvitationsFromList. Positive ending.
     * @throws EntityNotFoundException throws if entity is not found
     * @throws UserIsAlreadyInEventException thrown when user is already invited
     */
    @Test
    public void createNewInvitationsFromListCreatesNewInvitations() throws EntityNotFoundException, UserIsAlreadyInEventException {
        List<RegisteredUser> users = new ArrayList<>();
        users.add(new RegisteredUser("Ivan", "Stastny", "pass", "is@gmail.com", RoleEnum.USER));
        users.add(new RegisteredUser("Jirka", "Vesely", "pass", "is@email.cz", RoleEnum.USER));

        List<Invitation> invitationList = invitationService.createNewInvitationsFromList(users, 0L);

        Assert.assertEquals(users.get(0),invitationList.get(0).getIsFor());
        Assert.assertEquals(users.get(1),invitationList.get(1).getIsFor());
    }

    /**
     * Testing createNewInvitationsFromList with same user throws UserIsAlreadyInEventException
     * @throws EntityNotFoundException throws if entity is not found
     * @throws UserIsAlreadyInEventException thrown when user is already invited
     */
    @Test (expected = UserIsAlreadyInEventException.class)
    public void createNewInvitationsFromListThrowsUserIsAlreadyInEventException() throws EntityNotFoundException, UserIsAlreadyInEventException {
        List<RegisteredUser> users = new ArrayList<>();
        users.add(new RegisteredUser("Ivan", "Stastny", "pass", "is@gmail.com", RoleEnum.USER));
        users.add(new RegisteredUser("Pavel", "Smutny", "pass", "is@seznam.cz", RoleEnum.USER));

        List<Invitation> invitationList = invitationService.createNewInvitationsFromList(users, 0L);
    }

    /**
     * Testing createNewInvitationsFromList inviting for non-existing event throws EntityNotFound Exception
     * @throws UserIsAlreadyInEventException thrown when user is already invited
     */
    @Test
    public void createNewInvitationsFromListThrowsEntityNotFoundException() throws  UserIsAlreadyInEventException {
        List<RegisteredUser> users = new ArrayList<>();
        users.add(new RegisteredUser("Ivan", "Stastny", "pass", "is@gmail.com", RoleEnum.USER));
        users.add(new RegisteredUser("Pavel", "Smutny", "pass", "is@seznam.cz", RoleEnum.USER));

        try {
            List<Invitation> invitationList = invitationService.createNewInvitationsFromList(users, 1L);
        } catch (EntityNotFoundException e){
            Assert.assertEquals("Event entity not found!",e.getMessage());
        }
    }

    /**
     * Testing sorting and filtering method for Invitation list. Positive ending.
     */
    @Test
    public void returnSortedListReturnsSortedList(){
        List<Invitation> invitationList = new ArrayList<>();
        invitationList.add(new Invitation(LocalDateTime.now(),LocalDateTime.of(2021,12,6,14,10), StatusEnum.PENDING,loggedUser));
        invitationList.add(new Invitation(LocalDateTime.now(),LocalDateTime.of(2021,12,5,13,12), StatusEnum.ACCEPTED,loggedUser));
        invitationList.add(new Invitation(LocalDateTime.now(),LocalDateTime.of(2021,12,7,13,9), StatusEnum.ACCEPTED,loggedUser));
        invitationList.add(new Invitation(LocalDateTime.now(),LocalDateTime.of(2021,12,6,13,16), StatusEnum.ACCEPTED,loggedUser));

        List<InvitationDto> result = new ArrayList<>();

        for(Invitation invitation: invitationList){
            result.add(InvitationMapper.toDto(invitation));
        }

        result = invitationService.OrderListOfInvitationByDateForSpecificStatus(result,StatusEnum.ACCEPTED);

        Assert.assertEquals(invitationList.get(1).getChanged(),result.get(0).getChanged());
        Assert.assertEquals(invitationList.get(3).getChanged(),result.get(1).getChanged());
        Assert.assertEquals(invitationList.get(2).getChanged(),result.get(2).getChanged());
    }

    /**
     * Testing getting non-existing entity. Exception expected
     * @throws EntityNotFoundException Throws when entity is not found.
     */
    @Test (expected = EntityNotFoundException.class)
    public void gettingNonExistingInvitationThrowsEntityNotFoundException() throws EntityNotFoundException {
        invitationRepository.getInvitationById(1L);
    }

    /**
     * Testing creation of a new exception with user who was already invited throws exception.
     * @throws EntityNotFoundException Throws when entity is not found.
     * @throws UserIsAlreadyInEventException Throws when user is already invited to the event.
     */
    @Test (expected = UserIsAlreadyInEventException.class)
    public void invitingUserWhichIsAlreadyInvitedThrowsUserIsAlreadyInEventException() throws EntityNotFoundException, UserIsAlreadyInEventException {
        invitationService.createNewInvitation("is@seznam.cz",0L);
    }

    /**
     * Testing changeInvitationStatus for non-existent invitation.
     * @throws EntityNotFoundException throws if entity is not found
     */
    @Test (expected = EntityNotFoundException.class)
    public void changeInvitationStatusOfNonExistingInvitationThrowsEntityNotFoundException() throws EntityNotFoundException {
        Invitation invitation = invitationRepository.getInvitationById(1L);
       invitationService.changeInvitationStatus(invitation,StatusEnum.ACCEPTED);
    }

    /**
     * Testing deletion of Invitation. Invitation is not present. EntityNotFoundException expected
     */
    @Test
    public void deleteInvitationThrowsEntityNotFoundForNonExistingInvitation() {
        try {
            invitationService.deleteInvitation("is@email.cz", 0L);
        } catch (EntityNotFoundException e){
            Assert.assertEquals("Invitation entity not found!",e.getMessage());
        }
    }
    /**
     * Testing deletion of Invitation. User is not present. EntityNotFoundException expected
     */
    @Test
    public void deleteInvitationThrowsEntityNotFoundForNonExistingUser() {
        try {
            invitationService.deleteInvitation("is@emil.cz", 0L);
        } catch (EntityNotFoundException e){
            Assert.assertEquals("User entity not found!",e.getMessage());
        }
    }
    /**
     * Testing deletion of Invitation. Event is not present. EntityNotFoundException expected
     *
     */
    @Test
    public void deleteInvitationThrowsEntityNotFoundForNonExistingEvent() {
        try {
            invitationService.deleteInvitation("is@email.cz", 1L);
        } catch (EntityNotFoundException e){
            Assert.assertEquals("Event entity not found!",e.getMessage());
        }
    }
}
