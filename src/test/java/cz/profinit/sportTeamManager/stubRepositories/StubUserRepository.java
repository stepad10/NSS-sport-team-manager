package cz.profinit.sportTeamManager.stubRepositories;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.repositories.UserRepository;

import java.util.logging.Logger;

public class StubUserRepository implements UserRepository {
    private Logger logger = Logger.getLogger(String.valueOf(getClass()));

    @Override
    public RegisteredUser saveRegistredUser(RegisteredUser registeredUser) {
        logger.info("User saved to database");
        return registeredUser;
    }

    @Override
    public boolean emailExistsInDatabase(String email) {
        return email.equals("ab@gmail.com");
    }

    @Override
    public RegisteredUser findUserById(long id) {
        return new RegisteredUser(
                "Ivan",
                "Stastny",
                "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2",
                "is@gmail.com",
                RoleEnum.USER);
    }

    @Override
    public RegisteredUser findUserByEmail(String email) throws EntityNotFoundException {
        if (email.equals("is@gmail.com")) {
            return new RegisteredUser(
                    "Ivan",
                    "Stastny",
                    "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2",
                    "is@gmail.com",
                    RoleEnum.USER);
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }

    @Override
    public RegisteredUser findRegistredUserByFullName(String name, String surname) {
        logger.info("Getting user from database");
        return new RegisteredUser(name, surname, "pass1", "aaa@gmail.com", RoleEnum.USER);
    }
}
