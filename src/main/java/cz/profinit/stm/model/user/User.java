package cz.profinit.stm.model.user;

import cz.profinit.stm.model.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends Entity {
    private String name;
    private RoleEnum role;
}
