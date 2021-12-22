package cz.profinit.sportTeamManager.repositories;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("Main")
public class UserRepositoryImpl implements UserRepository {
    @Override
    public RegisteredUser createRegistredUser(RegisteredUser registeredUser) {
        return null;
    }

    @Override
    public RegisteredUser updateRegistredUser(RegisteredUser registeredUser) {
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
        if (email.equals("sportteammanagertest@gmail.com")) {
            return new RegisteredUser(
                    "Ivan",
                    "Stastny",
                    "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2",
                    "sportteammanagertest@gmail.com",
                    RoleEnum.USER);
        } else {
            throw new EntityNotFoundException("User");
        }
    }

}
