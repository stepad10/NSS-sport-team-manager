/*
 * InvitationMapper
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.sportTeamManager.mappers;

import cz.profinit.sportTeamManager.dto.MessageDto;
import cz.profinit.sportTeamManager.model.event.Message;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"test", "Main","stub_services"})
public class MessageMapper {
    //TODO Předělat pro userDto

    /**
     * Map Message class to MessageDto.
     * @param message Message that needs to be mapped.
     * @return MessageDto representing given Message
     */
    public static MessageDto toDto(Message message) {
        return new MessageDto(message.getUser(),message.getMessage(),message.getDate());
    }

    /**
     * Map MessageDto to Message
     * @param messageDto MessageDto that needs to be mapped.
     * @return Message representing given MessageDto
     */
    public static Message toMessage(MessageDto messageDto) {
        return new Message(messageDto.getUser(),messageDto.getMessage(),messageDto.getDate());
    }

}
