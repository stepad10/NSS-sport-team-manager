/*
 * InvitationService
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.stm.service.invitation;

import cz.profinit.stm.dto.invitation.InvitationDto;
import cz.profinit.stm.exception.EntityAlreadyExistsException;
import cz.profinit.stm.exception.EntityNotFoundException;
import cz.profinit.stm.exception.NonValidUriException;
import cz.profinit.stm.exception.UserIsAlreadyInEventException;
import cz.profinit.stm.model.invitation.Invitation;
import cz.profinit.stm.model.invitation.StatusEnum;
import cz.profinit.stm.model.user.RegisteredUser;

import java.util.List;

public interface InvitationService {

    Invitation createNewInvitation(String email, Long eventId) throws EntityNotFoundException, EntityAlreadyExistsException;
    Invitation changeInvitationStatus(Long eventId, String email, StatusEnum status) throws EntityNotFoundException;
    Invitation findInvitationByEventIdAndEmail(Long eventId,String email) throws EntityNotFoundException;
    List<Invitation> createNewInvitationsFromList (List<RegisteredUser> userList, Long eventId)
            throws EntityNotFoundException, UserIsAlreadyInEventException, EntityAlreadyExistsException;
    List<InvitationDto> OrderListOfInvitationByDateForSpecificStatus (List<InvitationDto> invitationDtoList, StatusEnum status);
    void deleteInvitation (String email, Long eventId) throws EntityNotFoundException;
    Invitation getGuestInvitation (String uri) throws EntityNotFoundException, NonValidUriException;
    Invitation createGuestInvitation (Long eventId, String name) throws EntityNotFoundException, EntityAlreadyExistsException;
    Invitation changeGuestInvitation (String uri, StatusEnum status) throws NonValidUriException, EntityNotFoundException;
}
