package cz.profinit.sportTeamManager.repositories.invitation;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.event.Event;
import cz.profinit.sportTeamManager.model.invitation.Invitation;
import cz.profinit.sportTeamManager.model.user.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("Main")
public class InvitationRepositoryImpl implements InvitationRepository {
    @Override
    public void insertInvitation(Invitation invitation) {

    }

    @Override
    public void updateInvitation(Invitation invitation) {

    }

    @Override
    public Invitation findInvitationById(Long id) throws EntityNotFoundException {
        return null;
    }

    @Override
    public Invitation findInvitationByEventIdAndUserEmail(Long eventId, String userEmail) throws EntityNotFoundException {
        return null;
    }

    @Override
    public boolean deleteInvitation(User user, Event event) throws EntityNotFoundException {
        return false;
    }

    @Override
    public boolean isUserInvitedToEvent(String userEmail, Long eventId) {
        return true;
    }
}
