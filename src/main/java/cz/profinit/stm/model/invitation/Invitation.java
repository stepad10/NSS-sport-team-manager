/*
 * Invitation
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.stm.model.invitation;

import cz.profinit.stm.model.entity.Entity;
import cz.profinit.stm.model.user.UserParent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invitation extends Entity  {

    private LocalDateTime created;
    private LocalDateTime changed;
    private StatusEnum status;
    private UserParent recipient;
    private Long eventId;
}