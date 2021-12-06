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

/**
 * Class representing Place entity. Contains name and address.
 */
@Data
@AllArgsConstructor
public class Place extends Entity {

    private final String name;
    private final String address;

}
