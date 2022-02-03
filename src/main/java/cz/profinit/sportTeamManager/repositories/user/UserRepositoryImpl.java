package cz.profinit.sportTeamManager.repositories.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import cz.profinit.sportTeamManager.exceptions.EntityAlreadyExistsException;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.mapperMyBatis.user.UserMapperMyBatis;
import cz.profinit.sportTeamManager.model.user.Guest;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@Repository
@Profile("Main")
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private UserMapperMyBatis userMapperMyBatis;

    @Override
    public void insertRegisteredUser(@NonNull RegisteredUser registeredUser) throws EntityAlreadyExistsException {
        if (userMapperMyBatis.findUserById(registeredUser.getEntityId()) != null) {
            throw new EntityAlreadyExistsException("User");
        }
        userMapperMyBatis.insertUser(registeredUser);
    }

    @Override
    public void updateRegisteredUser(@NonNull RegisteredUser registeredUser) throws EntityNotFoundException {
        if (userMapperMyBatis.findUserById(registeredUser.getEntityId()) == null) {
            throw new EntityNotFoundException("User");
        }
        userMapperMyBatis.updateUser(registeredUser);
    }

    @Override
    public RegisteredUser findUserByEmail(@NonNull String email) throws EntityNotFoundException {
        RegisteredUser foundUser = userMapperMyBatis.findUserByEmail(email);
        if (foundUser == null) {
            throw new EntityNotFoundException("User");
        }
        return foundUser;
    }

    @Override
    public RegisteredUser findUserById(@NonNull Long id) throws EntityNotFoundException {
        RegisteredUser foundUser = userMapperMyBatis.findUserById(id);
        if (foundUser == null) {
            throw new EntityNotFoundException("User");
        }
        return foundUser;
    }

    @Override
    public void deleteRegisteredUser(@NonNull Long userId) throws EntityNotFoundException {
        if (userMapperMyBatis.findUserById(userId) == null) {
            throw new EntityNotFoundException("User");
        }
        userMapperMyBatis.deleteUserById(userId);
    }

    //TODO IMPLEMENT THESE
    @Override
    public Guest insertGuest(@NonNull Guest guest) {
        return null;
    }

    @Override
    public Guest findGuestByUri(@NonNull String uri) throws EntityNotFoundException {
        return null;
    }

    @Override
    public Guest updateGuest(@NonNull Guest guest) {
        return null;
    }
}
