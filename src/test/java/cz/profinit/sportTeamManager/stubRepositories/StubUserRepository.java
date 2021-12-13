package cz.profinit.sportTeamManager.stubRepositories;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.repositories.UserRepository;

public class StubUserRepository implements UserRepository {

    private RegisteredUser loggedUser1 =  new RegisteredUser("Ivan", "Stastny", "pass", "is@gmail.com", RoleEnum.USER);
    private RegisteredUser loggedUser2 = new RegisteredUser("Pavel", "Smutny", "pass", "is@seznam.cz", RoleEnum.USER);
    private RegisteredUser loggedUser3 = new RegisteredUser("Jirka", "Vesely", "pass", "is@email.cz", RoleEnum.USER);



    @Override
    public RegisteredUser saveRegistredUser(RegisteredUser registeredUser) {
        return loggedUser1;
    }

    @Override
    public RegisteredUser findUserByEmail(String userEmail) throws EntityNotFoundException {
       if (userEmail == "is@seznam.cz"){
           return loggedUser2;
       } else if (userEmail == "is@gmail.com"){
           return loggedUser1;
       } else if (userEmail == "is@email.cz") {
           return loggedUser3;
       } else {
           throw new EntityNotFoundException("User entity not found!");
       }
    }
}
