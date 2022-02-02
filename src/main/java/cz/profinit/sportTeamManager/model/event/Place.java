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
import lombok.NoArgsConstructor;

/**
 * Class representing Place entity. Contains name and address.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Place extends Entity {

    private String name;
    private String address;
    private Long teamId;

}
