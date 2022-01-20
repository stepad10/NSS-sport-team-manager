package cz.profinit.sportTeamManager.repositories.user;

import cz.profinit.sportTeamManager.exceptions.EmailExistsException;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.mapperMyBatis.user.UserMapperMyBatis;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("Main")
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private UserMapperMyBatis userMapperMyBatis;

    @Override
    public void insertRegisteredUser(RegisteredUser registeredUser) {
        if (emailExistsInDatabase(registeredUser.getEmail())) {
            throw new EmailExistsException("Unable to insert RegisteredUser, email already exists!");
        }
        userMapperMyBatis.insertUser(registeredUser);
    }

    @Override
    public RegisteredUser updateRegisteredUser(RegisteredUser registeredUser) throws EntityNotFoundException {
        findRegisteredUser(registeredUser);
        userMapperMyBatis.updateUser(registeredUser);
        return registeredUser;
    }

    @Override
    public RegisteredUser findRegisteredUser(RegisteredUser registeredUser) throws EntityNotFoundException {
        if (registeredUser.getEntityId() != null) {
            return findUserById(registeredUser.getEntityId());
        }
        return findUserByEmail(registeredUser.getEmail());
    }

    @Override
    public boolean emailExistsInDatabase(String email) {
        return userMapperMyBatis.findUserByEmail(email) != null;
    }

    @Override
    public RegisteredUser findUserByEmail(String email) throws EntityNotFoundException {
        RegisteredUser foundUser = userMapperMyBatis.findUserByEmail(email);
        if (foundUser == null) {
            throw new EntityNotFoundException("User");
        }
        return foundUser;
    }

    @Override
    public RegisteredUser findUserById(Long id) throws EntityNotFoundException {
        RegisteredUser foundUser = userMapperMyBatis.findUserById(id);
        if (foundUser == null) {
            throw new EntityNotFoundException("User");
        }
        return foundUser;
    }

    @Override
    public void deleteRegisteredUser(Long userId) throws EntityNotFoundException {
        if (userMapperMyBatis.findUserById(userId) == null) {
            throw new EntityNotFoundException("User");
        }
        userMapperMyBatis.deleteUserById(userId);
    }
}
