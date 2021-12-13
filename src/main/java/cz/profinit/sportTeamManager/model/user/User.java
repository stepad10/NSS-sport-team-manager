package cz.profinit.sportTeamManager.model.user;

import cz.profinit.sportTeamManager.model.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends Entity {
    private String name;
}
