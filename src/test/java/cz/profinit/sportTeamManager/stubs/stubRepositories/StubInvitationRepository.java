/*
 * StubInvitationRepository
 *
 * 0.1
 *
 * Author: M. Halamka
 */


package cz.profinit.sportTeamManager.stubs.stubRepositories;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.event.Event;
import cz.profinit.sportTeamManager.model.invitation.Invitation;
import cz.profinit.sportTeamManager.model.invitation.StatusEnum;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.model.user.User;
import cz.profinit.sportTeamManager.repositories.InvitationRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class StubInvitationRepository implements InvitationRepository {

    Invitation invitation;
    User loggedUser;

    public StubInvitationRepository() {
        loggedUser = new RegisteredUser("Ivan", "Stastny", "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2", "is@gmail.com", RoleEnum.USER);
        invitation = new Invitation(LocalDateTime.now(),LocalDateTime.now(), StatusEnum.PENDING,loggedUser);
        invitation.setEntityId(0L);
    }

    @Override
    public Invitation createNewInvitation(Invitation invitation) {
        this.invitation = invitation;
        return invitation;
    }

    public Invitation getInvitationById(Long id) throws EntityNotFoundException {
        if (id == invitation.getEntityId()){
            return invitation;
        } else {
            throw new EntityNotFoundException("Invitation");
        }
    }

    @Override
    public boolean deleteInvitation(User user, Event event) throws EntityNotFoundException {
        if (user.getName() != loggedUser.getName() || event.getEntityId() != 0L){
            throw new EntityNotFoundException("Invitation");
        } else {
            return true;
        }
    }

    @Override
    public boolean isUserPresent(User user, Event event) {
        if (user.getName() == "Pavel") {
            return true;
        }
        return false;
    }

    @Override
    public Invitation updateInvitation(Invitation invitation) {
        return invitation;
    }
}
