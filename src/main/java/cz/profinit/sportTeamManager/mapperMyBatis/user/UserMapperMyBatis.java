package cz.profinit.sportTeamManager.mapperMyBatis.user;

import cz.profinit.sportTeamManager.model.user.RegisteredUser;

public interface UserMapperMyBatis {

    /**
     *
     * @param email to find user by
     * @return found user or null
     */
    RegisteredUser findUserByEmail(String email);

    /**
     *
     * @param id to find user by
     * @return found user or null
     */
    RegisteredUser findUserById(Long id);

    /**
     *
     * @param user to insert
     * @return inserted user or null
     */
    RegisteredUser insertUser(RegisteredUser user);

    /**
     *
     * @param id param to find right user to delete
     * @return deleted user or null
     */
    RegisteredUser deleteUserById(Long id);

    /**
     *
     * @param email param to find right user to delete
     * @return deleted user or null
     */
    RegisteredUser deleteUserByEmail(String email);

    /**
     *
     * @param user user to be updated
     * @return updated user or null
     */
    RegisteredUser updateUser(RegisteredUser user);
}
