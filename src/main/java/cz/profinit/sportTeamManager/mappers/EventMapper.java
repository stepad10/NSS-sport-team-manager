/*
 * EventMapper
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.sportTeamManager.mappers;

import cz.profinit.sportTeamManager.dto.EventDto;
import cz.profinit.sportTeamManager.model.event.Event;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Mapper class that allows mapping between data transfer objects and entities
 */
@Component
public class EventMapper {

    /**
     * Map Event class to EventDto.
     * @param event Event that needs to be mapped.
     * @return EventDto representing given Event
     */
    public EventDto toDto(Event event) {
        return new EventDto(event.getDate(), event.getPlace(), event.getMaxPersonAttendance(), event.getCreatedBy(),event.getIsCanceled());
    }

    /**
     * Map EventDto to Event
     * @param eventDto EventDto that needs to be mapped.
     * @return Event representing given EventDto
     */
    public Event toEvent(EventDto eventDto) {
        return new Event(eventDto.getDate(),eventDto.getPlace(),eventDto.getMaxPersonAttendance(),eventDto.getIsCanceled(),eventDto.getCreatedBy(), new ArrayList<>(),new ArrayList<>());
    }
}
