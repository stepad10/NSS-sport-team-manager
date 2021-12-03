package cz.profinit.sportTeamManager.exceptions;

public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}