/*
 * InvitationServiceImplTest
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.sportTeamManager.service.invitation;

import cz.profinit.sportTeamManager.configuration.StubRepositoryConfiguration;
import cz.profinit.sportTeamManager.dto.invitation.InvitationDto;
import cz.profinit.sportTeamManager.exception.EntityAlreadyExistsException;
import cz.profinit.sportTeamManager.exception.EntityNotFoundException;
import cz.profinit.sportTeamManager.exception.NonValidUriException;
import cz.profinit.sportTeamManager.exception.UserIsAlreadyInEventException;
import cz.profinit.sportTeamManager.mapper.InvitationMapper;
import cz.profinit.sportTeamManager.model.event.Event;
import cz.profinit.sportTeamManager.model.invitation.Invitation;
import cz.profinit.sportTeamManager.model.invitation.StatusEnum;
import cz.profinit.sportTeamManager.model.user.Guest;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.repository.invitation.InvitationRepository;
import cz.profinit.sportTeamManager.service.event.EventServiceImpl;
import cz.profinit.sportTeamManager.service.user.UserService;
import cz.profinit.sportTeamManager.service.user.UserServiceImpl;
import cz.profinit.sportTeamManager.repository.event.EventRepositoryStub;
import cz.profinit.sportTeamManager.repository.user.UserRepositoryStub;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
@ContextConfiguration(classes = StubRepositoryConfiguration.class)
@ActiveProfiles({"stub_repository"})
public class InvitationServiceImplTest {

    private InvitationService invitationService;
    private EventServiceImpl eventService;
    private RegisteredUser loggedUser;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private InvitationRepository invitationRepository;

    /**
     * Initialization of services and repositories used in tests
     */
    @Before
    public void setUp() {
        UserService userService = new UserServiceImpl(passwordEncoder, new UserRepositoryStub());
        eventService = new EventServiceImpl(new EventRepositoryStub(), userService);
        invitationService = new InvitationServiceImpl(invitationRepository,eventService, userService);
        loggedUser = new RegisteredUser("Ivan", "Stastny", "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2", "is@gmail.com", RoleEnum.USER);
    }

    /**
     * Testing creation of a new invitation. Positive ending
     */
    @Test
    public void createNewInvitationCreatesNewInvitation() throws EntityNotFoundException, UserIsAlreadyInEventException, EntityAlreadyExistsException {
        Invitation invitation = invitationService.createNewInvitation(loggedUser.getEmail(),0L);

        Assert.assertEquals(invitation.getStatus(),StatusEnum.PENDING);
        Event event = eventService.findEventById(0L);
        Assert.assertEquals(event.getInvitationList().get(0).getStatus(),StatusEnum.PENDING);
        Assert.assertEquals(event.getInvitationList().get(0).getRecipient(),loggedUser);
    }

    /**
     * Testing delete of invitation. Positive ending
     */
    @Test(expected = Test.None.class)
    public void deleteInvitationDeletesInvitation() throws EntityNotFoundException {
        invitationService.deleteInvitation(loggedUser.getEmail(), 0L);
    }

    /**
     * Testing status changing Positive ending.
     * @throws EntityNotFoundException throws if entity is not found
     */
    @Test
    public void changeInvitationStatusChangesInvitationStatus() throws EntityNotFoundException {
        invitationService.changeInvitationStatus(0L,"is@gmail.com",StatusEnum.ACCEPTED);

        Assert.assertEquals(invitationRepository.findInvitationByEventIdAndUserEmail(0L,"is@gmail.com").getStatus(),StatusEnum.ACCEPTED);
    }

    /**
     * Testing createNewInvitationsFromList. Positive ending.
     * @throws EntityNotFoundException throws if entity is not found
     * @throws UserIsAlreadyInEventException thrown when user is already invited
     */
    @Test
    public void createNewInvitationsFromListCreatesNewInvitations() throws EntityNotFoundException, UserIsAlreadyInEventException,
            EntityAlreadyExistsException {
        List<RegisteredUser> users = new ArrayList<>();
        users.add(new RegisteredUser("Ivan", "Stastny", "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2", "is@gmail.com", RoleEnum.USER));
        users.add(new RegisteredUser("Jirka", "Vesely", "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2", "is@email.cz", RoleEnum.USER));

        List<Invitation> invitationList = invitationService.createNewInvitationsFromList(users, 0L);

        Assert.assertEquals(users.get(0),invitationList.get(0).getRecipient());
        Assert.assertEquals(users.get(1),invitationList.get(1).getRecipient());
    }

    /**
     * Testing createNewInvitationsFromList with same user throws UserIsAlreadyInEventException
     * @throws EntityNotFoundException throws if entity is not found
     * @throws UserIsAlreadyInEventException thrown when user is already invited
     */
    @Test (expected = UserIsAlreadyInEventException.class)
    public void createNewInvitationsFromListThrowsUserIsAlreadyInEventException()
            throws EntityNotFoundException, UserIsAlreadyInEventException, EntityAlreadyExistsException {
        List<RegisteredUser> users = new ArrayList<>();
        users.add(new RegisteredUser("Ivan", "Stastny", "pass", "is@gmail.com", RoleEnum.USER));
        users.add(new RegisteredUser("Tomas", "Smutny", "pass2", "ts@gmail.com", RoleEnum.USER));

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
        Assert.assertThrows(EntityNotFoundException.class ,() -> invitationService.createNewInvitationsFromList(users, 1L));
    }

    /**
     * Testing sorting and filtering method for Invitation list. Positive ending.
     */
    @Test
    public void returnSortedListReturnsSortedList(){
        List<Invitation> invitationList = new ArrayList<>();
        invitationList.add(new Invitation(LocalDateTime.now(), LocalDateTime.of(2021,12,6,14,10), StatusEnum.PENDING, loggedUser, 0L));
        invitationList.add(new Invitation(LocalDateTime.now(), LocalDateTime.of(2021,12,5,13,12), StatusEnum.ACCEPTED, loggedUser, 0L));
        invitationList.add(new Invitation(LocalDateTime.now(), LocalDateTime.of(2021,12,7,13,9), StatusEnum.ACCEPTED, loggedUser, 0L));
        invitationList.add(new Invitation(LocalDateTime.now(), LocalDateTime.of(2021,12,6,13,16), StatusEnum.ACCEPTED, loggedUser, 0L));

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
        invitationRepository.findInvitationById(1L);
    }

    /**
     * Testing creation of a new exception with user who was already invited throws exception.
     * @throws EntityNotFoundException Throws when entity is not found.
     * @throws UserIsAlreadyInEventException Throws when user is already invited to the event.
     */
    @Test (expected = UserIsAlreadyInEventException.class)
    public void invitingUserWhichIsAlreadyInvitedThrowsUserIsAlreadyInEventException()
            throws EntityNotFoundException, UserIsAlreadyInEventException, EntityAlreadyExistsException {
        invitationService.createNewInvitation("ts@gmail.com",0L);
    }

    /**
     * Testing changeInvitationStatus for non-existent invitation.
     * @throws EntityNotFoundException throws if entity is not found
     */
    @Test (expected = EntityNotFoundException.class)
    public void changeInvitationStatusOfNonExistingInvitationThrowsEntityNotFoundException() throws EntityNotFoundException {
        Invitation invitation = invitationRepository.findInvitationById(1L);
       invitationService.changeInvitationStatus(0L,"is@seznam.cz",StatusEnum.ACCEPTED);
    }

    /**
     * Testing deletion of Invitation. Invitation is not present. EntityNotFoundException expected
     */
    @Test
    public void deleteInvitationThrowsEntityNotFoundForNonExistingInvitation() {
        Exception exception = Assert.assertThrows(EntityNotFoundException.class,() -> invitationService.deleteInvitation("xx@email.cz", 1L));
        Assert.assertEquals(new EntityNotFoundException("Invitation").getMessage(), exception.getMessage());
    }
    /**
     * Testing deletion of Invitation. User is not present. EntityNotFoundException expected
     */
    @Test
    public void deleteInvitationThrowsEntityNotFoundForNonExistingUser() {
        Exception exception = Assert.assertThrows(EntityNotFoundException.class,() -> invitationService.deleteInvitation("xx@email.cz", 0L));
        Assert.assertEquals(new EntityNotFoundException("RegisteredUser").getMessage(), exception.getMessage());
    }
    /**
     * Testing deletion of Invitation. Event is not present. EntityNotFoundException expected
     */
    @Test
    public void deleteInvitationThrowsEntityNotFoundForNonExistingEvent() {
        Exception exception = Assert.assertThrows(EntityNotFoundException.class,() -> invitationService.deleteInvitation("is@gmail.com", 1L));
        Assert.assertEquals(new EntityNotFoundException("Event").getMessage(), exception.getMessage());
    }

    /**
     * Testing creation of Guest Invitation with existent event. Generated correct URI expected.
     *
     * @throws EntityNotFoundException if Event entity is not found
     */
    @Test
    public void createGuestInvitationCreatesNewGuestInvitation() throws EntityNotFoundException, EntityAlreadyExistsException {
        Invitation invitation = invitationService.createGuestInvitation(0L,"Karel");

        Assert.assertEquals("mxPR4fbWzvai60UMLhD3aw==", ((Guest) invitation.getRecipient()).getUri());
        Assert.assertEquals(RoleEnum.GUEST, ((Guest) invitation.getRecipient()).getRole());
        Assert.assertEquals(StatusEnum.PENDING, invitation.getStatus());
    }

    /**
     * Testing creation of Invitation with non-existent event. EntityNotFoundException expected.
     */
    @Test
    public void createGuestInvitationThrowsEntityNotFoundExceptionForNonExistentEvent() {
        try {
            Invitation invitation = invitationService.createGuestInvitation(1L,"Karel");
        } catch (EntityNotFoundException | EntityAlreadyExistsException e) {
            Assert.assertEquals("Event entity not found!",e.getMessage());
        }
    }

    /**
     * Testing getGuestInvitation will return correct guest invitation for existing event and guest.
     *
     * @throws EntityNotFoundException thrown when Entity is not found
     * @throws NonValidUriException thrown when URI is not valid
     */
    @Test
    public void getGuestInvitationGetsInvitation() throws EntityNotFoundException, NonValidUriException {
        Invitation invitation = invitationService.getGuestInvitation("mxPR4fbWzvai60UMLhD3aw==");

        Assert.assertEquals("mxPR4fbWzvai60UMLhD3aw==", ((Guest) invitation.getRecipient()).getUri());
        Assert.assertEquals("Karel",invitation.getRecipient().getName());
        Assert.assertEquals(RoleEnum.GUEST, ((Guest) invitation.getRecipient()).getRole());
        Assert.assertEquals(StatusEnum.PENDING, invitation.getStatus());
    }

    /**
     * Testing getGuestInvitation for invalid URI. NonValidUriException expected
     *
     * @throws EntityNotFoundException thrown when Entity is not found
     */
    @Test
    public void getGuestInvitationThrowsNonValidUriExceptionForInvalidUri() throws EntityNotFoundException {
        try {
            Invitation invitation = invitationService.getGuestInvitation("mxPF4fbWzvai60UMLhD3aw==");
        } catch (NonValidUriException e) {
            Assert.assertEquals("URI is not valid!",e.getMessage());
        }
    }

    /**
     * ChangeStatusInvitation changes status invitation for correct URI and existing invitation
     *
     * @throws NonValidUriException thrown when URI is not valid
     * @throws EntityNotFoundException thrown when Entity is not found
     */
    @Test
    public void changeGuestsInvitationStatusChangesInvitationStatus() throws NonValidUriException, EntityNotFoundException {
        Invitation invitation = invitationService.changeGuestInvitation("mxPR4fbWzvai60UMLhD3aw==",StatusEnum.ACCEPTED);

        Assert.assertEquals("mxPR4fbWzvai60UMLhD3aw==", ((Guest) invitation.getRecipient()).getUri());
        Assert.assertEquals("Karel", invitation.getRecipient().getName());
        Assert.assertEquals(RoleEnum.GUEST, ((Guest) invitation.getRecipient()).getRole());
        Assert.assertEquals(StatusEnum.ACCEPTED, invitation.getStatus());
    }

    /**
     * Testing changeGuestInvitationStatus throws NonValidUriException for non-valid URI
     *
     * @throws EntityNotFoundException thrown when Entity is not found
     */
    @Test
    public void changeStatusThrowsNonValidUriExceptionForNonValidUri() throws EntityNotFoundException {
        try {
            Invitation invitation = invitationService.changeGuestInvitation("mxPF4fbWzvai60UMLhD3aw==",StatusEnum.ACCEPTED);
        } catch (NonValidUriException e) {
            Assert.assertEquals("URI is not valid!",e.getMessage());
        }

    }

    /**
     * Testing changeGuestInvitationStatus throws Entity not found exception for non-existent event
     *
     * @throws NonValidUriException thrown when URI is not valid
     */
    @Test
    public void changeStatusThrowsEntityNotFoundExceptionForNonExistentEvent() throws NonValidUriException {
        try {
            Invitation invitation = invitationService.changeGuestInvitation("LIGPFGwdE3HIk4YGdc/9Dg==",StatusEnum.ACCEPTED);
        } catch (EntityNotFoundException e) {
            Assert.assertEquals("Event entity not found!",e.getMessage());
        }
    }
}
