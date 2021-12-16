/*
 * InvitationMapper
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.sportTeamManager.mappers;

import cz.profinit.sportTeamManager.dto.InvitationDto;
import cz.profinit.sportTeamManager.model.invitation.Invitation;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


/**
 * Mapper class that allows mapping between data transfer objects and entities
 */
@Component
@Profile({"test", "Main","stub_services"})
public class InvitationMapper {
    //TODO Předělat pro userDto

    /**
     * Map Invitation class to InvitationDto.
     * @param invitation Invitation that needs to be mapped.
     * @return InvitationDto representing given Event
     */
    public static InvitationDto toDto(Invitation invitation) {
        return new InvitationDto(invitation.getCreated(),invitation.getChanged(),invitation.getStatus(),invitation.getIsFor());
    }

    /**
     * Map InvitationDto to Invitation
     * @param invitationDto InvitationDto that needs to be mapped.
     * @return Invitation representing given InvitationDto
     */
    public static Invitation toInvitation(InvitationDto invitationDto) {
        return new Invitation(invitationDto.getCreated(),invitationDto.getChanged(),invitationDto.getStatus(),invitationDto.getIsFor());
    }
}
