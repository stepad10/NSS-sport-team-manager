package cz.profinit.sportTeamManager.stubRepositories;

import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.repositories.UserRepository;

public class StubUserRepository implements UserRepository {

    private RegisteredUser loggedUser1 =  new RegisteredUser("Ivan", "Stastny", "pass", "is@gmail.com", RoleEnum.USER);
    private RegisteredUser loggedUser2 = new RegisteredUser("Pavel", "Stastny", "pass", "is@seznam.cz", RoleEnum.USER);



    @Override
    public RegisteredUser saveRegistredUser(RegisteredUser registeredUser) {
        return loggedUser1;
    }

    @Override
    public RegisteredUser findUserByEmail(String userEmail) {
       if (userEmail == "is@seznam.cz"){
           return loggedUser2;
       } else {
           return loggedUser1;
       }
    }
}
