/*
 * Invitation
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.sportTeamManager.model.invitation;

import cz.profinit.sportTeamManager.model.entity.Entity;
import cz.profinit.sportTeamManager.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Invitation extends Entity  {

    private LocalDateTime created;
    private LocalDateTime changed;
    private StatusEnum status;
    private User recipient;

}