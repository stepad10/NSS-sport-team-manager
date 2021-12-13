package cz.profinit.sportTeamManager.repositories;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    RegisteredUser saveRegistredUser(RegisteredUser registeredUser);

    boolean emailExistsInDatabase(String email);

    RegisteredUser findUserById(long id);

    RegisteredUser findUserByEmail(String email) throws EntityNotFoundException;
}
