/*
 * InvitationServiceImpl
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.sportTeamManager.service.invitation;

import cz.profinit.sportTeamManager.dto.invitation.InvitationDto;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.exceptions.UserIsAlreadyInEventException;
import cz.profinit.sportTeamManager.model.event.Event;
import cz.profinit.sportTeamManager.model.invitation.Invitation;
import cz.profinit.sportTeamManager.model.invitation.StatusEnum;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.User;
import cz.profinit.sportTeamManager.repositories.invitation.InvitationRepository;
import cz.profinit.sportTeamManager.service.event.EventService;
import cz.profinit.sportTeamManager.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    InvitationRepository invitationRepository;
    @Autowired
    EventService eventService;
    @Autowired
    UserService userService;

    /**
     * Creates now invitation and saves it to repository
     *
     * @param email email of user who gets invitation
     * @param eventId ID of event for which is new invitation is created
     * @return created invitation
     * @throws EntityNotFoundException if entity is not found.
     * @throws UserIsAlreadyInEventException user was already invited to an Event
     */
    public Invitation createNewInvitation(String email, Long eventId) throws EntityNotFoundException, UserIsAlreadyInEventException {
        Event event = eventService.findEventById(eventId);
        User user = userService.findUserByEmail(email);
        if (!invitationRepository.isUserPresent(user, event)) {
        Invitation invitation = invitationRepository.createNewInvitation(new Invitation(LocalDateTime.now(),LocalDateTime.now(), StatusEnum.PENDING,user));
        eventService.addNewInvitation(eventId, invitation);
        return invitation;
        } else {
            throw new UserIsAlreadyInEventException();
        }
    }

    /**
     * Changes StatusEnum of given invitation
     *
     * @param eventId ID of event
     * @param email email of user to which invitation belongs
     * @param status status into which will invitation be changed
     * @return Updated invitation
     * @throws EntityNotFoundException if entity is not found.
     */
    public Invitation changeInvitationStatus(Long eventId, String email, StatusEnum status) throws EntityNotFoundException {
        Invitation invitation = findInvitationByEventIdAndEmail(eventId,email);

        invitation.setStatus(status);
        invitation.setChanged(LocalDateTime.now());
        return invitationRepository.updateInvitation(invitation);
    }

    /**
     *  Finds Invitation for given user and from given event
     *
     * @param eventId ID of event
     * @param email email of user
     * @return Invitation for given user and from given event
     * @throws EntityNotFoundException if entity was not found.
     */
    public Invitation findInvitationByEventIdAndEmail(Long eventId,String email) throws EntityNotFoundException {
        Event event = eventService.findEventById(eventId);
        List <Invitation> invitationList = event.getListOfInvitation();
        User user = userService.findUserByEmail(email);

        for (Invitation invitation : invitationList){
            if (invitation.getIsFor().equals(user)){
                return invitation;
            }
        }
        throw new EntityNotFoundException("Invitation");
    }

    /**
     * Creates invitations from user list
     *
     * @param userList Users who get invitation
     * @param eventId ID of event to which they will be invited
     * @return List of created invitations
     * @throws EntityNotFoundException if entity was not found.
     */
    public List<Invitation> createNewInvitationsFromList (List<RegisteredUser> userList, Long eventId) throws EntityNotFoundException, UserIsAlreadyInEventException {
        List<Invitation> invitationList = new ArrayList<>();
        for(RegisteredUser user: userList){
            invitationList.add(createNewInvitation(user.getEmail(),eventId));
        }
        return invitationList;
    }

    /**
     * Methods deletes user invitation for selected event
     *
     * @param email email of user whose invitation will be deleted
     * @param eventId ID of event on which invitation will be deleted
     * @throws EntityNotFoundException if entity was not found.
     */
    public boolean deleteInvitation (String email, Long eventId) throws EntityNotFoundException {
        User user = userService.findUserByEmail(email);
        Event event = eventService.findEventById(eventId);

        return invitationRepository.deleteInvitation(user,event);
    }

    /**
     * Filter invitation list by status and sort it according to "changed" date from oldest to newest
     *
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