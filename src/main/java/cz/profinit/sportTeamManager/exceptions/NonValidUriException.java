package cz.profinit.sportTeamManager.exceptions;

/**
 * NonValidUriException is thrown when given URI is not valid.
 */
public class NonValidUriException extends Exception {

    public NonValidUriException() {
        super("URI is not valid!");
    }
}
