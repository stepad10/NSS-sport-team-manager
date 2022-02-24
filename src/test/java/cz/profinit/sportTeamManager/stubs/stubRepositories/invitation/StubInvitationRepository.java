/*
 * StubInvitationRepository
 *
 * 0.1
 *
 * Author: M. Halamka
 */


package cz.profinit.sportTeamManager.stubs.stubRepositories.invitation;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.event.Event;
import cz.profinit.sportTeamManager.model.invitation.Invitation;
import cz.profinit.sportTeamManager.model.invitation.StatusEnum;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.model.user.User;
import cz.profinit.sportTeamManager.repositories.invitation.InvitationRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class StubInvitationRepository implements InvitationRepository {

    Invitation invitation;
    RegisteredUser loggedUser;

    public StubInvitationRepository() {
        loggedUser = new RegisteredUser("Ivan", "Stastny", "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2", "is@gmail.com", RoleEnum.USER);
        invitation = new Invitation(LocalDateTime.now(), LocalDateTime.now(), StatusEnum.PENDING, loggedUser, 0L);
        invitation.setEntityId(0L);
    }

    @Override
    public void insertInvitation(Invitation invitation) {
        this.invitation = invitation;
    }

    public Invitation findInvitationById(Long id) throws EntityNotFoundException {
        if (id.equals(invitation.getEntityId())){
            return invitation;
        } else {
            throw new EntityNotFoundException("Invitation");
        }
    }

    @Override
    public Invitation findInvitationByEventIdAndUserEmail(Long eventId, String userEmail) throws EntityNotFoundException {
        if (eventId == 0L && loggedUser.getEmail().equals(userEmail))
            return invitation;
        throw new EntityNotFoundException("Invitation");
    }

    @Override
    public void deleteInvitation(String userEmail, Long eventId) throws EntityNotFoundException {
        if (eventId != 0L && !loggedUser.getEmail().equals(userEmail)) {
            throw new EntityNotFoundException("Invitation");
        }
        if (eventId != 0L) {
            throw new EntityNotFoundException("Event");
        }
        if (!loggedUser.getEmail().equals(userEmail)) {
            throw new EntityNotFoundException("RegisteredUser");
        }
    }

    @Override
    public boolean isUserInvitedToEvent(String userEmail, Long eventId) throws EntityNotFoundException {
        if (eventId == 1L) throw new EntityNotFoundException("Event");
        return userEmail.equals("ts@gmail.com");
    }

    @Override
    public void updateInvitation(Invitation invitation) {
        this.invitation = invitation;
    }
}
