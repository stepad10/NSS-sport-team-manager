package cz.profinit.sportTeamManager.dto;

import cz.profinit.sportTeamManager.model.user.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisteredUserDTO {
    private String name;
    private String surname;
    private String password;
    private String email;
    private RoleEnum role;
}
