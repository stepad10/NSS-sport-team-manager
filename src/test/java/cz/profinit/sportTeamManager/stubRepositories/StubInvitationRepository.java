/*
 * StubInvitationRepository
 *
 * 0.1
 *
 * Author: M. Halamka
 */


package cz.profinit.sportTeamManager.stubRepositories;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.event.Event;
import cz.profinit.sportTeamManager.model.invitation.Invitation;
import cz.profinit.sportTeamManager.model.invitation.StatusEnum;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.model.user.User;
import cz.profinit.sportTeamManager.repositories.InvitationRepository;

import java.time.LocalDateTime;

public class StubInvitationRepository implements InvitationRepository {

    Invitation invitation;
    User loggedUser;

    public StubInvitationRepository() {
        loggedUser = new RegisteredUser("Ivan", "Stastny", "pass", "is@gmail.com", RoleEnum.USER);
        invitation = new Invitation(LocalDateTime.now(),LocalDateTime.now(), StatusEnum.PENDING,loggedUser);
        invitation.setEntityId(0L);
    }

    @Override
    public Invitation createNewInvitation(Invitation invitation) {
        return invitation;
    }

    public Invitation getInvitationById(Long id) throws EntityNotFoundException {
        if (id == invitation.getEntityId()){
            return invitation;
        } else {
            throw new EntityNotFoundException("Invitation entity not found!");
        }
    }

    @Override
    public boolean deleteInvitation(User user, Event event) {
        if (user.getName() != loggedUser.getName() || event.getEntityId() != 0L){
            return false;
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
