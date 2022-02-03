package cz.profinit.sportTeamManager.mapperMyBatis.subgroupUser;

import cz.profinit.sportTeamManager.model.user.RegisteredUser;

import java.util.List;

public interface SubgroupUserMapperMyBatis {

    /**
     * create association between Subgroup and RegisteredUser
     * @param subgroupId id of a parent Subgroup
     * @param userId id of a parent RegisteredUser
     */
    void insertSubgroupUser(Long subgroupId, Long userId);

    /**
     * delete association between Subgroup and RegisteredUser
     * @param subgroupId id of a parent Subgroup
     * @param userId id of a parent RegisteredUser
     */
    void deleteSubgroupUser(Long subgroupId, Long userId);

    /**
     * deletes every association between subgroup and users
     * @param subgroupId id of a parent subgroup
     */
    void deleteAllSubgroupUsers(Long subgroupId);

    /**
     * find all RegisterUsers of a Subgroup by its id
     * @param subgroupId id of a parent Subgroup
     * @return list of all RegisteredUsers of a Subgroup
     */
    List<RegisteredUser> findUsersBySubgroupId(Long subgroupId);
}
