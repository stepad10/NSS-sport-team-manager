/*
 * InvitationServiceImpl
 *
 * 0.1
 *
 * Author: M. Halamka
 */


package cz.profinit.sportTeamManager.service;

import cz.profinit.sportTeamManager.dto.InvitationDto;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.exceptions.UserIsAlreadyInEventException;
import cz.profinit.sportTeamManager.mappers.InvitationMapper;
import cz.profinit.sportTeamManager.model.event.Event;
import cz.profinit.sportTeamManager.model.invitation.Invitation;
import cz.profinit.sportTeamManager.model.invitation.StatusEnum;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.User;
import cz.profinit.sportTeamManager.repositories.InvitationRepository;
import cz.profinit.sportTeamManager.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
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
@Profile("Main")
public class InvitationServiceImpl implements InvitationService{

    InvitationRepository invitationRepository;
    EventService eventService;
    UserService userService;

    /**
     * Creates now invitation and saves it to repository.
     * @param email email of user who gets invitation
     * @param eventId ID of event for which is new invitation is created
     * @return created invitation
     */
    public Invitation createNewInvitation(String email, Long eventId) throws EntityNotFoundException, UserIsAlreadyInEventException {
        Event event = eventService.findEventById(eventId);
        User user = userService.findUserByEmail(email);
        if (!invitationRepository.isUserPresent(user, event)) {
        Invitation invitation = invitationRepository.createNewInvitation(new Invitation(LocalDateTime.now(),LocalDateTime.now(), StatusEnum.PENDING,user));
        eventService.addNewInvitation(eventId, invitation);
        return invitation;
        } else {
            throw new UserIsAlreadyInEventException("User is already present!!");
        }
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
     * @param eventId ID of event to which they will be invited
     * @return List of created invitations
     */
    public List<Invitation> createNewInvitationsFromList (List<RegisteredUser> userList, Long eventId) throws EntityNotFoundException, UserIsAlreadyInEventException {
        List<Invitation> invitationList = new ArrayList<>();
        for(RegisteredUser user: userList){
            invitationList.add(createNewInvitation(user.getEmail(),eventId));
        }
        return invitationList;
    }

    /**
     * Methods deletes user invitation for selected event.
     * @param email emaiů of user whose invitation will be deleted
     * @param eventId ID of event on which invitation will be deleted
     */
    public boolean deleteInvitation (String email, Long eventId) throws EntityNotFoundException {
        User user = userService.findUserByEmail(email);
        Event event = eventService.findEventById(eventId);

        return invitationRepository.deleteInvitation(user,event);
    }

    /**
     * Filter invitation list by status and sort it according to "changed" date from oldest to newest
     * @param invitationDtoList List that is going to be sorted
     * @param status according to which list will be filtered
     * @return Sorted and filtered list
     */
    public List<InvitationDto> OrderListOfInvitationByDateForSpecificStatus (List<InvitationDto> invitationDtoList, StatusEnum status){
        List<InvitationDto> result = new ArrayList<>();
        for (InvitationDto invitationDto : invitationDtoList){
            if(invitationDto.getStatus() == status){
                result.add(invitationDto);
            }
        }
        Collections.sort(result);
        return result;
    }
}
