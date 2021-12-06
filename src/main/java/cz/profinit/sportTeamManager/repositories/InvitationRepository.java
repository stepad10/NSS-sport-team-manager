package cz.profinit.sportTeamManager.repositories;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.invitation.Invitation;

public interface InvitationRepository {

    Invitation createNewInvitation(Invitation invitation);
    Invitation updateInvitation(Invitation invitation);
    Invitation getInvitationById(Long id) throws EntityNotFoundException;
}
