package cz.profinit.sportTeamManager.service;

import cz.profinit.sportTeamManager.dto.InvitationDto;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.exceptions.UserIsAlreadyInEventException;
import cz.profinit.sportTeamManager.model.event.Event;
import cz.profinit.sportTeamManager.model.invitation.Invitation;
import cz.profinit.sportTeamManager.model.invitation.StatusEnum;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.User;

import java.util.List;

public interface InvitationService {

    Invitation createNewInvitation(String email, Long eventId) throws EntityNotFoundException, UserIsAlreadyInEventException;
    Invitation changeInvitationStatus(Invitation invitation, StatusEnum status);
    List<Invitation> createNewInvitationsFromList (List<RegisteredUser> userList, Long eventId) throws EntityNotFoundException, UserIsAlreadyInEventException;
    List<InvitationDto> OrderListOfInvitationByDateForSpecificStatus (List<InvitationDto> invitationDtoList, StatusEnum status);
    boolean deleteInvitation (String email, Long eventId) throws EntityNotFoundException;

}
