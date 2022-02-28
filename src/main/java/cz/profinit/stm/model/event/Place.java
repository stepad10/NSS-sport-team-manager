/*
 * Place
 *
 * 0.1
 *
 * Author: M. Halamka
 */
package cz.profinit.stm.model.event;

import cz.profinit.stm.model.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Class representing Place entity. Contains name and address.
 */
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Place extends Entity {

    private String name;
    private String address;
    private Long teamId;

}
