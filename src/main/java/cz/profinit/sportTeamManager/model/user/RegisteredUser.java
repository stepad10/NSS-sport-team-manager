package cz.profinit.sportTeamManager.model.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
public class RegisteredUser extends User {
    private String surname;
    private String password;
    private String email;

    public RegisteredUser(String name, String surname, String password, String email, RoleEnum role) {
        super(name, role);
        this.surname = surname;
        this.password = password;
        this.email = email;
    }
}
