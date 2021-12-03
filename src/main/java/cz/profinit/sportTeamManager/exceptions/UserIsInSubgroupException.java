package cz.profinit.sportTeamManager.exceptions;

public class UserIsInSubgroupException extends RuntimeException {

    public UserIsInSubgroupException(String message) {
        super(message);
    }
}
