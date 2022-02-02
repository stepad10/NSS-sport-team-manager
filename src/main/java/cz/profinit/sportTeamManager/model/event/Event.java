/*
 * Event
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.sportTeamManager.model.event;

import cz.profinit.sportTeamManager.model.entity.Entity;
import cz.profinit.sportTeamManager.model.invitation.Invitation;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Class representing Event entity. Contains date, place, maxPersonAttendance, isCanceled, createdBy, listOfMessages and listOfInvitations
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event extends Entity {

    private LocalDateTime date;
    private Integer capacity;
    private Boolean isCanceled;
    private Place place;
    private RegisteredUser createdBy;
    private List<Message> messageList;
    private List <Invitation> invitationList;

    /**
     * Adds new message to the Event
     * @param message Message that needs to be saved.
     */
    public void addNewMessage (Message message){
        if(!messageList.contains(message)){
            messageList.add(message);
        }
    }

    /**
     * Adds new invitation to the Event
     * @param invitation Invitation that needs to be saved.
     */
    public void addNewInvitation (Invitation invitation){
        if(!invitationList.contains(invitation)){
            invitationList.add(invitation);
        }
    }
}
