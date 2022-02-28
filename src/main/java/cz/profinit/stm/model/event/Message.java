/*
 * Message
 *
 * 0.1
 *
 * Author: M. Halamka
 */
package cz.profinit.stm.model.event;

import cz.profinit.stm.model.entity.Entity;
import cz.profinit.stm.model.user.RegisteredUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Class representing Message entity. Contains user, who sent message, message itself, and date when message was send.
 */
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message extends Entity {

    private RegisteredUser user;
    private String text;
    private LocalDateTime date;
    private Long eventId;

}
