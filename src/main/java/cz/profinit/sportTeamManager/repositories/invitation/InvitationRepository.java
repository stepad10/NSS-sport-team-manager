/*
 * InvitationRepository
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.sportTeamManager.repositories.invitation;

import cz.profinit.sportTeamManager.exceptions.EntityAlreadyExistsException;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.event.Event;
import cz.profinit.sportTeamManager.model.invitation.Invitation;
import cz.profinit.sportTeamManager.model.user.User;

public interface InvitationRepository {

    void insertInvitation(Invitation invitation) throws EntityNotFoundException, EntityAlreadyExistsException;
    void updateInvitation(Invitation invitation) throws EntityNotFoundException;
    Invitation findInvitationById(Long id) throws EntityNotFoundException;
    Invitation findInvitationByEventIdAndUserEmail(Long eventId, String userEmail) throws EntityNotFoundException;
    void deleteInvitation(String userEmail, Long eventId) throws EntityNotFoundException;
    boolean isUserInvitedToEvent(String userEmail, Long eventId) throws EntityNotFoundException;

}
