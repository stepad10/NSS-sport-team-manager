package cz.profinit.sportTeamManager.repositories;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.event.Event;
import cz.profinit.sportTeamManager.model.invitation.Invitation;
import cz.profinit.sportTeamManager.model.user.User;

public interface InvitationRepository {

    Invitation createNewInvitation(Invitation invitation);
    Invitation updateInvitation(Invitation invitation);
    Invitation getInvitationById(Long id) throws EntityNotFoundException;
    boolean deleteInvitation(User user, Event event);
    boolean isUserPresent(User user, Event event);

}
