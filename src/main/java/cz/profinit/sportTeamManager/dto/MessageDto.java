/*
 * MessageDto
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.sportTeamManager.dto;

import cz.profinit.sportTeamManager.adapters.LocalDateTimeAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;


/**
 * Data transfer object represents Message entity. Contains user, message and date.
 */
@Data
@AllArgsConstructor
public class MessageDto {

    @XmlElement(name = "user")
    private final RegisteredUserDTO user;
    @XmlElement(name = "message")
    private final String message;
    @XmlElement(name = "date")
    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    private final LocalDateTime date;

}
