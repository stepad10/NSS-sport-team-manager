package cz.profinit.stm.model.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
public class User extends UserParent {
    private String surname;
    private String password;
    private String email;

    public User(String name, String surname, String password, String email) {
        super(name, RoleEnum.USER);
        this.surname = surname;
        this.password = password;
        this.email = email;
    }
}
