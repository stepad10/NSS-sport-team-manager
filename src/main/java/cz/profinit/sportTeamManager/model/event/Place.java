/*
 * Place
 *
 * 0.1
 *
 * Author: M. Halamka
 */
package cz.profinit.sportTeamManager.model.event;

import cz.profinit.sportTeamManager.model.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Class representing Place entity. Contains name and address.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Place extends Entity {

    private String name;
    private String address;
    private Long teamId;

}
