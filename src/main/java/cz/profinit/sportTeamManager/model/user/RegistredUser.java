package cz.profinit.sportTeamManager.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistredUser extends User {
    private String name;
    private String surname;
    private String password;
    private String email;
    private RoleEnum role;
}
