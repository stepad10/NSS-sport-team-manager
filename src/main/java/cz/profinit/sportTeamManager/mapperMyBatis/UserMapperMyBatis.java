package cz.profinit.sportTeamManager.mapperMyBatis;

import cz.profinit.sportTeamManager.model.user.RegisteredUser;

public interface UserMapperMyBatis {

    /**
     *
     * @param email param for finding user
     * @return user or null
     */
    RegisteredUser findUserByEmail(String email);

    /**
     *
     * @param id param for finding user
     * @return user or null
     */
    RegisteredUser findUserById(Long id);

    /**
     *
     * @param user user to insert
     * @return user or null
     */
    RegisteredUser insertUser(RegisteredUser user);

    /**
     *
     * @param id param to find right user to delete
     * @return user or null
     */
    RegisteredUser deleteUserById(Long id);

    /**
     *
     * @param email param to find right user to delete
     * @return user or null
     */
    RegisteredUser deleteUserByEmail(String email);

    /**
     *
     * @param user user to be updated
     * @return user or null
     */
    RegisteredUser updateUser(RegisteredUser user);
}
