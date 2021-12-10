/*
 * MessageDto
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.sportTeamManager.dto;

import cz.profinit.sportTeamManager.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * Data transfer object represents Message entity. Contains user, message and date.
 */
@Data
@AllArgsConstructor
public class MessageDto {

    //TODO Remove User and add UserDto
    private final User user;
    private final String message;
    private final LocalDateTime date;

}
