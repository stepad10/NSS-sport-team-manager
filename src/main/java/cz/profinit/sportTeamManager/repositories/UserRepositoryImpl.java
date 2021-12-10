package cz.profinit.sportTeamManager.repositories;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("Main")
public class UserRepositoryImpl implements UserRepository {
    @Override
    public RegisteredUser saveRegistredUser(RegisteredUser registeredUser) {
        return null;
    }

    @Override
    public boolean emailExistsInDatabase(String email) {
        return false;
    }

    @Override
    public RegisteredUser findUserById(long id) {
        return null;
    }

    @Override
    public RegisteredUser findUserByEmail(String email) throws EntityNotFoundException {
        return null;
    }

}
