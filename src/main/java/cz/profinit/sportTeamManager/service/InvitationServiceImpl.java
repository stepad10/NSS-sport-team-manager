package cz.profinit.sportTeamManager.service;

import cz.profinit.sportTeamManager.model.event.Event;
import cz.profinit.sportTeamManager.model.invitation.Invitation;
import cz.profinit.sportTeamManager.model.invitation.StatusEnum;
import cz.profinit.sportTeamManager.model.user.User;
import cz.profinit.sportTeamManager.repositories.InvitationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of Invitation service interface. Includes all business logic related to invitations.
 */
@Service
@AllArgsConstructor
public class InvitationServiceImpl {

    InvitationRepository invitationRepository;
    EventService eventService;

    /**
     * Creates now invitation and saves it to repository.
     * @param user user who gets invitation
     * @param event for which is new invitation is created
     * @return created invitation
     */
    public Invitation createNewInvitation(User user, Event event){
        Invitation invitation = invitationRepository.createNewInvitation(new Invitation(LocalDateTime.now(),LocalDateTime.now(), StatusEnum.PENDING,user));
        eventService.addNewInvitation(event, invitation);
        return invitation;
    }

    /**
     * Changes status of invitation
     * @param invitation which status is going to be changed
     * @param status to which the invitation is going to be changed
     * @return updated invitation
     */
    public Invitation changeInvitationStatus(Invitation invitation, StatusEnum status){
        invitation.setStatus(status);
        invitation.setChanged(LocalDateTime.now());
        return invitationRepository.updateInvitation(invitation);
    }

    /**
     * Creates invitations from user list
     * @param userList Users who get invitation
     * @param event to which they will be invited
     * @return List of created invitations
     */
    public List<Invitation> createNewInvitationsFromList (List<User> userList, Event event){
        List<Invitation> invitationList = new ArrayList<>();
        for(User user: userList){
            invitationList.add(createNewInvitation(user,event));
        }
        return invitationList;
    }

    /**
     * Sort invitation list according to "changed" date from oldest to newest
     * @param invitationList List that is going to be sorted
     * @return new sorted list
     */
    public List<Invitation> getOrderedListOfInvitationsByDate (List<Invitation> invitationList){
        List<Invitation> result = new ArrayList();
        for(Invitation invitation : invitationList){
            result.add(invitation);
        }
        Collections.sort(result);
        return invitationList;
    }

    /**
     * Filter invitation list by status and sort it according to "changed" date from oldest to newest
     * @param invitationList List that is going to be sorted
     * @param status according to which list will be filtered
     * @return Sorted and filtered list
     */
    public List<Invitation> getOrderedListOfInvitationByDateForSpecificStatus (List<Invitation> invitationList, StatusEnum status){
        List<Invitation> result = new ArrayList<>();
        for (Invitation invitation : invitationList){
            if(invitation.getStatus() == status){
                result.add(invitation);
            }
        }
        Collections.sort(result);
        return result;
    }
}
