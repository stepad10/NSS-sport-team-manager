package cz.profinit.sportTeamManager.exceptions;

public class UserOrPasswordNotMatchException extends RuntimeException {
    public UserOrPasswordNotMatchException() {
        super("User and password do not match");
    }
}
