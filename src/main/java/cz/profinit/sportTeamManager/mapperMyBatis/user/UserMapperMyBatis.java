package cz.profinit.sportTeamManager.mapperMyBatis.user;

import cz.profinit.sportTeamManager.model.user.RegisteredUser;

public interface UserMapperMyBatis {

    /**
     *
     * @param email to find RegisteredUser by
     * @return found RegisteredUser or null
     */
    RegisteredUser findUserByEmail(String email);

    /**
     *
     * @param id to find RegisteredUser by
     * @return found RegisteredUser or null
     */
    RegisteredUser findUserById(Long id);

    /**
     * insert RegisteredUser to database and update it's entityId
     * @param user to insert
     */
    void insertUser(RegisteredUser user);

    /**
     *
     * @param id param to find right RegisteredUser to delete
     */
    void deleteUserById(Long id);

    /**
     *
     * @param email param to find right RegisteredUser to delete
     */
    void deleteUserByEmail(String email);

    /**
     * update RegisteredUser in database
     * @param user user to be updated
     */
    void updateUser(RegisteredUser user);
}
