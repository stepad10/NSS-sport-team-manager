package cz.profinit.sportTeamManager.service;

import cz.profinit.sportTeamManager.configuration.ApplicationConfiguration;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.mappers.EventMapper;
import cz.profinit.sportTeamManager.model.event.Event;
import cz.profinit.sportTeamManager.model.event.Place;
import cz.profinit.sportTeamManager.model.invitation.Invitation;
import cz.profinit.sportTeamManager.model.invitation.StatusEnum;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.model.user.User;
import cz.profinit.sportTeamManager.repositories.InvitationRepository;
import cz.profinit.sportTeamManager.stubRepositories.StubEventRepository;
import cz.profinit.sportTeamManager.stubRepositories.StubInvitationRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
public class InvitationServiceImplTest {

    private InvitationServiceImpl invitationService;
    private InvitationRepository invitationRepository;
    private EventServiceImpl eventService;
    private User loggedUser;

    @Before
    public void setUp() throws Exception {
        eventService = new EventServiceImpl(new StubEventRepository(), new EventMapper());
        invitationRepository = new StubInvitationRepository();
        invitationService = new InvitationServiceImpl(invitationRepository,eventService);
        loggedUser = new RegisteredUser("Ivan", "Stastny", "pass", "is@gmail.com", RoleEnum.USER);
    }

    @Test
    public void createNewInvitationCreatesNewInvitation(){
        Place place = new Place("Profinit","Tychonova 2");
        Event event = new Event(LocalDateTime.now(),place,6,false,loggedUser, new ArrayList<>(), new ArrayList<>());
        event.setEntityId(0L);
        Invitation invitation = invitationService.createNewInvitation(loggedUser,event);

        Assert.assertEquals(invitation.getStatus(),StatusEnum.PENDING);
        Assert.assertEquals(event.getListOfInvitation().get(0).getStatus(),StatusEnum.PENDING);
        Assert.assertEquals(event.getListOfInvitation().get(0).getIsFor(),loggedUser);
    }

    @Test
    public void changeInvitationStatusChangesInvitationStatus() throws EntityNotFoundException {
        Invitation invitation = invitationRepository.getInvitationById(0L);
        invitationService.changeInvitationStatus(invitation,StatusEnum.ACCEPTED);

        Assert.assertEquals(invitationRepository.getInvitationById(0L).getStatus(),StatusEnum.ACCEPTED);
    }

    @Test
    public void returnSortedListReturnsSortedList(){
        List<Invitation> invitationList = new ArrayList<>();
        invitationList.add(new Invitation(LocalDateTime.now(),LocalDateTime.of(2021,12,6,14,10), StatusEnum.PENDING,loggedUser));
        invitationList.add(new Invitation(LocalDateTime.now(),LocalDateTime.of(2021,12,5,13,12), StatusEnum.ACCEPTED,loggedUser));
        invitationList.add(new Invitation(LocalDateTime.now(),LocalDateTime.of(2021,12,7,13,9), StatusEnum.ACCEPTED,loggedUser));
        invitationList.add(new Invitation(LocalDateTime.now(),LocalDateTime.of(2021,12,6,13,16), StatusEnum.ACCEPTED,loggedUser));

        List<Invitation> result = invitationService.getOrderedListOfInvitationByDateForSpecificStatus(invitationList,StatusEnum.ACCEPTED);

        Assert.assertEquals(result.get(0).getChanged(),invitationList.get(1).getChanged());
        Assert.assertEquals(result.get(1).getChanged(),invitationList.get(3).getChanged());
        Assert.assertEquals(result.get(2).getChanged(),invitationList.get(2).getChanged());
    }

    @Test (expected = EntityNotFoundException.class)
    public void gettingNonExistingInvitationThrowsEntityNotFoundException() throws EntityNotFoundException {
        Invitation invitation = invitationRepository.getInvitationById(1L);
    }
}
