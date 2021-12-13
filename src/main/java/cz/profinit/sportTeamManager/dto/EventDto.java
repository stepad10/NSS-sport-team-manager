/*
 * EventDto
 *
 * 0.1
 *
 * Author: M. Halamka
 */
package cz.profinit.sportTeamManager.dto;

import cz.profinit.sportTeamManager.model.event.Place;
import cz.profinit.sportTeamManager.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Data transfer object for Event entity. Contains date, place, maxPersonAttendance, CreatedBy and isCanceled.
 */
@Data
@AllArgsConstructor
public class EventDto {

    private LocalDateTime date;
    private Place place;
    private Integer maxPersonAttendance;
    final private User createdBy;
    final Boolean isCanceled;

}
