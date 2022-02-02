package cz.profinit.sportTeamManager.mapperMyBatis.subgroup;

import cz.profinit.sportTeamManager.model.team.Subgroup;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
     * @param teamId id of a parent team
     * @param subgroupName name of a desired subgroup
     * @return found subgroup or null
     */
    Subgroup findSubgroupByNameAndTeamId(@Param("teamId") Long teamId, @Param("subgroupName") String subgroupName);

    /**
     *
     * @param teamId to find subgroups by
     * @return list of all subgroups that belongs to team
     */
    List<Subgroup> findSubgroupsByTeamId(Long teamId);
}
