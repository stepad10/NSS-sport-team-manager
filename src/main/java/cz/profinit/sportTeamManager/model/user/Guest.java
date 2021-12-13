package cz.profinit.sportTeamManager.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Guest extends User {
    private final RoleEnum role = RoleEnum.GUEST;
    private final byte[] salt;

    public Guest(String name, byte[] salt) {
        super(name);
        this.salt = salt;
    }
}
