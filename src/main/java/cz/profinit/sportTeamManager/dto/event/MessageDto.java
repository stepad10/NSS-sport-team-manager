/*
 * MessageDto
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.sportTeamManager.dto.event;

import cz.profinit.sportTeamManager.adapters.LocalDateTimeAdapter;
import cz.profinit.sportTeamManager.dto.user.RegisteredUserDTO;
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
    @XmlElement(name = "text")
    private final String text;
    @XmlElement(name = "date")
    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    private final LocalDateTime date;

}
