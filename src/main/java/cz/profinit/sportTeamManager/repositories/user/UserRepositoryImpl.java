package cz.profinit.sportTeamManager.repositories.user;

import cz.profinit.sportTeamManager.exceptions.EmailExistsException;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.mapperMyBatis.user.UserMapperMyBatis;
import cz.profinit.sportTeamManager.model.user.Guest;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import lombok.AllArgsConstructor;

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
        findUserById(registeredUser.getEntityId());
        userMapperMyBatis.updateUser(registeredUser);
        return registeredUser;
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

    //TODO IMPLEMENT THESE
    @Override
    public Guest insertGuest(Guest guest) {
        return null;
    }

    @Override
    public Guest findGuestByUri(String uri) throws EntityNotFoundException {
        return null;
    }

    @Override
    public Guest updateGuest(Guest guest) {
        return null;
    }
}
