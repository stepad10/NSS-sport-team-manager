package cz.profinit.sportTeamManager.model.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisteredUser extends User {
    private String surname;
    private String password;
    private String email;
    private RoleEnum role;

    public RegisteredUser(String name, String surname, String password, String email, RoleEnum role) {
        super(name);
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
