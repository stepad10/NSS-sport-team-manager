/*
 * StubInvitationRepository
 *
 * 0.1
 *
 * Author: M. Halamka
 */


package cz.profinit.stm.repository.invitation;

import cz.profinit.stm.exception.EntityNotFoundException;
import cz.profinit.stm.model.invitation.Invitation;
import cz.profinit.stm.model.invitation.StatusEnum;
import cz.profinit.stm.model.user.RegisteredUser;
import cz.profinit.stm.model.user.RoleEnum;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@Profile("stub_repository")
public class InvitationRepositoryStub implements InvitationRepository {

    Invitation invitation;
    RegisteredUser loggedUser;

    public InvitationRepositoryStub() {
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
