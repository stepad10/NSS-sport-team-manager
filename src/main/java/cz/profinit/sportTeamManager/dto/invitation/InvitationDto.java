/*
 * InvitationDto
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.sportTeamManager.dto.invitation;

import cz.profinit.sportTeamManager.model.invitation.StatusEnum;
import cz.profinit.sportTeamManager.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class InvitationDto implements Comparable<InvitationDto> {

    private LocalDateTime created;
    private LocalDateTime changed;
    private StatusEnum status;
    private User isFor; //TODO Replace with UserDto
    private Long eventId;

    /**
     * Method that defines how to compare Invitation entity (by date changed)
     * @param invitationDto with which the dto will be compared
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified invitation
     */
    @Override
    public int compareTo(InvitationDto invitationDto) {
        return this.getChanged().compareTo(invitationDto.getChanged());
    }
}
