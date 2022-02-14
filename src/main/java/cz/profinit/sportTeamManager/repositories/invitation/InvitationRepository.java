/*
 * InvitationRepository
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.sportTeamManager.repositories.invitation;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.event.Event;
import cz.profinit.sportTeamManager.model.invitation.Invitation;
import cz.profinit.sportTeamManager.model.user.User;

public interface InvitationRepository {

    void insertInvitation(Invitation invitation);
    void updateInvitation(Invitation invitation);
    Invitation getInvitationById(Long id) throws EntityNotFoundException;
    Invitation getInvitationByUserEmailAndEventId(Long eventId, String email) throws EntityNotFoundException;
    boolean deleteInvitation(User user, Event event) throws EntityNotFoundException;
    boolean isUserPresent(User user, Event event);

}
