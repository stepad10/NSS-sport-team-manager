package cz.profinit.sportTeamManager.model.user;

public class Guest extends User {
    private final RoleEnum role=RoleEnum.GUEST;
    private byte[] salt;

    public Guest(String name, byte[] salt) {
        super(name);
        this.salt = salt;
    }
}
