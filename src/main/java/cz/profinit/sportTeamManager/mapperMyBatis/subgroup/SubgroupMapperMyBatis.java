package cz.profinit.sportTeamManager.mapperMyBatis.subgroup;

import cz.profinit.sportTeamManager.model.team.Subgroup;

import java.util.List;

public interface SubgroupMapperMyBatis {

    /**
     * insert Subgroup to database and update it's entityId
     * @param subgroup to insert
     */
    void insertSubgroup(Subgroup subgroup);

    /**
     *
     * @param subgroup to be updated
     */
    void updateSubgroup(Subgroup subgroup);

    /**
     *
     * @param id to delete subgroup by
     */
    void deleteSubgroupById(Long id);

    /**
     *
     * @param id to find subgroup by
     * @return found subgroup or null
     */
    Subgroup findSubgroupById(Long id);

    /**
     *
     * @param teamId to find subgroups by
     * @return list of all subgroups that belongs to team
     */
    List<Subgroup> findSubgroupsByTeamId(Long teamId);

    /**
     * association between subgroup and user
     * @param subgroupId id of a parent subgroup
     * @param userId id of a parent user
     */
    void insertSubgroupUser(Long subgroupId, Long userId);

    /**
     * delete association between subgroup and user
     * @param subgroupId id of a parent subgroup
     * @param userId id of a parent user
     */
    void deleteSubgroupUser(Long subgroupId, Long userId);
}
