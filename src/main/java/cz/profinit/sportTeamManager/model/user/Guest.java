package cz.profinit.sportTeamManager.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Guest extends User {
    private final RoleEnum role = RoleEnum.GUEST;
    private String uri;

    public Guest(String name, String uri) {
        super(name);
        this.uri = uri;
    }
}
