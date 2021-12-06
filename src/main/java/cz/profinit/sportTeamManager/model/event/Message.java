/*
 * Message
 *
 * 0.1
 *
 * Author: M. Halamka
 */
package cz.profinit.sportTeamManager.model.event;

import cz.profinit.sportTeamManager.model.entity.Entity;
import cz.profinit.sportTeamManager.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Class representing Message entity. Contains user, who sent message, message itself, and date when message was send.
 */
@Data
@AllArgsConstructor
public class Message extends Entity {

    private final User user;
    private final String message;
    private final LocalDateTime date;

}
