package cz.profinit.sportTeamManager.model.invitation;

import cz.profinit.sportTeamManager.model.entity.Entity;
import cz.profinit.sportTeamManager.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Invitation extends Entity implements Comparable<Invitation> {

    private LocalDateTime created;
    private LocalDateTime changed;
    private StatusEnum status;
    private User isFor;

    /**
     * Method that defines how to compare Invitation entity (by date changed)
     * @param invitation with which the entity will be compared
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified invitation
     */
    @Override
    public int compareTo(Invitation invitation) {
        return this.getChanged().compareTo(invitation.getChanged());
    }

}