package cz.profinit.sportTeamManager.mapperMyBatis.invitation;

import cz.profinit.sportTeamManager.model.invitation.Invitation;

public interface InvitationMapperMyBatis {

    /**
     * insert Invitation to database and update it's entityId
     * @param invitation to insert
     */
    void insertInvitation(Invitation invitation);

    /**
     *
     * @param invitation to update
     */
    void updateInvitation(Invitation invitation);

    /**
     *
     * @param id to find Invitation by
     */
    void deleteInvitationById(Long id);

    /**
     *
     * @param id to find Invitation by
     * @return found Invitation or null
     */
    Invitation findInvitationById(Long id);

}
