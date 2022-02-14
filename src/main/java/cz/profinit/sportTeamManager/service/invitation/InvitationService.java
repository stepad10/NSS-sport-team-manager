/*
 * InvitationService
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.sportTeamManager.service.invitation;

import cz.profinit.sportTeamManager.dto.invitation.InvitationDto;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.exceptions.NonValidUriException;
import cz.profinit.sportTeamManager.exceptions.UserIsAlreadyInEventException;
import cz.profinit.sportTeamManager.model.invitation.Invitation;
import cz.profinit.sportTeamManager.model.invitation.StatusEnum;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;

import java.util.List;

public interface InvitationService {

    Invitation createNewInvitation(String email, Long eventId) throws EntityNotFoundException, UserIsAlreadyInEventException;
    Invitation changeInvitationStatus(Long eventId, String email, StatusEnum status) throws EntityNotFoundException;
    Invitation findInvitationByEventIdAndEmail(Long eventId,String email) throws EntityNotFoundException;
    List<Invitation> createNewInvitationsFromList (List<RegisteredUser> userList, Long eventId) throws EntityNotFoundException, UserIsAlreadyInEventException;
    List<InvitationDto> OrderListOfInvitationByDateForSpecificStatus (List<InvitationDto> invitationDtoList, StatusEnum status);
    void deleteInvitation (String email, Long eventId) throws EntityNotFoundException;
    Invitation getGuestInvitation (String uri) throws EntityNotFoundException, NonValidUriException;
    Invitation createGuestInvitation (Long eventId, String name) throws EntityNotFoundException;
    Invitation changeGuestInvitation (String uri, StatusEnum status) throws NonValidUriException, EntityNotFoundException;
}
