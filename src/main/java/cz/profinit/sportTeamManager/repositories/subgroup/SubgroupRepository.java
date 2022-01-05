package cz.profinit.sportTeamManager.repositories.subgroup;

import cz.profinit.sportTeamManager.model.team.Subgroup;

import java.util.List;

//TODO implement
public interface SubgroupRepository {
    Subgroup insertSubgroup(Subgroup subgroup);
    Subgroup updateSubgroup(Subgroup subgroup);
    Subgroup deleteSubgroup(Subgroup subgroup);
    List<Subgroup> findSubgroupsByTeamID(Long teamId);
}
