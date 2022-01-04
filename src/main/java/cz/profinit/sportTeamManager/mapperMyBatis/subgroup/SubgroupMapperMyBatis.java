package cz.profinit.sportTeamManager.mapperMyBatis.subgroup;

import cz.profinit.sportTeamManager.model.team.Subgroup;

import java.util.List;

public interface SubgroupMapperMyBatis {

    /**
     *
     * @param id to find subgroups by
     * @return list of all subgroups that belongs to team
     */
    List<Subgroup> findSubgroupsByTeamId(Long id);

    /**
     *
     * @param subgroup to insert
     * @return inserted subgroup or null
     */
    Subgroup insertSubgroup(Subgroup subgroup);

    /**
     *
     * @param subgroup to be updated
     * @return updated subgroup or null
     */
    Subgroup updateSubgroup(Subgroup subgroup);

    /**
     *
     * @param id to delete subgroup by
     * @return deleted subgroup or null
     */
    Subgroup deleteSubgroupById(Long id);

    /**
     *
     * @param id to find subgroup by
     * @return found subgroup or null
     */
    Subgroup findSubgroupById(Long id);
}
