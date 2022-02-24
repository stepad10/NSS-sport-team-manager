package cz.profinit.sportTeamManager.model.user;

import cz.profinit.sportTeamManager.model.entity.Entity;
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
